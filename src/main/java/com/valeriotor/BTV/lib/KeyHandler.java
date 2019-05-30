package com.valeriotor.BTV.lib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyHandler {
	
	public KeyBinding dodge = new KeyBinding(I18n.format("keys.dodge"), 56, "Beyond the Veil");
	public KeyBinding medallion = new KeyBinding(I18n.format("keys.medallion"), 44, "Beyond the Veil");
	public KeyBinding power = new KeyBinding(I18n.format("keys.power"), 37, "Beyond the Veil");
	
	
	public KeyHandler() {
		ClientRegistry.registerKeyBinding(dodge);
		ClientRegistry.registerKeyBinding(medallion);
		ClientRegistry.registerKeyBinding(power);
	}
	
}
