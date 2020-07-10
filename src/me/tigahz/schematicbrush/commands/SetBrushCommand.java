package me.tigahz.schematicbrush.commands;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.BrushUser;
import me.tigahz.schematicbrush.brush.BrushUtil;
import me.tigahz.schematicbrush.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetBrushCommand implements TabExecutor {

   private SchematicBrush plugin;

   public SetBrushCommand(SchematicBrush plugin) {
      this.plugin = plugin;
      plugin.getCommand("/setbrush").setExecutor(this);
      plugin.getCommand("/setbrush").setTabCompleter(this);
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

      if (command.getName().equalsIgnoreCase("/setbrush")) {
         if (sender instanceof Player) {

            Player player = (Player) sender;
            if (player.hasPermission("schematicbrush.use")) {

               if (args.length > 0) {

                  BrushUtil util = new BrushUtil();
                  if (args[0].equalsIgnoreCase("personal")) {
                     util.getFromList(plugin, player.getUniqueId()).setBrushToPersonal();
                     player.sendMessage(Util.format(plugin.getPrefix() + "Brush set to: Personal"));
                  } else if (args[0].equalsIgnoreCase("public")) {
                     if (args.length >= 2) {
                        util.getFromList(plugin, player.getUniqueId()).setBrushToPublic(args[1]);
                        player.sendMessage(Util.format(plugin.getPrefix() + "Brush set to: Public"));
                        player.sendMessage(Util.format(plugin.getPrefix() + "Public Brush set to: " + args[1]));
                     } else {
                        player.sendMessage(Util.format(plugin.getPrefix() + "Usage: //setbrush [personal/public/-r] <public brush name>"));
                        player.sendMessage(Util.format(plugin.getPrefix() + "Note: -r pastes schematics with a random rotation"));
                     }
                  } else if (args[0].equalsIgnoreCase("-r")) {
                     BrushUser user = util.getFromList(plugin, player.getUniqueId());
                     if (user.getRandomRotation()) {
                        user.setRandomRotation(false);
                        player.sendMessage(Util.format(plugin.getPrefix() + "Random Rotation turned off"));
                     } else {
                        user.setRandomRotation(true);
                        player.sendMessage(Util.format(plugin.getPrefix() + "Random Rotation turned on"));
                     }
                  } else {
                     player.sendMessage(Util.format(plugin.getPrefix() + "Usage: //setbrush [personal/public/-r] <public brush name>"));
                     player.sendMessage(Util.format(plugin.getPrefix() + "Note: -r pastes schematics with a random rotation"));
                  }
               } else {
                  player.sendMessage(Util.format(plugin.getPrefix() + "Usage: //setbrush [personal/public/-r] <public brush name>"));
                  player.sendMessage(Util.format(plugin.getPrefix() + "Note: -r pastes schematics with a random rotation"));
               }
            } else {
               player.sendMessage(Util.format(plugin.getPrefix() + "Sorry, you don't have access to this command!"));
            }
         } else {
            sender.sendMessage(Util.format(plugin.getPrefix() + "Sorry, only players can execute this command!"));
         }
         return true;
      }

      return false;
   }

   private final String[] SUB_COMMANDS = {"public", "personal", "-r"};

   @Override
   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
      final List<String> completions = new ArrayList<>();

      if (args.length == 1) {
         StringUtil.copyPartialMatches(args[0], Arrays.asList(SUB_COMMANDS), completions);
         Collections.sort(completions);
         return completions;
      }

      if (args.length == 2 && args[0].equalsIgnoreCase("public")) {
         StringUtil.copyPartialMatches(args[1], new BrushUtil().getPublicBrushes(plugin), completions);
         Collections.sort(completions);
         return completions;
      }

      return Collections.emptyList();
   }
}
