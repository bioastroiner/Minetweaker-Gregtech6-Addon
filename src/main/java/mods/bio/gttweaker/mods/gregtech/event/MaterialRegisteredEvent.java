package mods.bio.gttweaker.mods.gregtech.event;

import minetweaker.MineTweakerAPI;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.gregtech.event.MaterialRegisteredEvent")
public class MaterialRegisteredEvent {
	// Implement a Material Editor for already existing materials
	public MaterialRegisteredEvent(){
		MineTweakerAPI.logWarning("Materials registered.");
	}
}
