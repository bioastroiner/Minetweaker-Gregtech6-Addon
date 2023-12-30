package mods.bio.gttweaker.mods.gregtech.oredict;

import gregapi.oredict.OreDictMaterialStack;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterial;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenOperator;

import static mods.bio.gttweaker.mods.gregtech.oredict.CTUnifier.U;

public class CTMaterialStack implements mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialStack {
	private final OreDictMaterialStack _backingStack;

	@Override
	public IMaterial material(){
		return new CTMaterial(_backingStack.mMaterial);
	}

	@Override
	public long amount(){
		return _backingStack.mAmount;
	}

	public CTMaterialStack(OreDictMaterialStack backingStack) {
		_backingStack = backingStack;
	}

	/**
	 * @return <material:name> * amount U
	 */
	@Override
	public String toString() {
		return String.format("%s * %f U",material(),((double)amount())/ U());
	}
}
