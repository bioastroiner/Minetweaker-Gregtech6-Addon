package mods.bio.gttweaker.api.mods.gregtech.oredict;

import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenOperator;

@ZenClass("mods.gregtech.oredict.IMaterialStack")
public interface IMaterialStack {
	@ZenGetter
	IMaterial material();

	@ZenOperator(OperatorType.MUL)
	@ZenGetter
	long amount();
}
