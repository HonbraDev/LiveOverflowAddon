package com.patsore.liveoverflowaddon.modules;

import meteordevelopment.meteorclient.systems.commands.commands.GamemodeCommand;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerAbilities;

public class GameModeChanger extends Module{
    public GameModeChanger() {
        super(Categories.Misc, "Gamemode Changer", "Change the gamemode");
    }

    @Override
    public void onActivate() {
        super.onActivate();
    }
}
