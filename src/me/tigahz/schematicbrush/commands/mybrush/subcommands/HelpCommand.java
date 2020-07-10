package me.tigahz.schematicbrush.commands.mybrush.subcommands;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.util.MessageBuilder;
import me.tigahz.schematicbrush.util.SubCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;

public class HelpCommand implements SubCommand {
   @Override
   public String name() {
      return "help";
   }

   @Override
   public boolean execute(SchematicBrush plugin, Player player, String[] args) {
      if (args.length > 0) {
         if (args[0].equalsIgnoreCase(name())) {
            sendHelpMessage(plugin, player);
            return true;
         }
      }
      return false;
   }

   public void sendHelpMessage(SchematicBrush plugin, Player player) {
      player.sendMessage(format(plugin.getPrefix() + "//mybrush - Help"));

      new MessageBuilder("&7- &e//mybrush help &7- Displays this list")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//mybrush help").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e//mybrush save [name] &7- Saves your WorldEdit clipboard to &7your brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//mybrush add [name]").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e//mybrush delete [name] &7- Deletes schematic from your &7brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//mybrush remove [name]").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e//mybrush list &7- List schematics in your brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//mybrush list").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e//mybrush clear &7- Removes all the schematics from your &7brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//mybrush clear").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
   }

}
