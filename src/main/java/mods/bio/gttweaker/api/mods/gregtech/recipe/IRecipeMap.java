package mods.bio.gttweaker.api.mods.gregtech.recipe;

import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import mods.bio.gttweaker.mods.gregtech.recipe.CTRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.gregtech.recipe.IRecipeMap")
public interface IRecipeMap {
	@ZenMethod("name")
	String getNameShort();

	@ZenMethod("nameInternal")
	String getNameInternal();

	@ZenMethod("getRecipes")
	List<IRecipe> getRecipesCT();

	@ZenMethod("findRecipe")
	CTRecipe findRecipeCT(IItemStack[] itemsIn, ILiquidStack[] liquidsIn);

	@ZenMethod("removeRecipe")
	boolean removeRecipeCT(IItemStack[] itemsIn);

	@ZenMethod("removeRecipe")
	boolean removeRecipeCT(ILiquidStack[] liquidsIn);

	@ZenMethod("removeRecipe")
	boolean removeRecipeCT(IItemStack[] itemsIn, ILiquidStack[] liquidsIn);

	@ZenMethod
	boolean remove(IRecipe recipe);

	@ZenMethod
	boolean add(IRecipe recipe);

	@ZenMethod
	IRecipeFactory factory();

	@ZenGetter("minInputs")
	int getMinInputs();

	@ZenGetter("maxInputs")
	int getMaxInputs();

	@ZenGetter("minOutputs")
	int getMinOutputs();

	@ZenGetter("maxOutputs")
	int getMaxOutputs();

	@ZenGetter("minFluidInputs")
	int getMinFluidInputs();

	@ZenGetter("maxFluidInputs")
	int getMaxFluidInputs();

	@ZenGetter("minFluidOutputs")
	int getMinFluidOutputs();

	@ZenGetter("maxFluidOutputs")
	int getMaxFluidOutputs();

	@ZenMethod("register")
	void registerRecipe(IRecipe recipe);
}
