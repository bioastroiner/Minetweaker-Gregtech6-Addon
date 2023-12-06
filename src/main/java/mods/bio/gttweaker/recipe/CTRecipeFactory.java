package mods.bio.gttweaker.recipe;

import gregapi.recipes.Recipe;
import gregapi.util.ST;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import mods.bio.gttweaker.oredict.CTIOreDictExpansion;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.ImmutablePair;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static gregapi.data.CS.*;

@ZenClass("mods.gregtech.recipe.RecipeFactory")
public class CTRecipeFactory {
	private int energy,duration,circuit;

	public static List<Map.Entry<Recipe, Recipe.RecipeMap>> ADDED_RECIPES = new ArrayList<>();

	private List<ItemStack> inputs,outputs;
	private List<FluidStack> inputsFluid,outputsFluid;

	private List<Integer> chanced;
	private Recipe.RecipeMap recipeMap;
	private int special_value = 0;

	public CTRecipeFactory(Recipe.RecipeMap recipeMap){
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
		inputs.add(ST.size(0,MineTweakerMC.getItemStack(stack)));
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
		chanced.set(outputs.size(),chance);
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
	public CTRecipeFactory input(IIngredient ingredient){
		if(ingredient instanceof IItemStack){
			return input((IItemStack) ingredient);
		} else if (ingredient instanceof IOreDictEntry){
			return input(CTIOreDictExpansion.unify((IOreDictEntry) ingredient));
		} else if(ingredient instanceof ILiquidStack){
			return fluidInput((ILiquidStack) ingredient);
		}
		MineTweakerAPI.logError(ingredient + " is not a valid Ingredient!");
		return this;
	}

	public CTRecipeFactory inputs(IIngredient... ingredients){
		if(ingredients == null || ingredients.length < 1)
			MineTweakerAPI.logError(Arrays.toString(ingredients) + " is invalid! please provide more than 0 arguments");
		else for (IIngredient ingredient:ingredients) {
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
		built = new Recipe(T,T,inputs.toArray(new ItemStack[0]),outputs.toArray(new ItemStack[0]),null,chance,inputsFluid.toArray(new FluidStack[0]),outputsFluid.toArray(new FluidStack[0]),duration,energy,special_value);
		return new CTRecipe(built);
	}

	private boolean checkIO() {
		if(inputs.size() > recipeMap.mInputItemsCount){
			MineTweakerAPI.logError(
					"INPUT ITEMS COUNT MUST BE =< THAN {" +  recipeMap.mInputItemsCount + "}"
			);
			return true;
		}
		if(outputs.size() > recipeMap.mOutputItemsCount){
			MineTweakerAPI.logError(
					"OUTPUT ITEMS COUNT MUST BE =< THAN {" +  recipeMap.mOutputItemsCount + "}"
			);
			return true;
		}
		if(inputs.size() < recipeMap.mMinimalInputItems){
			MineTweakerAPI.logError(
					"INPUT ITEMS COUNT MUST BE > THAN {" +  recipeMap.mMinimalInputItems + "}"
			);
			return true;
		}
		if(recipeMap.mNeedsOutputs && (outputs.isEmpty() && outputsFluid.isEmpty())){
			MineTweakerAPI.logError("OUTPUT ITEMS MUST BE > 0");
			return true;
		}

		if(inputsFluid.size() > recipeMap.mInputFluidCount){
			MineTweakerAPI.logError(
					"INPUT FLUID COUNT MUST BE =< THAN {" +  recipeMap.mInputItemsCount + "}"
			);
			return true;
		}
		if(outputsFluid.size() > recipeMap.mOutputFluidCount){
			MineTweakerAPI.logError(
					"OUTPUT FLUID COUNT MUST BE =< THAN {" +  recipeMap.mOutputItemsCount + "}"
			);
			return true;
		}
		if(inputsFluid.size() < recipeMap.mMinimalInputFluids){
			MineTweakerAPI.logError(
					"INPUT FLUID COUNT MUST BE > THAN {" +  recipeMap.mMinimalInputItems + "}"
			);
			return true;
		}
		if(recipeMap.mNeedsOutputs && (outputs.isEmpty() && outputsFluid.isEmpty())){
			MineTweakerAPI.logError("OUTPUT (FLUID+ITEM) MUST BE > 0");
			return true;
		}
		return false;
	}

	@ZenMethod
	public void buildAndRegister() {
		CTRecipe res = build();
		if(res != null) {
			Recipe aRecipe =  this.recipeMap.addRecipe(res.backingRecipe);
			if(aRecipe==null){
				// if the recipe we adding already exists then just enable and deHide the existing one!
				MineTweakerAPI.logCommand(res + " is DUPLICATE!");
				Recipe altr = recipeMap.findRecipeInternal(null, null, F, F, Long.MAX_VALUE, null, res.backingRecipe.mFluidInputs, res.backingRecipe.mInputs);
				altr.mEnabled = true;
				altr.mHidden = false;
				ADDED_RECIPES.add(new ImmutablePair<>(altr,recipeMap));
			} else {
				ADDED_RECIPES.add(new ImmutablePair<>(res.backingRecipe,recipeMap));
				MineTweakerAPI.logInfo("Added Recipe for " + recipeMap.toString() + "\n" + res.toString());
			}
		}
		else MineTweakerAPI.logError("COULD NOT CREATE RECIPE FOR \n" + recipeMap.toString());
	}
}
