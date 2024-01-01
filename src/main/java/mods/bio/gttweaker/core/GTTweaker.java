package mods.bio.gttweaker.core;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.util.IEventHandler;
import mods.bio.gttweaker.api.mods.gregtech.oredict.*;
import mods.bio.gttweaker.api.mods.gregtech.recipe.IRecipe;
import mods.bio.gttweaker.api.mods.gregtech.recipe.IRecipeFactory;
import mods.bio.gttweaker.api.mods.gregtech.recipe.IRecipeMap;
import mods.bio.gttweaker.core.command.GTCommand;
import mods.bio.gttweaker.core.json.OreDictMaterial_Serializable;
import mods.bio.gttweaker.mods.gregtech.oredict.*;
import mods.bio.gttweaker.mods.gregtech.recipe.CTRecipe;
import mods.bio.gttweaker.mods.gregtech.recipe.CTRecipeMaps;
import mods.bio.gttweaker.mods.gregtech.oredict.bracket.CTMaterialBracketHandler;
import mods.bio.gttweaker.mods.gregtech.oredict.bracket.CTPrefixBracketHandler;
import mods.bio.gttweaker.mods.gregtech.recipe.bracket.CTRecipeMapBracketHandler;
import mods.bio.gttweaker.mods.minetweaker.CTIItemStackExpansion;
import mods.bio.gttweaker.mods.minetweaker.CTILiquidStackExpansion;
import mods.bio.gttweaker.mods.minetweaker.CTIOreDictExpansion;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import java.util.Objects;

@cpw.mods.fml.common.Mod(modid = GTTweaker.MOD_ID, name = GTTweaker.MOD_NAME, version = GTTweaker.VERSION)
public final class GTTweaker extends gregapi.api.Abstract_Mod {
	// https://github.com/GTNewHorizons/Minetweaker-Gregtech-5-Addon/blob/master/src/main/java/gttweaker/GTTweaker.java
	public static final String MOD_ID = "GRADLETOKEN_MODID";
	public static final String MOD_NAME = "GRADLETOKEN_MODNAME";
	public static final String VERSION = "GRADLETOKEN_VERSION";
	public static final String GROUPNAME = "GRADLETOKEN_GROUPNAME";
	public static gregapi.code.ModData MOD_DATA = new gregapi.code.ModData(MOD_ID, MOD_NAME);

	//@cpw.mods.fml.common.SidedProxy(modId = MOD_ID, clientSide = "gregapi.api.example.Example_Proxy_Client", serverSide = "gregapi.api.example.Example_Proxy_Server")
	public static gregapi.api.Abstract_Proxy PROXY;

	public static String FORMAT_RECIPE_MAP(Recipe.RecipeMap map){
		String[] tmp = map.toString().split("\\.");
		return tmp[tmp.length-1];
	}

	public static Recipe.RecipeMap FORMAT_RECIPE_MAP(String name){
		Recipe.RecipeMap out = null;
		if(Recipe.RecipeMap.RECIPE_MAPS.containsKey(name)){
			out = Recipe.RecipeMap.RECIPE_MAPS.get(name);
		}
		if(out == null) for (Recipe.RecipeMap map: Recipe.RecipeMap.RECIPE_MAP_LIST) {
			String[] tmp = map.toString().split("\\.");
			String sName = tmp[tmp.length-1];
			if(Objects.equals(sName.toLowerCase(),name.toLowerCase())){
				out = map;
			}
		}
		if(out!=null){
			return out;
		} else {
			MineTweakerAPI.logError(name + " is not a valid recipemap name!");
		}
		return out;
	}

	public GTTweaker() {
	}

	@Override
	public String getModID() {
		return MOD_ID;
	}

	@Override
	public String getModName() {
		return MOD_NAME;
	}

	@Override
	public String getModNameForLog() {
		return "GTTWEAKER";
	}

	@Override
	public gregapi.api.Abstract_Proxy getProxy() {
		return PROXY;
	}

	// Do not change these 7 Functions. Just keep them this way.
	@cpw.mods.fml.common.Mod.EventHandler
	public final void onPreLoad(cpw.mods.fml.common.event.FMLPreInitializationEvent aEvent) {
		onModPreInit(aEvent);
	}

	@cpw.mods.fml.common.Mod.EventHandler
	public final void onLoad(cpw.mods.fml.common.event.FMLInitializationEvent aEvent) {
		onModInit(aEvent);
	}

	@cpw.mods.fml.common.Mod.EventHandler
	public final void onPostLoad(cpw.mods.fml.common.event.FMLPostInitializationEvent aEvent) {
		onModPostInit(aEvent);
	}

	@cpw.mods.fml.common.Mod.EventHandler
	public final void onServerStarting(cpw.mods.fml.common.event.FMLServerStartingEvent aEvent) {
		onModServerStarting(aEvent);
	}

	@cpw.mods.fml.common.Mod.EventHandler
	public final void onServerStarted(cpw.mods.fml.common.event.FMLServerStartedEvent aEvent) {
		onModServerStarted(aEvent);
	}

	@cpw.mods.fml.common.Mod.EventHandler
	public final void onServerStopping(cpw.mods.fml.common.event.FMLServerStoppingEvent aEvent) {
		onModServerStopping(aEvent);
	}

	@cpw.mods.fml.common.Mod.EventHandler
	public final void onServerStopped(cpw.mods.fml.common.event.FMLServerStoppedEvent aEvent) {
		onModServerStopped(aEvent);
	}

	@Override
	public void onModPreInit2(cpw.mods.fml.common.event.FMLPreInitializationEvent aEvent) {
	}

	@Override
	public void onModInit2(FMLInitializationEvent aEvent) {
		OreDictMaterial_Serializable._INITLIZE();

		MineTweakerAPI.registerClass(IRecipe.class);
		MineTweakerAPI.registerClass(IRecipeFactory.class);
		MineTweakerAPI.registerClass(IRecipeMap.class);

		MineTweakerAPI.registerClass(IMaterial.class);
		MineTweakerAPI.registerClass(IMaterialFactory.class);
		MineTweakerAPI.registerClass(IMaterialStack.class);
		MineTweakerAPI.registerClass(IPrefix.class);
		MineTweakerAPI.registerClass(IMaterialData.class);

		MineTweakerAPI.registerClass(CTRecipeMaps.class);
		MineTweakerAPI.registerClass(CTUnifier.class);

		MineTweakerAPI.registerClass(CTIOreDictExpansion.class);
		MineTweakerAPI.registerClass(CTIItemStackExpansion.class);
		MineTweakerAPI.registerClass(CTILiquidStackExpansion.class);
		MineTweakerAPI.registerClass(CTIMaterialFactoryExpansion.class);

		MineTweakerAPI.registerBracketHandler(new CTRecipeMapBracketHandler());
		MineTweakerAPI.registerBracketHandler(new CTPrefixBracketHandler());
		MineTweakerAPI.registerBracketHandler(new CTMaterialBracketHandler());
		}

	@Override
	public void onModPostInit2(cpw.mods.fml.common.event.FMLPostInitializationEvent aEvent) {
		//MineTweakerAPI.registerClassRegistry(GTTweakerRegistry.class);
	}

	private void prePreInit() {
	}

	private void postPreInit() {
	}

	private void prePostInit() {
	}

	private void postPostInit() {
	}

	@Override
	public void onModServerStarting2(cpw.mods.fml.common.event.FMLServerStartingEvent aEvent) {
		// Insert your ServerStarting Code here and not above
	}

	@Override
	public void onModServerStarted2(cpw.mods.fml.common.event.FMLServerStartedEvent aEvent) {
		// Insert your ServerStarted Code here and not above
	}

	@Override
	public void onModServerStopping2(cpw.mods.fml.common.event.FMLServerStoppingEvent aEvent) {
		// Insert your ServerStopping Code here and not above
	}

	@Override
	public void onModServerStopped2(cpw.mods.fml.common.event.FMLServerStoppedEvent aEvent) {
		// Insert your ServerStopped Code here and not above
	}
}
