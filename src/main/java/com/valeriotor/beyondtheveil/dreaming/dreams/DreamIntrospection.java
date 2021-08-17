package com.valeriotor.beyondtheveil.dreaming.dreams;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.events.special.CrawlerWorshipEvents;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.worship.CrawlerWorship;
import com.valeriotor.beyondtheveil.worship.DGWorshipHelper;
import com.valeriotor.beyondtheveil.worship.Worship;
import com.valeriotor.beyondtheveil.worship.CrawlerWorship.WorshipType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class DreamIntrospection extends Dream{

	public DreamIntrospection(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos) {
		return this.activatePlayer(p, p, w);
	}
	
	@Override
	public boolean activatePlayer(EntityPlayer caster, EntityPlayer target, World w) {
		if(caster != target) {
			caster.sendMessage(new TextComponentTranslation("dreams.introspection.otherplayer", target.getName()));
		} else {
			caster.sendMessage(new TextComponentTranslation("dreams.introspection.caster"));
		}
		caster.sendMessage(new TextComponentTranslation("dreams.introspection.deity", Worship.getSelectedDeity(target).getName()));
		double aModifier = DGWorshipHelper.getAttackModifier(target);
		double dModifier = DGWorshipHelper.getDefenseModifier(target);
		String attackMod = "+".concat(String.valueOf(Math.round(aModifier*50))).concat("%");
		String attackMod2 = "+".concat(String.valueOf(Math.round((1+aModifier)*(1+aModifier)*100-100))).concat("%");
		String defenseMod = "-".concat(String.valueOf(100-Math.round(dModifier*100))).concat("%");
		String defenseMod2 = "-".concat(String.valueOf(100-Math.round(dModifier*dModifier*100))).concat("%");
		int dreamBoost = DGWorshipHelper.getDreamPower(target);
		caster.sendMessage(new TextComponentTranslation("dreams.introspection.attack", attackMod, attackMod2));
		caster.sendMessage(new TextComponentTranslation("dreams.introspection.defense", defenseMod, defenseMod2));
		caster.sendMessage(new TextComponentTranslation("dreams.introspection.dream", dreamBoost));
		if(target.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.VOID)) {
			caster.sendMessage(new TextComponentTranslation("dreams.introspection.void"));
		} else {
			caster.sendMessage(new TextComponentTranslation("dreams.introspection.novoid"));
		}
		CrawlerWorship cw = CrawlerWorshipEvents.getWorship(target);
		if(cw != null) {
			WorshipType type = cw.getWorshipType();
			int strength = cw.getStrength();
			caster.sendMessage(new TextComponentTranslation("dreams.introspection.worship", type.getLocalizedName(), strength+1));
		} else {
			caster.sendMessage(new TextComponentTranslation("dreams.introspection.noworship"));
		}
		return true;
	}

}
