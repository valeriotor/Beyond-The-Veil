package com.valeriotor.beyondtheveil.gui.research;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.gui.GuiHelper;
import com.valeriotor.beyondtheveil.gui.ScrollableTextArea;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageDOSkillsGui;
import com.valeriotor.beyondtheveil.worship.DOSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class GuiDOSkills extends GuiScreen {

    private static final ResourceLocation TREE = new ResourceLocation(References.MODID, "textures/gui/do_skill_tree.png");
    private static final ResourceLocation NODE = new ResourceLocation(References.MODID, "textures/gui/do_skill_node.png");
    private int treeTextureX;
    private static final int nodeSize = 189*3/5;
    private static final EnumMap<DOSkill, Point> coordinates;

    static {
        coordinates = new EnumMap<>(DOSkill.class);
        coordinates.put(DOSkill.QUICKSTEP, new Point(597, 965));
        coordinates.put(DOSkill.UPPERCUT, new Point(597, 777));
        coordinates.put(DOSkill.CLIMBING, new Point(292, 777));
        coordinates.put(DOSkill.ROARSINK, new Point(904, 777));
        coordinates.put(DOSkill.HEALTH, new Point(597, 585));
        coordinates.put(DOSkill.POISON, new Point(292, 408));
        coordinates.put(DOSkill.REGENERATION, new Point(904, 408));
    }

    private final EnumSet<DOSkill> unlockedSkills = EnumSet.noneOf(DOSkill.class);
    private final EnumSet<DOSkill> unlockableSkills = EnumSet.noneOf(DOSkill.class);
    private DOSkill selectedSkill;
    private final List<ArenaBossEntry> bossEntries;
    private final ScrollableTextArea descriptionTextArea = new ScrollableTextArea();

    public GuiDOSkills() {
        updateUnlockedSkills();
        IPlayerData data = PlayerDataLib.getCap(Minecraft.getMinecraft().player);
        bossEntries = ImmutableList.copyOf(data.getInts(false).entrySet().stream()
                .filter(s -> s.getKey().startsWith("arena-killed-"))
                .map(ArenaBossEntry::new)
                .collect(Collectors.toList()));
    }

    @Override
    public void initGui() {
        treeTextureX = width * 2 / 5;
        int x = treeTextureX/2 - 50;
        GuiButton unlockButton = new GuiButton(0, x, height * 9 / 10, 100, 20, "UNLOCK");
        buttonList.add(unlockButton);
        updateButton();
        descriptionTextArea.setScreenDimensions(height, width);
        descriptionTextArea.setHeightWidthRatio(0.6f, 19f/50);
        descriptionTextArea.setTextLineHeight(20);
        descriptionTextArea.setParagraphBreakHeight(10);
        descriptionTextArea.recompute();
        switch (mc.gameSettings.guiScale) {
            case 0:
                descriptionTextArea.setScrollAmount(9);
                break;
            case 2:
                descriptionTextArea.setScrollAmount(4);
                break;
            case 3:
                descriptionTextArea.setScrollAmount(7);
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, 0xFF000000);
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.color(1, 1, 1);
        GlStateManager.pushMatrix();
        float heightRatio = ((float)height)/1080;
        GlStateManager.translate(treeTextureX, 0, 0);
        GlStateManager.scale(heightRatio, heightRatio, 1);
        mc.renderEngine.bindTexture(TREE);
        drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 1200, 1080, 1200, 1080);
        mc.renderEngine.bindTexture(NODE);
        DOSkill hoveredSkill = hoveredSkill(mouseX, mouseY);
        for (Entry<DOSkill, Point> entry : coordinates.entrySet()) {
            GlStateManager.pushMatrix();
            Point p = entry.getValue();
            GlStateManager.translate(p.x, p.y, 0);
            if(!unlockedSkills.contains(entry.getKey())) {
                if(unlockableSkills.contains(entry.getKey()))
                    GlStateManager.color(1, 1, 0.3F);
                else
                    GlStateManager.color(0.3F, 0.3F, 0.3F);
            }
            if(entry.getKey() == hoveredSkill)
                GlStateManager.scale(1.2F, 1.2F, 1);
            drawModalRectWithCustomSizedTexture(-nodeSize/2, -nodeSize/2, 0, 0, nodeSize, nodeSize, nodeSize, nodeSize);
            GlStateManager.color(1, 1, 1);
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
        GlStateManager.color(1, 1, 1, 1);
        drawRect(treeTextureX, 0, treeTextureX+5, height, 0xFFAAAAAA);
        GlStateManager.color(1, 1, 1);
        int textY = height / 4;
        if(selectedSkill != null) {
            int titleY = height / 6;
            GlStateManager.pushMatrix();
            GlStateManager.translate(width / 100, titleY, 0);
            GlStateManager.scale(1.5, 1.5, 1);
            drawString(mc.fontRenderer, selectedSkill.getLocalizedName(), 0, 0, 0xFFFFFFFF);
            GlStateManager.popMatrix();
        }
        descriptionTextArea.drawScreen(this, 0xFF000000, 0xFFFFFFFF, width / 100, textY);
        textY = height/10;
        for (ArenaBossEntry bossEntry : bossEntries) {
            String s = I18n.format("doskill.bosskill", bossEntry.localizedBossName, bossEntry.amountKilled);
            drawString(mc.fontRenderer, s,width*7/10, textY, 0xFFFFFF);
            textY += 10;
        }

        if(hoveredSkill != null) {
            ArrayList<String> lines = Lists.newArrayList(hoveredSkill.getLocalizedName());
//            if(!unlockedSkills.contains(hoveredSkill) && !unlockableSkills.contains(hoveredSkill)) {
//                lines.add(hoveredSkill.getLocalizedKillMoreIctyaMessage());
//            }
            drawHoveringText(lines, mouseX, mouseY);
        }
    }

    private DOSkill hoveredSkill(int mouseX, int mouseY) {
        float heightRatio = ((float)height)/1080;
        mouseX -= treeTextureX;
        mouseX /= heightRatio;
        mouseY /= heightRatio;
        for (Entry<DOSkill, Point> entry : coordinates.entrySet()) {
            Point p = entry.getValue();
            if(mouseX >= p.x - nodeSize/2
            && mouseX <= p.x + nodeSize/2
            && mouseY >= p.y - nodeSize/2
            && mouseY <= p.y + nodeSize/2)
                return entry.getKey();
        }
        return null;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        DOSkill hoveredSkill = hoveredSkill(mouseX, mouseY);
        if(hoveredSkill != null && (unlockedSkills.contains(hoveredSkill) || unlockableSkills.contains(hoveredSkill))) {
            selectedSkill = hoveredSkill;
            descriptionTextArea.setText(createDescriptionParagraphs());
            descriptionTextArea.recompute();
            updateButton();
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        descriptionTextArea.handleMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (unlockedSkills.contains(selectedSkill)) {
            selectedSkill.toggle(mc.player);
            BTVPacketHandler.INSTANCE.sendToServer(new MessageDOSkillsGui(MessageDOSkillsGui.DOGUIActionType.TOGGLE, selectedSkill));
        } else if (unlockableSkills.contains(selectedSkill)) {
            selectedSkill.unlock(mc.player);
            BTVPacketHandler.INSTANCE.sendToServer(new MessageDOSkillsGui(MessageDOSkillsGui.DOGUIActionType.UNLOCK, selectedSkill));
        }
        updateUnlockedSkills();
        updateButton();
        descriptionTextArea.setText(createDescriptionParagraphs());
        descriptionTextArea.recompute();
    }

    private void updateButton() {
        GuiButton unlockButton = buttonList.get(0);
        unlockButton.enabled = true;
        unlockButton.visible = true;
        if(selectedSkill == null) {
            unlockButton.visible = false;
        } else if(unlockedSkills.contains(selectedSkill)) {
            unlockButton.displayString = I18n.format("doskill.toggle");
            if(!selectedSkill.isToggleable()) unlockButton.enabled = false;
        }
        else if(unlockableSkills.contains(selectedSkill)) {
            unlockButton.displayString = I18n.format("doskill.unlock");
            if(!selectedSkill.hasPlayerKilledEnoughIctya(mc.player)
            || !selectedSkill.hasPlayerKilledEnoughBosses(mc.player))
                unlockButton.enabled = false;
        }
    }

    private void updateUnlockedSkills() {
        unlockedSkills.clear();
        unlockableSkills.clear();
        for (DOSkill skill : coordinates.keySet()) {
            IPlayerData data = PlayerDataLib.getCap(Minecraft.getMinecraft().player);
            if(skill.isUnlocked(data))
                unlockedSkills.add(skill);
            else if(skill.isUnlockable(data))
                unlockableSkills.add(skill);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == 18)
            this.mc.displayGuiScreen(new GuiNecronomicon());
        super.keyTyped(typedChar, keyCode);
    }

    private List<String> createDescriptionParagraphs() {
        String name = selectedSkill.getName();
        String desc = I18n.format("doskill." + name + ".description");
        List<String> paragraphs = new ArrayList<>();
        paragraphs.addAll(Arrays.asList(desc.split("<BR>")));
        if(unlockableSkills.contains(selectedSkill)) {
            if (!selectedSkill.hasPlayerKilledEnoughIctya(mc.player)) {
                paragraphs.add(selectedSkill.getLocalizedKillMoreIctyaMessage());
            }
            if (!selectedSkill.hasPlayerKilledEnoughBosses(mc.player)) {
                paragraphs.add(selectedSkill.getLocalizedKillMoreBossesMessage());
            }
        } else {
            paragraphs.add("");
            if (selectedSkill.isActive(mc.player)) {
                paragraphs.add(I18n.format("doskill.toggled.on"));
            } else {
                paragraphs.add(I18n.format("doskill.toggled.off"));
            }
        }
        return paragraphs;
    }

    private static class ArenaBossEntry {
        private final String localizedBossName;
        private final int amountKilled;


        private ArenaBossEntry(Entry<String, Integer> entry) {
            this.localizedBossName = I18n.format("entity.beyondtheveil:" + entry.getKey().replace("arena-killed-", "") + ".name");
            this.amountKilled = entry.getValue();
        }
    }

}
