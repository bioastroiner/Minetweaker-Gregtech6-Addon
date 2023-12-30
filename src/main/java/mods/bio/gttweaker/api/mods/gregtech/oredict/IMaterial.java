package mods.bio.gttweaker.api.mods.gregtech.oredict;

import gregapi.oredict.OreDictMaterial;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenOperator;

@ZenClass("mods.gregtech.oredict.IMaterial")
public interface IMaterial {
	OreDictMaterial getMaterial();

	@ZenOperator(OperatorType.MUL)
	IMaterialStack multiply(long amount);
}
