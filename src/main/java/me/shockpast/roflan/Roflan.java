package me.shockpast.roflan;

import me.shockpast.roflan.commands.*;
import me.shockpast.roflan.listeners.*;
import me.shockpast.roflan.runnables.BrandRunnable;
import me.shockpast.roflan.utilities.RLanguage;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import lombok.Getter;

public final class Roflan extends JavaPlugin {
    @Getter
    private static Roflan instance;

    public ProtocolManager protocolManager;
    public PluginManager pluginManager;
    public SharedData data;

    @Override
    public void onEnable() {
        Roflan.instance = this;

        FileConfiguration config = this.getConfig();
        saveDefaultConfig();

        RLanguage.setLanguage(config.getString("main.language"));

        // Accesible Fields that are shared between files.
        protocolManager = ProtocolLibrary.getProtocolManager();
        pluginManager = getServer().getPluginManager();
        data = new SharedData();

        //
        Server server = getServer();
        BukkitScheduler scheduler = server.getScheduler();

        //
        pluginManager.registerEvents(new ChatListener(this), this);
        pluginManager.registerEvents(new PlayerListener(this, data), this);
        pluginManager.registerEvents(new EntityListener(), this);
        pluginManager.registerEvents(new TweakListener(this), this);

        //
        getCommand("vanish").setExecutor(new Vanish(this, data));
        getCommand("invsee").setExecutor(new Invsee());
        getCommand("item").setExecutor(new Item(this));
        getCommand("tweak").setExecutor(new Tweak(this));
        getCommand("fly").setExecutor(new Fly());
        getCommand("pm").setExecutor(new PM(this, data));
        getCommand("reply").setExecutor(new Reply(this, data));

        //
        if (config.getBoolean("features.custom_brand.enabled"))
            scheduler.runTaskTimerAsynchronously(
                this,
                new BrandRunnable()::run,
                config.getLong("features.custom_brand.delay") * 20L,
                config.getLong("features.custom_brand.period") * 20L
            );
    }
}
