package me.tigahz.schematicbrush.commands.publicbrush.subcommands;

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
      if (args.length > 1) {
         if (args[0].equalsIgnoreCase(name())) {
            sendHelpMessage(plugin, player);
            return true;
         }
      }
      return false;
   }

   public void sendHelpMessage(SchematicBrush plugin, Player player) {
      player.sendMessage(format(plugin.getPrefix() + "//publicbrush - Help"));

      new MessageBuilder("&7- &e//pbrush help &7- Displays this list")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//pbrush help").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e//pbrush [brush] create [name] &7- Create a brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//pbrush [brush] create [name]").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e//pbrush [brush] remove &7- Deletes the brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//mybrush [brush] remove").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e//pbrush [brush] save [name] &7- Saves your WorldEdit &7clipboard to a public brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//pbrush [brush] add [name]").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e//pbrush [brush] delete [name] &7- Deletes schematic from a &7public brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//pbrush [brush] remove [name]").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e//pbrush [brush] list &7- List schematics in your brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//pbrush [brush] list").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
      new MessageBuilder("&7- &e//pbrush [brush] clear &7- Removes all the schematics from &7your brush")
            .setClick(ClickEvent.Action.SUGGEST_COMMAND, "//pbrush [brush] clear").setHover(HoverEvent.Action.SHOW_TEXT, "&7Click to execute command")
            .send(player);
   }

}
