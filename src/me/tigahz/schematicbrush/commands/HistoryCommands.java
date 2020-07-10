package me.tigahz.schematicbrush.commands;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.BrushUser;
import me.tigahz.schematicbrush.brush.BrushUtil;
import me.tigahz.schematicbrush.util.SubCommand;
import org.bukkit.entity.Player;

public class HistoryCommands implements SubCommand {

   @Override
   public String name (){
      return "";
   }

   @Override
   public boolean execute(SchematicBrush plugin, Player player, String[] args) {
      if (args.length > 0) {
         if (player.hasPermission("schematicbrush.use")) {
            BrushUtil util = new BrushUtil();
            BrushUser user = util.getFromList(plugin, player.getUniqueId());

            if (!user.hasJobCountInit()) {
               user.setJobCountInit(true);
               user.updateJobCount();
            }

            if (args[0].equalsIgnoreCase("undo")) {
               if (user.undo()) player.sendMessage(format(plugin.getPrefix() + "Undone job"));
               else player.sendMessage(format(plugin.getPrefix() + "Nothing left to undo"));
               return true;
            } else if (args[0].equalsIgnoreCase("redo")) {
               if (user.redo()) player.sendMessage(format(plugin.getPrefix() + "Redone job"));
               else player.sendMessage(format(plugin.getPrefix() + "Nothing left to redo"));
               return true;
            }
         } else {
            player.sendMessage(format(plugin.getPrefix() + "Sorry, you don't have access to this command!"));
         }

      }
      return false;
   }

}
