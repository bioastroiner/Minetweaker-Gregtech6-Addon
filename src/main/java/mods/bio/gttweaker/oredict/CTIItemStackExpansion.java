package mods.bio.gttweaker.oredict;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("minetweaker.item.IItemStack")
public class CTIItemStackExpansion {
	@ZenMethod
	public static boolean removeRecycling(IItemStack aIItemStack){
		return CTUnifier.removeRecyclingCT(aIItemStack);
	}

	@ZenMethod
	public static boolean removeMaterialData(IItemStack aIItemStack){
		return CTUnifier.removeRecyclingCT(aIItemStack);
	}
}
