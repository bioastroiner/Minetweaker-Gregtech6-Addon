package mods.bio.gttweaker.command;

import gregapi.data.CS;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;
import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;

import java.util.Objects;

public class GTCommand implements ICommandFunction {
	public static final String[] DESCRIPTION = new String[]{"    Gregtech 6 commands:",
			"       /minetweaker gt6 materials/mats/MT", "        Lists all materials",
			"       /minetweaker gt6 prefixes/OP", "                   Lists all Prefixes with amounts",
			"       /minetweaker gt6 recipemaps/RM", "             Lists all possible RecipeMaps (case insensitive)"};
	public static GTCommand INSTANCE = new GTCommand();
	@Override
	public void execute(String[] arguments, IPlayer player) {
		if (arguments.length < 1) {
			player.sendChat("Please specify a gt6 command");
		} else {
			if(Objects.equals(arguments[0], "materials") || Objects.equals(arguments[0], "mats")|| Objects.equals(arguments[0], "MT")){
				MineTweakerAPI.logCommand("Materials:");
				OreDictMaterial.MATERIAL_MAP.forEach((name, id)->{
					MineTweakerAPI.logCommand("Material: " + id + ": " + name);
				});
			}
			if(Objects.equals(arguments[0], "prefixes") || Objects.equals(arguments[0], "OP")){
				MineTweakerAPI.logCommand("Prefixes:");
				OreDictPrefix.sPrefixes.forEach((name, prefix)->{
					MineTweakerAPI.logCommand("Prefix: "+ name + " Amount: " + prefix.mAmount/ CS.U + " U");
				});
			}
			if(Objects.equals(arguments[0].toLowerCase(), "recipemaps") || Objects.equals(arguments[0].toLowerCase(), "rm")){
				MineTweakerAPI.logCommand("Recipe Maps:");
				Recipe.RecipeMap.RECIPE_MAPS.forEach((name, map)->{
					MineTweakerAPI.logCommand("RM: " + name );
					MineTweakerAPI.logCommand("----I/O");
					MineTweakerAPI.logCommand(" -----------{Max Item Inputs: " + map.mInputItemsCount + "}");
					MineTweakerAPI.logCommand(" -----------{Min Item Inputs: " + map.mMinimalInputItems + "}");
					MineTweakerAPI.logCommand(" -----------{Max Fluid Inputs: " + map.mInputFluidCount + "}");
					MineTweakerAPI.logCommand(" -----------{Min Fluid Inputs: " + map.mMinimalInputFluids + "}" );
					MineTweakerAPI.logCommand(" -----------{Max Item Outputs: " + map.mOutputItemsCount + "}");
					MineTweakerAPI.logCommand(" -----------{Has Item Min Outputs: " + map.mNeedsOutputs + "}");
					MineTweakerAPI.logCommand(" -----------{Max Fluid Outputs: " + map.mOutputFluidCount + "}");
					MineTweakerAPI.logCommand(" -----------{Has Item Min Fluid Outputs: " + map.mNeedsOutputs + "}" );
					MineTweakerAPI.logCommand("==================================" );
				});
			}
		}
	}
}
