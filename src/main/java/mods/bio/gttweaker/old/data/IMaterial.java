package mods.bio.gttweaker.old.data;

import gregapi.oredict.OreDictMaterial;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.data")
@ModOnly("gregtech")
public interface IMaterial {
	public OreDictMaterial getMaterial();
	/**
	 * @return the internal name of Material starting with a Capitlaize letter
	 */
	@ZenGetter("name")
	public String getName();

	@ZenGetter("id")
	public int getID();

	/**
	 * @return gets all the items registered associated with this material
	 */
	@ZenGetter("items")
	public IItemStack[] getItems();

	/**
	 * @return an Item with an specefic prefix, for oredict refer to {ore}
	 */
	@ZenMethod("item")
	public IItemStack getItemWithPrefix(String prefix);

	@ZenMethod("ore")
	public IOreDictEntry getItemWithPrefix(IPrefix prefix);

	/**
	 * @return an ore dictionary object with an specefic prefix
	 */
	@ZenMethod("get")
	public IOreDictEntry getOreDictWithPrefix(String prefix);

	/**
	 * @return a quick way to retrive an ingot
	 */
	@ZenGetter("ingot")
	public IOreDictEntry getOreDictIngot();

	/**
	 * @return a list of all the ores countained within this material that means all the prefixes it has
	 */
	@ZenGetter("ores")
	public IOreDictEntry[] getOreDicts();

	@ZenGetter("fluid")
	public ILiquidStack getFluid();

	@ZenGetter("liquid")
	public default ILiquidStack getFluid2() {return getFluid();}
	@ZenGetter("plasma")
	public ILiquidStack getPlasma();
	@ZenGetter("gas")
	public ILiquidStack getGas();
}
