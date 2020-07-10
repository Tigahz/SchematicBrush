package me.tigahz.schematicbrush.brush;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.session.ClipboardHolder;
import me.tigahz.schematicbrush.SchematicBrush;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class PlayerBrush {

   private UUID uuid;
   private SchematicBrush plugin;

   public PlayerBrush(SchematicBrush plugin, UUID uuid) {
      this.plugin = plugin;
      this.uuid = uuid;
   }

   public UUID getUniqueId() {
      return uuid;
   }

   public Player getPlayer() {
      return Bukkit.getPlayer(uuid);
   }

   public String getBrushPath() {
      return "player-brushes" + File.separator + uuid.toString();
   }

   public boolean setupFolder() {
      return new File(plugin.getDataFolder(), getBrushPath()).mkdirs();
   }

   public List<File> getSchematics() {
      setupFolder();
      List<File> files = new ArrayList<>();
      Arrays.asList(Objects.requireNonNull(new File(plugin.getDataFolder(), getBrushPath()).listFiles())).forEach(file -> {
         if (file.getName().endsWith(".schem") || file.getName().endsWith(".schematic")) files.add(file);
      });
      return files;
   }

   public Brush getBrush() {
      return new Brush(uuid.toString(), getSchematics());
   }

   public void saveClipboard(String name) {
      final File file = new File(plugin.getDataFolder(), getBrushPath() + File.separator + name + ".schem");
      WorldEditPlugin worldEdit = (WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit");
      try {
         ClipboardHolder holder = worldEdit.getSession(getPlayer()).getClipboard();
         try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))) {
            writer.write(holder.getClipboard());
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public File getFolder() {
      return new File(plugin.getDataFolder(), getBrushPath());
   }

}
