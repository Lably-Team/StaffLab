package me.bryangaming.stafflab.loader;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.api.Loader;
import me.bryangaming.stafflab.builder.CommandLoaderBuilder;
import me.bryangaming.stafflab.builder.InteractBuilder;
import me.bryangaming.stafflab.command.*;
import me.bryangaming.stafflab.loader.file.FileManager;
import org.bukkit.Bukkit;

public class CommandLoader implements Loader {

    private final PluginCore pluginCore;

    public CommandLoader(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {
        registerCommands(
                CommandLoaderBuilder.create("stafflab", new StaffLabCommand(pluginCore)),
                CommandLoaderBuilder.create("staff", new StaffCommand(pluginCore)),
                CommandLoaderBuilder.create("staffchat", new StaffChatCommand(pluginCore)),
                CommandLoaderBuilder.create("freeze", new FreezeCommand(pluginCore)),
                CommandLoaderBuilder.create("vanish", new VanishCommand(pluginCore)),
                CommandLoaderBuilder.create("invsee", new InvseeCommand(pluginCore)));

    }

    public void registerCommands(CommandLoaderBuilder... commandLoaderBuilders){

        FileManager configFile = pluginCore.getFiles().getConfigFile();

        for (CommandLoaderBuilder commandLoaderBuilder : commandLoaderBuilders){
            if (!configFile.getBoolean("config." + commandLoaderBuilder.getCommandName(), true)){
                continue;
            }

            Bukkit.getPluginCommand(commandLoaderBuilder.getCommandName()).setExecutor(commandLoaderBuilder.getExecutor());
        }
    }


}
