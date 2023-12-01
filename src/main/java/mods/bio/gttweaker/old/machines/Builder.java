package mods.bio.gttweaker.old.machines;

import gregapi.data.RM;
import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import mods.bio.gttweaker.old.AddMultipleRecipeAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.List;

import static gregapi.data.CS.T;
import static gregapi.data.CS.ZL_LONG;

@ZenClass("mods.gregtech.RecipeBuilder")
public class Builder {
	Recipe.RecipeMap mRecipe = RM.Crusher;
	IIngredient[] inputs;
	IIngredient[] outputs;
	int euTicks = 0;
	int time = 0;
	String mapNameForLog = "NULL";

	long[] aOutputChances = ZL_LONG;

	private static ItemStack[] arrayOfStackSafe(List<ItemStack> stacks) {
		return stacks.toArray(new ItemStack[0]);
	}

	private static FluidStack[] arrayOfFluidSafe(List<FluidStack> stacks) {
		return stacks.toArray(new FluidStack[0]);
	}

	@ZenMethod("machine")
	public static Builder get(String map) {
		Builder build = new Builder();
		build.mapNameForLog = map;
		map = map.toLowerCase();
		map = map.replace(" ", "");
		if (!map.startsWith("gt.recipe.")) map = "gt.recipe." + map;
		if (Recipe.RecipeMap.RECIPE_MAPS.containsKey(map)) {
			build.mRecipe = Recipe.RecipeMap.RECIPE_MAPS.get(map);
		} else MineTweakerAPI.logError("ERROR: Recipe " + map + " is Invalid");
		return build;
	}

	@ZenMethod("input")
	public Builder input(IIngredient... input) {
		inputs = input;
		return this;
	}

	@ZenMethod("output")
	public Builder output(IIngredient... output) {
		outputs = output;
		return this;
	}

	@ZenMethod("eut")
	public Builder eu(int eu) {
		euTicks = eu;
		return this;
	}

	@ZenMethod("time")
	public Builder time(int time) {
		this.time = time;
		return this;
	}

	@ZenMethod("chance")
	public Builder chance(long[] chance) {
		aOutputChances = chance;
		return this;
	}

	@ZenMethod("build")
	public void build() {
		int inputItems = 0;
		int inputFluids = 0;
		int outputItems = 0;
		int outputFluids = 0;
		for (int i = 0; i < inputs.length; i++) {
			if (inputs[i] instanceof IOreDictEntry || inputs[i] instanceof IItemStack) {
				inputItems++;
			}
			if (inputs[i] instanceof ILiquidStack) {
				inputFluids++;
			}
		}
		for (int i = 0; i < outputs.length; i++) {
			if (inputs[i] instanceof IOreDictEntry || inputs[i] instanceof IItemStack) {
				outputItems++;
			}
			if (inputs[i] instanceof ILiquidStack) {
				outputFluids++;
			}
		}


		Recipe.RecipeMap recipeMap = mRecipe;
		if (recipeMap == null) {
			MineTweakerAPI.logError("Could not find recipe map named \"" + mapNameForLog + "\"");
			MineTweakerAPI.logError("List of All the maps you can use: " + java.util.Arrays.toString(gregapi.recipes.Recipe.RecipeMap.RECIPE_MAPS.keySet().toArray()));
			return;
		}
		boolean tFailed = false;
		if (recipeMap.mNeedsOutputs && outputItems + outputFluids <= 0) {
			MineTweakerAPI.logWarning("ERROR: Recipe from" + mapNameForLog + " has no Outputs!");
			tFailed = T;
		}
		if (inputItems < recipeMap.mMinimalInputItems) {
			MineTweakerAPI.logWarning("ERROR: Recipe from" + mapNameForLog + " has less than the minimal amount of Input ItemStacks!");
			tFailed = T;
		}
		if (inputFluids < recipeMap.mMinimalInputFluids) {
			MineTweakerAPI.logWarning("ERROR: Recipe from" + mapNameForLog + " has less than the minimal amount of Input FluidStacks!");
			tFailed = T;
		}
		if (inputFluids + inputItems < recipeMap.mMinimalInputs) {
			MineTweakerAPI.logWarning("ERROR: Recipe from" + mapNameForLog + " has less than the minimal amount of general Inputs!");
			tFailed = T;
		}
		if (inputItems > recipeMap.mInputItemsCount) {
			MineTweakerAPI.logWarning("ERROR: Recipe from" + mapNameForLog + " has more than the maximum amount of Input ItemStacks!");
			tFailed = T;
		}
		if (inputFluids > recipeMap.mInputFluidCount) {
			MineTweakerAPI.logWarning("ERROR: Recipe from" + mapNameForLog + " has more than the maximum amount of Input FluidStacks!");
			tFailed = T;
		}
		if (tFailed) return;

		MineTweakerAPI.apply(
				new AddMultipleRecipeAction(
						"Adding Recipe " + Arrays.toString(outputs),
						inputs, outputs, aOutputChances
						, time,
						euTicks
				) {

					@Override
					protected void applySingleRecipe(ArgIterator i) {

						mRecipe.addRecipe(true,
								i.nextItemArr(),
								i.nextItemArr(),
								null,
								aOutputChances,
								i.nextFluidArr(),
								i.nextFluidArr(), time, euTicks, 0);
					}
				});


	}
}
