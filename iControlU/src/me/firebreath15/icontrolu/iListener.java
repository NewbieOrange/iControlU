package me.firebreath15.icontrolu;

import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.PacketPlayInArmAnimation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class iListener implements Listener
{
    
    iControlU plugin;
    
    iListener(iControlU c)
    {
        plugin = c;
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        if (p.hasMetadata("iCU_P"))
        {
            e.setCancelled(true);
        }
        else
        {
            if (p.hasMetadata("iCU.H"))
            {
                // attempt to send everyone an arm-swing
                Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
                EntityPlayer ent = ((CraftPlayer) v).getHandle();
                ent.playerConnection.sendPacket(new PacketPlayInArmAnimation());
            }
        }
    }
    
    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e)
    {
        Player p = e.getPlayer();
        if (p.hasMetadata("iCU_P"))
        {
            e.setCancelled(true);
        }
        else
        {
            if (p.hasMetadata("iCU.H"))
            {
                // attempt to send everyone an arm-swing
                Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
                EntityPlayer ent = ((CraftPlayer) v).getHandle();
                ent.playerConnection.sendPacket(new PacketPlayInArmAnimation());
            }
        }
    }
    
    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e)
    {
        Player p = e.getPlayer();
        if (p.hasMetadata("iCU_P"))
        {
            e.setCancelled(true);
        }
        else
        {
            if (p.hasMetadata("iCU.H"))
            {
                // attempt to send everyone an arm-swing
                Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
                EntityPlayer ent = ((CraftPlayer) v).getHandle();
                ent.playerConnection.sendPacket(new PacketPlayInArmAnimation());
            }
        }
    }
    
    @EventHandler
    public void onPlaceBlock(BlockBreakEvent e)
    {
        Player p = e.getPlayer();
        if (p.hasMetadata("iCU_P"))
        {
            e.setCancelled(true);
        }
        else
        {
            if (p.hasMetadata("iCU.H"))
            {
                // attempt to send everyone an arm-swing
                Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
                EntityPlayer ent = ((CraftPlayer) v).getHandle();
                ent.playerConnection.sendPacket(new PacketPlayInArmAnimation());
            }
        }
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e)
    {
        Player p = e.getPlayer();
        if (p.hasMetadata("iCU_P"))
        {
            if (p.hasMetadata("iCU_Chat"))
            {
                p.removeMetadata("iCU_Chat", plugin);
            }
            else
            {
                e.setCancelled(true);
            }
        }
        else
        {
            if (p.hasMetadata("iCU_H"))
            {
                e.setCancelled(true);
                Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
                v.setMetadata("iCU_Chat", new FixedMetadataValue(plugin, true));
                v.chat(e.getMessage());
            }
        }
    }
    
    @EventHandler
    public void stopDamage(EntityDamageEvent e)
    {
        if (e instanceof EntityDamageByEntityEvent)
            return;
        
        if (e.getEntity() instanceof Player)
        {
            Player p = (Player) e.getEntity();
            if (p.hasMetadata("iCU_H"))
            {
                Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
                v.damage(e.getDamage());
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void stopDamage(EntityDamageByEntityEvent e)
    {
        if (e.getEntity() instanceof Player)
        {
            Player p = (Player) e.getEntity();
            if (p.hasMetadata("iCU_H"))
            {
                Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
                v.damage(e.getDamage(), e.getDamager());
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onPlayerRunCommand(PlayerCommandPreprocessEvent e)
    {
        Player p = e.getPlayer();
        if (p.hasMetadata("iCU_P"))
        {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onToggleSprint(PlayerToggleSprintEvent e)
    {
        Player p = e.getPlayer();
        if (p.hasMetadata("iCU_H"))
        {
            Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
            EntityPlayer pv = ((CraftPlayer) v).getHandle();
            pv.setSprinting(e.isSprinting());
        }
        else
        {
            if (p.hasMetadata("iCU_P"))
            {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onToggleSneak(PlayerToggleSneakEvent e)
    {
        Player p = e.getPlayer();
        if (p.hasMetadata("iCU_H"))
        {
            Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
            EntityPlayer pv = ((CraftPlayer) v).getHandle();
            pv.setSneaking(e.isSneaking());
        }
        else
        {
            if (p.hasMetadata("iCU_P"))
            {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        Player p = e.getEntity();
        if (p.hasMetadata("iCU_P"))
        {
            Player c = Bukkit.getPlayer(p.getMetadata("iCU_P").get(0).asString());
            c.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD + "iControlU"
                    + ChatColor.RED + "] " + p.getName() + " was died");
            
            p.removeMetadata("iCU_P", plugin);
            c.removeMetadata("iCU_H", plugin);
            
            c.getInventory().setContents(plugin.inventory.get(c.getName()));
            c.getInventory().setArmorContents(plugin.armor.get(c.getName()));
            
            for (Player player : Bukkit.getServer().getOnlinePlayers())
            {
                player.showPlayer(p);
            }
        }
        else
        {
            if (p.hasMetadata("iCU_H"))
            {
                Player c = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
                c.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD + "iControlU"
                        + ChatColor.RED + "] " + p.getName() + " was died");
                
                c.removeMetadata("iCU_P", plugin);
                c.removeMetadata("iCU_H", plugin);
                
                c.getInventory().setContents(plugin.inventory.get(c.getName()));
                c.getInventory().setArmorContents(plugin.armor.get(c.getName()));
                
                for (Player player : Bukkit.getServer().getOnlinePlayers())
                {
                    player.showPlayer(p);
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerGetKicked(PlayerKickEvent e)
    {
        Player p = e.getPlayer();
        if (p.hasMetadata("iCU_P"))
        {
            Player c = Bukkit.getPlayer(p.getMetadata("iCU_P").get(0).asString());
            c.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD + "iControlU"
                    + ChatColor.RED + "] " + p.getName() + " was disconnected");
            
            p.removeMetadata("iCU_P", plugin);
            c.removeMetadata("iCU_H", plugin);
            
            c.getInventory().setContents(plugin.inventory.get(c.getName()));
            c.getInventory().setArmorContents(plugin.armor.get(c.getName()));
            
            for (Player player : Bukkit.getServer().getOnlinePlayers())
            {
                player.showPlayer(p);
            }
        }
        else
        {
            if (p.hasMetadata("iCU_H"))
            {
                Player c = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
                c.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD + "iControlU"
                        + ChatColor.RED + "] " + p.getName() + " was disconnected");
                
                c.removeMetadata("iCU_P", plugin);
                c.removeMetadata("iCU_H", plugin);
                
                c.getInventory().setContents(plugin.inventory.get(c.getName()));
                c.getInventory().setArmorContents(plugin.armor.get(c.getName()));
                
                for (Player player : Bukkit.getServer().getOnlinePlayers())
                {
                    player.showPlayer(p);
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        if (p.hasMetadata("iCU_P"))
        {
            Player c = Bukkit.getPlayer(p.getMetadata("iCU_P").get(0).asString());
            c.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD + "iControlU"
                    + ChatColor.RED + "] " + p.getName() + " was disconnected");
            
            p.removeMetadata("iCU_P", plugin);
            c.removeMetadata("iCU_H", plugin);
            
            c.getInventory().setContents(plugin.inventory.get(c.getName()));
            c.getInventory().setArmorContents(plugin.armor.get(c.getName()));
            
            for (Player player : Bukkit.getServer().getOnlinePlayers())
            {
                player.showPlayer(p);
            }
        }
        else
        {
            if (p.hasMetadata("iCU_H"))
            {
                Player c = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
                c.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD + "iControlU"
                        + ChatColor.RED + "] " + p.getName() + " was disconnected");
                
                c.removeMetadata("iCU_P", plugin);
                c.removeMetadata("iCU_H", plugin);
                
                c.getInventory().setContents(plugin.inventory.get(c.getName()));
                c.getInventory().setArmorContents(plugin.armor.get(c.getName()));
                
                for (Player player : Bukkit.getServer().getOnlinePlayers())
                {
                    player.showPlayer(p);
                }
            }
        }
    }
}
