package me.tigahz.schematicbrush.commands.publicbrush.subcommands;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.PublicBrush;
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
      if (args.length > 1) {
         if (args[1].equalsIgnoreCase(name())) {
            if (args.length >= 2) {
               try {
                  PublicBrush brush = new PublicBrush(plugin, args[0]);
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
            } else {
               player.sendMessage(format(plugin.getPrefix() + "Usage: //pbrush [brush] list"));
            }
            return true;
         }
      }
      return false;
   }

}
