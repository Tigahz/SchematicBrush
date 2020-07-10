package me.tigahz.schematicbrush;

import me.tigahz.schematicbrush.brush.BrushUser;
import me.tigahz.schematicbrush.commands.SchematicBrushCommand;
import me.tigahz.schematicbrush.commands.SetBrushCommand;
import me.tigahz.schematicbrush.commands.mybrush.MyBrushCommand;
import me.tigahz.schematicbrush.commands.publicbrush.PublicBrushCommand;
import me.tigahz.schematicbrush.listeners.*;
import me.tigahz.schematicbrush.util.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class SchematicBrush extends JavaPlugin {

   @Override
   public void onEnable() {

      new File(this.getDataFolder(), "public-brushes").mkdirs();

      config = new Config(this);
      config.create();
      cooldownManager = new CooldownManager(this);

      users = new ArrayList<>();
      Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(Util.format(getPrefix() + "SchematicBrush will no longer work, unless you relog")));

      new JoinListener(this);
      new BrushListener(this);
      new MyBrushCommand(this);
      new SchematicBrushCommand(this);
      new PublicBrushCommand(this);
      new SetBrushCommand(this);

      int pluginId = 7878;
      MetricsLite metrics = new MetricsLite(this, pluginId);
   }

   @Override
   public void onDisable() {}

   public void registerListener(Listener listener) {
      this.getServer().getPluginManager().registerEvents(listener, this);
   }

   public final String getPrefix() {
      return "&e&lSchematic&6&lBrush &8&l> &7";
   }

   public Config config;

   public CooldownManager cooldownManager;

   public List<BrushUser> users;

}
