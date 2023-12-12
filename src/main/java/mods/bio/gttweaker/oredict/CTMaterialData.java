package mods.bio.gttweaker.oredict;

import gregapi.oredict.OreDictItemData;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ZenClass("mods.gregtech.oredict.MaterialData")
public class CTMaterialData {
	private final OreDictItemData backingData;
	public CTMaterialStack material(){
		return new CTMaterialStack(backingData.mMaterial);
	}
	public CTPrefix prefix(){
		return new CTPrefix(backingData.mPrefix);
	}
	private List<CTMaterialStack> byProducts(){
		return Arrays.stream(backingData.mByProducts).map(CTMaterialStack::new).collect(Collectors.toList());
	}

	public CTMaterialData(OreDictItemData backingData) {
		this.backingData = backingData;
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
		List<CTMaterialStack> list = byProducts();
		if(!list.isEmpty()) builder.append(",ByProducts:[");
		list.forEach(s->builder.append(String.format("%d: %s,",list.indexOf(s),s)));
		if (!list.isEmpty()) builder.reverse().deleteCharAt(0).reverse().append("]}");
		return builder.toString();
	}
}
