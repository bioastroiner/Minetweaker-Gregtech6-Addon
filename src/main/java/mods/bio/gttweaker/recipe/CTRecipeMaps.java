package mods.bio.gttweaker.recipe;

import mods.bio.gttweaker.GTTweaker;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.recipe.RecipeMaps")
public class CTRecipeMaps {

	@ZenMethod
	public static CTRecipeMap getRecipeMap(String name) {
		return new CTRecipeMap(GTTweaker.FORMAT_RECIPE_MAP(name));
	}
}
