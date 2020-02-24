package com.valeriotor.BTV.dreaming.dreams;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.events.special.CrawlerWorshipEvents;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.worship.CrawlerWorship;
import com.valeriotor.BTV.worship.CrawlerWorship.WorshipType;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Worship;

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
			caster.sendMessage(new TextComponentTranslation("dreams.introspection.otherplayer"));
		} else {
			caster.sendMessage(new TextComponentTranslation("dreams.introspection.caster"));
		}
		caster.sendMessage(new TextComponentTranslation("dreams.introspection.deity", Worship.getSelectedDeity(target).getName()));
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		String attackMod = df.format(DGWorshipHelper.getAttackModifier(target));
		String defenseMod = df.format(DGWorshipHelper.getDefenseModifier(target));
		int dreamBoost = DGWorshipHelper.getDreamPower(target);
		caster.sendMessage(new TextComponentTranslation("dreams.introspection.strength", attackMod, defenseMod, dreamBoost));
		if(target.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.VOID)) {
			caster.sendMessage(new TextComponentTranslation("dreams.introspection.void"));
		} else {
			caster.sendMessage(new TextComponentTranslation("dreams.introspection.novoid"));
		}
		CrawlerWorship cw = CrawlerWorshipEvents.getWorship(target);
		if(cw != null) {
			WorshipType type = cw.getWorshipType();
			int strength = cw.getStrength();
			caster.sendMessage(new TextComponentTranslation("dreams.introspection.worship", type.getLocalizedName(), strength));
		} else {
			caster.sendMessage(new TextComponentTranslation("dreams.introspection.noworship"));
		}
		return true;
	}

}
