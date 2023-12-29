package mods.bio.gttweaker.mods.gregtech.recipe;

import cpw.mods.fml.common.Optional;
import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import mods.bio.gttweaker.core.GTTweaker;
import mods.bio.gttweaker.mods.gregtech.recipe.actions.AddRecipeAction;
import mods.bio.gttweaker.mods.gregtech.recipe.actions.RemoveRecipeAction;
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

	public static String format(Recipe.RecipeMap recipeMap){
		return String.format("<recipemap:%s>",recipeMap.mNameInternal);
	}
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
		if (recipe == null) MineTweakerAPI.logWarning(String.format("No Recipe with \nItems: %s - \nFluids: %s Was Found!", Arrays.toString(itemsIn), Arrays.toString(liquidsIn)));
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
	public boolean remove(CTRecipe recipe) {
		if(recipe == null) {
			MineTweakerAPI.logError("NULL recipe was tried to be removed!!!");
			return false;
		}
		return RemoveRecipeAction.removeRecipeAction(recipe.backingRecipe,backingRecipeMap);
	}

	@ZenMethod
	@Optional.Method(
			modid = "MineTweaker3"
	)
	public boolean add(CTRecipe recipe) {
		MineTweakerAPI.logCommand("adding " + recipe + " to" + this);
		return AddRecipeAction.addRecipe(recipe.backingRecipe,backingRecipeMap);
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
		return format(backingRecipeMap);
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
