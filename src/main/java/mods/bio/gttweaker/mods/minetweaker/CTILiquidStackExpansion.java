package mods.bio.gttweaker.mods.minetweaker;

import minetweaker.api.liquid.ILiquidStack;
import mods.bio.gttweaker.mods.gregtech.oredict.CTMaterialData;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("minetweaker.item.ILiquidStack")
public class CTILiquidStackExpansion {
	@ZenGetter
	public static CTMaterialData association(ILiquidStack iLiquidStack) {
		return CTMaterialData.association(iLiquidStack);
	}

	@ZenGetter
	public static CTMaterialData data(ILiquidStack iLiquidStack) {
		return association(iLiquidStack);
	}
}
