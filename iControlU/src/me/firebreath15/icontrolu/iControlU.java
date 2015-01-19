package me.firebreath15.icontrolu;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

public class iControlU extends JavaPlugin
{
    
    public HashMap<String, ItemStack[]> inventory = new HashMap<String, ItemStack[]>();
    public HashMap<String, ItemStack[]> armor = new HashMap<String, ItemStack[]>();
    
    public void onEnable()
    {
        this.getServer().getPluginManager().registerEvents(new iListener(this), this);
    }
    
    // Made by FireBreath15
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String args[])
    {
        
        if (cmd.getName().equalsIgnoreCase("icu"))
        {
            if (sender instanceof Player)
            {
                Player p = (Player) sender;
                if (args.length == 0 || args.length > 2)
                {
                    // show help menu. they got their arguments wrong!
                    p.sendMessage(ChatColor.YELLOW
                            + "==========[ iControlU Help v1.8.0]==========");
                    p.sendMessage(ChatColor.BLUE + "/icu control <player>"
                            + ChatColor.GREEN + " Enter Control Mode with <player>.");
                    p.sendMessage(ChatColor.BLUE + "/icu stop" + ChatColor.GREEN
                            + " Exit Control Mode.");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.DARK_PURPLE
                            + "Created by FireBreath15, updated by chengzi");
                    p.sendMessage(ChatColor.YELLOW
                            + "==========[ iControlU Help v1.8.0]==========");
                }
                
                if (args.length == 2)
                {
                    if (args[0].equalsIgnoreCase("control"))
                    {
                        if (p.hasPermission("icu.control"))
                        {
                            Player victim = this.getServer().getPlayer(args[1]);
                            if (!p.hasMetadata("iCU_H"))
                            {
                                if (victim != null)
                                {
                                    if (!victim.hasMetadata("iCU_P"))
                                    {
                                        if (!(victim.hasPermission("icu.exempt")))
                                        {
                                            if (victim.isDead())
                                            {
                                                p.sendMessage(ChatColor.RED
                                                        + "["
                                                        + ChatColor.GOLD
                                                        + "iControlU"
                                                        + ChatColor.RED
                                                        + "]"
                                                        + ChatColor.RED
                                                        + " You can't control dead player!");
                                            }
                                            else
                                            {
                                                victim.hidePlayer(p);
                                                p.teleport(victim);
                                                p.hidePlayer(victim);
                                                for (Player player : Bukkit.getServer()
                                                        .getOnlinePlayers())
                                                {
                                                    player.hidePlayer(p);
                                                }
                                                
                                                victim.setMetadata(
                                                        "iCU_P",
                                                        new FixedMetadataValue(this, p
                                                                .getName()));
                                                p.setMetadata("iCU_H",
                                                        new FixedMetadataValue(this,
                                                                victim.getName()));
                                                victim.setAllowFlight(true);
                                                victim.setFlying(true);
                                                
                                                inventory.put(p.getName(), p
                                                        .getInventory().getContents());
                                                armor.put(p.getName(), p.getInventory()
                                                        .getArmorContents());
                                                p.getInventory().setContents(
                                                        victim.getInventory()
                                                                .getContents());
                                                p.getInventory().setArmorContents(
                                                        victim.getInventory()
                                                                .getArmorContents());
                                                
                                                @SuppressWarnings("unused")
                                                BukkitTask move = new MoveTask(p, victim)
                                                        .runTaskTimer(this, 1L, 1L);
                                                
                                                p.sendMessage(ChatColor.RED + "["
                                                        + ChatColor.GOLD + "iControlU"
                                                        + ChatColor.RED + "] "
                                                        + ChatColor.YELLOW
                                                        + "Control Mode activated with "
                                                        + ChatColor.GREEN
                                                        + victim.getName());
                                            }
                                        }
                                        else
                                        {
                                            p.sendMessage(ChatColor.RED + "["
                                                    + ChatColor.GOLD + "iControlU"
                                                    + ChatColor.RED + "]" + ChatColor.RED
                                                    + " You can't control that player!");
                                        }
                                    }
                                    else
                                    {
                                        p.sendMessage(ChatColor.RED
                                                + "["
                                                + ChatColor.GOLD
                                                + "iControlU"
                                                + ChatColor.RED
                                                + "]"
                                                + ChatColor.RED
                                                + " That player is already being controlled!");
                                    }
                                }
                                else
                                {
                                    p.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD
                                            + "iControlU" + ChatColor.RED + "]"
                                            + ChatColor.RED + " Player not found!");
                                }
                            }
                            else
                            {
                                p.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD
                                        + "iControlU" + ChatColor.RED + "]"
                                        + ChatColor.RED
                                        + " You are in Control Mode with someone else!");
                            }
                        }
                        else
                        {
                            p.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD
                                    + "iControlU" + ChatColor.RED + "]" + ChatColor.RED
                                    + " You don't have permission");
                        }
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD + "iControlU"
                                + ChatColor.RED + "]" + ChatColor.RED
                                + " Wrong command or usage!");
                    }
                }
                
                if (args.length == 1)
                {
                    if (args[0].equalsIgnoreCase("stop"))
                    {
                        if (p.hasPermission("icu.stop"))
                        {
                            if (p.hasMetadata("iCU_H"))
                            {
                                Player victim = Bukkit.getPlayer(p.getMetadata("iCU_H")
                                        .get(0).asString());
                                victim.showPlayer(p);
                                p.addPotionEffect(new PotionEffect(
                                        PotionEffectType.INVISIBILITY, 200, 1));
                                p.showPlayer(victim);
                                victim.removeMetadata("iCU_P", this);
                                victim.setAllowFlight(false);
                                victim.setFlying(false);
                                
                                for (Player player : Bukkit.getServer()
                                        .getOnlinePlayers())
                                {
                                    player.showPlayer(p);
                                }
                                
                                p.removeMetadata("iCU_H", this);
                                
                                p.getInventory().setContents(inventory.get(p.getName()));
                                p.getInventory().setArmorContents(armor.get(p.getName()));
                                
                                p.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD
                                        + "iControlU" + ChatColor.RED + "]"
                                        + ChatColor.YELLOW + " Control Mode deactivated");
                            }
                            else
                            {
                                p.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD
                                        + "iControlU" + ChatColor.RED + "]"
                                        + ChatColor.RED + " You are not in Control Mode!");
                            }
                        }
                        else
                        {
                            p.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD
                                    + "iControlU" + ChatColor.RED + "]" + ChatColor.RED
                                    + " You don't have permission!");
                        }
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "[" + ChatColor.GOLD + "iControlU"
                                + ChatColor.RED + "]" + ChatColor.RED
                                + " Wrong command or usage!");
                    }
                }
            }
            else
            {
                sender.sendMessage(ChatColor.GOLD + "[iControlU]" + ChatColor.RED
                        + " iControlU commands can only be sent from a player!");
            }
            return true;
        }
        
        return false;
    }
}
