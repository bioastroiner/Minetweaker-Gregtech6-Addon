package mods.bio.gttweaker.core.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gregapi.oredict.OreDictMaterial;

public class OreDictMaterial_Serializable {
	public final transient OreDictMaterial _mat;

	public static void _INITLIZE(){
		//TODO: we dont do anything yet...
//
//		// skip 0 = Empty
//		long milis = System.currentTimeMillis();
//		File materials_folder = new File("gt_materials");
//		materials_folder.mkdir();
//		for (int i = 1; i < OreDictMaterial.MATERIAL_ARRAY.length; i++) {
//			try {
//				if (OreDictMaterial.MATERIAL_ARRAY[i]==null) continue;
//				File toWrite = new File(materials_folder,String.format("%d.json",i));
//				if(toWrite.exists()) toWrite.delete();
//				toWrite.createNewFile();
//				FileWriter writer = new FileWriter(toWrite);
//				writer.write(OreDictMaterial_Serializable.serializeMaterial(OreDictMaterial.MATERIAL_ARRAY[i]));
//				writer.close();
//				System.out.println((String.format("Material with ID %d was Serialized Successfuly.",i)));
//			} catch (Exception ig){
//				ig.printStackTrace();
//			}
//		}
//		System.out.println(String.format("It took %d Seconds to generate the JSON Materials for Gregtech 6!",System.currentTimeMillis() - milis));

	}
	private transient short mID;
	//private final int mHashID;
	public String mNameInternal;
	public short[] mRGBaSolid, mRGBaLiquid, mRGBaGas, mRGBaPlasma;
	public short[][] mRGBa;
	public short[] fRGBaSolid, fRGBaLiquid, fRGBaGas, fRGBaPlasma;
	public short[][] fRGBa;
	//public String mNameLocal;
	public String mOriginalMod;
	//public ItemStack mDictionaryBook = null;
	public String mDescription[], mTooltipChemical;
	public byte mOreMultiplier, mOreProcessingMultiplier;
	public long mFurnaceBurnTime;
	public byte mToolTypes;
	public byte mToolQuality;
	public long mToolDurability;
	public float mToolSpeed;
	public float mHeatDamage;
	public boolean mHidden;
	//public boolean mHasMetallum = F;
	public double mGramPerCubicCentimeter;
	public long mMeltingPoint, mBoilingPoint, mPlasmaPoint;
	//public List<IIconContainer> mTextureSetsBlock = TextureSet.SET_NONE[0].mList, mTextureSetsItems = TextureSet.SET_NONE[1].mList;
	//public ITexture mTextureSolid = null, mTextureSmooth = null, mTextureMolten = null, mTextureDust = null, mTextureGem = null;
	//public final List<TC.TC_AspectStack> mAspects = new ArrayListNoNulls<>(1);
	//public IOreDictConfigurationComponent mComponents = null;
	//public final Set<OreDictMaterial> mReRegistrations = new HashSetNoNulls<>(), mToThis = new HashSetNoNulls<>();
	//public final List<OreDictMaterial> mByProducts = new ArrayListNoNulls<>();
	//public final Set<OreDictMaterial> mAlloyComponentReferences = new HashSetNoNulls<>();
	//public final Set<OreDictMaterial> mSourceOf = new HashSetNoNulls<>();
	//public final List<IOreDictConfigurationComponent> mAlloyCreationRecipes = new ArrayListNoNulls<>();
	//public final List<Achievement> mAchievementsForCreation = new ArrayListNoNulls<>();
	public int mPriorityPrefixIndex;
	public long mLiquidUnit, mGasUnit, mPlasmaUnit;
	public long mNeutrons, mProtons, mElectrons;
	public OreDictMaterial_Serializable(OreDictMaterial matIn) {
		_mat = matIn;
		updateFields();
	}

	public static String serializeMaterial(OreDictMaterial material) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(new OreDictMaterial_Serializable(material));
	}

	public OreDictMaterial deserializeMaterial(String json){
		return new OreDictMaterial_Serializable(new Gson().fromJson(json, OreDictMaterial_Serializable.class)._mat)._mat;
	}

	private void updateFields() {
		mID = _mat.mID;
		mNameInternal = _mat.mNameInternal;
		mRGBaSolid = _mat.mRGBaSolid;
		mRGBaLiquid = _mat.mRGBaLiquid;
		mRGBaGas = _mat.mRGBaGas;
		mRGBaPlasma = _mat.mRGBaPlasma;
		mRGBa = _mat.mRGBa;
		fRGBaSolid = _mat.fRGBaSolid;
		fRGBaLiquid = _mat.fRGBaLiquid;
		fRGBaGas = _mat.fRGBaGas;
		fRGBaPlasma = _mat.fRGBaPlasma;
		fRGBa = _mat.fRGBa;
		mOriginalMod = _mat.mOriginalMod.mID;
		mDescription = _mat.mDescription;
		mTooltipChemical = _mat.mTooltipChemical;
		mOreMultiplier = _mat.mOreMultiplier;
		mToolTypes = _mat.mToolTypes;
		mFurnaceBurnTime = _mat.mFurnaceBurnTime;
		mOreProcessingMultiplier = _mat.mOreProcessingMultiplier;
		mToolDurability = _mat.mToolDurability;
		mToolQuality = _mat.mToolQuality;
		mToolSpeed = _mat.mToolSpeed;
		mHeatDamage = _mat.mHeatDamage;
		mHidden = _mat.mHidden;
		mGramPerCubicCentimeter = _mat.mGramPerCubicCentimeter;
		mMeltingPoint = _mat.mMeltingPoint;
		mBoilingPoint = _mat.mBoilingPoint;
		mPlasmaPoint = _mat.mPlasmaPoint;
		mPriorityPrefixIndex = _mat.mPriorityPrefixIndex;
		mLiquidUnit = _mat.mLiquidUnit;
		mGasUnit = _mat.mGasUnit;
		mPlasmaUnit = _mat.mPlasmaUnit;
		mNeutrons = _mat.mNeutrons;
		mProtons = _mat.mProtons;
		mElectrons = _mat.mElectrons;
	}
	//public OreDictPrefix mPriorityPrefix = null;
	//public OreDictMaterial mTargetRegistration = this;
	//public OreDictMaterial mHandleMaterial = this;

//	public OreDictMaterialStack
//			mTargetCrushing     = OM.stack(this, U),
//			mTargetPulver       = OM.stack(this, U),
//			mTargetSmelting     = OM.stack(this, U),
//			mTargetSolidifying  = OM.stack(this, U),
//			mTargetSmashing     = OM.stack(this, U),
//			mTargetCutting      = OM.stack(this, U),
//			mTargetWorking      = OM.stack(this, U),
//			mTargetForging      = OM.stack(this, U),
//			mTargetBurning      = OM.stack(this, 0), // The remaining Material when being burned. Used for getting the Ashes.
//			mTargetBending      = OM.stack(this, U),
//			mTargetCompressing  = OM.stack(this, U);
//
//	public final Set<OreDictMaterial>
//			mTargetedCrushing     = new HashSetNoNulls<>(F, this),
//			mTargetedPulver       = new HashSetNoNulls<>(F, this),
//			mTargetedSmelting     = new HashSetNoNulls<>(F, this),
//			mTargetedSolidifying  = new HashSetNoNulls<>(F, this),
//			mTargetedSmashing     = new HashSetNoNulls<>(F, this),
//			mTargetedCutting      = new HashSetNoNulls<>(F, this),
//			mTargetedWorking      = new HashSetNoNulls<>(F, this),
//			mTargetedForging      = new HashSetNoNulls<>(F, this),
//			mTargetedBurning      = new HashSetNoNulls<>(F, this),
//			mTargetedBending      = new HashSetNoNulls<>(F, this),
//			mTargetedCompressing  = new HashSetNoNulls<>(F, this);


	//public FluidStack mLiquid, mGas, mPlasma;
	//private final Set<TagData> mTags = new HashSetNoNulls<>();
	//public final List<ObjectStack<Enchantment>> mEnchantmentTools = new ArrayListNoNulls<>(1), mEnchantmentWeapons = new ArrayListNoNulls<>(1), mEnchantmentAmmo = new ArrayListNoNulls<>(1), mEnchantmentRanged = new ArrayListNoNulls<>(1), mEnchantmentArmors = new ArrayListNoNulls<>(1);


}
