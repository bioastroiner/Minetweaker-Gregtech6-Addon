package mods.bio.gttweaker.mods.minetweaker;

import minetweaker.api.liquid.ILiquidStack;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialData;
import mods.bio.gttweaker.mods.gregtech.oredict.CTMaterialData;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("minetweaker.item.ILiquidStack")
public class CTILiquidStackExpansion {
	@ZenGetter
	public static IMaterialData association(ILiquidStack iLiquidStack) {
		return IMaterialData.association(iLiquidStack);
	}

	@ZenGetter
	public static IMaterialData data(ILiquidStack iLiquidStack) {
		return association(iLiquidStack);
	}
}
