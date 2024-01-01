package mods.bio.gttweaker.api.oredict;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

import java.util.List;

@ZenClass("mods.gregtech.oredict.IMaterialData")
public interface IMaterialData {

	@ZenGetter
	IMaterialStack material();

	@ZenGetter
	IPrefix prefix();

	@ZenGetter
	List<IMaterialStack> byProducts();

	@ZenGetter
	List<IMaterialStack> materials();
}
