package mods.bio.gttweaker.api.recipe;

import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import mods.bio.gttweaker.api.oredict.IMaterial;
import mods.bio.gttweaker.api.oredict.IPrefix;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.recipe.IRecipeFactory")
public interface IRecipeFactory {
	@ZenMethod
	IRecipeFactory EUt(int energy);

	@ZenMethod
	IRecipeFactory duration(int ticks);

	@ZenMethod
	IRecipeFactory nonConsumable(IItemStack stack);

	@ZenMethod
	IRecipeFactory circuit(int config);

	@ZenMethod
	IRecipeFactory specialValue(int config);

	@ZenMethod
	IRecipeFactory input(IPrefix aPrefix, IMaterial aMaterial);

	@ZenMethod
	IRecipeFactory input(IPrefix aPrefix, IMaterial aMaterial, int aAmount);

	@ZenMethod
	IRecipeFactory output(IPrefix aPrefix, IMaterial aMaterial);

	@ZenMethod
	IRecipeFactory output(IPrefix aPrefix, IMaterial aMaterial, int aAmount);

	@ZenMethod
	IRecipeFactory input(IItemStack input);

	@ZenMethod
	IRecipeFactory inputs(IItemStack... inputs);

	@ZenMethod("inputFluid")
	IRecipeFactory fluidInput(ILiquidStack input);

	@ZenMethod("inputFluids")
	IRecipeFactory fluidInputs(ILiquidStack... inputs);

	@ZenMethod
	IRecipeFactory output(IItemStack output);

	@ZenMethod
	IRecipeFactory outputs(IItemStack... outputs);

	@ZenMethod
	IRecipeFactory chancedOutput(IItemStack output, int chance);

	@ZenMethod("outputFluid")
	IRecipeFactory fluidOutput(ILiquidStack output);

	@ZenMethod("outputFluids")
	IRecipeFactory fluidOutputs(ILiquidStack... fluids);

	@ZenMethod
	IRecipeFactory output(IIngredient ingredient);

	@ZenMethod
	IRecipeFactory input(IIngredient ingredient);

	@ZenMethod
	IRecipeFactory inputs(IIngredient... ingredients);

	@ZenMethod
	IRecipe build();

	@ZenMethod
	void buildAndRegister();
}
