package mods.bio.gttweaker.gregtech.recipe;

import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import mods.bio.gttweaker.api.recipe.IRecipe;
import mods.bio.gttweaker.api.recipe.IRecipeFactory;
import mods.bio.gttweaker.api.recipe.IRecipeMap;
import mods.bio.gttweaker.core.GregTweakerAPI;
import mods.bio.gttweaker.gregtech.recipe.actions.AddRecipeAction;
import mods.bio.gttweaker.gregtech.recipe.actions.RemoveRecipeAction;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static gregapi.data.CS.T;

/**
 * Wraper for GT RecipeMap since that dose not have MT annotations
 */
public class CTRecipeMap implements IRecipeMap {
	public final Recipe.RecipeMap backingRecipeMap;
	public final String nameShort,nameInternal;

	public static String format(Recipe.RecipeMap recipeMap){
		return String.format("<recipemap:%s>",recipeMap.mNameInternal);
	}
	public CTRecipeMap(Recipe.RecipeMap mapIn){
		backingRecipeMap = mapIn;
		nameInternal = mapIn.mNameInternal;
		nameShort = GregTweakerAPI.FORMAT_RECIPE_MAP(mapIn);
	}
	@Override
	public String getNameShort(){
		return nameShort;
	}

	@Override
	public String getNameInternal(){
		return nameInternal;
	}

	@Override
	public List<IRecipe> getRecipesCT() {
		return backingRecipeMap.mRecipeList.stream().map(CTRecipe::new).collect(Collectors.toList());
	}

	@Override
	public CTRecipe findRecipeCT(IItemStack[] itemsIn, ILiquidStack[] liquidsIn) {
		Recipe recipe = backingRecipeMap.findRecipe(null,null,T,Long.MAX_VALUE,null,MineTweakerMC.getLiquidStacks(liquidsIn),MineTweakerMC.getItemStacks(itemsIn));
		if (recipe == null) MineTweakerAPI.logWarning(String.format("No Recipe with \nItems: %s - \nFluids: %s Was Found!", Arrays.toString(itemsIn), Arrays.toString(liquidsIn)));
		return recipe == null ? null : new CTRecipe(recipe);
	}

	@Override
	public boolean removeRecipeCT(IItemStack[] itemsIn) {
		return removeRecipeCT(itemsIn, null);
	}

	@Override
	public boolean removeRecipeCT(ILiquidStack[] liquidsIn) {
		return removeRecipeCT(null, liquidsIn);
	}

	@Override
	public boolean removeRecipeCT(IItemStack[] itemsIn, ILiquidStack[] liquidsIn){
		return remove(findRecipeCT(itemsIn,liquidsIn));
	}

	/**
	 * Remover dose not remove on Reload!!! but it hides and disables the recipes so do not worry.
	 */
	@Override
	public boolean remove(IRecipe recipe) {
		if(recipe == null) {
			MineTweakerAPI.logError("NULL recipe was tried to be removed!!!");
			return false;
		}
		return RemoveRecipeAction.removeRecipeAction(((CTRecipe) recipe).backingRecipe,backingRecipeMap);
	}

	@Override
	public boolean add(IRecipe recipe) {
		MineTweakerAPI.logCommand("adding " + recipe + " to" + this);
		return AddRecipeAction.addRecipe(((CTRecipe) recipe).backingRecipe,backingRecipeMap);
	}

	@Override
	public IRecipeFactory factory() {
		return new CTRecipeFactory(this.backingRecipeMap); // TODO buggy cause CNF EXC
	}


	@Override
	public int getMinInputs() {
		return backingRecipeMap.mMinimalInputItems;
	}

	@Override
	public int getMaxInputs() {
		return backingRecipeMap.mInputItemsCount;
	}

	@Override
	public int getMinOutputs() {
		//return backingRecipeMap.mOutputItemsCount;
		return 0;
	}

	@Override
	public int getMaxOutputs() {
		return backingRecipeMap.mOutputItemsCount;
	}

	@Override
	public int getMinFluidInputs() {
		return backingRecipeMap.mMinimalInputFluids;
	}

	@Override
	public int getMaxFluidInputs() {
		return backingRecipeMap.mInputFluidCount;
	}

	@Override
	public int getMinFluidOutputs() {
		return 0;
	}

	@Override
	public int getMaxFluidOutputs() {
		return backingRecipeMap.mOutputFluidCount;
	}

	@Override
	public void registerRecipe(IRecipe recipe) {
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
