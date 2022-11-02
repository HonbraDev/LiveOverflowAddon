package com.patsore.liveoverflowaddon.modules;

import com.patsore.liveoverflowaddon.Addon;
import meteordevelopment.meteorclient.systems.modules.Module;

public class ModuleExample extends Module {
    public ModuleExample() {
        super(Addon.CATEGORY, "example", "An example module in a custom category.");
    }
}
