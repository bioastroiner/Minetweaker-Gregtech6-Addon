package mods.bio.gttweaker.oredict;

import gregapi.oredict.OreDictMaterial;
import minetweaker.MineTweakerAPI;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.Objects;

@ZenClass("mods.gregtech.oredict.Material")
public class CTMaterial {
	public final OreDictMaterial material_internal;

	public CTMaterial(OreDictMaterial aMaterial){
		if (aMaterial == null){
			MineTweakerAPI.logError("Null Material was provided unable to create a new <material:>");
		}
		material_internal = aMaterial;
	}

	@Override
	public String toString() {
		return "CTMaterial{" +
				"name=" + material_internal.mNameInternal +
				'}';
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
