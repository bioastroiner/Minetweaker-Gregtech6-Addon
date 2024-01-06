package mods.bio.gttweaker.gregtech.oredict;

import gregapi.data.CS;
import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictMaterialStack;
import minetweaker.MineTweakerAPI;
import mods.bio.gttweaker.api.oredict.IMaterialData;
import mods.bio.gttweaker.api.oredict.IMaterialStack;
import mods.bio.gttweaker.api.oredict.IPrefix;
import mods.bio.gttweaker.core.GregTweakerAPI;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenGetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CTMaterialData implements IMaterialData {
	public final OreDictItemData backingData;

	public CTMaterialData(@NotNull OreDictItemData backingData) {
		this.backingData = backingData;
		if (backingData == null) {
			MineTweakerAPI.logError("Material Data cannot be null", new NullPointerException("Not a valid Material Data."));
		}
	}

	public CTMaterialData(@NotNull CTMaterialData... data) {
		this.backingData = new OreDictItemData(Arrays.stream(data).map(CTMaterialData::getBackingData).collect(Collectors.toList()).toArray(CS.ZL_OREDICTITEMDATA));
	}

	public CTMaterialData(@NotNull CTMaterialData data, @NotNull CTMaterialStack material) {
		List<OreDictMaterialStack> t = Arrays.asList(data.getBackingData().mByProducts);
		t.add(material.get_backingStack());
		this.backingData = new OreDictItemData(data.getBackingData().mMaterial, t.toArray(new OreDictMaterialStack[0]));
	}

	public CTMaterialData(IMaterialStack mainMaterial, @NotNull IMaterialStack... stacks) {
		this.backingData = new OreDictItemData(GregTweakerAPI.getMaterialStack(mainMaterial), GregTweakerAPI.getMaterialStacks(stacks));
	}

	public OreDictItemData getBackingData() {
		return backingData;
	}

	@Override
	@ZenGetter
	public IMaterialStack material() {
		if (backingData.mMaterial != null) return new CTMaterialStack(backingData.mMaterial);
		MineTweakerAPI.logError(this + " dose not have any Main Material");
		return null;
	}

	@Override
	@ZenGetter
	public IPrefix prefix() {
		if (backingData.mPrefix != null) return new CTPrefix(backingData.mPrefix);
		MineTweakerAPI.logError(this + " dose not have any GT prefix");
		return null;
	}

	@Override
	public List<IMaterialStack> byProducts() {
		if (backingData.mByProducts.length < 1) {
			MineTweakerAPI.logError(this + " dose not have any GT byproduct");
			return new ArrayList<>();
		}
		return Arrays.stream(backingData.mByProducts).map(CTMaterialStack::new).collect(Collectors.toList());
	}

	@Override
	@ZenGetter
	public List<IMaterialStack> materials() {
		List<IMaterialStack> list = new ArrayList<>();
		list.add(material());
		list.addAll(byProducts());
		return list;
	}

	/**
	 * @return a very JSON like CTMaterialData Representation tough it's not ready for json parsing at all
	 * but is human readable.
	 * {
	 * MainMaterial: <material:name> * amount U ,
	 * ByProducts: [
	 * 1: <material:name> * amount U,
	 * 2 :<material:name> * amount U,
	 * 3: <material:name> * amount U
	 * ]
	 * }
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (material() != null) builder.append("{MainMaterial:").append(material());
		else builder.append("{NULL");
		List<IMaterialStack> list = byProducts();
		if (!list.isEmpty()) builder.append(",ByProducts:[");
		list.forEach(s -> builder.append(String.format("%d: %s,", list.indexOf(s), s)));
		if (!list.isEmpty()) builder.reverse().deleteCharAt(0).reverse().append("]}");
		return builder.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		CTMaterialData that = (CTMaterialData) o;
		if (material() != that.material()) return false;
		for (IMaterialStack byProduct : byProducts()) {
			if (!that.byProducts().contains(byProduct)) return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return backingData.hashCode();
	}
}
