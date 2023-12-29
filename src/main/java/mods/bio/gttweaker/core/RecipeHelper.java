package mods.bio.gttweaker.core;

import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import mods.bio.gttweaker.mods.gregtech.recipe.CTRecipe;
import mods.bio.gttweaker.mods.gregtech.recipe.CTRecipeMap;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.stream.Collectors;

public class RecipeHelper {

	public static boolean removeGTRecipe(@Nonnull Recipe mRecipe, @Nonnull Recipe.RecipeMap mRecipeMap) {
		if (mRecipeMap.mRecipeList.contains(mRecipe))
			MineTweakerAPI.logCommand(CTRecipe.format(mRecipe) + " of " + CTRecipeMap.format(mRecipeMap) + " has been Hidden due to reloading");
		boolean ret = mRecipeMap.mRecipeList.remove(mRecipe);
		if (!ret)
			MineTweakerAPI.logError(String.format("Recipe: %s \nwas not Removed from %s RecipeMap!", CTRecipe.format(mRecipe), CTRecipeMap.format(mRecipeMap)));
		else {
			mRecipeMap.mRecipeItemMap.entrySet().stream().filter(e -> e.getValue().removeIf(r -> r == mRecipe) && e.getValue().isEmpty()).map(Map.Entry::getKey).collect(Collectors.toSet()).forEach(mRecipeMap.mRecipeItemMap::remove);
			mRecipeMap.mRecipeFluidMap.entrySet().stream().filter(e -> e.getValue().removeIf(r -> r == mRecipe) && e.getValue().isEmpty()).map(Map.Entry::getKey).collect(Collectors.toSet()).forEach(mRecipeMap.mRecipeFluidMap::remove);
		}
		return ret;
	}

	public static boolean addGTRecipe(@Nonnull Recipe mRecipe, @Nonnull Recipe.RecipeMap mRecipeMap) {
		return addGTRecipe(mRecipe, mRecipeMap, true);
	}

	public static boolean addGTRecipe(@Nonnull Recipe mRecipe, @Nonnull Recipe.RecipeMap mRecipeMap, boolean checkForCollisions) {
		// if NULL was returned then that recipe already existed, IF check for collision is TRUE
		return mRecipeMap.addRecipe(mRecipe, checkForCollisions, mRecipe.mFakeRecipe, mRecipe.mHidden) != null;
	}
}
