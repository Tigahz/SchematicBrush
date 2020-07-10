package me.tigahz.schematicbrush.listeners;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.BrushUser;
import me.tigahz.schematicbrush.brush.BrushUtil;
import me.tigahz.schematicbrush.brush.PlayerBrush;
import me.tigahz.schematicbrush.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

   private SchematicBrush plugin;

   public JoinListener(SchematicBrush plugin) {
      this.plugin = plugin;
      this.plugin.registerListener(this);
   }

   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      UUID uuid = event.getPlayer().getUniqueId();

      if (uuid.toString().replace("-", "").equalsIgnoreCase("c9daba714a5d436eb805e110745e6851")) {
         event.getPlayer().sendMessage(Util.format(plugin.getPrefix() + "Server is running SchematicBrush v" + plugin.getDescription().getVersion()));
      }

      PlayerBrush brush = new PlayerBrush(plugin, uuid);
      brush.setupFolder();

      if (new BrushUtil().getFromList(plugin, uuid) == null) {
         BrushUser user = new BrushUser(uuid);
         plugin.users.add(user);
      }
   }

}
