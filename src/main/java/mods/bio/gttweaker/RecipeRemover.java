package mods.bio.gttweaker;

import gregapi.recipes.Recipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@ZenClass("mods.gregtech.RecipeRemover")
@ModOnly("gregtech")
public class RecipeRemover {

	public static void remove(String map, IIngredient[] inputs){
		remove(map,inputs,null);
	}

	public static void remove(String map, ILiquidStack[] inputs){
		remove(map,null,inputs);
	}

	@ZenMethod
	public static void remove(String map, IIngredient[] inputs, IIngredient[] fluidInputs) {
		Recipe.RecipeMap recipeMap = Recipe.RecipeMap.RECIPE_MAPS.get(map);
		if (recipeMap == null) {
			MineTweakerAPI.logError("Could not find recipe map named \"" + map + "\"");
			return;
		}
		Recipe recipe = recipeMap.findRecipe(
				null,null,
				false,
				//true,
				Long.MAX_VALUE,null,
				Arrays.stream(fluidInputs)
						.map(GTTweaker::getFluidStackOrNull)
						.filter(Objects::nonNull)
						.toArray(FluidStack[]::new),
				Arrays.stream(inputs)
						.map(GTTweaker::getItemStackOrNull)
						.filter(Objects::nonNull)
						.toArray(ItemStack[]::new));
		if (recipe == null) {
			MineTweakerAPI.logWarning("Could not find recipe to remove!");
			return;
		}
		MineTweakerAPI.apply(new RecipeRemoveAction(recipe, recipeMap));
	}

	public static class RecipeRemoveAction implements IUndoableAction {

		Recipe recipe;
		Recipe.RecipeMap map;

		public RecipeRemoveAction(Recipe recipe, Recipe.RecipeMap map) {
			this.recipe = recipe;
			this.map = map;
		}

		@Override
		public void apply() {
			map.mRecipeList.remove(recipe);
			map.mRecipeItemMap.entrySet()
					.stream()
					.filter(
							e -> e.getValue()
									.removeIf(r -> r == recipe)
									&& e.getValue()
									.size() == 0)
					.map(Map.Entry::getKey)
					.collect(Collectors.toSet())
					.forEach(k -> map.mRecipeItemMap.remove(k));
			map.mRecipeFluidMap.entrySet()
					.stream()
					.filter(
							e -> e.getValue()
									.removeIf(r -> r == recipe)
									&& e.getValue()
									.size() == 0)
					.map(Map.Entry::getKey)
					.collect(Collectors.toSet())
					.forEach(k -> map.mRecipeFluidMap.remove(k));
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			map.add(recipe);
		}

		@Override
		public String describe() {
			return "Remove recipe from GT Machine";
		}

		@Override
		public String describeUndo() {
			return "Re-add recipe to GT Machine";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
