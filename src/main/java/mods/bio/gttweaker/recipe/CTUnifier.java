package mods.bio.gttweaker.recipe;

import gregapi.data.CS;
import gregapi.oredict.OreDictManager;
import gregapi.recipes.Recipe;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import static gregapi.data.CS.T;

@ZenClass("mods.gregtech.recipe.RecipeMaps")
public class CTUnifier {
	@ZenMethod
	public static IItemStack unifyItem(IOreDictEntry ore){
		return MineTweakerMC.getIItemStack(OreDictManager.INSTANCE.getStack(ore.getName(),ore.getAmount()));
	}

	@ZenMethod
	public static IItemStack unifyItem(IItemStack stack){
		return MineTweakerMC.getIItemStack(OreDictManager.INSTANCE.getStack(T,MineTweakerMC.getItemStack(stack)));
	}

}
