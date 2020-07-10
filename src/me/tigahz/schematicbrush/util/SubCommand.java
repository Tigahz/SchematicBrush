package me.tigahz.schematicbrush.util;

import me.tigahz.schematicbrush.SchematicBrush;
import org.bukkit.entity.Player;

public interface SubCommand {

   String name();

   boolean execute(final SchematicBrush plugin, final Player player, final String[] args);

   default String format(String string) {
      return Util.format(string);
   }

}
