package mods.bio.gttweaker.api.recipe;

import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

import java.util.List;

@ZenClass("mods.gregtech.recipe.IRecipe")
public interface IRecipe {
	@ZenGetter
	int duration();

	@ZenGetter
	int EUt();

	@ZenGetter
	int meta();

	@ZenGetter("outputs")
	List<IItemStack> getOutputs();

	@ZenGetter("inputs")
	List<IItemStack> getInputs();

	@ZenGetter("fluidOutputs")
	List<ILiquidStack> getFluidOutputs();

	@ZenGetter("fluidInputs")
	List<ILiquidStack> getFluidInputs();

	boolean remove(IRecipeMap recipeMap);

	boolean add(IRecipeMap recipeMap);
}
