package mods.bio.gttweaker.api.mods.gregtech;

import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterial;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialStack;
import mods.bio.gttweaker.mods.gregtech.oredict.CTMaterialStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

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

	public enum ScriptProvider{
		POST_PREINIT("scripts_postPreInit","____REGISTER_GT6_MATERIALS_HERE",null),
		AFTER_INIT("scripts_afterInit","____REGISTER_GT6_TILEENTITIES_HERE",null)
		;
		File script_dir;
		File hint_dir;
		String hint_name;
		String hint_content = null;
		ScriptProvider(String folderName,String hint_name, String hint_content){
			script_dir = new File(folderName);
			hint_name = "____REGISTER_GT6_MATERIALS_HERE";
		}
		public ScriptProviderDirectory create(){
			ScriptProviderDirectory provider = new ScriptProviderDirectory(script_dir);
			if (!script_dir.exists()) {
				if(!script_dir.mkdirs()) MineTweakerAPI.logError(String.format("<%s> Folder was failed to be created!", script_dir));
			}
			if(hint_dir == null) return provider;
			hint_dir = new File(script_dir,hint_name);
			try {
				if(!hint_dir.createNewFile()) MineTweakerAPI.logError("Script Hint file was failed to be created!");
			} catch (IOException e) {
				MineTweakerAPI.logError("Script Hint file was failed to be created!",e);
			}
			return provider;
		}

		public void setContent(String write){
			hint_content = write;
		}
	}

	static {
		ScriptProvider.POST_PREINIT.hint_content = "";
		ScriptProvider.AFTER_INIT.hint_content = "";
	}
}
