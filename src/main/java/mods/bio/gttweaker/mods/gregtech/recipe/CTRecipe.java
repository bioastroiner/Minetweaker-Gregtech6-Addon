package mods.bio.gttweaker.mods.gregtech.recipe;

import gregapi.recipes.Recipe;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mc1710.liquid.MCLiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ZenClass("mods.gregtech.recipe.Recipe")

public class CTRecipe {
	public final Recipe backingRecipe;
	public CTRecipe(Recipe recipe){
		this.backingRecipe = recipe;
	}

	@ZenGetter
	public int duration() {
		return (int) this.backingRecipe.mDuration;
	}

	@ZenGetter
	public int EUt() {
		return (int) this.backingRecipe.mEUt;
	}

	@ZenGetter
	public int meta() {
		return (int) this.backingRecipe.mSpecialValue;
	}

	@ZenGetter("outputs")
	public List<IItemStack> getOutputs() {
		return Arrays.stream(this.backingRecipe.getOutputs()).map(MineTweakerMC::getIItemStack).collect(Collectors.toList());
	}

	@ZenGetter("inputs")
	public List<IItemStack> getInputs() {
		return Arrays.stream(this.backingRecipe.mInputs).map(MineTweakerMC::getIItemStack).collect(Collectors.toList());
	}

//	@ZenGetter("chancedOutputs")
//	public List<CTChancedOutput> getChancedOutputs() {
//		return (List)this.backingRecipe.getChancedOutputs().stream().map(CTChancedOutput::new).collect(Collectors.toList());
//	}

	@ZenGetter("fluidOutputs")
	public List<ILiquidStack> getFluidOutputs() {
		return Arrays.stream(this.backingRecipe.getFluidOutputs()).map(MCLiquidStack::new).collect(Collectors.toList());
	}

	@ZenGetter("fluidInputs")
	public List<ILiquidStack> getFluidInputs() {
		return Arrays.stream(this.backingRecipe.mFluidInputs).map(MCLiquidStack::new).collect(Collectors.toList());
	}

	public boolean remove(CTRecipeMap recipeMap){
		return recipeMap.remove(this);
	}

	public String toString() {
		return String.format("Recipe(EUt=%d, duration=%d, enabled=%s)\nin=%s\nout=%s\nfluidIn=%s\nfluidOut=%s\nspecial_value=%s", backingRecipe.mEUt, backingRecipe.mDuration, backingRecipe.mEnabled, backingRecipe.mInputs.toString(), backingRecipe.mOutputs.toString(), backingRecipe.mFluidInputs.toString(), backingRecipe.getFluidOutputs().toString(), backingRecipe.mSpecialValue);
	}
}
