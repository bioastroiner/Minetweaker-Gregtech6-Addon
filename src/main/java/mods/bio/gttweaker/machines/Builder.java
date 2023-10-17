package mods.bio.gttweaker.machines;

import gregapi.recipes.Recipe;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.*;

@ZenClass("mods.gregtech.RecipeBuilder")
public class Builder {
	IIngredient[] inputs;
	IIngredient[] outputs;
	ILiquidStack[] inputsF;
	ILiquidStack[] outputsF;
	int eu = 0;
	int time = 0;

	public Builder inputs(ILiquidStack... fluidsIn) {
		inputsF = fluidsIn;
		return this;
	}

	public Builder outputs(ILiquidStack... fluidsIn) {
		outputsF = fluidsIn;
		return this;
	}

	public Builder inputs(IIngredient... fluidsIn) {
		outputs = fluidsIn;
		return this;
	}

	public Builder outputs(IIngredient... fluidsIn) {
		inputs = fluidsIn;
		return this;
	}

	public Builder eu(int eu) {
		this.eu = eu;
		return this;
	}

	public Builder time(int t) {
		time = t;
		return this;
	}

	public Recipe build() {
		Map<IIngredient,List<ItemStack>> map = new HashMap<>();
		for (var I:inputs) {
			map.put(I,getItemStacks(I));
		}
		for(var O:outputs){
			map.put(O,getItemStacks(O));
		}
		//IItemStack[] inputS = getItemStacks(inputs);
		IItemStack[] outputS;
//		IIngredient[] inputI;
//		IIngredient[] outputI;
		return null;
	}

	private List<ItemStack> getItemStacks(minetweaker.api.item.IIngredient ingredientArg) {
		List<IItemStack> items = ingredientArg.getItems();
		if (items == null) {
			//throw new AnyIngredientException();
			throw new IllegalArgumentException();
		}
		if (items.size() == 0) {
			//throw new EmptyIngredientException(ingredientArg);
			throw new IllegalArgumentException();
		}
		List<ItemStack> itemStackList = Arrays.asList(MineTweakerMC.getItemStacks(items));
		int amount = ingredientArg.getAmount();
		if (amount < 0) {
			throw new RuntimeException("Negative amount for ingredient " + ingredientArg);
		}
		for (ItemStack stack : itemStackList) {
			if (amount > stack.getMaxStackSize()) {
				//throw new OutOfStackSizeException(ingredientArg, amount);
				throw new IllegalArgumentException();
			}
			stack.stackSize = amount;
		}
		return itemStackList;
	}
}
