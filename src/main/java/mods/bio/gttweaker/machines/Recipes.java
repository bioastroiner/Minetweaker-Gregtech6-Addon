package mods.bio.gttweaker.machines;

import gregapi.data.FL;
import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import mods.bio.gttweaker.AddMultipleRecipeAction;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

import static gregapi.data.CS.*;

@ZenClass("mods.gregtech.Recipes")
@ModOnly("gregtech")
public class Recipes {
	@ZenMethod
	public static void addRecipe(String map,IIngredient[] output,IIngredient[] input,int duration,int eu){
		addRecipe(map,output,input,ZL_INTEGER,duration,eu);
	}
	@ZenMethod
	public static void addRecipe(String map,IIngredient[] output,IIngredient[] input){
		addRecipe(map,output,input,ZL_INTEGER,0,0);
	}
	@ZenMethod
	public static void addRecipe(String map,IIngredient[] output,IIngredient[] inputs,int[] chances,int duration,int eu){
		final gregapi.recipes.Recipe.RecipeMap recipeMap = gregapi.recipes.Recipe.RecipeMap.RECIPE_MAPS.get(map);
		if (recipeMap == null) {
			MineTweakerAPI.logError("Could not find recipe map named \"" + map + "\"");
			MineTweakerAPI.logError("List of All the maps you can use: " + java.util.Arrays.toString(gregapi.recipes.Recipe.RecipeMap.RECIPE_MAPS.keySet().toArray()));
			return;
		}
		boolean tFailed = false;
		var inputsF = ZL_FS;
		if (recipeMap.mNeedsOutputs && output.length + inputsF.length <= 0) {MineTweakerAPI.logWarning("ERROR: Recipe from" + map+" has no Outputs!"                                          ); tFailed = T;}
		if (inputs         .length < recipeMap.mMinimalInputItems                        ) {MineTweakerAPI.logWarning("ERROR: Recipe from" + map+" has less than the minimal amount of Input ItemStacks!"    ); tFailed = T;}
		if (inputsF    .length < recipeMap.mMinimalInputFluids                       ) {MineTweakerAPI.logWarning("ERROR: Recipe from" + map+" has less than the minimal amount of Input FluidStacks!"   ); tFailed = T;}
		if (inputsF.length + inputs.length < recipeMap.mMinimalInputs       ) {MineTweakerAPI.logWarning("ERROR: Recipe from" + map+" has less than the minimal amount of general Inputs!"      ); tFailed = T;}
		if (inputs         .length > recipeMap.mInputItemsCount                          ) {MineTweakerAPI.logWarning("ERROR: Recipe from" + map+" has more than the maximum amount of Input ItemStacks!"    ); tFailed = T;}
		if (inputsF    .length > recipeMap.mInputFluidCount                          ) {MineTweakerAPI.logWarning("ERROR: Recipe from" + map+" has more than the maximum amount of Input FluidStacks!"   ); tFailed = T;}
		if(tFailed) return;
		MineTweakerAPI.apply(
				new AddMultipleRecipeAction(
						"Adding Recipe " + Arrays.toString(output),
						inputs,output, chances
						, duration,
						eu
				) {

					@Override
					protected void applySingleRecipe(ArgIterator i) {
						Recipe aRecipe = recipeMap.addRecipe(true, i.nextItemArr(),i.nextItemArr(),null,Arrays.stream(i.nextIntArr()).asLongStream().toArray(),ZL_FS,ZL_FS,i.nextInt(),i.nextInt(),0);

					}
				});
	}

}
