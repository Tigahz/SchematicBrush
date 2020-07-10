package me.tigahz.schematicbrush.commands.publicbrush;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.BrushUtil;
import me.tigahz.schematicbrush.brush.PublicBrush;
import me.tigahz.schematicbrush.commands.publicbrush.subcommands.*;
import me.tigahz.schematicbrush.util.SubCommand;
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

public class PublicBrushCommand implements TabExecutor {

   private SchematicBrush plugin;

   public PublicBrushCommand(SchematicBrush plugin) {
      this.plugin = plugin;
      plugin.getCommand("/publicbrush").setExecutor(this);
      plugin.getCommand("/publicbrush").setTabCompleter(this);
   }

   private SubCommand createCommand = new CreateCommand();
   private SubCommand saveCommand = new SaveCommand();
   private SubCommand deleteCommand = new DeleteCommand();
   private SubCommand listCommand = new ListCommand();
   private SubCommand clearCommand = new ClearCommand();
   private SubCommand removeCommand = new RemoveCommand();
   private HelpCommand helpCommand = new HelpCommand();

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

      if (command.getName().equalsIgnoreCase("/publicbrush")) {
         if (sender instanceof Player) {

            Player player = (Player) sender;
            if (player.hasPermission("schematicbrush.admin")) {

               if (helpCommand.execute(plugin, player, args)) return true;
               if (createCommand.execute(plugin, player, args)) return true;
               if (saveCommand.execute(plugin, player, args)) return true;
               if (deleteCommand.execute(plugin, player, args)) return true;
               if (clearCommand.execute(plugin, player, args)) return true;
               if (removeCommand.execute(plugin, player, args)) return true;
               if (listCommand.execute(plugin, player, args)) return true;

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

   private final String[] SUB_COMMANDS = {"save", "help", "list", "delete", "create", "clear", "remove"};

   @Override
   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

      if (command.getName().equalsIgnoreCase("/publicbrush")) {
         final List<String> completions = new ArrayList<>();

         if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], new BrushUtil().getPublicBrushes(plugin), completions);
            Collections.sort(completions);
            return completions;
         }
         if (args.length == 2) {
            StringUtil.copyPartialMatches(args[1], Arrays.asList(SUB_COMMANDS), completions);
            Collections.sort(completions);
            return completions;
         }
         if (args.length == 3) {
            if (args[1].equalsIgnoreCase("delete")) {
               PublicBrush brush = new PublicBrush(plugin, args[0]);
               Arrays.asList(brush.getFolder().listFiles()).forEach(file -> completions.add(file.getName()));
               Collections.sort(completions);
               return completions;
            }
         }
      }

      return Collections.emptyList();
   }
}
