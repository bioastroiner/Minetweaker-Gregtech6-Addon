package mods.bio.gttweaker.api.mods.gregtech.oredict;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.oredict.IMaterialFactory")
public interface IMaterialFactory {
	//
	@ZenMethod
	IMaterialFactory setOriginalMod(String modID);

	@ZenMethod
	IMaterialFactory setOriginalMod(String modID, String name);

	@ZenMethod
	IMaterialFactory addIdenticalNames(String... names);

	@ZenMethod
	IMaterialFactory local(String local);

	@ZenMethod
	IMaterialFactory setRGBa(long aR, long aG, long aB, long aA);

	@ZenMethod
	IMaterialFactory setRGBa(int aRBGa);

	@ZenMethod
	IMaterialFactory texture(String text);

	/**
	 * @param tag this block adds tags, defined in GTAPI.TD class
	 *            this parameter is case insensetive.
	 */
	@ZenMethod
	IMaterialFactory add(String tag);

	@ZenMethod
	IMaterialFactory add(String... tags);

	@ZenMethod
	IMaterialFactory qual(float aSpeed, long aDurability, long aQuality);

	@ZenMethod
	IMaterialFactory qual(long aType, double aSpeed, long aDurability, long aQuality);

	@ZenMethod
	IMaterialFactory quality(long qual);

	@ZenMethod
	IMaterialFactory speed(float spd);

	@ZenMethod
	IMaterialFactory duribility(long dur);

	@ZenMethod
	IMaterialFactory heat(long aMeltingPoint);

	@ZenMethod
	IMaterialFactory heat(long aMeltingPoint, long aBoilingPoint);

	@ZenMethod
	IMaterialFactory setStats(long aProtonsAndElectrons, long aNeutrons, long aMeltingPoint, long aBoilingPoint, double aGramPerCubicCentimeter);

	@ZenMethod
	IMaterialFactory setStatsElement(long aProtons, long aElectrons, long aNeutrons, long aAdditionalMass, double aGramPerCubicCentimeter);

	@ZenMethod
	IMaterialFactory steal(IMaterial aStatsToCopy);

	@ZenMethod
	IMaterialFactory stealLooks(IMaterial aStatsToCopy);

	@ZenMethod
	IMaterialFactory stealStatsElement(IMaterial aStatsToCopy);

	@ZenMethod
	IMaterialFactory setDensity(double aGramPerCubicCentimeter);

	@ZenMethod
	IMaterialFactory addSourceOf(IMaterial... aMaterials);

	@ZenMethod
	IMaterialFactory hide();

	@ZenMethod
	IMaterialFactory hide(boolean aHidden);

	@ZenMethod
	IMaterialFactory visName(String... aOreDictNames);

	@ZenMethod
	IMaterialFactory visPrefix(String... aOreDictNames);

	@ZenMethod
	IMaterialFactory visDefault(IMaterial... aMaterials);

	@ZenMethod
	IMaterialFactory lens(byte aColor);

	@ZenMethod
	IMaterialFactory alloyCentrifuge();

	@ZenMethod
	IMaterialFactory alloyElectrolyzer();

	@ZenMethod
	IMaterialFactory alloySimple();

	@ZenMethod
	IMaterialFactory alloyCentrifuge(long aMelt);

	@ZenMethod
	IMaterialFactory alloyElectrolyzer(long aMelt);

	@ZenMethod
	IMaterialFactory alloySimple(long aMelt);

	@ZenMethod
	IMaterialFactory alloyCentrifuge(long aMelt, long aBoil);

	@ZenMethod
	IMaterialFactory alloyElectrolyzer(long aMelt, long aBoil);

	@ZenMethod
	IMaterialFactory alloySimple(long aMelt, long aBoil);

	@ZenMethod
	IMaterialFactory alloyCentrifuge(IMaterial aHeat);

	@ZenMethod
	IMaterialFactory alloyElectrolyzer(IMaterial aHeat);

	@ZenMethod
	IMaterialFactory alloySimple(IMaterial aHeat);

	//
	@ZenMethod
	IMaterial build();
}
