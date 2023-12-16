package mods.bio.gttweaker.mods.gregtech.oredict;

import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static gregapi.data.CS.F;

@ZenClass("mods.gregtech.oredict.MaterialData")
public class CTMaterialData {
	public final OreDictItemData backingData;
	@ZenGetter
	public CTMaterialStack material(){
		if(backingData.mMaterial != null) return new CTMaterialStack(backingData.mMaterial);
		MineTweakerAPI.logError(this + " dose not have any Main Material");
		return null;
	}
	@ZenGetter
	public CTPrefix prefix(){
		if(backingData.mPrefix != null) return new CTPrefix(backingData.mPrefix);
		MineTweakerAPI.logError(this + " dose not have any GT prefix");
		return null;
	}
	@ZenGetter
	private List<CTMaterialStack> byProducts(){
		if(backingData.mByProducts.length < 1) {
			MineTweakerAPI.logError(this + " dose not have any GT byproduct");
			return new ArrayList<>();
		}
		return Arrays.stream(backingData.mByProducts).map(CTMaterialStack::new).collect(Collectors.toList());
	}
	@ZenGetter
	public List<CTMaterialStack> materials(){
		List<CTMaterialStack> list = new ArrayList<>();
		list.add(material());
		list.addAll(byProducts());
		return list;
	}

	private CTMaterialData(OreDictItemData backingData) {
		this.backingData = backingData;
		if(backingData==null) {
			MineTweakerAPI.logError("Material Data cannot be null",new NullPointerException("Not a valid Material Data."));
		}
	}

	@ZenMethod
	public static CTMaterialData association(IItemStack item){
		OreDictItemData data = OreDictManager.INSTANCE.getAssociation(MineTweakerMC.getItemStack(item),F);
		if(data!=null) return new CTMaterialData(data);
		MineTweakerAPI.logError(item + " dose not have a GT Association!");
		return null;
	}

	@ZenMethod
	public static CTMaterialData association(ILiquidStack iLiquidStack){
		OreDictMaterialStack stack = OreDictMaterial.FLUID_MAP.get(MineTweakerMC.getLiquidStack(iLiquidStack).getFluid().getName());
		if(stack!=null) {
			OreDictItemData data = new OreDictItemData(stack);
			return new CTMaterialData(data);
		}
		MineTweakerAPI.logError(iLiquidStack + " dose not have a GT Association!");
		return null;
	}

	@ZenMethod
	public static CTMaterialData association(IOreDictEntry ore){
		OreDictItemData data = OreDictManager.INSTANCE.getAutomaticItemData(ore.getName());
		if(data!=null) return new CTMaterialData(data);
		MineTweakerAPI.logError(ore + " dose not have a GT Association!");
		return null;
	}

	/**
	 * @return a very JSON like CTMaterialData Representation tough it's not ready for json parsing at all
	 *                 but is human readable.
	 *                 {
	 *                      MainMaterial: <material:name> * amount U ,
	 *                      ByProducts: [
	 *                          1: <material:name> * amount U,
	 *                          2 :<material:name> * amount U,
	 *                          3: <material:name> * amount U
	 *                      ]
	 *                 }
	 *
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if(material() != null) builder.append("{MainMaterial:").append(material());
		else builder.append("{NULL");
		List<CTMaterialStack> list = byProducts();
		if(!list.isEmpty()) builder.append(",ByProducts:[");
		list.forEach(s->builder.append(String.format("%d: %s,",list.indexOf(s),s)));
		if (!list.isEmpty()) builder.reverse().deleteCharAt(0).reverse().append("]}");
		return builder.toString();
	}
}
