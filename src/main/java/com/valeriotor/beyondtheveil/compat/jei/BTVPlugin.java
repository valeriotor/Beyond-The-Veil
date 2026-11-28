package com.valeriotor.beyondtheveil.compat.jei;

import com.valeriotor.beyondtheveil.client.gui.DrownedGui;
import com.valeriotor.beyondtheveil.client.gui.LetterBoxGui;
import com.valeriotor.beyondtheveil.client.gui.dialogue.DrownedDialogueGui;
import com.valeriotor.beyondtheveil.client.gui.dialogue.EntityDialogueGui;
import com.valeriotor.beyondtheveil.client.gui.dialogue.MirrorDialogueGui;
import com.valeriotor.beyondtheveil.client.gui.dialogue.ShoremanCultistDialogueGui;
import com.valeriotor.beyondtheveil.container.dialogue.EntityDialogueMenu;
import com.valeriotor.beyondtheveil.lib.References;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class BTVPlugin implements IModPlugin {

    public static final ResourceLocation MOD = new ResourceLocation(References.MODID, References.MODID);

    @Override
    public ResourceLocation getPluginUid() {
        return MOD;
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGuiScreenHandler(DrownedDialogueGui.class, screen -> null);
        registration.addGuiScreenHandler(EntityDialogueGui.class, screen -> null);
        registration.addGuiScreenHandler(MirrorDialogueGui.class, screen -> null);
        registration.addGuiScreenHandler(ShoremanCultistDialogueGui.class, screen -> null);
        registration.addGuiScreenHandler(DrownedGui.class, screen -> null);
        registration.addGuiScreenHandler(LetterBoxGui.class, screen -> null);
    }
}
