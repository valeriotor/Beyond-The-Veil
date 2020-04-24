package com.valeriotor.beyondtheveil.entities.AI;

import com.valeriotor.beyondtheveil.blocks.BlockWateryCradle;
import com.valeriotor.beyondtheveil.entities.EntitySurgeon;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.tileEntities.TileWateryCradle;
import com.valeriotor.beyondtheveil.tileEntities.TileWateryCradle.PatientStatus;
import com.valeriotor.beyondtheveil.tileEntities.TileWateryCradle.PatientTypes;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class AISurgeon extends EntityAIBase{
	private EntitySurgeon surgeon;
	private int counter = 0;
	
	public AISurgeon(EntitySurgeon surgeon) {
		this.surgeon = surgeon;
		this.setMutexBits(1);
	}
	
	@Override
	public boolean shouldExecute() {
		if(surgeon.patient != null && !surgeon.patient.isEmpty()) return false;
		if(surgeon.getOperation() == -1) return false;
		BlockPos cradle = surgeon.getCradle();
		if(cradle != null) {
			TileEntity te = surgeon.world.getTileEntity(cradle);
			if(te instanceof TileWateryCradle) {
				TileWateryCradle twc = (TileWateryCradle)te;
				PatientStatus ps = twc.getPatientStatus();
				if(ps.getPatientType() != PatientTypes.NONE) {
					int op = surgeon.getOperation();
					if(counter > 0 || (!ps.isHeartless() && op == 3) || (!ps.isSpineless() && op == 0) || (ps.getPatientType() != PatientTypes.WEEPER && op == 1))
						return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void startExecuting() {
		this.counter = 50;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if(counter > 0 && shouldExecute()) return true;
		else {
			// stop animation
			return false;
		}
	}
	
	@Override
	public void updateTask() {
		BlockPos cradle = surgeon.getCradle();
		BlockPos pos = cradle.offset(surgeon.world.getBlockState(cradle).getValue(BlockWateryCradle.FACING).getOpposite());
		if(surgeon.getDistanceSq(pos) > 1)
			surgeon.getNavigator().setPath(surgeon.getNavigator().getPathToPos(pos), 1.4);
		else {
			TileWateryCradle twc = (TileWateryCradle)surgeon.world.getTileEntity(cradle);
			if(this.counter == 50) {
				// start animation
			}
			if(this.counter == 1) {
				SoundEvent sound = null;
				switch(surgeon.getOperation()) {
					case 3: 
						if(!twc.getPatientStatus().isHeartless()) {
							twc.setPatient(twc.getPatientStatus().withHeartless(true));
							sound = BTVSounds.heartRip;
							surgeon.hearts++;
						}
						break;
					case 1:
						if(twc.getPatientType() != PatientTypes.WEEPER) {
							twc.setPatient(twc.getPatientStatus().withPatient(PatientTypes.WEEPER));
						}
						break;
					case 0:
						if(!twc.getPatientStatus().isSpineless()) {
							twc.setPatient(twc.getPatientStatus().withSpineless(true));
							sound = BTVSounds.spineRip;
							surgeon.spines++;
						}
				}
				if(surgeon.getContainer() != null) {
					surgeon.patient = twc.getPatientItem();
				}
				twc.setPatient(PatientStatus.getNoPatientStatus());
				if(sound != null)
					surgeon.world.playSound(null, pos, sound, SoundCategory.PLAYERS, 1, 1);
			}
			this.counter--;
		}
	}

}
