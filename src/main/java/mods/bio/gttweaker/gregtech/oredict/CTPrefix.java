package mods.bio.gttweaker.gregtech.oredict;

import gregapi.oredict.OreDictPrefix;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import mods.bio.gttweaker.api.oredict.IMaterial;
import mods.bio.gttweaker.api.oredict.IMaterialStack;
import mods.bio.gttweaker.api.oredict.IPrefix;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Objects;

@ZenClass("mods.gregtech.oredict.Prefix")
public class CTPrefix implements IPrefix {
	public final OreDictPrefix prefix_internal;

	public CTPrefix(OreDictPrefix aPrefix) {
		prefix_internal = aPrefix;
	}

	/**
	 * @return formatted string as it is formatted in bracketHandler
	 */
	@Override
	public String toString() {
		return String.format("<prefix:%s>", prefix_internal.mNameInternal);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CTPrefix that = (CTPrefix) o;
		return Objects.equals(prefix_internal, that.prefix_internal);
	}

	@Override
	public int hashCode() {
		return prefix_internal.hashCode();
	}

	/*      GETTERS     */

	@Override
	public String oreDictName() {
		return prefix_internal.mNameInternal;
	}

	/**
	 * @return gets a qualified amount on terms of U, its a float TODO we need to work on Unit System and make it a bit unified
	 */
	@Override
	@ZenGetter
	public long amount() {
		return prefix_internal.mAmount;
	}

	/*      METHODS     */

	@Override
	@ZenMethod
	public IItemStack withMaterial(IMaterial aMaterial) {
		IItemStack aStack = MineTweakerMC.getIItemStack(prefix_internal.mat(aMaterial.getMaterial(), 1));
		if (aStack == null)
			MineTweakerAPI.logError(String.format("%s dose not return a valid Item in %s.", aMaterial, this));
		return aStack;
	}

	@Override
	@ZenMethod
	public IItemStack mat(IMaterial aMaterial) {
		return withMaterial(aMaterial);
	}

	@Override
	@ZenMethod
	public IItemStack material(IMaterial aMaterial) {
		return withMaterial(aMaterial);
	}

	@Override
	@ZenMethod
	public IPrefix disableItemGeneration() {
		prefix_internal.disableItemGeneration();
		MineTweakerAPI.logInfo(String.format("ItemGeneration for %s has been disabled.", this));
		return this;
	}

	@Override
	@ZenMethod
	public IPrefix forceItemGeneration() {
		prefix_internal.forceItemGeneration();
		MineTweakerAPI.logInfo(String.format("ItemGeneration for %s has been Forced to be generated.", this));
		return this;
	}

	@Override
	public IMaterialStack stack(IMaterial material) {
		return material.multiply(amount());
	}

	@Override
	@ZenMethod
	public boolean contains(IItemStack aIItemStack) {
		return prefix_internal.contains(MineTweakerMC.getItemStack(aIItemStack));
	}

	@Override
	@ZenMethod
	public boolean contains(IItemStack... aIItemStacks) {
		return prefix_internal.contains(MineTweakerMC.getItemStacks(aIItemStacks));
	}

	@Override
	@ZenMethod
	// TODO there is more to this visit later, but it's rather ready for production
	public IPrefix contains(int stackSize) {
		MineTweakerAPI.logInfo(String.format("New StackSize has been set for %s from %d to %d", this, prefix_internal.mDefaultStackSize, stackSize));
		prefix_internal.setStacksize(stackSize);
		return this;
	}

	// TODO: to be implemented
	@Override
	public boolean contains(IOreDictEntry aIOreDictEntry) {
		return false;
	}

}
