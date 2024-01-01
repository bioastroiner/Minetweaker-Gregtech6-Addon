package mods.bio.gttweaker.mods.gregtech.oredict;

import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialFactory;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;

@ZenExpansion("mods.gregtech.oredict.IMaterialFactory")
public class CTIMaterialFactoryExpansion {
	@ZenMethod
	public static IMaterialFactory create(short id, @Optional @NotNull String oreDictName, @Optional @Nullable String localName){
		return new CTMaterialFactory(id,oreDictName,localName);
	}
}
