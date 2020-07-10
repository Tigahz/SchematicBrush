package me.tigahz.schematicbrush.brush;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import me.tigahz.schematicbrush.SchematicBrush;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import static me.tigahz.schematicbrush.util.Util.format;

public class BrushUtil {

   public ItemStack getWand() {
      ItemStack item = new ItemStack(Material.BLAZE_ROD);
      ItemMeta meta = item.getItemMeta();
      meta.setDisplayName(format("&7&l> &8&l> &eBrush &6Wand &8&l< &7&l<"));
      meta.addEnchant(Enchantment.LUCK, 1, false);
      meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      item.setItemMeta(meta);
      return item;
   }

   public void paste(final Player player, final File file, SchematicBrush plugin) {
      ClipboardFormat format = ClipboardFormats.findByFile(file);

      try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {

         Clipboard clipboard = reader.read();
         ClipboardHolder holder = new ClipboardHolder(clipboard);
         Location location = player.getLocation();

         BrushUser user = new BrushUtil().getFromList(plugin, player.getUniqueId());

         if (user.getEditSession() != null) {
            user.getSessions().add(user.getEditSession());
         }
         EditSession editSession = user.getLocalSession().createEditSession(BukkitAdapter.adapt(player));

         if (user.getJobCount() == 0) {
            user.getSessions().clear();
            user.getSessions().add(editSession);
            user.updateJobCount();
         } else {
            if (user.getJobCount() < user.getSessions().size()) {
               int difference = user.getSessions().size() - user.getJobCount();
               while (difference > 0) {
                  user.getSessions().remove(user.getSessions().size() -1);
                  difference = difference - 1;
               }
               user.updateJobCount();
            }
            user.getSessions().add(editSession);
            user.setJobCount(user.getJobCount() + 1);
         }

         AffineTransform transform = new AffineTransform();
         double cardinal;
         if (user.getRandomRotation()) cardinal = new Random().nextInt(4);
         else cardinal = getDirection(player);
         transform = transform.rotateY(cardinal * 90);
         holder.setTransform(holder.getTransform().combine(transform));

         Operation operation = holder
               .createPaste(editSession)
               .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
               .ignoreAirBlocks(true)
               .copyBiomes(true)
               .build();

         Operations.completeLegacy(operation);
         editSession.flushSession();

         player.sendMessage(format(plugin.getPrefix() + "Pasted " + editSession.getBlockChangeCount() + " blocks"));

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public double getDirection(Player player) {
      double rotation = (player.getLocation().getYaw() - 180) % 360;
      if (rotation < 0) rotation += 360;

      if (0 <= rotation && rotation < 45) return 2;
      else if (45 <= rotation && rotation < 135) return 1;
      else if (135 <= rotation && rotation < 225) return 0;
      else if (225 <= rotation && rotation < 315) return 3;
      else if (315 <= rotation && rotation < 360)return 2;
      return 0;
   }

   public List<String> getPublicBrushes(SchematicBrush plugin) {
      List<String> names = new ArrayList<>();
      Arrays.asList(new File(plugin.getDataFolder(), "public-brushes").listFiles(File::isDirectory)).forEach(file -> names.add(file.getName()));
      return names;
   }

   public BrushUser getFromList(SchematicBrush plugin, UUID uuid) {
      for (BrushUser user : plugin.users) {
         if (user.getUniqueId().equals(uuid)) return user;
      }
      return null;
   }

}
