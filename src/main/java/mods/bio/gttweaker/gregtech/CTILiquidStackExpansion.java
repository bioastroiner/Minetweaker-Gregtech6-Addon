package mods.bio.gttweaker.gregtech;

import minetweaker.api.liquid.ILiquidStack;
import mods.bio.gttweaker.api.oredict.IMaterialData;
import mods.bio.gttweaker.gregtech.oredict.CTMaterialManager;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("minetweaker.item.ILiquidStack")
public class CTILiquidStackExpansion {
	@ZenGetter
	public static IMaterialData association(ILiquidStack iLiquidStack) {
		return CTMaterialManager.association(iLiquidStack);
	}

	@ZenGetter
	public static IMaterialData data(ILiquidStack iLiquidStack) {
		return association(iLiquidStack);
	}
}
