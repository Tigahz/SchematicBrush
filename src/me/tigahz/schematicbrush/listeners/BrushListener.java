package me.tigahz.schematicbrush.listeners;

import me.tigahz.schematicbrush.SchematicBrush;
import me.tigahz.schematicbrush.brush.BrushUser;
import me.tigahz.schematicbrush.brush.PlayerBrush;
import me.tigahz.schematicbrush.brush.BrushUtil;
import me.tigahz.schematicbrush.brush.PublicBrush;
import me.tigahz.schematicbrush.util.Util;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.concurrent.TimeUnit;

public class BrushListener implements Listener {

   private SchematicBrush plugin;

   public BrushListener(SchematicBrush plugin) {
      this.plugin = plugin;
      this.plugin.registerListener(this);
   }

   @EventHandler
   public void onInteract(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      BrushUtil util = new BrushUtil();

      if (player.getInventory().getItemInMainHand().equals(util.getWand()) && player.getGameMode().equals(GameMode.CREATIVE)) {

         if (event.getHand() == EquipmentSlot.OFF_HAND) return;

         long timeLeft = System.currentTimeMillis() - plugin.cooldownManager.getCooldown(player.getUniqueId());
         if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= plugin.cooldownManager.getLength()) {
            try {
               BrushUser user = util.getFromList(plugin, player.getUniqueId());
               if (user.isUsingPublic()) {
                  PublicBrush brush = user.getPublicBrush(plugin);
                  util.paste(player, brush.getBrush().getRandom(), plugin);
               } else {
                  PlayerBrush brush = new PlayerBrush(plugin, player.getUniqueId());
                  util.paste(player, brush.getBrush().getRandom(), plugin);
               }
            } catch (IllegalArgumentException e) {
               player.sendMessage(Util.format(plugin.getPrefix() + "You have no schematics in your brush!"));
            } catch (NullPointerException e) {
               player.sendMessage(Util.format(plugin.getPrefix() + "Error, please try and re-set your brush!"));
            }
            plugin.cooldownManager.setCooldown(player.getUniqueId(), System.currentTimeMillis());
         } else {
            timeLeft = (plugin.cooldownManager.getLength() - TimeUnit.MILLISECONDS.toMillis(timeLeft)) + (plugin.cooldownManager.getLength() * 1000);
            player.sendMessage(Util.format(plugin.getPrefix() + "Please wait " + timeLeft + "ms"));
         }
         event.setCancelled(true);
      }

   }

}
