package mods.bio.gttweaker.mods.gregtech.recipe;

import gregapi.recipes.Recipe;
import gregapi.util.ST;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import mods.bio.gttweaker.mods.gregtech.oredict.CTMaterial;
import mods.bio.gttweaker.mods.gregtech.oredict.CTPrefix;
import mods.bio.gttweaker.mods.minetweaker.CTIOreDictExpansion;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gregapi.data.CS.T;
import static mods.bio.gttweaker.core.RecipeHelper.checkRecipeIO;

@ZenClass("mods.gregtech.recipe.RecipeFactory")
public class CTRecipeFactory {
	private int energy, duration;

	private final List<ItemStack> inputs, outputs;
	private final List<FluidStack> inputsFluid, outputsFluid;

	private final List<Integer> chanced;
	private final Recipe.RecipeMap recipeMap;
	private int special_value = 0;

	public CTRecipeFactory(Recipe.RecipeMap recipeMap) {
		this.recipeMap = recipeMap;
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();
		inputsFluid = new ArrayList<>();
		outputsFluid = new ArrayList<>();
		chanced = new ArrayList<>();
	}

	@ZenMethod
	public CTRecipeFactory EUt(int energy) {
		MineTweakerAPI.logCommand("setting EUt: " + energy + "... ");
		this.energy = energy;
		return this;
	}

	@ZenMethod
	public CTRecipeFactory duration(int ticks) {
		MineTweakerAPI.logCommand("setting duration: " + ticks + "... ");
		duration = ticks;
		return this;
	}

	@ZenMethod
	public CTRecipeFactory nonConsumable(IItemStack stack) {
		inputs.add(ST.size(0, MineTweakerMC.getItemStack(stack)));
		return this;
	}

	@ZenMethod
	public CTRecipeFactory circuit(int config) {
		return input(MineTweakerMC.getIItemStack(ST.tag(config)));
	}

	@ZenMethod
	public CTRecipeFactory specialValue(int config) {
		special_value = config;
		return this;
	}

	@ZenMethod
	public CTRecipeFactory input(CTPrefix aPrefix, CTMaterial aMaterial) {
		return input(aPrefix, aMaterial, 1);
	}

	@ZenMethod
	public CTRecipeFactory input(CTPrefix aPrefix, CTMaterial aMaterial, int aAmount) {
		return input(aPrefix.withMaterial(aMaterial).amount(aAmount));
	}

	@ZenMethod
	public CTRecipeFactory output(CTPrefix aPrefix, CTMaterial aMaterial) {
		return output(aPrefix, aMaterial, 1);
	}

	@ZenMethod
	public CTRecipeFactory output(CTPrefix aPrefix, CTMaterial aMaterial, int aAmount) {
		return output(aPrefix.withMaterial(aMaterial).amount(aAmount));
	}

	@ZenMethod
	public CTRecipeFactory input(IItemStack input) {
		MineTweakerAPI.logCommand("adding Input " + input + "... ");
		inputs.add(MineTweakerMC.getItemStack(input));
		return this;
	}

	@ZenMethod
	public CTRecipeFactory inputs(IItemStack... inputs) {
		Arrays.stream(inputs).forEach(this::input);
		return this;
	}

	@ZenMethod("inputFluid")
	public CTRecipeFactory fluidInput(ILiquidStack input) {
		MineTweakerAPI.logCommand("adding Input " + input + "... ");
		inputsFluid.add(MineTweakerMC.getLiquidStack(input));
		return this;
	}

	@ZenMethod("inputFluids")
	public CTRecipeFactory fluidInputs(ILiquidStack... inputs) {
		Arrays.stream(inputs).forEach(this::fluidInput);
		return this;
	}

	@ZenMethod
	public CTRecipeFactory output(IItemStack output) {
		MineTweakerAPI.logCommand("adding Output " + output + "... ");
		outputs.add(MineTweakerMC.getItemStack(output));
		return this;
	}

	@ZenMethod
	public CTRecipeFactory outputs(IItemStack... outputs) {
		Arrays.stream(outputs).forEach(this::output);
		return this;
	}

	@ZenMethod
	public CTRecipeFactory chancedOutput(IItemStack output, int chance) {
		output(output);
		chanced.set(outputs.size(), chance);
		return this;
	}

	@ZenMethod("outputFluid")
	public CTRecipeFactory fluidOutput(ILiquidStack output) {
		MineTweakerAPI.logCommand("adding Output " + output + "... ");
		outputsFluid.add(MineTweakerMC.getLiquidStack(output));
		return this;
	}

	@ZenMethod("outputFluids")
	public CTRecipeFactory fluidOutputs(ILiquidStack... fluids) {
		Arrays.stream(fluids).forEach(this::fluidOutput);
		return this;
	}

	@ZenMethod
	public CTRecipeFactory output(IIngredient ingredient) {
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

	@ZenMethod
	public CTRecipeFactory input(IIngredient ingredient) {
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

	@ZenMethod
	public CTRecipeFactory inputs(IIngredient... ingredients) {
		if (ingredients == null || ingredients.length < 1)
			MineTweakerAPI.logError(Arrays.toString(ingredients) + " is invalid! please provide more than 0 arguments");
		else for (IIngredient ingredient : ingredients) {
			input(ingredient);
		}
		return this;
	}

	@ZenMethod
	public CTRecipe build() {
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

	@ZenMethod
	public void buildAndRegister() {
		CTRecipe res = build();
		if (res != null) {
			res.add(new CTRecipeMap(recipeMap));
		} else MineTweakerAPI.logError("COULD NOT CREATE RECIPE FOR \n" + recipeMap.toString());
	}
}
