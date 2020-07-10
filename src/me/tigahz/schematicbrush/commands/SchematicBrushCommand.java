package me.tigahz.schematicbrush.commands;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.BrushUtil;
import me.tigahz.schematicbrush.util.MessageBuilder;
import me.tigahz.schematicbrush.util.SubCommand;
import me.tigahz.schematicbrush.util.Util;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SchematicBrushCommand implements TabExecutor {

   private SchematicBrush plugin;

   public SchematicBrushCommand(SchematicBrush plugin) {
      this.plugin = plugin;
      plugin.getCommand("schematicbrush").setExecutor(this);
      plugin.getCommand("schematicbrush").setTabCompleter(this);
   }

   private SubCommand historyCommands = new HistoryCommands();

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

      if (command.getName().equalsIgnoreCase("schematicbrush")) {
         if (sender instanceof Player) {

            Player player = (Player) sender;
            if (player.hasPermission("schematicbrush.use")) {

               if (args.length > 0) {
                  if (historyCommands.execute(plugin, player, args)) return true;
                  if (args[0].equalsIgnoreCase("reload")) {
                     if (player.hasPermission("schematicbrush.admin")) {
                        plugin.config.reload();
                        player.sendMessage(Util.format(plugin.getPrefix() + "Reloaded plugin"));
                     } else {
                        player.sendMessage(Util.format(plugin.getPrefix() + "Sorry, you don't have access to this command!"));
                     }
                  } else if (args[0].equalsIgnoreCase("setcooldown")) {
                     if (args.length >= 2) {
                        try {
                           plugin.config.get().set("duration", Long.valueOf(args[1]));
                           plugin.config.save();
                           player.sendMessage(Util.format(plugin.getPrefix() + "Cooldown set to " + args[1] + " seconds"));
                        } catch (NumberFormatException e) { player.sendMessage(Util.format(plugin.getPrefix() + "Invalid number")); }
                     } else {
                        player.sendMessage(Util.format(plugin.getPrefix() + "Usage: /schematicbrush setcooldown <time in seconds>"));
                     }
                  } else if (args[0].equalsIgnoreCase("info")) {
                     player.sendMessage(Util.format(plugin.getPrefix() + "SchematicBrush v" + plugin.getDescription().getVersion() + " by Tigahz"));
                  } else if (args[0].equalsIgnoreCase("help")) {
                     help(player);
                  } else if (args[0].equalsIgnoreCase("wand")) {
                     player.getInventory().addItem(new BrushUtil().getWand());
                     player.sendMessage(Util.format(plugin.getPrefix() + "Click to paste schematic from your chosen brush"));
                     player.sendMessage(Util.format(plugin.getPrefix() + "Use //setbrush to change brush"));
                  } else {
                     help(player);
                  }
               } else {
                  help(player);
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

   private final String[] SUB_COMMANDS = {"info", "help", "reload", "setcooldown", "wand", "undo", "redo"};

   @Override
   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

      if (command.getName().equalsIgnoreCase("schematicbrush")) {
         final List<String> completions = new ArrayList<>();

         if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], Arrays.asList(SUB_COMMANDS), completions);
            Collections.sort(completions);
            return completions;
         }
      }

      return Collections.emptyList();
   }

   private void help(Player player) {
      player.sendMessage(Util.format(plugin.getPrefix() + "SchematicBrush v" + plugin.getDescription().getVersion() + " Help"));

      new MessageBuilder("&7- &e/schematicbrush help &7- Displays this list")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "/schematicbrush help").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e/schematicbrush info &7- Info about the plugin")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "/schematicbrush info").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e/schematicbrush setcooldown &7- Set the brush cooldown")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "/schematicbrush setcooldown [time]").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e/schematicbrush reload &7- Reloads the plugin")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "/schematicbrush reload").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);

      player.sendMessage("");

      new MessageBuilder("&7- &e/schematicbrush wand &7- Gives you your wand")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//mybrush wand").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e/schematicbrush undo &7- Undoes your last job")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "/sb undo").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e/schematicbrush redo &7- Redoes a job")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "/sb redo").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);

      player.sendMessage("");

      new MessageBuilder("&7- &e//setbrush [personal/public] <public brush name> &7- Sets &7your brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//setbrush ").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e//mybrush help &7- Help for your brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//mybrush help").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e//pbrush help &7- Help for setting up a public brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//pbrush help").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
   }

}
