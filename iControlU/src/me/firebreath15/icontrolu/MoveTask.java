package me.firebreath15.icontrolu;

import java.util.HashSet;

import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayOutPosition;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MoveTask extends BukkitRunnable
{
    private Player vi;
    private EntityPlayer c;
    private EntityPlayer v;
    
    MoveTask(Player co, Player vi)
    {
        this.vi = vi;
        c = ((CraftPlayer) co).getHandle();
        v = ((CraftPlayer) vi).getHandle();
    }
    
    //
    public void run()
    {
        if (!c.getBukkitEntity().hasMetadata("iCU_H"))
        {
            this.cancel();
        }
        
        vi.setAllowFlight(true);
        vi.setFlying(true);
        Location from = new Location(c.getBukkitEntity().getWorld(), c.lastX, c.lastY,
                c.lastZ, c.lastYaw, c.lastPitch);
        Location to = new Location(c.getBukkitEntity().getWorld(), c.locX, c.locY,
                c.locZ, c.yaw, c.pitch);
        vi.teleport(to);
        Packet packet;
        
        if (from.getX() == to.getX() && from.getY() == to.getY()
                && from.getZ() == to.getZ())
        {
            packet = new PacketPlayOutPosition(to.getX(), to.getY(), to.getZ(),
                    to.getYaw(), to.getPitch(), new HashSet<Object>());
        }
        else
        {
            if (c.isSneaking())
            {
                packet = new PacketPlayOutPosition(to.getX(), to.getY()
                        + v.getBukkitEntity().getEyeHeight(true), to.getZ(), to.getYaw(),
                        to.getPitch(), new HashSet<Object>());
            }
            else
            {
                packet = new PacketPlayOutPosition(to.getX(), to.getY()
                        + v.getBukkitEntity().getEyeHeight(false), to.getZ(),
                        to.getYaw(), to.getPitch(), new HashSet<Object>());
            }
        }
        
        v.playerConnection.sendPacket(packet);
        if (v.getBukkitEntity().hasMetadata("iCU_P"))
        {
            v.getBukkitEntity().getInventory()
                    .setContents(c.getBukkitEntity().getInventory().getContents());
            v.getBukkitEntity()
                    .getInventory()
                    .setArmorContents(
                            c.getBukkitEntity().getInventory().getArmorContents());
        }
    }
}
