package me.tigahz.schematicbrush.commands.mybrush.subcommands;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.PlayerBrush;
import me.tigahz.schematicbrush.util.SubCommand;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FilenameUtils;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;

public class ClearCommand implements SubCommand {

   @Override
   public String name() {
      return "clear";
   }

   @Override
   public boolean execute(SchematicBrush plugin, Player player, String[] args) {
      if (args.length > 0) {
         if (args[0].equalsIgnoreCase(name())) {
            PlayerBrush brush = new PlayerBrush(plugin, player.getUniqueId());
            try {
               for (File file : Arrays.asList(brush.getFolder().listFiles())) {
                  if (FilenameUtils.getExtension(file.getName()).endsWith("schem") || FilenameUtils.getExtension(file.getName()).endsWith("schematic")) {
                     file.delete();
                  }
               }
               player.sendMessage(format(plugin.getPrefix() + "Cleared the schematics in your brush"));
            } catch (NullPointerException e) {
               player.sendMessage(format(plugin.getPrefix() + "Specified brush/file/folder doesn't exist"));
            }
            return true;
         }
      }
      return false;
   }

}
