package mods.bio.gttweaker.mods.minetweaker;

import minetweaker.api.item.IItemStack;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialData;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialStack;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IPrefix;
import mods.bio.gttweaker.mods.gregtech.oredict.CTUnifier;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;
import java.util.Objects;

@ZenExpansion("minetweaker.item.IItemStack")
public class CTIItemStackExpansion {
	@ZenMethod
	public static boolean removeRecycling(IItemStack aIItemStack) {
		return CTUnifier.removeRecyclingCT(aIItemStack);
	}

	@ZenMethod
	public static boolean removeMaterialData(IItemStack aIItemStack) {
		return CTUnifier.removeRecyclingCT(aIItemStack);
	}

	@ZenGetter
	public static IMaterialData association(IItemStack iItemStack){
		return IMaterialData.association(iItemStack);
	}

	@ZenGetter
	public static IMaterialData data(IItemStack iItemStack){
		return association(iItemStack);
	}

	@ZenGetter
	public static List<IMaterialStack> materials(IItemStack aIItemStack) {
		if(data(aIItemStack) == null) return null;
		return Objects.requireNonNull(data(aIItemStack)).materials();
	}

	@ZenGetter
	public static IMaterialStack material(IItemStack aIItemStack) {
		if(data(aIItemStack) == null) return null;
		return Objects.requireNonNull(data(aIItemStack)).material();
	}

	@ZenGetter
	public static IPrefix prefix(IItemStack aIItemStack) {
		if(data(aIItemStack) == null) return null;
		return Objects.requireNonNull(data(aIItemStack)).prefix();
	}
}
