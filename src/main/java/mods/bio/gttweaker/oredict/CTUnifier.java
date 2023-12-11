package mods.bio.gttweaker.oredict;

import gregapi.code.ItemStackContainer;
import gregapi.data.CS;
import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.util.OM;
import gregapi.util.UT;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gregapi.data.CS.*;

@ZenClass("mods.gregtech.oredict.Unifier")
// TODO: Zen Expansion for IOreDict#unify
public class CTUnifier {
	@ZenMethod("unify")
	public static IItemStack unifyItem(IOreDictEntry ore){
		return MineTweakerMC.getIItemStack(OreDictManager.INSTANCE.getStack(ore.getName(),ore.getAmount()));
	}

	@ZenMethod("unify")
	public static IItemStack unifyItem(IItemStack stack) {
		return MineTweakerMC.getIItemStack(OreDictManager.INSTANCE.getStack(T, MineTweakerMC.getItemStack(stack)));
	}

	@Deprecated
	public static List<OreDictMaterialStack> getMaterialItemData(ItemStack aStack){
		OreDictItemData rData = OreDictManager.INSTANCE.getItemData(aStack);
		List<OreDictMaterialStack> aList = new ArrayList<>();
		if(rData == null) {
			MineTweakerAPI.logError(aStack + " dose not contain a valid material");
			return null;
		}
		OreDictMaterialStack[] tByProducts = new OreDictMaterialStack[rData.mByProducts.length];
		for (int i = 0; i < tByProducts.length; i++) {
			tByProducts[i] = OM.stack(rData.mByProducts[i].mMaterial, UT.Code.units(aStack.getMaxDamage()-aStack.getItemDamage(), aStack.getMaxDamage(), rData.mByProducts[i].mAmount, F));
			aList.add(tByProducts[i]);
		}
		return aList;
	}

	public static OreDictItemData makeMaterialItemData(List<OreDictMaterialStack> aList){
		OreDictMaterialStack main = aList.get(0);
		OreDictMaterialStack[] bys = aList.subList(1,aList.size()).toArray(new OreDictMaterialStack[0]);
		return new OreDictItemData(main,bys);
	}

	// removes any instance of a material existing in an itemData from either by pros or main material set
	public static boolean removeItemData(ItemStack aStack, OreDictMaterial aMaterial){
		OreDictItemData rData = null;
		try{
			// TODO REFLECTION MAGIC!!! replace with propper access transformer later...
			Map<ItemStackContainer, OreDictItemData> sItemStack2DataMap = (Map<ItemStackContainer, OreDictItemData>) UT.Reflection.getField(OreDictManager.INSTANCE,"sItemStack2DataMap",true,true).get(OreDictManager.INSTANCE);
			ItemStackContainer container = new ItemStackContainer(aStack);
			if(sItemStack2DataMap == null) return F;
			rData = sItemStack2DataMap.get(container);
			if(rData==null) container = new ItemStackContainer(aStack,W);
			rData = sItemStack2DataMap.get(container);
			if(rData==null && aStack.getItem().isDamageable()){
				container = new ItemStackContainer(aStack, 0);
				rData = sItemStack2DataMap.get(container);
				if (rData != null && rData.mUseVanillaDamage) {
					sItemStack2DataMap.remove(container);
					if(rData.mMaterial != null && rData.mMaterial.mMaterial == aMaterial) rData = new OreDictItemData(null,rData.mByProducts);
					List<OreDictMaterialStack> aList = new ArrayList<>();
					for (OreDictMaterialStack om:rData.mByProducts) {
						if(om.mMaterial != aMaterial) aList.add(om);
					}
					if(aList.size() != rData.mByProducts.length)
						rData = new OreDictItemData(rData.mMaterial,rData.mByProducts);
					sItemStack2DataMap.put(container,rData);
					return T;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return F;
	}

	@ZenMethod
	public static boolean remove(IItemStack aStack, CTMaterial aMaterial){
		if(removeItemData(MineTweakerMC.getItemStack(aStack),aMaterial.material_internal))
			return T;
		MineTweakerAPI.logError(aMaterial + " was not removed from " + aStack); return F;
	}

	public static boolean add(IItemStack aStack, CTMaterial aMaterial, float aAmount){
		new OreDictMaterialStack(aMaterial.material_internal,(long)(U * aAmount));

		return T;
	}
}

