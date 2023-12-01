package mods.bio.gttweaker.recipe;

import gregapi.data.IL;
import gregapi.recipes.Recipe;
import gregapi.util.ST;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static gregapi.data.CS.T;

@ZenClass("mods.gregtech.recipe.RecipeFactory")
public class CTRecipeFactory {
	private int energy,duration,circuit;

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
		this.energy = energy;
		return this;
	}

	@ZenMethod
	public CTRecipeFactory duration(int ticks) {
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
		inputs.add(MineTweakerMC.getItemStack(input));
		return this;
	}

	@ZenMethod
	public CTRecipeFactory inputs(IItemStack... inputs) {
		Arrays.stream(inputs).forEach(this::input);
		return this;
	}

	@ZenMethod
	public CTRecipeFactory fluidInput(ILiquidStack fluid) {
		inputsFluid.add(MineTweakerMC.getLiquidStack(fluid));
		return this;
	}

	@ZenMethod
	public CTRecipeFactory fluidInputs(ILiquidStack... fluids) {
		Arrays.stream(fluids).forEach(this::fluidInput);
		return this;
	}

	@ZenMethod
	public CTRecipeFactory output(IItemStack output) {
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

	@ZenMethod
	public CTRecipeFactory fluidOutput(ILiquidStack fluid) {
		outputsFluid.add(MineTweakerMC.getLiquidStack(fluid));
		return this;
	}

	@ZenMethod
	public CTRecipeFactory fluidOutputs(ILiquidStack... fluids) {
		Arrays.stream(fluids).forEach(this::fluidOutput);
		return this;
	}

	@ZenMethod
	public CTRecipe build() {
		Recipe built;

		if(inputs.size() > recipeMap.mInputItemsCount){
			MineTweakerAPI.logError(
					"INPUT ITEMS COUNT MUST BE =< THAN {" +  recipeMap.mInputItemsCount + "}"
			);
			return null;
		}
		if(outputs.size() > recipeMap.mOutputItemsCount){
			MineTweakerAPI.logError(
					"OUTPUT ITEMS COUNT MUST BE =< THAN {" +  recipeMap.mOutputItemsCount + "}"
			);
			return null;
		}
		if(inputs.size() < recipeMap.mMinimalInputItems){
			MineTweakerAPI.logError(
					"INPUT ITEMS COUNT MUST BE > THAN {" +  recipeMap.mMinimalInputItems + "}"
			);
			return null;
		}
		if(recipeMap.mNeedsOutputs && (outputs.isEmpty() && outputsFluid.isEmpty())){
			MineTweakerAPI.logError("OUTPUT ITEMS MUST BE > 0");
			return null;
		}

		if(inputsFluid.size() > recipeMap.mInputFluidCount){
			MineTweakerAPI.logError(
					"INPUT FLUID COUNT MUST BE =< THAN {" +  recipeMap.mInputItemsCount + "}"
			);
			return null;
		}
		if(outputsFluid.size() > recipeMap.mOutputFluidCount){
			MineTweakerAPI.logError(
					"OUTPUT FLUID COUNT MUST BE =< THAN {" +  recipeMap.mOutputItemsCount + "}"
			);
			return null;
		}
		if(inputsFluid.size() < recipeMap.mMinimalInputFluids){
			MineTweakerAPI.logError(
					"INPUT FLUID COUNT MUST BE > THAN {" +  recipeMap.mMinimalInputItems + "}"
			);
			return null;
		}
		if(recipeMap.mNeedsOutputs && (outputs.isEmpty() && outputsFluid.isEmpty())){
			MineTweakerAPI.logError("OUTPUT (FLUID+ITEM) MUST BE > 0");
			return null;
		}

		long[] chance = new long[chanced.size()];
		for (int i = 0; i < chanced.size(); i++) {
			chance[i] = chanced.get(i);
		}

		built = new Recipe(T,T,inputs.toArray(ItemStack[]::new),outputs.toArray(ItemStack[]::new),null,chance,inputsFluid.toArray(FluidStack[]::new),outputsFluid.toArray(FluidStack[]::new),duration,energy,special_value);
		return new CTRecipe(built);
	}

	@ZenMethod
	public void buildAndRegister() {
		CTRecipe res = build();
		if(res != null) {
			this.recipeMap.addRecipe(res.backingRecipe);
			MineTweakerAPI.logInfo("Added Recipe for " + recipeMap.toString() + "\n" + res.toString());
		}
		else MineTweakerAPI.logError("COULD NOT CREATE RECIPE FOR \n" + recipeMap.toString());
	}
}
