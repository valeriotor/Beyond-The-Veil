package com.valeriotor.beyondtheveil.animations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.valeriotor.beyondtheveil.entities.models.ModelAnimated;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class AnimationTemplate {
	
	public String name;
	public int length;
	public Class<? extends EntityLivingBase> entityType;
	public ModelAnimated modelType;
	public List<PartMover> transforms = new ArrayList<>();

	public static AnimationTemplate createTemplate(String name, List<AnimationTemplate> anims) {
		AnimationTemplate template = new AnimationTemplate(name);
		anims.add(template);
		return template;
	}

	public static AnimationTemplate createDummyTemplate(List<AnimationTemplate> anims) {
		AnimationTemplate template = new AnimationTemplate();
		anims.add(template);
		return template;
	}

	private AnimationTemplate() {
	}

	private AnimationTemplate(String name) {
		this.name = name;
		ResourceLocation location = new ResourceLocation(References.MODID, String.format("animations/%s.btvanim", name));

		BTVAnimHelper processor = new BTVAnimHelper(name, this);
		try(InputStream stream = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {

			HashMap<String, Integer> keys = new HashMap<>();
			HashMap<Integer, EnumMap<Transformation, List<IntervalDoubleBiOperator>>> map = new HashMap<>();
			String s;
			boolean readingKeys = false;
			boolean makingOps = false;
			int start = 0;
			int end = 0;
			while((s = br.readLine()) != null) {
				if(s.contains("end")) {
					readingKeys = false;
					makingOps = false;
					continue;
				}else if(s.contains("keys")) {
					if(makingOps) sendError();
					else readingKeys = true;
					continue;
				}else if(s.contains("start")) {
					String[] ss = s.replaceAll("\\s+", "").split(":");
					start = Integer.valueOf(ss[1]);
					end = Integer.valueOf(ss[2]);
					if(readingKeys) sendError();
					else makingOps = true;
					continue;
				}
				processor.processLine(s, readingKeys, makingOps, start, end, keys, map);
			}
			for(Entry<Integer, EnumMap<Transformation, List<IntervalDoubleBiOperator>>> entry : map.entrySet()) {
				List<Transformator> list = new ArrayList<>();
				transforms.add(new PartMover(entry.getKey(), list));
				for(Entry<Transformation, List<IntervalDoubleBiOperator>> tinyEntry : entry.getValue().entrySet()) {
					list.add(Transformator.getTransformator(tinyEntry.getKey(), tinyEntry.getValue(), this.modelType, entry.getKey(), this.length));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			sendError();
			return;
		}
	}
	
	public void sendError() {
		System.err.println("Something went wrong with loading " + this.name + ".btvanim, blame valeriotor (aka me)");
	}
	
	public void debug() {
		for(PartMover entry : this.transforms) {
			System.out.println(entry.part + ":");
			for(Transformator t : entry.movers) {
				System.out.println(t);
			}
		}
	}
	
	public static class PartMover {
		public final int part;
		public final List<Transformator> movers;
		public PartMover(int part, List<Transformator> movers) {
			this.part = part;
			this.movers = movers;
		}
	}
	
	public enum Transformation{
		ROTX, ROTY, ROTZ, TRAX, TRAY, TRAZ, VISI;	
	}
}
