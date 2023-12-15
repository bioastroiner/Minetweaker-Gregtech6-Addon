package mods.bio.gttweaker.oredict;

import gregapi.data.FL;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("minetweaker.item.ILiquidStack")
public class CTILiquidStackExpansion {
	@ZenGetter
	public CTMaterial material(ILiquidStack iLiquidStack){
		return materialStack(iLiquidStack).material();
	}

	@ZenGetter
	public long amount(ILiquidStack iLiquidStack){
		return materialStack(iLiquidStack).amount();
	}

	@ZenGetter
	public CTMaterialStack materialStack(ILiquidStack iLiquidStack){
		if(MineTweakerMC.getLiquidStack(iLiquidStack) == null) return null;
		OreDictMaterialStack materialStack = OreDictMaterial.FLUID_MAP.get(MineTweakerMC.getLiquidStack(iLiquidStack).getFluid().getName());
		if(materialStack!=null) return new CTMaterialStack(materialStack);
		MineTweakerAPI.logError(String.format("%s dose not have a Material", iLiquidStack));
		return null;
	}
}
