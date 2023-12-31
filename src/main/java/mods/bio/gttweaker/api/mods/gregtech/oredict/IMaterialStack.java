package mods.bio.gttweaker.api.mods.gregtech.oredict;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

@ZenClass("mods.gregtech.oredict.IMaterialStack")
public interface IMaterialStack {
	@ZenGetter
	IMaterial material();

	@ZenMethod
	IItemStack item(IPrefix aPrefix);

	@ZenOperator(OperatorType.MUL)
	@ZenMethod
	long multiply(long amount);

	@ZenOperator(OperatorType.DIV)
	@ZenGetter
	long devide(long amount);

	@ZenOperator(OperatorType.ADD)
	IMaterialData add(IMaterialStack materialStack);

	@ZenGetter
	long amount();

}
