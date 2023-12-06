package mods.bio.gttweaker.recipe;

import gregapi.recipes.Recipe;
import mods.bio.gttweaker.GTTweaker;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.stream.Collectors;

@ZenClass("mods.gregtech.recipe.RecipeMaps")
public class CTRecipeMaps {

	@ZenMethod
	public static CTRecipeMap getRecipeMap(String name) {
		return new CTRecipeMap(GTTweaker.FORMAT_RECIPE_MAP(name));
	}

	@ZenGetter
	public static CTRecipeMap[] getRecipeMaps(){
		return Recipe.RecipeMap.RECIPE_MAP_LIST.stream().map(CTRecipeMap::new).toArray(CTRecipeMap[]::new);
	}
}
