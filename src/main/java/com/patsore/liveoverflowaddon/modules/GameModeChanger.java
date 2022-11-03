package com.patsore.liveoverflowaddon.modules;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.world.GameMode;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;


import static com.mojang.brigadier.Command.SINGLE_SUCCESS;


public class GameModeChanger extends Module{
    public GameModeChanger() {
        super(Categories.Misc, "Gamemode changer", "Changes the gamemode to survival upon joining a server");

    }

    @EventHandler
    public void EntityJoinWorldEvent() {
    mc.interactionManager.setGameMode(GameMode.SURVIVAL);

    }

    @Override
    public void onActivate() {
        super.onActivate();
    }
}
