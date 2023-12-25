package mods.bio.gttweaker.core.command;

import gregapi.data.CS;
import gregapi.data.TD;
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
import mods.bio.gttweaker.mods.gregtech.oredict.CTMaterialData;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * TODO: Migrate to MC native Commands to support autocompletion and stuff
 */
public class GTCommand implements ICommandFunction {
	public static final String[] DESCRIPTION = new String[]{"    Gregtech 6 commands:",
			"      /minetweaker gt materials/mats/MT", "        Lists all materials",
			"      /minetweaker gt prefixes/OP", "                   Lists all Prefixes with amounts",
			"      /minetweaker gt recipemaps/RM {recipemap name}", "             Lists all possible RecipeMaps (case insensitive), enter a name to get that recipemap entry coppied to your clipboard.",
			"      /minetweaker gt hand {ore}", "             oredict information nd gt handlers about the item your holding",
			"      /minetweaker gt tags", "             prints all available tags for Materials and other things. Sadly it's hard to make a Dynamic documentation for what these Tags do, i hope the names are descriptive enough you can visit TD class in gt6 source code where the javadoc is readble."};
	public static GTCommand INSTANCE = new GTCommand();
	@Override
	public void execute(String[] arguments, IPlayer player) {
		if(player == null) return;
		if (arguments.length < 1) {
			player.sendChat("Please specify a gt6 command");
			Arrays.stream(DESCRIPTION).forEach(player::sendChat);
		} else {
			MAT(arguments, player);
			MATS(arguments, player);
			PREFIXES(arguments, player);
			RECIPE_MAPS(arguments, player);
			HAND(arguments, player);
			if(arguments[0].equals("tags")){
				StringBuilder builder = new StringBuilder();
				builder.append("Available Tags: ");
				try {
					for (Class<?> aClass : TD.class.getClasses()) {
						for (Field field : aClass.getFields()) {
							builder.append(field.getName());
							builder.append(", ");
						}
					}
				} catch (Exception ignored){
					ignored.printStackTrace();
				}
				MineTweakerAPI.logInfo(builder.toString());
			}
		}
	}

	private static void HAND(String[] arguments, IPlayer player) {
		if (arguments[0].equals("hand") && player != null) {
			// TODO Implement GT Hand recipe for held item
			IItemStack item = player.getCurrentItem();
			if (item != null) {
				CTMaterialData data = CTMaterialData.association(item);
				if(data!=null){
				List<String> list = new ArrayList<>();
				list.add("=============================");
				list.add("== OP: " + data.prefix());
				list.add("== MT: "+ data.material());
				list.add("== UNI: " + MineTweakerMC.getIItemStack(data.backingData.getStack(1)));
				list.add("** MineTweaker **");
				list.add("== IOreDict: <ore:"+data.backingData +">");
				list.add("== IItemStack: "+item);
				list.add("=============================");
				list.forEach(player::sendChat);
				list.forEach(MineTweakerAPI::logCommand);
				// copy the <prefix:material> into inventory
				// copies the <ore:prefix-material> into clipboard
				if(arguments.length < 2)
					player.sendChat("Supply additional flags \"ore\" and \"item\" to copy the IOreDict and IItemStack into your clipboard respectively.");
				if(arguments.length > 1 && Objects.equals(arguments[1],"ore")){
					player.sendChat("<ore:"+data.backingData +">" + " was coppied to your clipboard");
					copyToClipboard("<ore:"+data.backingData +">");
				}
				else if(arguments.length > 1 && Objects.equals(arguments[1],"item")) {
					player.sendChat(MineTweakerMC.getIItemStack(data.backingData.getStack(1)).toString() +" was coppied to your clipboard");
					//player.sendChat("use /minetweaker gt6 hand {ore} , In Order to get the <ore:prefix-material> enrty");
					copyToClipboard(MineTweakerMC.getIItemStack(data.backingData.getStack(1)).toString());
				}
				}else player.sendChat("Item { "+item+ "} dose not contain GT associated data!");
			} else player.sendChat("No item was found");
		}
	}

	private static void RECIPE_MAPS(String[] arguments, IPlayer player) {
		// FIXME dosent work
		if(Objects.equals(arguments[0].toLowerCase(), "recipemaps") ||
				Objects.equals(arguments[0].toLowerCase(), "recipemap") ||
				Objects.equals(arguments[0].toLowerCase(), "rm")){
			//rms are 3:
			// gt.recipe.<>
			// gt.fuel.<>
			// gt.recipe.furnacefuel // special

			if(arguments.length > 1 && Objects.nonNull(arguments[1])){
				//TODO implement the bracket handler
				String sInput = arguments[1];
				boolean sucess = false;
				for (Recipe.RecipeMap map: Recipe.RecipeMap.RECIPE_MAP_LIST) {
					String[] tmp = map.toString().split("\\.");
					String sName = tmp[tmp.length-1];
					if(sName.toLowerCase()==sInput.toLowerCase()){
						player.sendChat("<recipemap:"+ sName+"> was coppied into your clipboard." );
						copyToClipboard("<recipemap:"+ sName.toLowerCase()+">");
						logRM(player, map);
						sucess = true;
						break;
					}
				}
				if(!sucess) MineTweakerAPI.logCommand(arguments[1] + " is not a Valid RecipeMap. Use /mt gt recipemaps/rm to get a list of all possible RecipeMaps.");
			} else {
				MineTweakerAPI.logCommand("Recipe Maps: ");
				Recipe.RecipeMap.RECIPE_MAP_LIST.forEach(GTCommand::logRM);
				player.sendChat("List of RecipeMaps generated, see minetweaker.log");
			}
		}
	}

	private static void PREFIXES(String[] arguments, IPlayer player) {
		if(Objects.equals(arguments[0], "prefixes") || Objects.equals(arguments[0].toLowerCase(), "op")){
			MineTweakerAPI.logCommand("Prefixes: ");
			OreDictPrefix.sPrefixes.forEach((name, prefix)->{
				MineTweakerAPI.logCommand("Prefix: "+ name + " Amount: " + ((double)prefix.mAmount)/ CS.UD + " U");
			});
			player.sendChat("List of Prefixes generated, see minetweaker.log");
		}
	}

	private static void MATS(String[] arguments, IPlayer player) {
		if(Objects.equals(arguments[0], "materials") || Objects.equals(arguments[0], "mats")|| Objects.equals(arguments[0].toLowerCase(), "mt")){
			MineTweakerAPI.logCommand("Materials: ________________________________________");
			Arrays.stream(OreDictMaterial.MATERIAL_ARRAY).forEach(GTCommand::logMat);
			player.sendChat("List of Materials generated, see minetweaker.log");
		}
	}

	private static void MAT(String[] arguments, IPlayer player) {
		if(Objects.equals(arguments[0],"material") || Objects.equals(arguments[0],"mat")){
			OreDictMaterial mat = null;
			if(player.getCurrentItem() != null){
				OreDictItemData data =
						OreDictManager.INSTANCE.getAssociation(MineTweakerMC.getItemStack(player.getCurrentItem()),true);
				if(data==null) player.sendChat("The Item in your hand " + player.getCurrentItem() + " dose not contain a GT Material Data.");
				else mat = data.mMaterial.mMaterial;
			}
			else {
				player.sendChat("No Item was found.");
			}
			if(arguments.length > 1){
				String sInput = StringUtils.capitalize(arguments[1].toLowerCase());
				if (OreDictMaterial.MATERIAL_MAP.containsKey(sInput)) {
					mat = OreDictMaterial.MATERIAL_MAP.get(sInput);
				} else {
					player.sendChat(sInput + " is not a valid material!!");
				}
			} else {
				player.sendChat("Please Provide an argument for \"material\"");
				player.sendChat("-  possible arguments are: {name of any gt material}");
				player.sendChat(" ");
			}
			logMat(player, mat);
		}
	}

	private static void logRM(Recipe.RecipeMap map) {
		logRM(null, map);
	}

	private static void logRM(@Nullable IPlayer player, Recipe.RecipeMap map) {
		List<String> list = new ArrayList<>();
		String[] arr = map.toString().split("\\.");
		list.add("Recipe-Map: " + arr[arr.length-1]);
		list.add("Internal: " + map);
		list.add("Recipes: " + map.mRecipeList.size());
		list.add("----I/O");
		list.add(" -----------{Max Item Inputs: " + map.mInputItemsCount + "}");
		list.add(" -----------{Min Item Inputs: " + map.mMinimalInputItems + "}");
		list.add(" -----------{Max Fluid Inputs: " + map.mInputFluidCount + "}");
		list.add(" -----------{Min Fluid Inputs: " + map.mMinimalInputFluids + "}" );
		list.add(" -----------{Max Item Outputs: " + map.mOutputItemsCount + "}");
		list.add(" -----------{Has Item Min Outputs: " + map.mNeedsOutputs + "}");
		list.add(" -----------{Max Fluid Outputs: " + map.mOutputFluidCount + "}");
		list.add(" -----------{Has Item Min Fluid Outputs: " + map.mNeedsOutputs + "}" );
		list.add("==================================" );
		list.forEach(MineTweakerAPI::logCommand);
		if(player != null) list.forEach(player::sendChat);
	}

	private static void logMat(OreDictMaterial mat) {
		logMat(null, mat);
	}

	private static void logMat(@Nullable IPlayer player, OreDictMaterial mat) {
		if(mat !=null){
			List<String> list = new ArrayList<>();
			list.add("=============================");
			list.add("== Material: " + mat);
			// TODO: check for material if it has tool or not
			list.add("** STATS **");
			list.add("== Q: " + mat.mToolQuality);
			list.add("== S: " + mat.mToolSpeed);
			list.add("== D: " + mat.mToolDurability);

			list.add("** WORK **");
			list.add("== W: " + mat.getWeight(CS.U) + "KG per 1U");
			list.add("== Melting: " + mat.mMeltingPoint);

			list.add("** MISC **");
			list.add("== Mod: " + mat.mOriginalMod.mName);
			list.add("== Priority Prefix: " + mat.mPriorityPrefix);
			if(mat.mTooltipChemical != "null")
				list.add("== Chemical Formula: " + mat.mTooltipChemical);
			list.add("=============================");
			list.forEach(MineTweakerAPI::logCommand);
			if(player != null) list.forEach(player::sendChat);
		}
	}

	private static void copyToClipboard(String value) {
		StringSelection stringSelection = new StringSelection(value);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, (ClipboardOwner)null);
	}
}
