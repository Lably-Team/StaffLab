package me.bryangaming.stafflab.builder;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommandLoaderBuilder {

    private final String commandName;
    private final CommandExecutor commandExecutor;

    public static CommandLoaderBuilder create(String commandName, CommandExecutor entity) {
        return new CommandLoaderBuilder(commandName, entity);
    }

    private CommandLoaderBuilder(String command, CommandExecutor entity){
        this.commandName = command;
        this.commandExecutor = entity;
    }

    public String getCommandName() {
        return commandName;
    }

    public CommandExecutor getExecutor() {
        return commandExecutor;
    }

}
