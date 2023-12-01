package mods.bio.gttweaker.old.data;

import gregapi.data.OP;
import gregapi.oredict.OreDictPrefix;
import minetweaker.MineTweakerAPI;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;

public class MCPrefix implements IPrefix {
	public final String mNameInternal;

	public MCPrefix(OreDictPrefix prefix){
		this.mNameInternal = prefix.mNameInternal;
	}

	public MCPrefix(String name) {
		// case inSensetive search
		String out = "INVALID";
		for (String k : OreDictPrefix.sPrefixes.keySet()) {
			String k2 = k;
			k2 = k2.toLowerCase().replace(" ", "");
			if (k2.equals(name.toLowerCase()) || k.equals(name.toLowerCase())) out = OreDictPrefix.sPrefixes.get(k).mNameInternal;
		}
		if (out=="INVALID") {
			MineTweakerAPI.logError("ERROR: The Material <prefix:" + name + "> dose not exist.");
			MineTweakerAPI.logWarning(" -Using Ingot as a Prefix PlaceHolder for: " + name);
			this.mNameInternal = OP.ingot.mNameInternal;
			return;
		}
		this.mNameInternal = name;

		//if(!OreDictPrefix.sPrefixes.containsKey(name)) MineTweakerAPI.logError("ERROR: Prefix" + name + " dose not exist");
	}

	public static MCPrefix getMCPrefix(OreDictPrefix prefix) {
		return new MCPrefix(prefix.mNameInternal);
	}

	@Override
	public OreDictPrefix getPrefix() {
		return OreDictPrefix.get(mNameInternal);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public IOreDictEntry withMaterial(IMaterial mat) {
		return null;
	}

	@Override
	public boolean contains(ItemStack... aStacks) {
		return false;
	}
}
