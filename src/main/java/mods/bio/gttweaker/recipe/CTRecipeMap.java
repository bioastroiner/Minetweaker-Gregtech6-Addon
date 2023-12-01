package mods.bio.gttweaker.recipe;

import cpw.mods.fml.common.Optional;
import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.*;
import java.util.stream.Collectors;

import static gregapi.data.CS.*;

/**
 * Wraper for GT RecipeMap since that dose not have MT annotations
 */
@ZenClass("mods.gregtech.recipe.RecipeMap")
public class CTRecipeMap {
	public final Recipe.RecipeMap backingRecipeMap;
	public CTRecipeMap(Recipe.RecipeMap mapIn){
		backingRecipeMap = mapIn;
	}
	@ZenMethod("name")
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public String getName(){
		return backingRecipeMap.mNameInternal;
	}

	@ZenMethod("getRecipes")
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public List<CTRecipe> getRecipesCT() {
		return backingRecipeMap.mRecipeList.stream().map(CTRecipe::new).collect(Collectors.toList());
	}

	@ZenMethod("findRecipe")
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public CTRecipe findRecipeCT(IItemStack[] itemsIn, ILiquidStack[] liquidsIn) {
		Recipe recipe = backingRecipeMap.findRecipe(null,null,T,Long.MAX_VALUE,null,MineTweakerMC.getLiquidStacks(liquidsIn),MineTweakerMC.getItemStacks(itemsIn));
		return recipe == null ? null : new CTRecipe(recipe);
	}

	@ZenMethod
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public boolean remove(CTRecipe recipe) {
		if(recipe==null) {
			MineTweakerAPI.logWarning("Recipe " + recipe.toString() + " was not found and was not removed.");
			return true;
		}
		boolean ret = backingRecipeMap.mRecipeList.remove(recipe);
		backingRecipeMap.mRecipeItemMap.entrySet()
				.stream()
				.filter(
						e -> e.getValue()
								.removeIf(r -> r == recipe.backingRecipe)
								&& e.getValue().isEmpty())
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet())
				.forEach(backingRecipeMap.mRecipeItemMap::remove);
		backingRecipeMap.mRecipeFluidMap.entrySet()
				.stream()
				.filter(
						e -> e.getValue()
								.removeIf(r -> r == recipe.backingRecipe)
								&& e.getValue().isEmpty())
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet())
				.forEach(backingRecipeMap.mRecipeFluidMap::remove);
		return ret;
	}

	@ZenMethod("factory")
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public CTRecipeFactory factoryCT() {
		return new CTRecipeFactory(this.backingRecipeMap);
	}


	@ZenGetter("minInputs")
	public int getMinInputs() {
		return backingRecipeMap.mMinimalInputItems;
	}

	@ZenGetter("maxInputs")
	public int getMaxInputs() {
		return backingRecipeMap.mInputItemsCount;
	}

	@ZenGetter("minOutputs")
	public int getMinOutputs() {
		//return backingRecipeMap.mOutputItemsCount;
		return 0;
	}

	@ZenGetter("maxOutputs")
	public int getMaxOutputs() {
		return backingRecipeMap.mOutputItemsCount;
	}

	@ZenGetter("minFluidInputs")
	public int getMinFluidInputs() {
		return backingRecipeMap.mMinimalInputFluids;
	}

	@ZenGetter("maxFluidInputs")
	public int getMaxFluidInputs() {
		return backingRecipeMap.mInputFluidCount;
	}

	@ZenGetter("minFluidOutputs")
	public int getMinFluidOutputs() {
		return 0;
	}

	@ZenGetter("maxFluidOutputs")
	public int getMaxFluidOutputs() {
		return backingRecipeMap.mOutputFluidCount;
	}

	@ZenMethod("register")
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public void registerCT(CTRecipe recipe) {
		backingRecipeMap.add(recipe.backingRecipe);
	}
}