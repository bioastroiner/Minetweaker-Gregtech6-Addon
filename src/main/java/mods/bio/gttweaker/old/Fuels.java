package mods.bio.gttweaker.old;

import gregapi.data.FM;
import gregapi.recipes.Recipe;
import gregapi.recipes.maps.RecipeMapFuel;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.Fuels")
@ModOnly("gregtech")
public class Fuels {

	@ZenMethod
	public static void addFluidBed(IItemStack output, IIngredient input, int euPerMillibucket){
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 0));
	}

	@ZenMethod
	public static void addBurn(IItemStack output, IIngredient input, int euPerMillibucket){
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 1));
	}

	@ZenMethod
	public static void addGas(IItemStack output, IIngredient input, int euPerMillibucket){
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 2));
	}

	@ZenMethod
	public static void addHot(IItemStack output, IIngredient input, int euPerMillibucket){
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 3));
	}

	@ZenMethod
	public static void addPlasma(IItemStack output, IIngredient input, int euPerMillibucket){
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 4));
	}

	@ZenMethod
	public static void addEngine(IItemStack output, IIngredient input, int euPerMillibucket){
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 5));
	}

	@ZenMethod
	public static void addTurbine(IItemStack output, IIngredient input, int euPerMillibucket){
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 6));
	}

	@ZenMethod
	public static void addMagic(IItemStack output, IIngredient input, int euPerMillibucket){
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 7));
	}


	private static class AddRecipeAction extends AddMultipleRecipeAction {

		private static final String[] GENERATORS = { "fluidbed","burn","gas","hot","plasma","engine","turbine","magic" };
		private static final Recipe.RecipeMap[] GENERATORS_FM = {FM.FluidBed,FM.Burn,FM.Gas,FM.Hot,FM.Plasma,FM.Engine,FM.Turbine,FM.Magic };

		public AddRecipeAction(IItemStack output, IIngredient input, int euPerMillibucket, int type) {
			super("Adding " + GENERATORS[type] + " fuel " + input, type, input, output, euPerMillibucket);
		}

		@Override
		protected void applySingleRecipe(ArgIterator i) {
			RecipeMapFuel recipeMapFuel = (RecipeMapFuel) GENERATORS_FM[i.nextInt()];
			recipeMapFuel.addFuel(i.nextItem(),i.nextItem(),i.nextInt());
		}
	}
}
