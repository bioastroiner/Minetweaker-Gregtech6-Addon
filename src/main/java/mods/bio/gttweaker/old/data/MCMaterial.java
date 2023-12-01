package mods.bio.gttweaker.old.data;

import gregapi.code.ItemStackContainer;
import gregapi.data.MT;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.mc1710.liquid.MCLiquidStack;

import java.util.stream.Collectors;

public class MCMaterial implements IMaterial {

	private final short id;

	public MCMaterial(short id) {
		if(OreDictMaterial.MATERIAL_ARRAY[id] == null) {
			id = -1;
			MineTweakerAPI.logError("ERROR: Material with the ID: " + id + " dose not exist! Replacing it with NULL ID = -1.");
		}
		this.id = id;
	}

	public MCMaterial(String name) {
		// case inSensetive search
		int id = -1;
		for (String k : OreDictMaterial.MATERIAL_MAP.keySet()) {
			String k2 = k;
			k2 = k2.toLowerCase().replace(" ", "");
			if (k2.equals(name.toLowerCase()) || k.equals(name.toLowerCase())) id = OreDictMaterial.MATERIAL_MAP.get(k).mID;
		}
		//case sensetive search
//		if(id == -1)
//			id = OreDictMaterial.MATERIAL_MAP.get(name).mID;
		if (id == -1) {
			MineTweakerAPI.logError("ERROR: The Material <material:" + name + "> dose not exist.");
		}
		this.id = (short) id;
	}

	public static OreDictMaterial getOreDictMaterial(IMaterial mat) {
		return mat.getMaterial();
	}

	public static IMaterial getIMaterial(OreDictMaterial mat) {
		return mat == null ? null : new MCMaterial(mat.mID);
	}

	public OreDictMaterial getMaterial() {
		if(id==-1) return MT.NULL;
		return OreDictMaterial.MATERIAL_ARRAY[id];
	}

	/**
	 * @return this name has no whitespaces but has capitalized letters, prefect for oredicts
	 */
	@Override
	public String getName() {
		return getMaterial().mNameInternal.replace(" ", "");
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public IItemStack[] getItems() {
		return MineTweakerMC.getIItemStacks((getMaterial().mRegisteredItems.stream().map((ItemStackContainer::toStack)).collect(Collectors.toList())));
	}

	@Override
	public IItemStack getItemWithPrefix(String prefix) {
		var pfx = OreDictPrefix.get(prefix);
		if (pfx == null) MineTweakerAPI.logWarning("ERROR: " + prefix + " for "+ getName()+" Is Invalid!");
		assert pfx != null;
		return MineTweakerMC.getIItemStack(pfx.mat(getMaterial(), 1));
	}

	@Override
	public IOreDictEntry getItemWithPrefix(IPrefix prefix) {
		return prefix.withMaterial(this);
	}

	@Override
	public IOreDictEntry getOreDictWithPrefix(String prefix) {
		var pfx = OreDictPrefix.get(prefix);
		if (pfx == null) MineTweakerAPI.logWarning("ERROR: " + prefix + " for "+ getName()+" Is Invalid!");
		assert pfx != null;
		return MineTweakerMC.getOreDict(prefix + getName());
	}

	@Override
	public IOreDictEntry getOreDictIngot() {
		return getOreDictWithPrefix("ingot");
	}

	@Override
	public IOreDictEntry[] getOreDicts() {
		return (IOreDictEntry[]) OreDictPrefix.VALUES_SORTED.stream().map(p -> getOreDictWithPrefix(p.mNameInternal)).toArray();
	}

	@Override
	public ILiquidStack getFluid() {
		return new MCLiquidStack(getMaterial().mLiquid);
	}

	@Override
	public ILiquidStack getPlasma() {
		return new MCLiquidStack(getMaterial().mPlasma);
	}

	@Override
	public ILiquidStack getGas() {
		return new MCLiquidStack(getMaterial().mGas);
	}
}
