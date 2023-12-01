package mods.bio.gttweaker.recipe;

import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.gregtech.recipe.RecipeMaps")
public class CTRecipeMaps {

	@ZenMethod
	public static CTRecipeMap getRecipeMap(String name) {
		if(!Recipe.RecipeMap.RECIPE_MAPS.containsKey(name)){
			MineTweakerAPI.logError("RECIPEMAP " + name + " NOT FOUND!");
			return null;
		}
		return new CTRecipeMap(Recipe.RecipeMap.RECIPE_MAPS.get(name));
	}

	@ZenMethod
	public static List<CTRecipeMap> getRecipeMaps(){
		List<CTRecipeMap> ctMaps = new ArrayList<>();
		Recipe.RecipeMap.RECIPE_MAPS.forEach((name,map)->{
			ctMaps.add(new CTRecipeMap(map));
			MineTweakerAPI.logInfo(name +
					"\n {Max Inputs: " + map.mInputItemsCount + "}" +
					"\n {Min Inputs: " + map.mMinimalInputItems + "}" +
					"\n {Max Fluid Inputs: " + map.mInputFluidCount + "}" +
					"\n {Min Fluid Inputs: " + map.mMinimalInputFluids + "}" );
			// TODO outputs
		});
		return ctMaps;
	}

	@ZenMethod
	public static void logRecipeMaps(){
		Recipe.RecipeMap.RECIPE_MAPS.forEach((name,map)->{
			MineTweakerAPI.logInfo(name +
					"\n {Max Inputs: " + map.mInputItemsCount + "}" +
					"\n {Min Inputs: " + map.mMinimalInputItems + "}" +
					"\n {Max Fluid Inputs: " + map.mInputFluidCount + "}" +
					"\n {Min Fluid Inputs: " + map.mMinimalInputFluids + "}" );
			// TODO outputs
		});
	}
}
