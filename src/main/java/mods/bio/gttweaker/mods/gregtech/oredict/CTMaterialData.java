package mods.bio.gttweaker.mods.gregtech.oredict;

import gregapi.oredict.OreDictItemData;
import minetweaker.MineTweakerAPI;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialStack;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IPrefix;
import stanhebben.zenscript.annotations.ZenGetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CTMaterialData implements mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialData {
	public final OreDictItemData backingData;
	@Override
	@ZenGetter
	public IMaterialStack material(){
		if(backingData.mMaterial != null) return new CTMaterialStack(backingData.mMaterial);
		MineTweakerAPI.logError(this + " dose not have any Main Material");
		return null;
	}
	@Override
	@ZenGetter
	public IPrefix prefix(){
		if(backingData.mPrefix != null) return new CTPrefix(backingData.mPrefix);
		MineTweakerAPI.logError(this + " dose not have any GT prefix");
		return null;
	}
	@Override
	public List<IMaterialStack> byProducts(){
		if(backingData.mByProducts.length < 1) {
			MineTweakerAPI.logError(this + " dose not have any GT byproduct");
			return new ArrayList<>();
		}
		return Arrays.stream(backingData.mByProducts).map(CTMaterialStack::new).collect(Collectors.toList());
	}
	@Override
	@ZenGetter
	public List<IMaterialStack> materials(){
		List<IMaterialStack> list = new ArrayList<>();
		list.add(material());
		list.addAll(byProducts());
		return list;
	}

	public CTMaterialData(OreDictItemData backingData) {
		this.backingData = backingData;
		if(backingData==null) {
			MineTweakerAPI.logError("Material Data cannot be null",new NullPointerException("Not a valid Material Data."));
		}
	}

	/**
	 * @return a very JSON like CTMaterialData Representation tough it's not ready for json parsing at all
	 *                 but is human readable.
	 *                 {
	 *                      MainMaterial: <material:name> * amount U ,
	 *                      ByProducts: [
	 *                          1: <material:name> * amount U,
	 *                          2 :<material:name> * amount U,
	 *                          3: <material:name> * amount U
	 *                      ]
	 *                 }
	 *
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if(material() != null) builder.append("{MainMaterial:").append(material());
		else builder.append("{NULL");
		List<IMaterialStack> list = byProducts();
		if(!list.isEmpty()) builder.append(",ByProducts:[");
		list.forEach(s->builder.append(String.format("%d: %s,",list.indexOf(s),s)));
		if (!list.isEmpty()) builder.reverse().deleteCharAt(0).reverse().append("]}");
		return builder.toString();
	}
}
