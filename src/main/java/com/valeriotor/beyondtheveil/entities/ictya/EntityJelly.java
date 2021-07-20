package com.valeriotor.beyondtheveil.entities.ictya;

import com.valeriotor.beyondtheveil.entities.EntityDeepOne;
import com.valeriotor.beyondtheveil.entities.ictya.EntityIctya.AIAttackMeleeAgainstLargeCreatures;
import com.valeriotor.beyondtheveil.entities.util.WaterMoveHelper;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityJelly extends EntityMob{
	private int timeToDie;
	private final EntityManOWar master;
	
	public EntityJelly(World worldIn) {
		this(worldIn, 100, null);
	}
	
	public EntityJelly(World worldIn, int time, EntityManOWar master) {
		super(worldIn);
		moveHelper = new WaterMoveHelper(this);
        setPathPriority(PathNodeType.WALKABLE, -8.0F);
        setPathPriority(PathNodeType.BLOCKED, -8.0F);
        setPathPriority(PathNodeType.WATER, 16.0F);
        this.timeToDie = time;
        this.master = master;
	}
	
	@Override
	protected void initEntityAI() {	 	
		super.initEntityAI();
		this.tasks.addTask(0, new AIAttackMeleeAgainstLargeCreatures(this, 0.35, false));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(70.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);	
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8D);
	}

	public EntityManOWar getMaster() {
		return master;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if(!world.isRemote) {
			timeToDie--;
			if(timeToDie <= 0 || master == null || master.isDead) {
				world.removeEntity(this);
			} else {
				setAttackTarget(master.getAttackTarget());
			}
		}
	}
	
	@Override
	public void onKillEntity(EntityLivingBase e) {
		if(e instanceof EntityIctya) {
			master.feed(((EntityIctya)e).getFoodValue());
			master.heal((float)((EntityIctya)e).getFoodValue()/5);
		}
		else if(e instanceof EntityPlayer || e instanceof EntityDeepOne)
			master.feed(200);
		world.removeEntity(this);
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	@Override
	protected PathNavigate createNavigator(World worldIn) {
		return new PathNavigateSwimmer(this, worldIn);
	}
	
	@Override
    public float getBlockPathWeight(BlockPos pos) {
        return world.getBlockState(pos).getMaterial() == Material.WATER ? 10.0F + world.getLightBrightness(pos) - 0.5F : super.getBlockPathWeight(pos);
    }
	
	@Override
	public void travel(float strafe, float vertical, float forward) {
		if (isServerWorld()) {
            if (isInWater()) {
                moveRelative(strafe, vertical, forward, 0.2F);
                move(MoverType.SELF, motionX, motionY, motionZ);
                motionX *= 0.9D;
                motionY *= 0.9D;
                motionZ *= 0.9D;

            } else {
                super.travel(strafe, vertical, forward);
            }
        } else {
            super.travel(strafe, vertical, forward);
        }
	}

}
