package com.patsore.liveoverflowaddon;

import com.patsore.liveoverflowaddon.commands.CommandExample;
import com.patsore.liveoverflowaddon.hud.HudExample;
import com.patsore.liveoverflowaddon.modules.ModuleExample;
import com.mojang.logging.LogUtils;
import com.patsore.liveoverflowaddon.modules.WorldGuardBypass;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class Addon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("LiveOverflow");
    public static final HudGroup HUD_GROUP = new HudGroup("LiveOverflow");

    @Override
    public void onInitialize() {
        LOG.info("Initializing LiveOverflowAddon");

        // Modules
        Modules.get().add(new WorldGuardBypass());

        // Commands
        Commands.get().add(new CommandExample());

        // HUD
        Hud.get().register(HudExample.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.patsore.liveoverflowaddon";
    }
}
