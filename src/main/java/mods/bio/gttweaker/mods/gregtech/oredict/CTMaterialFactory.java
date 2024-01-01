package mods.bio.gttweaker.mods.gregtech.oredict;

import gregapi.code.ModData;
import gregapi.data.MD;
import gregapi.data.MT;
import gregapi.data.TD;
import gregapi.oredict.OreDictMaterial;
import gregapi.render.TextureSet;
import gregapi.util.UT;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterial;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IMaterialFactory;
import mods.bio.gttweaker.api.mods.gregtech.oredict.IPrefix;
import mods.bio.gttweaker.core.GTTweaker;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Arrays;

public class CTMaterialFactory implements IMaterialFactory {
	final OreDictMaterial backing_material;

	public CTMaterialFactory(short id, @Optional @NotNull String oreDictName, @Optional @Nullable String localName) {
		if (OreDictMaterial.MATERIAL_ARRAY[id] != null || OreDictMaterial.MATERIAL_ARRAY[id] != MT.NULL) {
			MineTweakerAPI.logInfo(String.format("Material with id %d exists, therefore will edit the existing one.", id));
			if (localName != null) OreDictMaterial.MATERIAL_ARRAY[id].mNameLocal = localName;
			// ignoring the oreDictName for existing material
		} else {
			if (oreDictName == null && localName != null) oreDictName = localName;
			if (oreDictName != null && localName == null) localName = oreDictName;
			if (oreDictName == null && localName == null) {
				MineTweakerAPI.logError("Please provide a name for material with id: " + id);
			}
			MineTweakerAPI.apply(new BuildMaterialAction(id, oreDictName, localName));
		}
		backing_material = OreDictMaterial.MATERIAL_ARRAY[id];
		backing_material.setOriginalMod(GTTweaker.MOD_DATA);
	}

	public CTMaterialFactory(IMaterial material) {
		backing_material = material.getMaterial();
	}

	//
	@Override
	@ZenMethod
	public IMaterialFactory setOriginalMod(String modID) {
		backing_material.setOriginalMod(new ModData(modID, modID));
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory setOriginalMod(String modID, String name) {
		backing_material.setOriginalMod(new ModData(modID, name));
		return this;
	}

	@Override
	public IMaterialFactory formula(String formula) {
		return tooltip(formula);
	}

	@Override
	public IMaterialFactory tooltip(String tooltip) {
		backing_material.tooltip(tooltip);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory addIdenticalNames(String... names) {
		backing_material.addIdenticalNames(names);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory local(String local) {
		backing_material.setLocal(local);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory setRGBa(long aR, long aG, long aB, long aA) {
		backing_material.setRGBa(aR, aG, aB, aA);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory setRGBa(int aRBGa) {
		short[] sRGBa = UT.Code.getRGBaArray(aRBGa);
		return setRGBa(sRGBa[0], sRGBa[1], sRGBa[2], sRGBa[3]);
	}

	@Override
	@ZenMethod
	public IMaterialFactory texture(String text) {
		backing_material.setTextures(TextureSet.addTextureSet(MD.GT.mID, false, text.toUpperCase()), TextureSet.addTextureSet(MD.GT.mID, true, text.toUpperCase()));
		return this;
	}


	@Override
	@ZenMethod
	public IMaterialFactory add(String tag) {
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

	@Override
	@ZenMethod
	public IMaterialFactory add(String... tags) {
		IMaterialFactory ret = this;
		for (String tag : tags) {
			ret = add(tag);
		}
		return ret;
	}

	@Override
	public IMaterialFactory tag(String tag) {
		return add(tag);
	}

	@Override
	public IMaterialFactory tag(String... tags) {
		return add(tags);
	}

	@Override
	@ZenMethod
	public IMaterialFactory qual(float aSpeed, long aDurability, long aQuality) {
		backing_material.qual(3, aSpeed, aDurability, aQuality);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory qual(long aType, double aSpeed, long aDurability, long aQuality) {
		backing_material.qual(aType, aSpeed, aDurability, aQuality);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory quality(long qual) {
		backing_material.qual(qual);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory speed(float spd) {
		return qual(spd, backing_material.mToolDurability, backing_material.mToolQuality);
	}

	@Override
	@ZenMethod
	public IMaterialFactory duribility(long dur) {
		return qual(backing_material.mToolDurability, dur, backing_material.mToolQuality);
	}

	@Override
	@ZenMethod
	public IMaterialFactory heat(long aMeltingPoint) {
		backing_material.heat(aMeltingPoint);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory heat(long aMeltingPoint, long aBoilingPoint) {
		backing_material.heat(aMeltingPoint, aBoilingPoint);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory setStats(long aProtonsAndElectrons, long aNeutrons, long aMeltingPoint, long aBoilingPoint, double aGramPerCubicCentimeter) {
		backing_material.setStats(aProtonsAndElectrons, aNeutrons, aMeltingPoint, aBoilingPoint, aGramPerCubicCentimeter);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory setStatsElement(long aProtons, long aElectrons, long aNeutrons, long aAdditionalMass, double aGramPerCubicCentimeter) {
		backing_material.setStats(aProtons, aElectrons, aNeutrons, aAdditionalMass, aGramPerCubicCentimeter);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory steal(IMaterial aStatsToCopy) {
		backing_material.steal(aStatsToCopy.getMaterial());
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory stealLooks(IMaterial aStatsToCopy) {
		backing_material.stealLooks(aStatsToCopy.getMaterial());
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory stealStatsElement(IMaterial aStatsToCopy) {
		backing_material.stealStatsElement(aStatsToCopy.getMaterial());
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory setDensity(double aGramPerCubicCentimeter) {
		backing_material.setDensity(aGramPerCubicCentimeter);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory addSourceOf(IMaterial... aMaterials) {
		backing_material.addSourceOf(Arrays.stream(aMaterials).map(IMaterial::getMaterial).toArray(OreDictMaterial[]::new));
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory hide() {
		backing_material.hide();
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory hide(boolean aHidden) {
		backing_material.hide(aHidden);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory visName(String... aOreDictNames) {
		backing_material.visName(aOreDictNames);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory visPrefix(String... aOreDictNames) {
		backing_material.visPrefix(aOreDictNames);
		return this;
	}

	@Override
	public IMaterialFactory visPrefix(IPrefix... aPrefixes) {
		return visPrefix(Arrays.stream(aPrefixes).map(IPrefix::oreDictName).toArray(String[]::new));
	}

	@Override
	@ZenMethod
	public IMaterialFactory visDefault(IMaterial... aMaterials) {
		backing_material.visDefault(Arrays.stream(aMaterials).map(IMaterial::getMaterial).toArray(OreDictMaterial[]::new));
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory lens(byte aColor) {
		backing_material.lens(aColor);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory alloyCentrifuge() {
		backing_material.alloyCentrifuge();
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory alloyElectrolyzer() {
		backing_material.alloyElectrolyzer();
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory alloySimple() {
		backing_material.alloySimple();
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory alloyCentrifuge(long aMelt) {
		backing_material.alloyCentrifuge(aMelt);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory alloyElectrolyzer(long aMelt) {
		backing_material.alloyElectrolyzer(aMelt);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory alloySimple(long aMelt) {
		backing_material.alloySimple(aMelt);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory alloyCentrifuge(long aMelt, long aBoil) {
		backing_material.alloyCentrifuge(aMelt, aBoil);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory alloyElectrolyzer(long aMelt, long aBoil) {
		backing_material.alloyElectrolyzer(aMelt, aBoil);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory alloySimple(long aMelt, long aBoil) {
		backing_material.alloySimple(aMelt, aBoil);
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory alloyCentrifuge(IMaterial aHeat) {
		backing_material.alloyCentrifuge(aHeat.getMaterial());
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory alloyElectrolyzer(IMaterial aHeat) {
		backing_material.alloyElectrolyzer(aHeat.getMaterial());
		return this;
	}

	@Override
	@ZenMethod
	public IMaterialFactory alloySimple(IMaterial aHeat) {
		backing_material.alloySimple(aHeat.getMaterial());
		return this;
	}

	//
	@Override
	@ZenMethod
	public IMaterial build() {
		return new CTMaterial(backing_material);
	}


	/**
	 * OneWay Material Creator used for already non-existing Materials
	 * , Ideally you need to Reload the whole game for this to take effect.
	 */
	private static class BuildMaterialAction implements IUndoableAction {
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
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			// dont do anything?
		}

		@Override
		public String describe() {
			return String.format("Registering Material: %s with ID: %d",localName,id);
		}

		@Override
		public String describeUndo() {
			return "Cannot remove Material during runtime.";
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

