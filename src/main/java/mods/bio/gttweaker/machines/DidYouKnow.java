package mods.bio.gttweaker.machines;

import gregapi.data.RM;
import gregapi.util.ST;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import mods.bio.gttweaker.AddMultipleRecipeAction;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

import static gregapi.data.CS.ZL_FS;
import static gregapi.data.CS.ZL_LONG;

@ZenClass("mods.gregtech.DidYouKnow")
@ModOnly("gregtech")
public class DidYouKnow {
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input1, IIngredient input2) {
		addRecipe(new IItemStack[]{output}, new IIngredient[]{input1, input2});
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input1){
		addRecipe(output,input1);
	}

	@ZenMethod
	public static void addRecipe(IItemStack[] output, IIngredient[] input1){
		MineTweakerAPI.apply(
		new AddMultipleRecipeAction(
				"Adding Fake Recipe " + Arrays.toString(output),
				input1,
				output
				//, durationTicks,
				//euPerTick
		) {

			@Override
			protected void applySingleRecipe(ArgIterator i) {
				RM.DidYouKnow.addFakeRecipe(true, i.nextItemArr(),i.nextItemArr(),null,ZL_LONG,ZL_FS,ZL_FS,0,0,0);
				//.add(i.nextItem(), i.nextItem(), i.nextItem(), i.nextInt(), i.nextInt());
			}
		});
	}
}
