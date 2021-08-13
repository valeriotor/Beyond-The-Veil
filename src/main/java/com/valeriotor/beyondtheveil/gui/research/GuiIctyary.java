package com.valeriotor.beyondtheveil.gui.research;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.entities.ictya.EntityIctya;
import com.valeriotor.beyondtheveil.entities.ictya.EntitySandflatter;
import com.valeriotor.beyondtheveil.gui.GuiHelper;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GuiIctyary extends GuiScreen {

    private List<List<EntityIctya>> ictyaPages;
    private int entryHeight = 0;
    private int currentPage = 0;
    private int entrySeparatorBarHeight;
    private int stringDistanceFromEntryCeiling;
    private EntityIctya selectedEntity;
    private int rotationX;
    private int rotationY;
    private int factor;
    private int translateX;
    private int translateY;
    private List<List<String>> description = new ArrayList<>();

    public GuiIctyary() {
        IPlayerData data = PlayerDataLib.getCap(Minecraft.getMinecraft().player);
        List<EntityIctya> ictyas = data.getStrings(false).stream()
                .filter(s -> s.startsWith("ictya-"))
                .map(s -> s.replace("ictya-", "beyondtheveil:"))
                .map(ResourceLocation::new)
                .map(ForgeRegistries.ENTITIES::getValue)
                .filter(Objects::nonNull)
                .map(entry -> entry.newInstance(Minecraft.getMinecraft().world))
                .map(entity -> (EntityIctya) entity)
                .sorted(Comparator.comparingInt(EntityIctya::getSizeInt).thenComparing(EntityIctya::getName))
                .collect(Collectors.toList());
        for (EntityIctya ictya : ictyas) {
            if(ictya instanceof EntitySandflatter)
                ((EntitySandflatter)ictya).stopAmbushing();
        }
        ictyaPages = new ArrayList<>();
        ictyaPages.add(ictyas);
    }

    @Override
    public void initGui() {
        int guiScale = mc.gameSettings.guiScale;
        int ictyasPerPage =  0;
        switch (guiScale) {
            case 0:
            case 3: ictyasPerPage = 4;
                    break;
            case 1: ictyasPerPage = 8;
                    break;
            case 2: ictyasPerPage = 6;
        }
        List<EntityIctya> ictyas = ictyaPages.stream().flatMap(Collection::stream).collect(Collectors.toList());
        ictyaPages.clear();
        ictyaPages.add(new ArrayList<>());
        int i = 0;
        for (EntityIctya e : ictyas) {
            ictyaPages.get(ictyaPages.size() - 1).add(e);
            i++;
            if (i == ictyasPerPage) {
                i = 0;
                ictyaPages.add(new ArrayList<>());
            }
        }

        int lastIndex = ictyaPages.size() - 1;
        if(ictyaPages.get(lastIndex).isEmpty() && ictyaPages.size() > 1) ictyaPages.remove(lastIndex); // remove eventual empty final list

        entryHeight = (height*9/10)/ictyasPerPage;
        entrySeparatorBarHeight = height/85;
        stringDistanceFromEntryCeiling = height/20;

        int buttonId = 0;
        buttonList.add(new GuiButton(buttonId++, width/8 - width/20 - width/80, height*375/400, width/40, 20,"<"));
        buttonList.add(new GuiButton(buttonId++, width/8 + width/20 - width/80, height*375/400, width/40, 20,">"));

        buttonList.get(0).enabled = currentPage != 0;
        buttonList.get(1).enabled = currentPage != ictyaPages.size()-1;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        // Entity model background - z = -900
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, -900);
        drawRect(width/4, 0, width*3/4, height, 0xFF000000);
        GlStateManager.popMatrix();

        // Text, entries etc. - z = 900
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 900);
        drawRect(0, 0, width/4, height, 0xFF000000);
        drawRect(width*3/4, 0, width, height, 0xFF000000);
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawRect(width/4, 0, width*13/50, height, 0xFFAAAAAA);
        drawRect(width*37/50, 0, width*3/4, height, 0xFFAAAAAA);
        int distanceFromCeiling = height/40;
        int hoveredEntry = hoveredEntry(mouseX, mouseY);
        List<EntityIctya> currentPageList = ictyaPages.get(currentPage);
        for (int i = 0, getSize = currentPageList.size(); i < getSize; i++) {
            EntityIctya e = currentPageList.get(i);
            int stringColor = i == hoveredEntry ? Color.YELLOW.getRGB() : 0xFFFFFFFF;
            drawRect(0, distanceFromCeiling, width / 4, distanceFromCeiling + entrySeparatorBarHeight, 0xFFAAAAAA);
            drawString(mc.fontRenderer, e.getDisplayName().getFormattedText(), width / 100, distanceFromCeiling + stringDistanceFromEntryCeiling, stringColor);
            GlStateManager.pushMatrix();
            GlStateManager.translate(width * 2 / 100, distanceFromCeiling + stringDistanceFromEntryCeiling * 2, 0);
            GlStateManager.scale(0.8, 0.8, 0.8);
            drawString(mc.fontRenderer, I18n.format("ictya.size." + e.getSize().name()), 0, 0, stringColor);
            GlStateManager.popMatrix();
            distanceFromCeiling += entryHeight;
        }
        drawRect(0, distanceFromCeiling, width/4, distanceFromCeiling+entrySeparatorBarHeight, 0xFFAAAAAA);
        String pageCount = (currentPage + 1) + "/" + ictyaPages.size();
        drawCenteredString(mc.fontRenderer, pageCount, width/8, height*380/400, 0xFFFFFFFF);
        int textY = height/4;
        for (List<String> ss :
                description) {
            for (String s: ss) {
                drawString(mc.fontRenderer, s, width*76/100, textY, 0xFFFFFFFF);
                textY += 20;
            }
            textY += 10;
        }
        GlStateManager.popMatrix();


        // Entity model - z = 0
        if(selectedEntity != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(width/2+translateX, height*11/20+translateY, 0);
            GlStateManager.rotate(180+rotationY, 1, 0, 0);
            GlStateManager.pushMatrix();
            int scaleFactor = 5*factor;
            GlStateManager.scale(scaleFactor, scaleFactor, scaleFactor);
            GlStateManager.rotate(rotationX, 0, 1, 0);
            Minecraft.getMinecraft().getRenderManager().renderEntity(selectedEntity, 0,0,0, 0, partialTicks, false);
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
        }
    }

    public int hoveredEntry(int mouseX, int mouseY) {
        if(mouseX > width/4) return -1;
        if(mouseY > height*39/40 || mouseY < height/40) return -1;
        int distanceFromCeiling = height/40;
        for (int i = 0; i < ictyaPages.get(currentPage).size(); i++) {
            distanceFromCeiling += entryHeight;
            if(mouseY < distanceFromCeiling) return i;
        }
        return -1;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int hoveredEntry = hoveredEntry(mouseX, mouseY);
        if(hoveredEntry != -1) {
            selectedEntity = ictyaPages.get(currentPage).get(hoveredEntry);
            factor = 8;
            rotationX = 235;
            rotationY = 0;
            translateX = 0;
            translateY = 0;
            mc.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1, 1);
            description = createDescription();
        }
    }

    private List<List<String>> createDescription() {
        String name = ForgeRegistries.ENTITIES.getKey(EntityRegistry.getEntry(selectedEntity.getClass())).getResourcePath();
//        String name = selectedEntity.getName().replace("entity.","").replace(".name","");
        String desc = I18n.format("ictya." + name + ".description");
        List<String> paragraphs = Arrays.asList(desc.split("<BR>"));
        List<List<String>> description = paragraphs.stream()
                .map(s -> GuiHelper.splitStringsByWidth(s, width*23/100, mc.fontRenderer))
                .collect(Collectors.toList());
        return description;
    }

    @Override
    public void handleMouseInput() throws IOException {
        if(Mouse.isButtonDown(0)) {
            rotationX = ((rotationX - Mouse.getDX()));
            rotationY = MathHelperBTV.clamp(-90, 90, (rotationY + Mouse.getDY()));
        }
        if(Mouse.isButtonDown(1)) {
            translateX = MathHelperBTV.clamp(-200, 200, (translateX + Mouse.getDX()));
            translateY -= Mouse.getDY();
        }
        this.factor = MathHelperBTV.clamp(1, 20, this.factor + (int)Math.signum(Mouse.getDWheel()));
        super.handleMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: currentPage = Math.max(currentPage-1, 0);
                break;
            case 1: currentPage = Math.min(currentPage+1, ictyaPages.size()-1);
                break;
        }
        buttonList.get(0).enabled = currentPage != 0;
        buttonList.get(1).enabled = currentPage != ictyaPages.size()-1;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == 18)
            this.mc.displayGuiScreen(new GuiNecronomicon());
        super.keyTyped(typedChar, keyCode);
    }

}
