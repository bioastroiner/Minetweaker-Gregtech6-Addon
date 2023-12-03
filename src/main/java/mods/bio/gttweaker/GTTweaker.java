package mods.bio.gttweaker;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.util.IEventHandler;
import mods.bio.gttweaker.command.GTCommand;
import mods.bio.gttweaker.recipe.*;
import mods.bio.gttweaker.recipe.bracket.CTRecipeMapBracketHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Objects;

@cpw.mods.fml.common.Mod(modid = mods.bio.gttweaker.GTTweaker.MOD_ID, name = mods.bio.gttweaker.GTTweaker.MOD_NAME, version = mods.bio.gttweaker.GTTweaker.VERSION)
public final class GTTweaker extends gregapi.api.Abstract_Mod {
	// https://github.com/GTNewHorizons/Minetweaker-Gregtech-5-Addon/blob/master/src/main/java/gttweaker/GTTweaker.java
	public static final String MOD_ID = "GRADLETOKEN_MODID";
	public static final String MOD_NAME = "GRADLETOKEN_MODNAME";
	public static final String VERSION = "GRADLETOKEN_VERSION";
	public static final String GROUPNAME = "GRADLETOKEN_GROUPNAME";
	public static gregapi.code.ModData MOD_DATA = new gregapi.code.ModData(MOD_ID, MOD_NAME);

	//@cpw.mods.fml.common.SidedProxy(modId = MOD_ID, clientSide = "gregapi.api.example.Example_Proxy_Client", serverSide = "gregapi.api.example.Example_Proxy_Server")
	public static gregapi.api.Abstract_Proxy PROXY;

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
		MineTweakerAPI.registerClass(CTRecipe.class);
		MineTweakerAPI.registerClass(CTRecipeFactory.class);
		MineTweakerAPI.registerClass(CTRecipeMap.class);
		MineTweakerAPI.registerClass(CTRecipeMaps.class);
		MineTweakerAPI.registerClass(CTUnifier.class);
		MineTweakerAPI.registerBracketHandler(new CTRecipeMapBracketHandler());

		MineTweakerImplementationAPI.onPostReload(new IEventHandler<MineTweakerImplementationAPI.ReloadEvent>() {
			@Override
			public void handle(MineTweakerImplementationAPI.ReloadEvent reloadEvent) {

			}
		});

		// this happens right before the scripts get loaded so its safe here to remove the pervios added recipes
		MineTweakerImplementationAPI.onReloadEvent(new IEventHandler<MineTweakerImplementationAPI.ReloadEvent>() {
			@Override
			public void handle(MineTweakerImplementationAPI.ReloadEvent reloadEvent) {
				if(!CTRecipeFactory.ADDED_RECIPES.isEmpty()){
					//MineTweakerAPI.logWarning("GT Recipes addition detected!!");
					//List<Map.Entry<Recipe, Recipe.RecipeMap>> REMOVED = new ArrayList<>();
					//MineTweakerAPI.logWarning(CTRecipeFactory.ADDED_RECIPES.size() + " recipes scheduled for removal... (HIDDEN)");
					CTRecipeFactory.ADDED_RECIPES.forEach(e->{
						e.getKey().mHidden = true; // HACK
						e.getKey().mEnabled = false;
						MineTweakerAPI.logCommand(e.getKey() + " has been Hidden due to reloading");
//						if(new CTRecipeMap(e.getValue()).remove(new CTRecipe(e.getKey()))) {
//							// cool
//							REMOVED.add(e);
//						} else {
//							MineTweakerAPI.logWarning(e.getKey() + " could not be removed from " + e.getValue());
//							MineTweakerAPI.logWarning("it has therefore STUCK! please reload the game entirely to fix.");
//						}
					});
					CTRecipeFactory.ADDED_RECIPES.clear();
//					REMOVED.forEach(e->{
//						for (Map.Entry<Recipe, Recipe.RecipeMap> x:CTRecipeFactory.ADDED_RECIPES) {
//							if(x==e){
//								CTRecipeFactory.ADDED_RECIPES.remove(x);
//								break;
//							}
//						}
//					});
//					MineTweakerAPI.logWarning(REMOVED.size() + " recipes have been swipped!");
//					MineTweakerAPI.logWarning(CTRecipeFactory.ADDED_RECIPES.size() + " have been stuck! if this is anything other than 0 then its NOT GOOD!!!!");
				} else MineTweakerAPI.logWarning("No GT Recipes added detected!");
				MineTweakerImplementationAPI.addMineTweakerCommand("gt", GTCommand.DESCRIPTION, GTCommand.INSTANCE);
				// TODO implement a way of reHandling gt MAT DATA during reload for removed recipe compat
			}
		});
		};

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

	public static ItemStack getItemStackOrNull(IIngredient ingredient) {
		Object internal = ingredient.getInternal();
		if (internal instanceof ItemStack) return (ItemStack) internal;
		else if (internal instanceof String) return OreDictionary.getOres((String) internal)
				.size() > 0 ? OreDictionary.getOres((String) internal)
				.get(0) : null;
		return null;
	}

	public static FluidStack getFluidStackOrNull(IIngredient ingredient) {
		Object internal = ingredient.getInternal();
		if (internal instanceof FluidStack) return (FluidStack) internal;
		return null;
	}
}
