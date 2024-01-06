package mods.bio.gttweaker.gregtech;

import gregapi.oredict.OreDictManager;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import mods.bio.gttweaker.api.oredict.IMaterialData;
import mods.bio.gttweaker.api.oredict.IPrefix;
import mods.bio.gttweaker.gregtech.oredict.CTMaterial;
import mods.bio.gttweaker.gregtech.oredict.CTMaterialManager;
import mods.bio.gttweaker.gregtech.oredict.CTPrefix;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenExpansion("minetweaker.oredict.IOreDictEntry")
public class CTIOreDictExpansion {
	@ZenGetter
	public static IItemStack unified(IOreDictEntry oreDictEntry){
		return CTMaterialManager.unifyItem(oreDictEntry);
	}
	@ZenGetter
	public static CTMaterial material(IOreDictEntry oreDictEntry){
		return new CTMaterial(OreDictManager.INSTANCE.getAutomaticItemData(oreDictEntry.getName()).mMaterial.mMaterial);
	}

	@ZenGetter
	public static IPrefix prefix(IOreDictEntry oreDictEntry){
		return new CTPrefix(OreDictManager.INSTANCE.getAutomaticItemData(oreDictEntry.getName()).mPrefix);
	}

	@ZenGetter
	public static long amount(IOreDictEntry oreDictEntry){
		return prefix(oreDictEntry).amount();
	}

	@ZenGetter
	public static IMaterialData association(IOreDictEntry oreDictEntry){
		return CTMaterialManager.association(oreDictEntry);
	}

	@ZenGetter
	public static IMaterialData data(IOreDictEntry oreDictEntry){
		return association(oreDictEntry);
	}
}
