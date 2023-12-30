package mods.bio.gttweaker.mods.gregtech.recipe;

import gregapi.recipes.Recipe;
import gregapi.util.ST;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterial;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IPrefix;
import mods.bio.gttweaker.api.mods.gregtech.recipe.IRecipe;
import mods.bio.gttweaker.api.mods.gregtech.recipe.IRecipeFactory;
import mods.bio.gttweaker.mods.minetweaker.CTIOreDictExpansion;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gregapi.data.CS.T;
import static mods.bio.gttweaker.core.RecipeHelper.checkRecipeIO;

public class CTRecipeFactory implements mods.bio.gttweaker.api.mods.gregtech.recipe.IRecipeFactory {
	private final List<ItemStack> inputs, outputs;
	private final List<FluidStack> inputsFluid, outputsFluid;
	private final List<Integer> chanced;
	private final Recipe.RecipeMap recipeMap;
	private int energy, duration;
	private int special_value = 0;

	public CTRecipeFactory(Recipe.RecipeMap recipeMap) {
		this.recipeMap = recipeMap;
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();
		inputsFluid = new ArrayList<>();
		outputsFluid = new ArrayList<>();
		chanced = new ArrayList<>();
	}

	@Override
	public IRecipeFactory EUt(int energy) {
		MineTweakerAPI.logCommand("setting EUt: " + energy + "... ");
		this.energy = energy;
		return this;
	}

	@Override
	public IRecipeFactory duration(int ticks) {
		MineTweakerAPI.logCommand("setting duration: " + ticks + "... ");
		duration = ticks;
		return this;
	}

	@Override
	public IRecipeFactory nonConsumable(IItemStack stack) {
		inputs.add(ST.size(0, MineTweakerMC.getItemStack(stack)));
		return this;
	}

	@Override
	public IRecipeFactory circuit(int config) {
		return input(MineTweakerMC.getIItemStack(ST.tag(config)));
	}

	@Override
	public IRecipeFactory specialValue(int config) {
		special_value = config;
		return this;
	}

	@Override
	public IRecipeFactory input(IPrefix aPrefix, IMaterial aMaterial) {
		return input(aPrefix, aMaterial, 1);
	}

	@Override
	public IRecipeFactory input(IPrefix aPrefix, IMaterial aMaterial, int aAmount) {
		return input(aPrefix.withMaterial(aMaterial).amount(aAmount));
	}

	@Override

	public IRecipeFactory output(IPrefix aPrefix, IMaterial aMaterial) {
		return output(aPrefix, aMaterial, 1);
	}

	@Override
	public IRecipeFactory output(IPrefix aPrefix, IMaterial aMaterial, int aAmount) {
		return output(aPrefix.withMaterial(aMaterial).amount(aAmount));
	}

	@Override

	public IRecipeFactory input(IItemStack input) {
		MineTweakerAPI.logCommand("adding Input " + input + "... ");
		inputs.add(MineTweakerMC.getItemStack(input));
		return this;
	}

	@Override
	public IRecipeFactory inputs(IItemStack... inputs) {
		Arrays.stream(inputs).forEach(this::input);
		return this;
	}

	@Override
	public IRecipeFactory fluidInput(ILiquidStack input) {
		MineTweakerAPI.logCommand("adding Input " + input + "... ");
		inputsFluid.add(MineTweakerMC.getLiquidStack(input));
		return this;
	}

	@Override

	public IRecipeFactory fluidInputs(ILiquidStack... inputs) {
		Arrays.stream(inputs).forEach(this::fluidInput);
		return this;
	}

	@Override
	public IRecipeFactory output(IItemStack output) {
		MineTweakerAPI.logCommand("adding Output " + output + "... ");
		outputs.add(MineTweakerMC.getItemStack(output));
		return this;
	}

	@Override
	public IRecipeFactory outputs(IItemStack... outputs) {
		Arrays.stream(outputs).forEach(this::output);
		return this;
	}

	@Override
	public IRecipeFactory chancedOutput(IItemStack output, int chance) {
		output(output);
		chanced.set(outputs.size(), chance);
		return this;
	}

	@Override
	public IRecipeFactory fluidOutput(ILiquidStack output) {
		MineTweakerAPI.logCommand("adding Output " + output + "... ");
		outputsFluid.add(MineTweakerMC.getLiquidStack(output));
		return this;
	}

	@Override

	public IRecipeFactory fluidOutputs(ILiquidStack... fluids) {
		Arrays.stream(fluids).forEach(this::fluidOutput);
		return this;
	}

	@Override
	public IRecipeFactory output(IIngredient ingredient) {
		if (ingredient instanceof IItemStack) {
			return output((IItemStack) ingredient);
		} else if (ingredient instanceof IOreDictEntry) {
			return output(CTIOreDictExpansion.unified((IOreDictEntry) ingredient));
		} else if (ingredient instanceof ILiquidStack) {
			return fluidOutput((ILiquidStack) ingredient);
		}
		MineTweakerAPI.logError(ingredient + " is not a valid Ingredient!");
		return this;
	}

	@Override
	public IRecipeFactory input(IIngredient ingredient) {
		if (ingredient instanceof IItemStack) {
			return input((IItemStack) ingredient);
		} else if (ingredient instanceof IOreDictEntry) {
			return input(CTIOreDictExpansion.unified((IOreDictEntry) ingredient));
		} else if (ingredient instanceof ILiquidStack) {
			return fluidInput((ILiquidStack) ingredient);
		}
		MineTweakerAPI.logError(ingredient + " is not a valid Ingredient!");
		return this;
	}

	@Override
	public IRecipeFactory inputs(IIngredient... ingredients) {
		if (ingredients == null || ingredients.length < 1)
			MineTweakerAPI.logError(Arrays.toString(ingredients) + " is invalid! please provide more than 0 arguments");
		else for (IIngredient ingredient : ingredients) {
			input(ingredient);
		}
		return this;
	}

	@Override
	public IRecipe build() {
		Recipe built;
		if (checkIO()) return null;
		long[] chance = new long[chanced.size()];
		for (int i = 0; i < chanced.size(); i++) {
			chance[i] = chanced.get(i);
		}
		built = new Recipe(T, T, inputs.toArray(new ItemStack[0]), outputs.toArray(new ItemStack[0]), null, chance, inputsFluid.toArray(new FluidStack[0]), outputsFluid.toArray(new FluidStack[0]), duration, energy, special_value);
		return new CTRecipe(built);
	}

	private boolean checkIO() {
		return checkRecipeIO(recipeMap, inputs, outputs, inputsFluid, outputsFluid);
	}

	@Override

	public void buildAndRegister() {
		mods.bio.gttweaker.api.mods.gregtech.recipe.IRecipe res = build();
		if (res != null) {
			res.add(new CTRecipeMap(recipeMap));
		} else MineTweakerAPI.logError("COULD NOT CREATE RECIPE FOR \n" + recipeMap.toString());
	}
}
