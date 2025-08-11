package fr.cleboost.createchocolatefactory.core;


import com.mojang.brigadier.CommandDispatcher;
import fr.cleboost.createchocolatefactory.command.ChocolateCommand;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;

public class CCFCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        ChocolateCommand.register(dispatcher, buildContext);
    }
}
