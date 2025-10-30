package dk.prayser.frozencore;

import dk.prayser.frozencore.listeners.BlockHitFix;
import dk.prayser.frozencore.listeners.DurabilityFix;
import dk.prayser.frozencore.listeners.IronDoorListener;
import dk.prayser.frozencore.utils.Config;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class FrozenCore extends JavaPlugin {
    @Getter
    public static FrozenCore instance;
    private static Config config;
    private static FileConfiguration configYML;

    @Override
    public void onEnable() {
        instance = this;
        initialiseConfigs();
        if (configYML.getBoolean("listeners_enabled.iron_door")) {
            getServer().getPluginManager().registerEvents(new IronDoorListener(), this);
        }
        if (configYML.getBoolean("listeners_enabled.block_hit")) {
            getServer().getPluginManager().registerEvents(new BlockHitFix(), this);
        }
        if (configYML.getBoolean("listeners_enabled.dura_fix")) {
            getServer().getPluginManager().registerEvents(new DurabilityFix(), this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void initialiseConfigs() {
        saveDefaultConfig();
        File file = new File(getDataFolder(), "config.yml");
        if (!(new File(getDataFolder(), "config.yml")).exists())saveResource("config.yml", false);
        config = new Config(this, null, "config.yml");
        configYML = config.getConfig();
        config.checkNonNullValues(configYML);
    }
}
