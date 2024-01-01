package mods.bio.gttweaker.gregtech.oredict;

import mods.bio.gttweaker.api.oredict.IMaterialFactory;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;

@ZenClass("mods.gregtech.oredict.MaterialRegistry")
public class MaterialRegistry {
	@ZenMethod
	public static IMaterialFactory create(short id, @Optional @NotNull String oreDictName, @Optional @Nullable String localName){
		return new CTMaterialFactory(id,oreDictName,localName);
	}
}
