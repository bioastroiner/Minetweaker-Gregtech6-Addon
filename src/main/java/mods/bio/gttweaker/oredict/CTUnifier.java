package mods.bio.gttweaker.oredict;

import gregapi.code.ItemStackContainer;
import gregapi.data.RM;
import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictManager;
import gregapi.util.UT;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import mods.bio.gttweaker.recipe.CTRecipeMap;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.HashMap;
import java.util.Map;

import static gregapi.data.CS.*;

@ZenClass("mods.gregtech.oredict.Unifier")
// TODO: Zen Expansion for IOreDict#unify
public class CTUnifier {
	public static final Map<ItemStackContainer, OreDictItemData> REMOVED_DATA = new HashMap<>();

	@ZenGetter
	public static long U() {
		return U;
	}

	@ZenMethod("unify")
	public static IItemStack unifyItem(IOreDictEntry ore) {
		return MineTweakerMC.getIItemStack(OreDictManager.INSTANCE.getStack(ore.getName(), ore.getAmount()));
	}

	@ZenMethod("unify")
	public static IItemStack unifyItem(IItemStack stack) {
		return MineTweakerMC.getIItemStack(OreDictManager.INSTANCE.getStack(T, MineTweakerMC.getItemStack(stack)));
	}

	public static boolean removeItemData(ItemStack aStack) {
		Map<ItemStackContainer, OreDictItemData> sItemStack2DataMap = null;
		try {
			sItemStack2DataMap = (Map<ItemStackContainer, OreDictItemData>) UT.Reflection.getField(OreDictManager.INSTANCE, "sItemStack2DataMap", true, true).get(OreDictManager.INSTANCE);
		} catch (Exception e) {
			MineTweakerAPI.logError("Unexpected Exception at removing ItemData for %s: ".formatted(MineTweakerMC.getIItemStack(aStack)), e);
		}
		OreDictItemData rData = null;
		ItemStackContainer container = new ItemStackContainer(aStack);
		if (sItemStack2DataMap == null) return F;
		rData = sItemStack2DataMap.get(container);
		if (rData == null) container = new ItemStackContainer(aStack, W);
		rData = sItemStack2DataMap.get(container);
		if (rData == null && aStack.getItem().isDamageable()) {
			container = new ItemStackContainer(aStack, 0);
			rData = sItemStack2DataMap.get(container);
		}
		if (rData != null) {
			REMOVED_DATA.put(container, rData);
			sItemStack2DataMap.remove(container);
			return true;
		}
		MineTweakerAPI.logError(String.format("%s dose not have Any Associated Material contained.", MineTweakerMC.getIItemStack(aStack)));
		return false;
	}

	@ZenMethod
	public static boolean remove(IItemStack aStack) {
		return removeItemData(MineTweakerMC.getItemStack(aStack));
	}

	// removes recycling for an itemstack without wiping its oredict material info
	@ZenMethod("removeRecycling")
	public static boolean removeRecyclingCT(IItemStack aStack) {
		return removeRecycling(MineTweakerMC.getItemStack(aStack));
	}

	public static boolean removeRecycling(ItemStack aStack) {
		return new CTRecipeMap(RM.Smelter).removeRecipeCT(MineTweakerMC.getIItemStacks(aStack)) || new CTRecipeMap(RM.Mortar).removeRecipeCT(MineTweakerMC.getIItemStacks(aStack));
	}
}

