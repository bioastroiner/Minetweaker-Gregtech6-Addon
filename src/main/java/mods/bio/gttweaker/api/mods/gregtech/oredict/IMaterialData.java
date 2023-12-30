package mods.bio.gttweaker.api.mods.gregtech.oredict;

import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import mods.bio.gttweaker.mods.gregtech.oredict.CTMaterialData;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

import static gregapi.data.CS.F;

@ZenClass("mods.gregtech.oredict.IMaterialData")
public interface IMaterialData {
	@ZenMethod
	public static IMaterialData association(IItemStack item) {
		OreDictItemData data = OreDictManager.INSTANCE.getAssociation(MineTweakerMC.getItemStack(item), F);
		if (data != null) return new CTMaterialData(data);
		MineTweakerAPI.logError(item + " dose not have a GT Association!");
		return null;
	}

	@ZenMethod
	public static IMaterialData association(ILiquidStack iLiquidStack) {
		OreDictMaterialStack stack = OreDictMaterial.FLUID_MAP.get(MineTweakerMC.getLiquidStack(iLiquidStack).getFluid().getName());
		if (stack != null) {
			OreDictItemData data = new OreDictItemData(stack);
			return new CTMaterialData(data);
		}
		MineTweakerAPI.logError(iLiquidStack + " dose not have a GT Association!");
		return null;
	}

	@ZenMethod
	public static IMaterialData association(IOreDictEntry ore) {
		OreDictItemData data = OreDictManager.INSTANCE.getAutomaticItemData(ore.getName());
		if (data != null) return new CTMaterialData(data);
		MineTweakerAPI.logError(ore + " dose not have a GT Association!");
		return null;
	}

	@ZenGetter
	IMaterialStack material();

	@ZenGetter
	IPrefix prefix();

	@ZenGetter
	List<IMaterialStack> byProducts();

	@ZenGetter
	List<IMaterialStack> materials();
}
