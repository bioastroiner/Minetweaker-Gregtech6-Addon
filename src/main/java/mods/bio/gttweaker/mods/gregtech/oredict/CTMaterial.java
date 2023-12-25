package mods.bio.gttweaker.mods.gregtech.oredict;

import gregapi.oredict.OreDictMaterial;
import minetweaker.MineTweakerAPI;
import stanhebben.zenscript.annotations.*;

import java.util.Objects;

@ZenClass("mods.gregtech.oredict.Material")
public class CTMaterial {
	private final OreDictMaterial material_internal;

	public OreDictMaterial getMaterial(){
		return material_internal;
	}

	@ZenMethod
	public static CTMaterialFactory create(short id, @NotNull String oreDictName, @Optional String localName){
		if (localName == null) localName = oreDictName;
		return new CTMaterialFactory(id, oreDictName, localName);
	}

	public CTMaterial(OreDictMaterial aMaterial){
		if (aMaterial == null){
			MineTweakerAPI.logError("Null Material was provided unable to create a new <material:>");
		}
		material_internal = aMaterial;
	}

	/**
	 * @return formatted string as it is formatted in bracketHandler
	 */
	@Override
	public String toString() {
		return String.format("<material:%s>",material_internal.mNameInternal);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CTMaterial that = (CTMaterial) o;
		return Objects.equals(material_internal, that.material_internal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(material_internal);
	}
}
