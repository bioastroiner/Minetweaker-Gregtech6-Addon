package mods.bio.gttweaker.mods.gregtech.recipe;

import gregapi.recipes.Recipe;
import mods.bio.gttweaker.api.mods.gregtech.recipe.IRecipeMap;
import mods.bio.gttweaker.core.GTTweaker;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.recipe.RecipeMaps")
public class CTRecipeMaps {

	@ZenMethod
	public static IRecipeMap getRecipeMap(String name) {
		return new CTRecipeMap(GTTweaker.FORMAT_RECIPE_MAP(name));
	}

	@ZenGetter
	public static IRecipeMap[] getRecipeMaps(){
		return Recipe.RecipeMap.RECIPE_MAP_LIST.stream().map(CTRecipeMap::new).toArray(IRecipeMap[]::new);
	}
}
