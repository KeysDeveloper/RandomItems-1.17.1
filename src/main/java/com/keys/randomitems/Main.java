package com.keys.randomitems;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class Main extends JavaPlugin implements Listener {

    //ATTENTION!!! THIS VERSION OF THE PLUGIN IS FOR 1.17.1 THE VERSION FOR 1.18.1 IS ON MY GITHUB AND IS THE ONE THAT IS NOT NAMED 1.17.1 VERSION OF THIS PLUGIN


    /*
        RESETTING LOOT TABLES INSTRUCTIONS:
            1. STOP YOUR SERVER
            2. DELETE THE FOLDER THAT THIS PLUGIN CREATED IN THE PLUGINS FOLDER IN YOUR SERVER
            3. START YOUR SERVER AGAIN IT SHOULD CREATE A NEW FOLDER AND IT WILL NOW HAVE
            DIFFERENT DROPS
     */

    public Material[] mats = Material.values();
    public EntityType[] ent = EntityType.values();
    public Random ran = new Random();

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();

        for(int i = 0; i < mats.length; i++) {
            if(mats[i].isBlock()) {
                this.getConfig().createSection(mats[i].toString());

                this.getConfig().set(mats[i].toString(), mats[ran.nextInt(mats.length)].toString());
            }
        }

        for(int i = 0; i < ent.length; i++) {
            if(ent[i].isAlive()) {
                this.getConfig().createSection(ent[i].toString());

                this.getConfig().set(ent[i].toString(), mats[ran.nextInt(mats.length)].toString());
            }
        }


        this.saveConfig();

        Bukkit.getPluginManager().registerEvents(this, this);

    }


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(getConfig().get(e.getBlock().getType().toString()) != null && (e.getBlock().getType() != Material.AIR || e.getBlock().getType() != Material.CAVE_AIR)) {
            e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.matchMaterial(getConfig().get(e.getBlock().getType().toString()).toString()), ran.nextInt(500)+1));
        }
    }

    @EventHandler
    public void onBlockDrop(BlockDropItemEvent e) { e.setCancelled(true); }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if(getConfig().get(e.getEntity().getType().toString()) != null && (e.getEntity().getType() != EntityType.AREA_EFFECT_CLOUD || e.getEntity().getType() != EntityType.ARROW || e.getEntity().getType() != EntityType.DRAGON_FIREBALL || e.getEntity().getType() != EntityType.DROPPED_ITEM || e.getEntity().getType() != EntityType.EGG || e.getEntity().getType() != EntityType.ENDER_CRYSTAL || e.getEntity().getType() != EntityType.ENDER_PEARL || e.getEntity().getType() != EntityType.ENDER_SIGNAL || e.getEntity().getType() != EntityType.FALLING_BLOCK)) {
            e.getEntity().getLocation().getWorld().dropItemNaturally(e.getEntity().getLocation(), new ItemStack(Material.matchMaterial(getConfig().get(e.getEntity().getType().toString()).toString()), ran.nextInt(500)+1));
        }
    }

    @EventHandler
    public void onEntityDrop(EntityDropItemEvent e) { e.setCancelled(true); }
}
