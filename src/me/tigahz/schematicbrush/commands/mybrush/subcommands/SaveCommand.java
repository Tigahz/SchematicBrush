package me.tigahz.schematicbrush.commands.mybrush.subcommands;

import com.google.common.base.Joiner;
import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.PlayerBrush;
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
      if (args.length > 0) {
         if (args[0].equalsIgnoreCase(name())) {
            PlayerBrush brush = new PlayerBrush(plugin, player.getUniqueId());
            String name;
            if (args.length >= 2) {
               String[] filename = Arrays.copyOfRange(args, 1, args.length);
               name = Joiner.on(' ').skipNulls().join(filename);
               brush.saveClipboard(name);
            } else {
               name = UUID.randomUUID().toString();
               brush.saveClipboard(name);
            }
            player.sendMessage(format(plugin.getPrefix() + "Saved the schematic as " + name + ".schem"));
            return true;
         }
      }
      return false;
   }

}
