package mods.bio.gttweaker.oredict;

import gregapi.data.CS;
import gregapi.oredict.OreDictPrefix;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Objects;

@ZenClass("mods.gregtech.oredict.Prefix")
public class CTPrefix {
	public final OreDictPrefix prefix_internal;
	public CTPrefix(OreDictPrefix aPrefix){
		prefix_internal = aPrefix;
	}

	/**
	 * @return formatted string as it is formatted in bracketHandler
	 */
	@Override
	public String toString() {
		return String.format("<prefix:%s>",prefix_internal.mNameInternal);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CTPrefix ctPrefix = (CTPrefix) o;
		return Objects.equals(prefix_internal, ctPrefix.prefix_internal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(prefix_internal);
	}

	/*      GETTERS     */

	/**
	 * @return gets a qualified amount on terms of U, its a float TODO we need to work on Unit System and make it a bit unified
	 */
	@ZenGetter
	public float amount(){
		return ((float) prefix_internal.mAmount / CS.U);
	}

	/*      METHODS     */

	@ZenMethod
	public CTPrefix disableItemGeneration(){
		prefix_internal.disableItemGeneration();
		MineTweakerAPI.logInfo(String.format("ItemGeneration for %s has been disabled.", this));
		return this;
	}

	@ZenMethod
	public CTPrefix forceItemGeneration(){
		prefix_internal.forceItemGeneration();
		MineTweakerAPI.logInfo(String.format("ItemGeneration for %s has been Forced to be generated.", this));
		return this;
	}

	@ZenMethod
	public boolean contains(IItemStack aIItemStack){
		return prefix_internal.contains(MineTweakerMC.getItemStack(aIItemStack));
	}

	@ZenMethod
	public boolean contains(IItemStack... aIItemStacks){
		return prefix_internal.contains(MineTweakerMC.getItemStacks(aIItemStacks));
	}

	@ZenMethod
	// TODO there is more to this visit later, but it's rather ready for production
	public CTPrefix contains(int stackSize){
		MineTweakerAPI.logInfo(String.format("New StackSize has been set for %s from %d to %d", this,prefix_internal.mDefaultStackSize,stackSize));
		prefix_internal.setStacksize(stackSize);
		return this;
	}

	// TODO: to be implemented
	public boolean contains(IOreDictEntry aIOreDictEntry){
		return false;
	}

}
