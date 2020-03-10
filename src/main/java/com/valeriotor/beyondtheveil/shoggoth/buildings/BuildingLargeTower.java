package com.valeriotor.beyondtheveil.shoggoth.buildings;

import java.io.IOException;
import java.util.HashMap;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.gui.GuiCityMapper;
import com.valeriotor.beyondtheveil.items.TestItem.JSonStructureBuilder;
import com.valeriotor.beyondtheveil.shoggoth.BlockBuffer;
import com.valeriotor.beyondtheveil.shoggoth.BuildingRegistry;
import com.valeriotor.beyondtheveil.shoggoth.BuildingTemplate;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;

public class BuildingLargeTower extends BuildingTemplate{

	public BuildingLargeTower(String name, int index) {
		super(20, 20, false, name, index);
		String file;
		try {
			file = Resources.toString(BeyondTheVeil.class.getResource("/assets/beyondtheveil/buildings/large_tower.json"), Charsets.UTF_8);
			JSonStructureBuilder jssb = BeyondTheVeil.gson.fromJson(file, JSonStructureBuilder.class);
			HashMap<Block, byte[][]> map = jssb.getMap();
			this.buffer = new BlockBuffer(map, jssb.getXSize(), jssb.getYSize(), jssb.getZSize());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isDefault() {
		return true;
	}

	@Override
	public void drawScaledTexture(GuiCityMapper gui, int xTopLeft, int yTopLeft, float scale, int width, int height) {
		this.drawHelper(gui, xTopLeft, yTopLeft, 32, 0, scale);
	}

}
