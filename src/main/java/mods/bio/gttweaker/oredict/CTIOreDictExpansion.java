package mods.bio.gttweaker.oredict;

import gregapi.oredict.OreDictManager;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.mc1710.oredict.MCOreDictEntry;
import mods.bio.gttweaker.command.GTCommand;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("minetweaker.oredict.IOreDictEntry")
public class CTIOreDictExpansion {
	@ZenMethod
	public static IItemStack unify(IOreDictEntry oreDictEntry){
		return CTUnifier.unifyItem(oreDictEntry);
	}
	@ZenGetter
	public static CTMaterial material(IOreDictEntry oreDictEntry){
		return new CTMaterial(OreDictManager.INSTANCE.getAutomaticItemData(oreDictEntry.getName()).mMaterial.mMaterial);
	}
}
