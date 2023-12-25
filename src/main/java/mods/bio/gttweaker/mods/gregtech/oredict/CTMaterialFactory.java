package mods.bio.gttweaker.mods.gregtech.oredict;

import gregapi.code.ModData;
import gregapi.data.MD;
import gregapi.data.TD;
import gregapi.oredict.OreDictMaterial;
import gregapi.render.TextureSet;
import gregapi.util.UT;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.lang.reflect.Field;
import java.util.Arrays;

@ZenClass("mods.gregtech.oredict.MaterialFactory")
public class CTMaterialFactory {
	final OreDictMaterial backing_material;

	public CTMaterialFactory(short id, String oreDictName, String localName) {
		MineTweakerAPI.apply(new BuildMaterialAction(id, oreDictName, localName));
		backing_material = OreDictMaterial.MATERIAL_ARRAY[id];
	}

	public CTMaterialFactory(CTMaterial material) {
		backing_material = material.getMaterial();
	}

	//
	@ZenMethod
	public CTMaterialFactory setOriginalMod(String modID) {
		backing_material.setOriginalMod(new ModData(modID, modID));
		return this;
	}

	@ZenMethod
	public CTMaterialFactory setOriginalMod(String modID, String name) {
		backing_material.setOriginalMod(new ModData(modID, name));
		return this;
	}

	@ZenMethod
	public CTMaterialFactory addIdenticalNames(String... names) {
		backing_material.addIdenticalNames(names);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory local(String local) {
		backing_material.setLocal(local);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory setRGBa(long aR, long aG, long aB, long aA) {
		backing_material.setRGBa(aR, aG, aB, aA);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory setRGBa(int aRBGa) {
		short[] sRGBa = UT.Code.getRGBaArray(aRBGa);
		return setRGBa(sRGBa[0], sRGBa[1], sRGBa[2], sRGBa[3]);
	}

	@ZenMethod
	public CTMaterialFactory texture(String text) {
		backing_material.setTextures(TextureSet.addTextureSet(MD.GT.mID, false, text.toUpperCase()), TextureSet.addTextureSet(MD.GT.mID, true, text.toUpperCase()));
		return this;
	}


	/**
	 * @param tag this block adds tags, defined in GTAPI.TD class
	 *            this parameter is case insensetive.
	 */
	@ZenMethod
	public CTMaterialFactory add(String tag) {
		try {
			for (Class<?> aClass : TD.class.getClasses()) {
				for (Field field : aClass.getFields()) {
					if (field.getName().equalsIgnoreCase(tag)) {
						backing_material.put(UT.Reflection.getField(null, field.getName()));
						MineTweakerAPI.logInfo(tag + " is valid and was added successfully.");
						return this;
					}
				}
			}
		} catch (Exception ignored) {
			MineTweakerAPI.logError(tag + " is not valid.");
		}
		return this;
	}

	@ZenMethod
	public CTMaterialFactory add(String... tags) {
		CTMaterialFactory ret = this;
		for (String tag : tags) {
			ret = add(tag);
		}
		return ret;
	}

	@ZenMethod
	public CTMaterialFactory qual(float aSpeed, long aDurability, long aQuality) {
		backing_material.qual(3, aSpeed, aDurability, aQuality);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory qual(long aType, double aSpeed, long aDurability, long aQuality) {
		backing_material.qual(aType, aSpeed, aDurability, aQuality);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory quality(long qual) {
		backing_material.qual(qual);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory speed(float spd) {
		return qual(spd, backing_material.mToolDurability, backing_material.mToolQuality);
	}

	@ZenMethod
	public CTMaterialFactory duribility(long dur) {
		return qual(backing_material.mToolDurability, dur, backing_material.mToolQuality);
	}

	@ZenMethod
	public CTMaterialFactory heat(long aMeltingPoint) {
		backing_material.heat(aMeltingPoint);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory heat(long aMeltingPoint, long aBoilingPoint) {
		backing_material.heat(aMeltingPoint, aBoilingPoint);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory setStats(long aProtonsAndElectrons, long aNeutrons, long aMeltingPoint, long aBoilingPoint, double aGramPerCubicCentimeter) {
		backing_material.setStats(aProtonsAndElectrons, aNeutrons, aMeltingPoint, aBoilingPoint, aGramPerCubicCentimeter);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory setStatsElement(long aProtons, long aElectrons, long aNeutrons, long aAdditionalMass, double aGramPerCubicCentimeter) {
		backing_material.setStats(aProtons, aElectrons, aNeutrons, aAdditionalMass, aGramPerCubicCentimeter);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory steal(CTMaterial aStatsToCopy) {
		backing_material.steal(aStatsToCopy.getMaterial());
		return this;
	}

	@ZenMethod
	public CTMaterialFactory stealLooks(CTMaterial aStatsToCopy) {
		backing_material.stealLooks(aStatsToCopy.getMaterial());
		return this;
	}

	@ZenMethod
	public CTMaterialFactory stealStatsElement(CTMaterial aStatsToCopy) {
		backing_material.stealStatsElement(aStatsToCopy.getMaterial());
		return this;
	}

	@ZenMethod
	public CTMaterialFactory setDensity(double aGramPerCubicCentimeter) {
		backing_material.setDensity(aGramPerCubicCentimeter);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory addSourceOf(CTMaterial... aMaterials) {
		backing_material.addSourceOf(Arrays.stream(aMaterials).map(CTMaterial::getMaterial).toArray(OreDictMaterial[]::new));
		return this;
	}

	@ZenMethod
	public CTMaterialFactory hide() {
		backing_material.hide();
		return this;
	}

	@ZenMethod
	public CTMaterialFactory hide(boolean aHidden) {
		backing_material.hide(aHidden);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory visName(String... aOreDictNames) {
		backing_material.visName(aOreDictNames);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory visPrefix(String... aOreDictNames) {
		backing_material.visPrefix(aOreDictNames);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory visDefault(CTMaterial... aMaterials) {
		backing_material.visDefault(Arrays.stream(aMaterials).map(CTMaterial::getMaterial).toArray(OreDictMaterial[]::new));
		return this;
	}

	@ZenMethod
	public CTMaterialFactory lens(byte aColor) {
		backing_material.lens(aColor);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory alloyCentrifuge() {
		backing_material.alloyCentrifuge();
		return this;
	}

	@ZenMethod
	public CTMaterialFactory alloyElectrolyzer() {
		backing_material.alloyElectrolyzer();
		return this;
	}

	@ZenMethod
	public CTMaterialFactory alloySimple() {
		backing_material.alloySimple();
		return this;
	}

	@ZenMethod
	public CTMaterialFactory alloyCentrifuge(long aMelt) {
		backing_material.alloyCentrifuge(aMelt);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory alloyElectrolyzer(long aMelt) {
		backing_material.alloyElectrolyzer(aMelt);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory alloySimple(long aMelt) {
		backing_material.alloySimple(aMelt);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory alloyCentrifuge(long aMelt, long aBoil) {
		backing_material.alloyCentrifuge(aMelt, aBoil);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory alloyElectrolyzer(long aMelt, long aBoil) {
		backing_material.alloyElectrolyzer(aMelt, aBoil);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory alloySimple(long aMelt, long aBoil) {
		backing_material.alloySimple(aMelt, aBoil);
		return this;
	}

	@ZenMethod
	public CTMaterialFactory alloyCentrifuge(CTMaterial aHeat) {
		backing_material.alloyCentrifuge(aHeat.getMaterial());
		return this;
	}

	@ZenMethod
	public CTMaterialFactory alloyElectrolyzer(CTMaterial aHeat) {
		backing_material.alloyElectrolyzer(aHeat.getMaterial());
		return this;
	}

	@ZenMethod
	public CTMaterialFactory alloySimple(CTMaterial aHeat) {
		backing_material.alloySimple(aHeat.getMaterial());
		return this;
	}

	//
	@ZenMethod
	public CTMaterial build() {
		return new CTMaterial(backing_material);
	}


	/**
	 * OneWay Material Creator used for already non-existing Materials
	 * , Ideally you need to Reload the whole game for this to take effect.
	 */
	private static class BuildMaterialAction extends OneWayAction {
		short id;
		String oreDictName, localName;

		public BuildMaterialAction(short id, String oreDictName, String localName) {
			if (id < 32000)
				MineTweakerAPI.logWarning("ID range below 32000, this might cause collisons please use range [32000,32765] for MineTweaker Materials.");
			this.id = id;
			this.oreDictName = oreDictName;
			this.localName = localName;
		}

		@Override
		public void apply() {
			try {
				OreDictMaterial.createMaterial(id, oreDictName, localName);
				MineTweakerAPI.logInfo(String.format("Created material with ID: %d ,OreDict: %s ,Name: %s", id, oreDictName, localName));
			} catch (Exception e) {
				MineTweakerAPI.logError(e.getMessage(), e);
			}
		}

		@Override
		public String describe() {
			return null;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		/**
		 * @param o Just having the Same ID is enough to prevent the action from
		 *          occuring Twice!
		 * @return compares the IDs only
		 */
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;

			if (o == null || getClass() != o.getClass()) return false;

			BuildMaterialAction that = (BuildMaterialAction) o;

			return new EqualsBuilder().append(id, that.id).isEquals();
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder(17, 37).append(id).toHashCode();
		}
	}
}
