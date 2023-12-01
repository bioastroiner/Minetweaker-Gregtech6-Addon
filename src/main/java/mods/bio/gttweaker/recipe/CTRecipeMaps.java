package mods.bio.gttweaker.recipe;

import gregapi.recipes.Recipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@ZenClass("mods.gregtechmod.recipe.RecipeMaps")
public class CTRecipeMaps {

	@ZenMethod
	public static CTRecipeMap getRecipeMap(String name) {
		return new CTRecipeMap(Recipe.RecipeMap.RECIPE_MAPS.get(name));
	}
}
