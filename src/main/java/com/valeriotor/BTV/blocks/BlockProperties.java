package com.valeriotor.BTV.blocks;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

public class BlockProperties {
	
	public static final PropertyDirection HORIZONTAL_FACING = PropertyDirection.create("horizontal_facing", new Predicate<EnumFacing>()
    {
        public boolean apply(@Nullable EnumFacing p_apply_1_)
        {
            return p_apply_1_ != EnumFacing.DOWN && p_apply_1_ != EnumFacing.UP;
        }
    });
	
}
