package mods.bio.gttweaker.api.mods.gregtech.oredict;

import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

public interface IPrefix {
	@ZenGetter
	long amount();

	@ZenMethod
	IItemStack withMaterial(IMaterial aMaterial);

	@ZenMethod
	IItemStack mat(IMaterial aMaterial);

	@ZenMethod
	IItemStack material(IMaterial aMaterial);

	@ZenMethod
	IPrefix disableItemGeneration();

	@ZenMethod
	IPrefix forceItemGeneration();

	@ZenMethod
	IMaterialStack stack(IMaterial material);

	@ZenMethod
	boolean contains(IItemStack aIItemStack);

	@ZenMethod
	boolean contains(IItemStack... aIItemStacks);

	@ZenMethod
		// TODO there is more to this visit later, but it's rather ready for production
	IPrefix contains(int stackSize);

	// TODO: to be implemented
	boolean contains(IOreDictEntry aIOreDictEntry);
}
