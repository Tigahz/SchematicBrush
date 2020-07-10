package me.tigahz.schematicbrush.commands.publicbrush.subcommands;

import com.google.common.base.Joiner;
import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.PublicBrush;
import me.tigahz.schematicbrush.util.SubCommand;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;

public class DeleteCommand implements SubCommand {

   @Override
   public String name() {
      return "delete";
   }

   @Override
   public boolean execute(SchematicBrush plugin, Player player, String[] args) {
      if (args.length > 1) {
         if (args[1].equalsIgnoreCase(name())) {
            if (args.length >= 3) {
               PublicBrush brush = new PublicBrush(plugin, args[0]);
               String[] filename = Arrays.copyOfRange(args, 2, args.length);
               String name = Joiner.on(' ').skipNulls().join(filename);
               try {
                  for (File file : Arrays.asList(brush.getFolder().listFiles())) {
                     if (file.getName().startsWith(name.split("\\.(?=[^\\.]+$)")[0]) || file.getName().equalsIgnoreCase(name)) {
                        file.delete();
                        player.sendMessage(format(plugin.getPrefix() + "Schematic " + file.getName() + " deleted"));
                        return true;
                     }
                  }
                  player.sendMessage(format(plugin.getPrefix() + "Specified brush/file/folder doesn't exist"));
               } catch (NullPointerException e) {
                  player.sendMessage(format(plugin.getPrefix() + "Specified brush/file/folder doesn't exist"));
               }
            } else {
               player.sendMessage(format(plugin.getPrefix() + "Usage: //pbrush [brush] delete [name]"));
            }
            return true;
         }
      }
      return false;
   }

}
