package mods.bio.gttweaker.gregtech.recipe;

import gregapi.recipes.Recipe;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mc1710.liquid.MCLiquidStack;
import mods.bio.gttweaker.api.recipe.IRecipe;
import mods.bio.gttweaker.api.recipe.IRecipeMap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CTRecipe implements IRecipe {
	public final Recipe backingRecipe;

	public static String format(Recipe recipe) {
		return String.format("Recipe(EUt=%d, duration=%d, enabled=%s)\nin=%s\nout=%s\nfluidIn=%s\nfluidOut=%s\nspecial_value=%s", recipe.mEUt, recipe.mDuration, recipe.mEnabled, recipe.mInputs.toString(), recipe.mOutputs.toString(), recipe.mFluidInputs.toString(), recipe.getFluidOutputs().toString(), recipe.mSpecialValue);
	}

	public CTRecipe(Recipe recipe){
		this.backingRecipe = recipe;
	}

	@Override

	public int duration() {
		return (int) this.backingRecipe.mDuration;
	}

	@Override

	public int EUt() {
		return (int) this.backingRecipe.mEUt;
	}

	@Override

	public int meta() {
		return (int) this.backingRecipe.mSpecialValue;
	}

	@Override
	public List<IItemStack> getOutputs() {
		return Arrays.stream(this.backingRecipe.getOutputs()).map(MineTweakerMC::getIItemStack).collect(Collectors.toList());
	}

	@Override
	public List<IItemStack> getInputs() {
		return Arrays.stream(this.backingRecipe.mInputs).map(MineTweakerMC::getIItemStack).collect(Collectors.toList());
	}

	@Override
	public List<ILiquidStack> getFluidOutputs() {
		return Arrays.stream(this.backingRecipe.getFluidOutputs()).map(MCLiquidStack::new).collect(Collectors.toList());
	}

	@Override
	public List<ILiquidStack> getFluidInputs() {
		return Arrays.stream(this.backingRecipe.mFluidInputs).map(MCLiquidStack::new).collect(Collectors.toList());
	}

	@Override
	public boolean remove(IRecipeMap recipeMap){
		return recipeMap.remove(this);
	}
	@Override
	public boolean add(IRecipeMap recipeMap){
		return recipeMap.add(this);
	}


	public String toString() {
		return format(backingRecipe);
	}
}
