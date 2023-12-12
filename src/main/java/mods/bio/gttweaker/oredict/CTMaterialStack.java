package mods.bio.gttweaker.oredict;

import gregapi.oredict.OreDictMaterialStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

import static mods.bio.gttweaker.oredict.CTUnifier.U;

@ZenClass("mods.gregtech.MaterialStack")
public class CTMaterialStack {
	private final OreDictMaterialStack _backingStack;

	@ZenGetter
	public CTMaterial material(){
		return new CTMaterial(_backingStack.mMaterial);
	}

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
		// NOTE THAT THIS IS NOT VALID IN ZS
		return String.format("%s * %f U",material(),((double)amount())/ U());
	}
}
