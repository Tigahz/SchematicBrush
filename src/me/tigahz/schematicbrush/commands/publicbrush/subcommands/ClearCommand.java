package me.tigahz.schematicbrush.commands.publicbrush.subcommands;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.PublicBrush;
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
      if (args.length > 1) {
         if (args[1].equalsIgnoreCase(name())) {
            PublicBrush brush = new PublicBrush(plugin, args[0]);
            try {
               for (File file : Arrays.asList(brush.getFolder().listFiles())) {
                  if (FilenameUtils.getExtension(file.getName()).endsWith("schem") || FilenameUtils.getExtension(file.getName()).endsWith("schematic")) {
                     file.delete();
                  }
               }
               player.sendMessage(format(plugin.getPrefix() + "Cleared the schematics in the brush " + brush.getName()));
            } catch (NullPointerException e) {
               player.sendMessage(format(plugin.getPrefix() + "Specified brush/file/folder doesn't exist"));
            }
            return true;
         }
      }
      return false;
   }

}
