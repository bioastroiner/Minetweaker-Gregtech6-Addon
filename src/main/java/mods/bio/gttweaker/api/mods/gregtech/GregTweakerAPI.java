package mods.bio.gttweaker.api.mods.gregtech;

import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import minetweaker.api.item.IIngredient;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterial;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialStack;
import mods.bio.gttweaker.mods.gregtech.oredict.CTMaterialStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

public class GregTweakerAPI {
	public static OreDictMaterial getMaterial(IMaterial iMaterial){
		return iMaterial.getMaterial();
	}
	public static OreDictMaterialStack getMaterialStack(IMaterialStack iMaterialStack){
		return ((CTMaterialStack) iMaterialStack).get_backingStack();
	}
	public static OreDictMaterialStack[] getMaterialStacks(IMaterialStack... iMaterialStacks){
		return (OreDictMaterialStack[]) Arrays.stream(iMaterialStacks).map(GregTweakerAPI::getMaterialStack).toArray();
	}

//	public static OreDictPrefix getPrefix(IPrefix iPrefix){
//		return iPrefix.
//	}

	public static ItemStack getItemStackOrNull(IIngredient ingredient) {
		Object internal = ingredient.getInternal();
		if (internal instanceof ItemStack) return (ItemStack) internal;
		else if (internal instanceof String) return !OreDictionary.getOres((String) internal).isEmpty() ? OreDictionary.getOres((String) internal)
				.get(0) : null;
		return null;
	}

	public static FluidStack getFluidStackOrNull(IIngredient ingredient) {
		Object internal = ingredient.getInternal();
		if (internal instanceof FluidStack) return (FluidStack) internal;
		return null;
	}

	public static String FORMAT_RECIPE_MAP(Recipe.RecipeMap map){
		String[] tmp = map.toString().split("\\.");
		return tmp[tmp.length-1];
	}

	public static Recipe.RecipeMap FORMAT_RECIPE_MAP(String name){
		Recipe.RecipeMap out = null;
		if(Recipe.RecipeMap.RECIPE_MAPS.containsKey(name)){
			out = Recipe.RecipeMap.RECIPE_MAPS.get(name);
		}
		if(out == null) for (Recipe.RecipeMap map: Recipe.RecipeMap.RECIPE_MAP_LIST) {
			String[] tmp = map.toString().split("\\.");
			String sName = tmp[tmp.length-1];
			if(Objects.equals(sName.toLowerCase(),name.toLowerCase())){
				out = map;
			}
		}
		if(out!=null){
			return out;
		} else {
			MineTweakerAPI.logError(name + " is not a valid recipemap name!");
		}
		return out;
	}
}
