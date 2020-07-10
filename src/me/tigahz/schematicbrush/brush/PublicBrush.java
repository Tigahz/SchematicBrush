package me.tigahz.schematicbrush.brush;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.session.ClipboardHolder;
import me.tigahz.schematicbrush.SchematicBrush;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class PublicBrush {

   private String name;
   private SchematicBrush plugin;

   public PublicBrush(SchematicBrush plugin, String name) {
      this.plugin = plugin;
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public String getBrushPath() {
      return "public-brushes" + File.separator + name;
   }

   public boolean setupFolder() {
      return new File(plugin.getDataFolder(), getBrushPath()).mkdirs();
   }

   public boolean deleteFolder() {
      return new File(plugin.getDataFolder(), getBrushPath()).delete();
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
      return new Brush(name, getSchematics());
   }

   public void saveClipboard(Player player, String name) {
      final File file = new File(plugin.getDataFolder(), getBrushPath() + File.separator + name + ".schem");
      WorldEditPlugin worldEdit = (WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit");
      try {
         ClipboardHolder holder = worldEdit.getSession(player).getClipboard();
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
