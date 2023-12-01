package mods.bio.gttweaker.command;

import gregapi.data.CS;
import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;
import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.util.Objects;

public class GTCommand implements ICommandFunction {
	public static final String[] DESCRIPTION = new String[]{"    Gregtech 6 commands:",
			"       /minetweaker gt6 materials/mats/MT", "        Lists all materials",
			"       /minetweaker gt6 prefixes/OP", "                   Lists all Prefixes with amounts",
			"       /minetweaker gt6 recipemaps/RM {recipemap name}", "             Lists all possible RecipeMaps (case insensitive), enter a name to get that recipemap entry coppied to your clipboard.",
			"       /minetweaker gt6 hand {ore}", "             oredict information nd gt handlers about the item your holding"};
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
				if(Objects.nonNull(arguments[1])){
					//TODO implement the bracket handler
					if(Recipe.RecipeMap.RECIPE_MAPS.containsKey(arguments[1].toLowerCase())){
						MineTweakerAPI.logCommand("<recipemap:"+arguments[1].toLowerCase()+"> was coppied into your clipboard." );
						copyToClipboard("<recipemap:"+arguments[1].toLowerCase()+">");
					}
					else MineTweakerAPI.logCommand(arguments[1] + " is not a Valid RecipeMap. Use /mt gt6 RM to get a list of all possible RecipeMaps.");
				}
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
			if (arguments[0].equals("hand") && player != null) {
				// TODO Implement GT Hand recipe for held item
				IItemStack item = player.getCurrentItem();
				if (item != null) {
					OreDictItemData data = OreDictManager.INSTANCE.getAssociation(MineTweakerMC.getItemStack(item),true);
					player.sendChat("Prefix: " + data.mPrefix);
					player.sendChat("Material: "+data.mMaterial);
					player.sendChat("Unifies To: " +data.mUnificationTarget);
					player.sendChat("<ore:"+data +">");
					// copy the <prefix:material> into inventory
					// copies the <ore:prefix-material> into clipboard
					if(Objects.equals(arguments[1],"ore")){
						player.sendChat("was coppied to your clipboard");
						copyToClipboard("<ore:"+data +">");
					}
					else {
						player.sendChat(MineTweakerMC.getIItemStack(data.mUnificationTarget).getName() +" was coppied to your clipboard");
						//player.sendChat("use /minetweaker gt6 hand {ore} , In Order to get the <ore:prefix-material> enrty");
						copyToClipboard(MineTweakerMC.getIItemStack(data.mUnificationTarget).getName());
					}

				} else {
					player.sendChat("No item was found");
				}
			}
		}
	}

	private static void copyToClipboard(String value) {
		StringSelection stringSelection = new StringSelection(value);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, (ClipboardOwner)null);
	}
}
