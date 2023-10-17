package mods.bio.gttweaker.data;

import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;
import gregapi.oredict.event.IOreDictListenerRecyclable;
import gregapi.util.OM;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.Data")
@ModOnly("grgetech")
public class GTData {
//	@ZenMethod
//	public static void data(IItemStack item) {
//		OM.data(MineTweakerMC.getItemStack(item));
//	}

	@ZenMethod
	public static void removeAll(IItemStack item) {
		var om = OreDictManager.INSTANCE.getItemData((MineTweakerMC.getItemStack(item)));
		OreDictItemData.
	}

	@ZenMethod
	public static void blackList(IItemStack item) {
		OM.blacklist(MineTweakerMC.getItemStack(item));
	}

	@ZenMethod
	public static IItemStack item(String prefix,String material) {
		return MineTweakerMC.getIItemStack(OM.get(OreDictPrefix.get(prefix), OreDictMaterial.get(material),1));
	}

	@ZenMethod
	public static IItemStack crushed(IIngredient item) {
		return MineTweakerMC.getIItemStack(OM.crushed(OM.anyassociation(MineTweakerMC.getItemStack(item)).mMaterial.mMaterial, 1));
	}

	@ZenMethod
	public static IItemStack gem(IIngredient item) {
		ItemStack stack = null;
		if(item instanceof IOreDictEntry) stack = MineTweakerMC.getItemStacks(item.getItems())[0];
		return MineTweakerMC.getIItemStack(OM.gem(OM.anyassociation(stack).mMaterial.mMaterial, 1));
	}
}
