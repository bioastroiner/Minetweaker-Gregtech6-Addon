package mods.bio.gttweaker.mods.minetweaker;

import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictManager;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import mods.bio.gttweaker.mods.gregtech.oredict.CTMaterialData;
import mods.bio.gttweaker.mods.gregtech.oredict.CTMaterialStack;
import mods.bio.gttweaker.mods.gregtech.oredict.CTPrefix;
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
	public static CTMaterialData data(IItemStack iItemStack){
		OreDictItemData data = OreDictManager.INSTANCE.getAssociation_(MineTweakerMC.getItemStack(iItemStack), false);
		if(data!=null) return new CTMaterialData(data);
		MineTweakerAPI.logError(String.format("%s dose not contain any gt material data", iItemStack));
		return null;
	}

	@ZenGetter
	public static List<CTMaterialStack> materials(IItemStack aIItemStack) {
		if(data(aIItemStack) == null) return null;
		return Objects.requireNonNull(data(aIItemStack)).materials();
	}

	@ZenGetter
	public static CTMaterialStack material(IItemStack aIItemStack) {
		if(data(aIItemStack) == null) return null;
		return Objects.requireNonNull(data(aIItemStack)).material();
	}

	@ZenGetter
	public static CTPrefix prefix(IItemStack aIItemStack) {
		if(data(aIItemStack) == null) return null;
		return Objects.requireNonNull(data(aIItemStack)).prefix();
	}
}
