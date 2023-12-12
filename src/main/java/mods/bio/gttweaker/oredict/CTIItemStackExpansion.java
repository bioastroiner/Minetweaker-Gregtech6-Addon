package mods.bio.gttweaker.oredict;

import gregapi.oredict.OreDictManager;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

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
	public static CTMaterial[] materials(IItemStack aIItemStack) {
		return OreDictManager.INSTANCE.getAssociation_(MineTweakerMC.getItemStack(aIItemStack), false).getAllMaterialStacks().stream().map(s -> s.mMaterial).map(CTMaterial::new).toArray(CTMaterial[]::new);
	}

	@ZenGetter
	public static CTMaterial material(IItemStack aIItemStack) {
		return new CTMaterial(OreDictManager.INSTANCE.getAssociation_(MineTweakerMC.getItemStack(aIItemStack), false).mMaterial.mMaterial);
	}

	@ZenGetter
	public static CTPrefix prefix(IItemStack aIItemStack) {
		return new CTPrefix(OreDictManager.INSTANCE.getAssociation_(MineTweakerMC.getItemStack(aIItemStack), false).mPrefix);
	}
}
