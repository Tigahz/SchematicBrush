package me.tigahz.schematicbrush.util;

import me.tigahz.schematicbrush.SchematicBrush;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

   private SchematicBrush plugin;

   public CooldownManager(SchematicBrush plugin) {
      this.plugin = plugin;
   }

   private final Map<UUID, Long> cooldowns = new HashMap<>();

   private final long DEFAULT_COOLDOWN = 1;

   public final long getLength() {
      try {
         return plugin.config.get().getLong("duration");
      } catch (Exception e) {
         return DEFAULT_COOLDOWN;
      }
   }

   public void setCooldown(UUID uuid, long time) {
      if (time < 1) cooldowns.remove(uuid);
      else cooldowns.put(uuid, time);
   }

   public long getCooldown(UUID uuid) {
      return cooldowns.getOrDefault(uuid, 0L);
   }

}
