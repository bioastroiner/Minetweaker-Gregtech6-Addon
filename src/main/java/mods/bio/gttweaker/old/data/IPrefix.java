package mods.bio.gttweaker.old.data;

import gregapi.oredict.OreDictPrefix;
import minetweaker.annotations.ModOnly;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.data")
@ModOnly("gregtech")
public interface IPrefix {
	public OreDictPrefix getPrefix();
	@ZenGetter("name")
	public String getName();

	@ZenMethod("mat")
	public IOreDictEntry withMaterial(IMaterial mat);

	@ZenMethod("contains")
	public boolean contains(ItemStack... aStacks);
}
