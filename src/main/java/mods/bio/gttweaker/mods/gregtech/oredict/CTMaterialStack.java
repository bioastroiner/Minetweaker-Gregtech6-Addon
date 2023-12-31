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

	public CTMaterialStack(OreDictMaterialStack backingStack) {
		_backingStack = backingStack;
	}

	public OreDictMaterialStack get_backingStack() {
		return _backingStack;
	}

	@Override
	public IMaterial material(){
		return new CTMaterial(_backingStack.mMaterial);
	}

	@Override
	public long amount() {
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
		return String.format("%s * %f U", material(), ((double) amount()) / U());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		CTMaterialStack that = (CTMaterialStack) o;

		if (amount() != that.amount()) return false;
		return _backingStack.mMaterial.equals(that._backingStack.mMaterial);
	}

	@Override
	public int hashCode() {
		return _backingStack.hashCode();
	}
}
