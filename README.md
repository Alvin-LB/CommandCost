# CommandCost
Source code for the Bukkit Plugin CommandCost, developed by AlvinLB. BukkitDev Page: 
https://dev.bukkit.org/bukkit-plugins/commandcost/

**IMPORTANT: This plugin requires [Vault](https://dev.bukkit.org/bukkit-plugins/vault/) to work!**

## What is it?
CommandCost is a very simple plugin which lets you take money from players when they execute a certain command.

## Features
* Support for infinetly many commands! (Or at least until the config file takes up the entire hard drive...)
* Compatible with most Economy plugin through the use of the [Vault API](https://dev.bukkit.org/bukkit-plugins/vault/).
* Permission to bypass the command cost!
## Commands/Permissions
/cmdcost reload - Reloads the plugins configuration file. Permission node: commandcost.reload
commandcost.bypass - Allows users to execute commands without having to pay.
commandcost.* - Gives access to all of the plugin's permissions.
## Configuration
```
# Configuration file for CommandCost.
# The '§' character can be used for colour codes in this file.
# Message to be displayed when the money has been deducted from the players account.
command-success-message: '§e%amount% has been deducted from your account!'
# Message to be displayed when the user doesn't have enough money.
command-fail-message: '§cYou do not have enough money to execute this command!'
# Command names should be without the '/' character.
#
# Example:
#  mycommand:
#    cost: 60
# Please note that indentations need to be spaces, not tabs! If you are having problems getting your config file to work correctly, try putting it through a YAML parser.
# An online YAML parser can be found here:
# http://yaml-online-parser.appspot.com/
commands:
```
Commands should be under the "commands:" section, following the format described in the comments of the file.

## Other
Have you found a bug/want to make a feature request? Post an issue on the GitHub page
