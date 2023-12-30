package mods.bio.gttweaker.mods.gregtech.oredict;

import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import minetweaker.MineTweakerAPI;
import mods.bio.gttweaker.api.mods.gregtech.oredict.CTMaterialFactory;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialFactory;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialStack;
import stanhebben.zenscript.annotations.*;

import java.util.Objects;

public class CTMaterial implements mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterial {
	private final OreDictMaterial material_internal;

	@Override
	public OreDictMaterial getMaterial(){
		return material_internal;
	}

	@Override
	public IMaterialFactory edit() {
		return new CTMaterialFactory(this);
	}

	@Override
	public IMaterialStack multiply(long amount) {
		return new CTMaterialStack(new OreDictMaterialStack(getMaterial(),amount));
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
