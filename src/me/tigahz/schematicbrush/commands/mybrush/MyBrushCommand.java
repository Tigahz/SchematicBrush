package me.tigahz.schematicbrush.commands.mybrush;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.PlayerBrush;
import me.tigahz.schematicbrush.commands.mybrush.subcommands.*;
import me.tigahz.schematicbrush.util.SubCommand;
import me.tigahz.schematicbrush.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class MyBrushCommand implements TabExecutor {

   private SchematicBrush plugin;

   public MyBrushCommand(SchematicBrush plugin) {
      this.plugin = plugin;
      plugin.getCommand("/mybrush").setExecutor(this);
      plugin.getCommand("/mybrush").setTabCompleter(this);
   }

   private SubCommand saveCommand = new SaveCommand();
   private SubCommand deleteCommand = new DeleteCommand();
   private SubCommand listCommand = new ListCommand();
   private SubCommand clearCommand = new ClearCommand();
   private HelpCommand helpCommand = new HelpCommand();

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

      if (command.getName().equalsIgnoreCase("/mybrush")) {
         if (sender instanceof Player) {

            Player player = (Player) sender;
            if (player.hasPermission("schematicbrush.use")) {

               if (saveCommand.execute(plugin, player, args)) return true;
               if (deleteCommand.execute(plugin, player, args)) return true;
               if (listCommand.execute(plugin, player, args)) return true;
               if (clearCommand.execute(plugin, player, args)) return true;
               if (helpCommand.execute(plugin, player, args)) return true;

               helpCommand.sendHelpMessage(plugin, player);

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

   private final String[] SUB_COMMANDS = {"save", "help", "list", "delete", "clear"};

   @Override
   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

      if (command.getName().equalsIgnoreCase("/mybrush")) {
         final List<String> completions = new ArrayList<>();

         if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], Arrays.asList(SUB_COMMANDS), completions);
            Collections.sort(completions);
            return completions;
         }
         if (args.length == 2) {
            if (args[0].equalsIgnoreCase("delete")) {
               PlayerBrush user = new PlayerBrush(plugin, ((Player) sender).getUniqueId());
               Arrays.asList(user.getFolder().listFiles()).forEach(file -> completions.add(file.getName()));
               Collections.sort(completions);
               return completions;
            }
         }
      }

      return Collections.emptyList();
   }
}
