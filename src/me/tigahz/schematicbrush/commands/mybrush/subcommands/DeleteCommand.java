package me.tigahz.schematicbrush.commands.mybrush.subcommands;

import com.google.common.base.Joiner;
import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.PlayerBrush;
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
      if (args.length > 0) {
         if (args[0].equalsIgnoreCase(name())) {
            if (args.length >= 2) {
               PlayerBrush brush = new PlayerBrush(plugin, player.getUniqueId());
               String[] filename = Arrays.copyOfRange(args, 1, args.length);
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
                  player.sendMessage(format(plugin.getPrefix() + "Specified file/folder doesn't exist"));
               }
               return true;
            }
         }
      }
      return false;
   }

}
