package mods.bio.gttweaker.core;

import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import mods.bio.gttweaker.api.oredict.IMaterial;
import mods.bio.gttweaker.api.oredict.IMaterialStack;
import mods.bio.gttweaker.gregtech.oredict.CTMaterialStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class GregTweakerAPI {
	public static OreDictMaterial getMaterial(IMaterial iMaterial) {
		return iMaterial.getMaterial();
	}

	public static OreDictMaterialStack getMaterialStack(IMaterialStack iMaterialStack) {
		return ((CTMaterialStack) iMaterialStack).get_backingStack();
	}

//	public static OreDictPrefix getPrefix(IPrefix iPrefix){
//		return iPrefix.
//	}

	public static OreDictMaterialStack[] getMaterialStacks(IMaterialStack... iMaterialStacks) {
		return (OreDictMaterialStack[]) Arrays.stream(iMaterialStacks).map(GregTweakerAPI::getMaterialStack).toArray();
	}

	public static ItemStack getItemStackOrNull(IIngredient ingredient) {
		Object internal = ingredient.getInternal();
		if (internal instanceof ItemStack) return (ItemStack) internal;
		else if (internal instanceof String)
			return !OreDictionary.getOres((String) internal).isEmpty() ? OreDictionary.getOres((String) internal)
					.get(0) : null;
		return null;
	}

	public static FluidStack getFluidStackOrNull(IIngredient ingredient) {
		Object internal = ingredient.getInternal();
		if (internal instanceof FluidStack) return (FluidStack) internal;
		return null;
	}

	public static String FORMAT_RECIPE_MAP(Recipe.RecipeMap map) {
		String[] tmp = map.toString().split("\\.");
		return tmp[tmp.length - 1];
	}

	public static Recipe.RecipeMap FORMAT_RECIPE_MAP(String name) {
		Recipe.RecipeMap out = null;
		if (Recipe.RecipeMap.RECIPE_MAPS.containsKey(name)) {
			out = Recipe.RecipeMap.RECIPE_MAPS.get(name);
		}
		if (out == null) for (Recipe.RecipeMap map : Recipe.RecipeMap.RECIPE_MAP_LIST) {
			String[] tmp = map.toString().split("\\.");
			String sName = tmp[tmp.length - 1];
			if (Objects.equals(sName.toLowerCase(), name.toLowerCase())) {
				out = map;
			}
		}
		if (out != null) {
			return out;
		} else {
			MineTweakerAPI.logError(name + " is not a valid recipemap name!");
		}
		return out;
	}

	public enum ScriptProvider {
		AFTER_PREINIT("scripts_afterPreInit", "____REGISTER_GT6_MATERIALS_HERE", null),
		AFTER_INIT("scripts_afterInit", "____REGISTER_GT6_TILEENTITIES_HERE", null);

		static {
			AFTER_PREINIT.hint_content = "import mods.gregtech.oredict.MaterialRegistry;\n" +
					"\n" +
					"var factory = MaterialRegistry.create(32750,\"ExampeliumExamplie\",\"Exampellies Examplium\");\n" +
					"\n" +
					"factory.addIdenticalNames([\"Exampelate Exampelittium\",\"Example Example Example\",\"Exxxxxxx\"]);\n" +
					"//factory.setRGBa(0xFFFFFF); // white\n" +
					"//factory.setRGBa(255,255,255,255);\n" +
					"factory.stealLooks(<material:copper>);\n" +
					"//factory.steal(<material:iron>);\n" +
					"factory.texture(\"DULL\");\n" +
					"factory.qual(5,8,8); // or call .speed, .duribility and .qual alone\n" +
					"factory.heat(3000); // in Kelvin\n" +
					"//factory.hide();\n" +
					"\n" +
					"factory.formula(\"ExAmPl\"); //.tooltip\n" +
					"\n" +
					"// automatically gets set to GTTweaker but you can change it\n" +
					"//factory.setOriginalMod(\"GTTweaker\");\n" +
					"\n" +
					"/* other useful builtin Methods\n" +
					"IMaterialFactory stealStatsElement(IMaterial aStatsToCopy);\n" +
					"IMaterialFactory setDensity(double aGramPerCubicCentimeter);\n" +
					"IMaterialFactory addSourceOf(IMaterial... aMaterials);\n" +
					"IMaterialFactory hide();\n" +
					"IMaterialFactory hide(boolean aHidden);\n" +
					"\n" +
					"// use TD.Tags data, use commands to get a list of all possible tags you can choose from\n" +
					"IMaterialFactory.tag(String tag);\n" +
					"IMaterialFactory.add(String tag);\n" +
					"\n" +
					"// sets visible names\n" +
					"IMaterialFactory visName(String... aOreDictNames);\n" +
					"IMaterialFactory visPrefix(String... aOreDictNames);\n" +
					"IMaterialFactory visDefault(IMaterial... aMaterials);\n" +
					"\n" +
					"IMaterialFactory lens(byte aColor);\n" +
					"IMaterialFactory alloyCentrifuge();\n" +
					"IMaterialFactory alloyElectrolyzer();\n" +
					"IMaterialFactory alloySimple();\n" +
					"IMaterialFactory alloyCentrifuge(long aMelt);\n" +
					"IMaterialFactory alloyElectrolyzer(long aMelt);\n" +
					"IMaterialFactory alloySimple(long aMelt);\n" +
					"IMaterialFactory alloyCentrifuge(long aMelt, long aBoil);\n" +
					"IMaterialFactory alloyElectrolyzer(long aMelt, long aBoil);\n" +
					"IMaterialFactory alloySimple(long aMelt, long aBoil);\n" +
					"IMaterialFactory alloyCentrifuge(IMaterial aHeat);\n" +
					"IMaterialFactory alloyElectrolyzer(IMaterial aHeat);\n" +
					"IMaterialFactory alloySimple(IMaterial aHeat);\n" +
					"*/\n";
			AFTER_INIT.hint_content = "";
		}

		File script_dir;
		File hint_dir;
		String hint_content = null;

		ScriptProvider(String folderName, String hint_name, String hint_content) {
			script_dir = new File(folderName);
			hint_dir = new File(script_dir, hint_name);
			this.hint_content = hint_content;
		}

		public ScriptProviderDirectory create() {
			ScriptProviderDirectory provider = new ScriptProviderDirectory(script_dir);
			if (!script_dir.exists()) {
				if (!script_dir.mkdirs())
					MineTweakerAPI.logError(String.format("\"%s\" Folder was failed to be created!", script_dir));
			}
			if (hint_dir == null) return provider;
			try {
				hint_dir.createNewFile();
				if (hint_content != null) new FileWriter(hint_dir).write(hint_content);
				else MineTweakerAPI.logError("Script Hint file was failed to be created!");
			} catch (IOException e) {
				MineTweakerAPI.logError("Script Hint file was failed to be created!", e);
			}
			return provider;
		}
	}
}
