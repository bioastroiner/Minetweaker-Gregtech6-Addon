package mods.bio.gttweaker.mods.gregtech.recipe;

import cpw.mods.fml.common.Optional;
import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import mods.bio.gttweaker.core.GTTweaker;
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
	public final String nameShort,nameInternal;
	public CTRecipeMap(Recipe.RecipeMap mapIn){
		backingRecipeMap = mapIn;
		nameInternal = mapIn.mNameInternal;
		nameShort = GTTweaker.FORMAT_RECIPE_MAP(mapIn);
	}
	@ZenMethod("name")
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public String getNameShort(){
		return nameShort;
	}

	@ZenMethod("nameInternal")
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public String getNameInternal(){
		return nameInternal;
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

	@ZenMethod("removeRecipe")
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public boolean removeRecipeCT(IItemStack[] itemsIn) {
		return removeRecipeCT(itemsIn, null);
	}

	@ZenMethod("removeRecipe")
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public boolean removeRecipeCT(ILiquidStack[] liquidsIn) {
		return removeRecipeCT(null, liquidsIn);
	}

	@ZenMethod("removeRecipe")
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public boolean removeRecipeCT(IItemStack[] itemsIn, ILiquidStack[] liquidsIn){
		return remove(findRecipeCT(itemsIn,liquidsIn));
	}

	/**
	 * Remover dose not remove on Reload!!! but it hides and disables the recipes so do not worry.
	 */
	@ZenMethod
	@Optional.Method(
			modid = "MineTweaker3"
	)
	//FIXME remover
	public boolean remove(CTRecipe recipe) {
		if (recipe == null) {
			MineTweakerAPI.logWarning("NULL recipe was tried to be removed!!!");
			return true;
		}
//		if (backingRecipeMap.mRecipeList.contains(recipe.backingRecipe))
//			MineTweakerAPI.logError(recipe + " was not removed. WHY");
		recipe.backingRecipe.mHidden = true;
		recipe.backingRecipe.mEnabled = false;
		boolean ret = backingRecipeMap.mRecipeList.remove(recipe.backingRecipe);
		if(!ret) MineTweakerAPI.logError(String.format("Recipe: %s \nwas not Removed from %s RecipeMap!",recipe,this));
		else {
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
		}
		return ret;
	}

	@ZenMethod
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public void add(CTRecipe recipe) {
		MineTweakerAPI.logCommand("adding " + recipe + " to" + this);
		backingRecipeMap.addRecipe(recipe.backingRecipe);
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
		add(recipe);
	}

	/**
	 * @return formatted string as it is formatted in bracketHandler
	 */
	@Override
	public String toString() {
		return String.format("<recipemap:%s>",nameInternal);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CTRecipeMap){
			return backingRecipeMap == ((CTRecipeMap) obj).backingRecipeMap;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(backingRecipeMap);
	}
}
