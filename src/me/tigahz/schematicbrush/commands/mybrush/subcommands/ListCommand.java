package me.tigahz.schematicbrush.commands.mybrush.subcommands;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.PlayerBrush;
import me.tigahz.schematicbrush.util.SubCommand;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FilenameUtils;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListCommand implements SubCommand {

   @Override
   public String name() {
      return "list";
   }

   @Override
   public boolean execute(SchematicBrush plugin, Player player, String[] args) {
      if (args.length > 0) {
         if (args[0].equalsIgnoreCase(name())) {
            try {
               PlayerBrush brush = new PlayerBrush(plugin, player.getUniqueId());
               List<String> files = new ArrayList<>();
               for (File file : brush.getFolder().listFiles()) {
                  if (FilenameUtils.getExtension(file.getName()).endsWith("schem") || FilenameUtils.getExtension(file.getName()).endsWith("schematic")) {
                     files.add(file.getName());
                  }
               }
               player.sendMessage(format(plugin.getPrefix() + "Schematics in your brush:"));
               files.forEach(name -> player.sendMessage(format("&7- &e" + name)));
            } catch (Exception e) {
               player.sendMessage(format(plugin.getPrefix() + "Error listing schematics"));
            }
            return true;
         }
      }
      return false;
   }

}
