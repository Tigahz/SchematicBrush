package me.tigahz.schematicbrush.commands.publicbrush.subcommands;

import com.google.common.base.Joiner;
import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.PublicBrush;
import me.tigahz.schematicbrush.util.SubCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class SaveCommand implements SubCommand {

   @Override
   public String name() {
      return "save";
   }

   @Override
   public boolean execute(SchematicBrush plugin, Player player, String[] args) {
      if (args.length > 1) {
         if (args[1].equalsIgnoreCase(name())) {
            if (args.length >= 2) {
               PublicBrush brush = new PublicBrush(plugin, args[0]);
               String name;
               if (args.length >= 3) {
                  String[] filename = Arrays.copyOfRange(args, 2, args.length);
                  name = Joiner.on(' ').skipNulls().join(filename);
                  brush.saveClipboard(player, name);
               } else {
                  name = UUID.randomUUID().toString();
                  brush.saveClipboard(player, name);
               }
               player.sendMessage(format(plugin.getPrefix() + "Saved the schematic as " + name + ".schem"));
            } else {
               player.sendMessage(format(plugin.getPrefix() + "Usage: //pbrush [brush] save [name]"));
            }
            return true;
         }
      }
      return false;
   }

}
