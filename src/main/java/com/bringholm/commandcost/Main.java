package com.bringholm.commandcost;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin implements Listener {
    private HashMap<String, Double> commands = new HashMap<>();
    private String commandSuccessMessage;
    private String commandFailMessage;

    private void loadConfigValues() {
        reloadConfig();
        commandSuccessMessage = getConfig().getString("command-success-message");
        commandFailMessage = getConfig().getString("command-fail-message");
        commands.clear();
        if (getConfig().getConfigurationSection("commands") != null) {
            for (String s : getConfig().getConfigurationSection("commands").getKeys(false)) {
                double cost = getConfig().getConfigurationSection("commands").getConfigurationSection(s).getDouble("cost");
                commands.put(s, cost);
            }
        }
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
        if (!getServer().getPluginManager().isPluginEnabled("Vault")) {
            this.getLogger().severe("Vault cannot be found! Please install a working version of Vault, otherwise this plugin will not work!");
            this.setEnabled(false);
        }
        loadConfigValues();
    }

    @EventHandler
    public void onCommandEvent(PlayerCommandPreprocessEvent e) {
        if (e.getPlayer().hasPermission("commandcost.bypass")) {
            return;
        }
        for (HashMap.Entry<String, Double> entry : commands.entrySet()) {
            if (e.getMessage().startsWith("/" + entry.getKey())) {
                Economy economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
                if (economy.has(e.getPlayer(), entry.getValue())) {
                    economy.withdrawPlayer(e.getPlayer(), entry.getValue());
                    if (commandSuccessMessage != null) {
                        e.getPlayer().sendMessage(commandSuccessMessage.replace("%amount%", entry.getValue().toString()));
                    }
                } else {
                    if (commandFailMessage != null) {
                        e.getPlayer().sendMessage(ChatColor.RED + "You do not have enough money to execute this command!");
                    }
                    e.setCancelled(true);
                }
                break;
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender.hasPermission("commandcost.reload")) {
            if (args[0].equals("reload")) {
                loadConfigValues();
                commandSender.sendMessage(ChatColor.YELLOW + "CommandCost's configuration file has been reloaded!");
                return true;
            } else {
                return false;
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to do this!");
            return true;
        }
    }
}
