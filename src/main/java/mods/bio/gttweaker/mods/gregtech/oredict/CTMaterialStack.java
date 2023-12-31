package mods.bio.gttweaker.mods.gregtech.oredict;

import gregapi.oredict.OreDictMaterialStack;
import gregapi.util.ST;
import gregapi.util.UT;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterial;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialData;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialStack;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IPrefix;

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
	public IMaterial material() {
		return new CTMaterial(_backingStack.mMaterial);
	}

	@Override
	public IItemStack item(IPrefix aPrefix) {
		return MineTweakerMC.getIItemStack(ST.size(UT.Code.divup(amount(), U() * aPrefix.amount()), MineTweakerMC.getItemStack(aPrefix.mat(material()))));
	}

	@Override
	public long multiply(long amount) {
		return _backingStack.mAmount *= amount;
	}

	@Override
	public long devide(long amount) {
		_backingStack.mAmount = UT.Code.divup(_backingStack.mAmount, amount);
		return amount();
	}

	@Override
	public IMaterialData add(IMaterialStack materialStack) {
		return new CTMaterialData(this, materialStack);
	}

	@Override
	public long amount() {
		return _backingStack.mAmount;
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
