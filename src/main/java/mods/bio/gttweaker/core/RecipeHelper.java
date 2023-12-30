package mods.bio.gttweaker.core;

import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import mods.bio.gttweaker.api.mods.gregtech.recipe.IRecipe;
import mods.bio.gttweaker.mods.gregtech.recipe.CTRecipe;
import mods.bio.gttweaker.mods.gregtech.recipe.CTRecipeMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

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

	public static boolean checkRecipeIO(Recipe.RecipeMap aRecipeMap, List<ItemStack> aInputs, List<ItemStack> aOutputs, List<FluidStack> aInputFluids, List<FluidStack> aOutputFluids) {
		if(aInputs.size() > aRecipeMap.mInputItemsCount){
			MineTweakerAPI.logError(
					"INPUT ITEMS COUNT MUST BE =< THAN {" +  aRecipeMap.mInputItemsCount + "}"
			);
			return true;
		}
		if(aOutputs.size() > aRecipeMap.mOutputItemsCount){
			MineTweakerAPI.logError(
					"OUTPUT ITEMS COUNT MUST BE =< THAN {" +  aRecipeMap.mOutputItemsCount + "}"
			);
			return true;
		}
		if(aInputs.size() < aRecipeMap.mMinimalInputItems){
			MineTweakerAPI.logError(
					"INPUT ITEMS COUNT MUST BE > THAN {" +  aRecipeMap.mMinimalInputItems + "}"
			);
			return true;
		}
		if(aRecipeMap.mNeedsOutputs && (aOutputs.isEmpty() && aOutputFluids.isEmpty())){
			MineTweakerAPI.logError("OUTPUT ITEMS MUST BE > 0");
			return true;
		}

		if(aInputFluids.size() > aRecipeMap.mInputFluidCount){
			MineTweakerAPI.logError(
					"INPUT FLUID COUNT MUST BE =< THAN {" +  aRecipeMap.mInputItemsCount + "}"
			);
			return true;
		}
		if(aOutputFluids.size() > aRecipeMap.mOutputFluidCount){
			MineTweakerAPI.logError(
					"OUTPUT FLUID COUNT MUST BE =< THAN {" +  aRecipeMap.mOutputItemsCount + "}"
			);
			return true;
		}
		if(aInputFluids.size() < aRecipeMap.mMinimalInputFluids){
			MineTweakerAPI.logError(
					"INPUT FLUID COUNT MUST BE > THAN {" +  aRecipeMap.mMinimalInputItems + "}"
			);
			return true;
		}
		if(aRecipeMap.mNeedsOutputs && (aOutputs.isEmpty() && aOutputFluids.isEmpty())){
			MineTweakerAPI.logError("OUTPUT (FLUID+ITEM) MUST BE > 0");
			return true;
		}
		return false;
	}

	public static boolean checkRecipeIO(Recipe.RecipeMap aRecipeMap,Recipe aRecipe){
		return checkRecipeIO(aRecipeMap, asList(aRecipe.mInputs), asList(aRecipe.mOutputs), asList(aRecipe.mFluidInputs), asList(aRecipe.mFluidOutputs));
	}
}
