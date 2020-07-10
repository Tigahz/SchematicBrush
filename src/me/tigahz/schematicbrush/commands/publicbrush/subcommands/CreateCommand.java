package me.tigahz.schematicbrush.commands.publicbrush.subcommands;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.PublicBrush;
import me.tigahz.schematicbrush.util.SubCommand;
import org.bukkit.entity.Player;

public class CreateCommand implements SubCommand {

   @Override
   public String name() {
      return "create";
   }

   @Override
   public boolean execute(SchematicBrush plugin, Player player, String[] args) {
      if (args.length > 1) {
         if (args[1].equalsIgnoreCase(name())) {
            if (args.length >= 2) {
               PublicBrush brush = new PublicBrush(plugin, args[0]);
               brush.setupFolder();
               player.sendMessage(format(plugin.getPrefix() + "Created the brush " + args[0]));
            } else {
               player.sendMessage(format(plugin.getPrefix() + "Usage: //pbrush [brush] create"));
            }
            return true;
         }
      }
      return false;
   }

}
