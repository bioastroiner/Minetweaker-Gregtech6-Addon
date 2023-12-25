package mods.bio.gttweaker.mods.gregtech.event;

import minetweaker.api.event.IEventHandle;
import minetweaker.api.event.PlayerCraftedEvent;
import minetweaker.util.IEventHandler;

public interface IEventManagerGT {
	public IEventHandle onMaterialRegistered(IEventHandler<PlayerCraftedEvent> ev);
}
