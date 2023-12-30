package mods.bio.gttweaker.api.mods.gregtech;

import gregapi.oredict.OreDictMaterial;
import minetweaker.api.item.IIngredient;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class GregTweakerAPI {
	public static OreDictMaterial getMaterial(IMaterial iMaterial){
		return iMaterial.getMaterial();
	}

//	public static OreDictPrefix getPrefix(IPrefix iPrefix){
//		return iPrefix.
//	}

	public static ItemStack getItemStackOrNull(IIngredient ingredient) {
		Object internal = ingredient.getInternal();
		if (internal instanceof ItemStack) return (ItemStack) internal;
		else if (internal instanceof String) return OreDictionary.getOres((String) internal)
				.size() > 0 ? OreDictionary.getOres((String) internal)
				.get(0) : null;
		return null;
	}

	public static FluidStack getFluidStackOrNull(IIngredient ingredient) {
		Object internal = ingredient.getInternal();
		if (internal instanceof FluidStack) return (FluidStack) internal;
		return null;
	}
}
