package mods.bio.gttweaker.oredict;

import gregapi.oredict.OreDictManager;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenExpansion("minetweaker.oredict.IOreDictEntry")
public class CTIOreDictExpansion {
	@ZenGetter
	public static IItemStack unify(IOreDictEntry oreDictEntry){
		return CTUnifier.unifyItem(oreDictEntry);
	}
	@ZenGetter
	public static CTMaterial material(IOreDictEntry oreDictEntry){
		return new CTMaterial(OreDictManager.INSTANCE.getAutomaticItemData(oreDictEntry.getName()).mMaterial.mMaterial);
	}

	@ZenGetter
	public static CTPrefix prefix(IOreDictEntry oreDictEntry){
		return new CTPrefix(OreDictManager.INSTANCE.getAutomaticItemData(oreDictEntry.getName()).mPrefix);
	}

	@ZenGetter
	public static long amount(IOreDictEntry oreDictEntry){
		return prefix(oreDictEntry).amount();
	}
}
