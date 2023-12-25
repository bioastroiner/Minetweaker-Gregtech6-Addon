//package mods.bio.gttweaker.core.command;
//
//import gregapi.data.MT;
//import gregapi.oredict.OreDictMaterial;
//import gregapi.render.TextureSet;
//import gregapi.util.UT;
//import minetweaker.MineTweakerAPI;
//import net.minecraft.command.CommandBase;
//import net.minecraft.command.ICommandSender;
//import net.minecraft.util.ChatComponentText;
//import org.apache.commons.lang3.math.NumberUtils;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static gregapi.util.UT.Code.*;
//
//public class MaterialRegistryCommand extends CommandBase {
//	private static void createMaterial(ICommandSender aSender, short aID, String aName, int aColor,String text) {
//		TextureSet[] set = TextureSet.SET_NONE;
//		try {
//			if(TextureSet.class.getField("SET_"+text) != null){
//				set = (TextureSet[]) TextureSet.class.getField("SET_"+text).get(null);
//			}
//		} catch (Exception e) {
//			MineTweakerAPI.logError(text + " is not a valid TEXTURE SET");
//		}
//		OreDictMaterial.createMaterial(aID, aName, aName).setRGBa(getR(aColor), getG(aColor), getB(aColor), getA(aColor))
//				.setTextures(set).setStatsEnergetic();
//		aSender.addChatMessage(new ChatComponentText(String.format("Material with %s as Name scedueld to be added please Restard the game.", aName)));
//		aSender.addChatMessage(new ChatComponentText(String.format("Note that you can edit the Materials created as Jsons and even create new ones.")));
//	}
//
//	@Override
//	public String getCommandName() {
//		return "Material";
//	}
//
//	@Override
//	public List getCommandAliases() {
//		return Arrays.asList("material", "mat", "materialGT");
//	}
//
//	@Override
//	public String getCommandUsage(ICommandSender sender) {
//		return null;
//	}
//
//	@Override
//	public void processCommand(ICommandSender sender, String[] args) {
//		if (args.length > 1) {
//			if (args[1].equals("create")) {
//				// material create {id} {name} {color} {} {} {}
//				if (args.length > 5) {
//					int color = 0;
//					if (args[3].startsWith("0x"))
//						color = Integer.decode(args[3]);
//					if (NumberUtils.isNumber(args[3]))
//						color = Integer.parseInt(args[3]);
//					else throw new IllegalArgumentException("Not Implemented to use Literal Colors: " + args[3]);
//					createMaterial(sender, Short.parseShort(args[2]), args[3], color,args[5]);
//				}
//			}
//			if (args[1].equals("get")) {
//				String mat_name = args[1];
//			}
//		}
//	}
//
//	@Override
//	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
//		if (args.length == 1) {
//			return getListOfStringsMatchingLastWord(args, "create", "get");
//		}
//		if (args.length == 2) {
//			return getListOfStringsMatchingLastWord(args, OreDictMaterial.MATERIAL_MAP.keySet().toArray(new String[0]));
//		}
//		if (args.length == 3) {
//			if (args[2].equals("create")) {
//				// get a list of all availble IDs
//				List<Integer> freeIDs = new ArrayList<>();
//				for (int i = OreDictMaterial.MATERIAL_ARRAY.length - 1; i >= 0; i--) {
//					if (OreDictMaterial.MATERIAL_ARRAY[i] == null || OreDictMaterial.MATERIAL_ARRAY[i] == MT.NULL)
//						freeIDs.add(i);
//				}
//				return getListOfStringsMatchingLastWord(args, freeIDs.stream().map(Object::toString).toArray(String[]::new));
//			}
//		}
//		if (args.length == 4) {
//			//
//		}
//		if (args.length == 5) {
//			if (args[2].equals("create")) {
//				// colors
//				return getListOfStringsMatchingLastWord(args, "0xFFFFFF", "White", "Orange", "Magenta", "Light_Blue", "Yellow", "Lime", "Pink", "Gray", "Light_Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black");
//			}
//		}
//		if (args.length == 6) {
//			if (args[2].equals("create")) {
//				// TEXT
//				return getListOfStringsMatchingLastWord(args, "NONE", "DULL", "RAD", "HEX", "RUBY", "OPAL", "LEAF", "SAND", "FINE", "FOOD", "WOOD", "CUBE", "FIERY", "GAS", "FLUID", "PLASMA", "ROUGH", "STONE", "SPACE", "PAPER", "GLASS", "FLINT", "LAPIS", "SHINY", "RUBBER", "SHARDS", "POWDER", "COPPER", "QUARTZ", "EMERALD", "DIAMOND", "LIGNITE", "REDSTONE", "MAGNETIC", "METALLIC", "PRISMARINE", "CUBE_SHINY", "NETHERSTAR", "GEM_VERTICAL", "GEM_HORIZONTAL");
//			}
//		}
//		return super.addTabCompletionOptions(sender, args);
//	}
//}
