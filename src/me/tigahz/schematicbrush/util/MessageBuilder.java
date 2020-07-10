package me.tigahz.schematicbrush.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class MessageBuilder {

   private String format(String string) {
      return ChatColor.translateAlternateColorCodes('&', string);
   }

   private String regular;
   private TextComponent component;

   public MessageBuilder(String regular) {
      this.component = new TextComponent(format(regular));
   }

   public void send(Player player) {
      player.spigot().sendMessage(component);
   }

   public MessageBuilder setClick(ClickEvent.Action action, String string) {
      component.setClickEvent(new ClickEvent(action, format(string)));
      return this;
   }

   public MessageBuilder setHover(HoverEvent.Action action, String string) {
      component.setHoverEvent(new HoverEvent(action, new ComponentBuilder(format(string)).create()));
      return this;
   }

}
