package fr.cleboost.createchocolatefactory.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFLangs;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

public class ChocolateCommand {
    public ChocolateCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(Commands.literal("chocolate")
                .requires((commandSource) -> commandSource.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.literal("get").executes((commandSource) -> {
                    return get(commandSource.getSource());
                }))
                .then(Commands.literal("set")
                        .then(Commands.literal("chocolate")
                        .then(Commands.argument("strength", FloatArgumentType.floatArg(0f, 1f))
                                .then(Commands.argument("sugar", FloatArgumentType.floatArg(0f, 1f))
                                        .then(Commands.argument("cocoaButter", FloatArgumentType.floatArg(0f, 1f))
                                                .then(Commands.argument("milk", FloatArgumentType.floatArg(0f, 1f))
                                                        .executes((commandSource) -> {
                                                            return setBasic(
                                                                    commandSource.getSource(),
                                                                    FloatArgumentType.getFloat(commandSource, "strength"),
                                                                    FloatArgumentType.getFloat(commandSource, "sugar"),
                                                                    FloatArgumentType.getFloat(commandSource, "cocoaButter"),
                                                                    FloatArgumentType.getFloat(commandSource, "milk")
                                                            );
                                                        }))))))
                        .then(Commands.literal("taste")
                        .then(Commands.argument("item", ItemArgument.item(context))
                                .executes((commandSource) -> {
                                    return setTaste(commandSource.getSource(), ItemArgument.getItem(commandSource, "taste").getItem());
                                })))
                )
        );
    }

    private static int get(CommandSourceStack commandSource) {
        if (commandSource.isPlayer() && commandSource.getPlayer().getMainHandItem().has(CCFDataComponents.CHOCOLATE)) {
            Chocolate chocolate = commandSource.getPlayer().getMainHandItem().get(CCFDataComponents.CHOCOLATE);
            commandSource.sendSuccess(() -> {
                return CCFLangs.CHOCOLATE_COMPOSITION.getComponent(chocolate.getStrength(), chocolate.getSugar(), chocolate.getCocoaButter(), chocolate.getMilk(), chocolate.getTasteText());
            }, true);
            return 1;
        } else {
            commandSource.sendFailure(CCFLangs.COMMAND_FAIL_NO_CHOCOLATE.getComponent());
            return 0;
        }
    }

    private static int setBasic(CommandSourceStack commandSource, float strength, float sugar, float cocoaButter, float milk) {
        if (commandSource.isPlayer() && commandSource.getPlayer().getMainHandItem().has(CCFDataComponents.CHOCOLATE)) {
            Chocolate oldChocolate = commandSource.getPlayer().getMainHandItem().get(CCFDataComponents.CHOCOLATE);
            Chocolate newChocolate = new Chocolate(strength, sugar, cocoaButter, milk, oldChocolate.getTasteItemHolder());
            commandSource.getPlayer().getMainHandItem().set(CCFDataComponents.CHOCOLATE, newChocolate);
            commandSource.sendSuccess(CCFLangs.COMMAND_SET_CHOCOLATE::getComponent, true);
            return 1;
        } else {
            commandSource.sendFailure(CCFLangs.COMMAND_FAIL_NO_CHOCOLATE.getComponent());
            return 0;
        }
    }

    private static int setTaste(CommandSourceStack commandSource, Item item) {
        if (commandSource.isPlayer() && commandSource.getPlayer().getMainHandItem().has(CCFDataComponents.CHOCOLATE)) {
            Chocolate oldChocolate = commandSource.getPlayer().getMainHandItem().get(CCFDataComponents.CHOCOLATE);
            Chocolate newChocolate = oldChocolate.addTaste(item);
            commandSource.getPlayer().getMainHandItem().set(CCFDataComponents.CHOCOLATE, newChocolate);
            commandSource.sendSuccess(CCFLangs.COMMAND_SET_TASTE::getComponent, true);
            return 1;
        } else {
            commandSource.sendFailure(CCFLangs.COMMAND_FAIL_NO_CHOCOLATE.getComponent());
            return 0;
        }
    }
}
