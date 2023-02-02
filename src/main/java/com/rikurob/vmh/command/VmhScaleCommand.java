
package com.rikurob.vmh.command;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.rikurob.vmh.util.ScalingHandler;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class VmhScaleCommand {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher()
                .register(
                        Commands.literal(
                                "vmhScale").requires(
                                        s -> s.hasPermission(4))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .then(Commands.argument("scale", DoubleArgumentType.doubleArg(0.01D, 5D))
                                                .then(Commands.argument("min", DoubleArgumentType.doubleArg(0.01D, 5D))
                                                        .then(Commands.argument("max", DoubleArgumentType.doubleArg(0.01D, 5D))
                                                                .then(Commands.argument("median", DoubleArgumentType.doubleArg(0.01D, 5D))
                                                                        .then(Commands.argument("dist", IntegerArgumentType.integer(0, 4)).executes(arguments -> {
                                                                            Entity entity = new Object() {
                                                                                public Entity getEntity() {
                                                                                    try {
                                                                                        return EntityArgument.getEntity(arguments, "targets");
                                                                                    } catch (CommandSyntaxException e) {
                                                                                        e.printStackTrace();
                                                                                        return null;
                                                                                    }
                                                                                }
                                                                            }.getEntity();
                                                                            float scale = (float)DoubleArgumentType.getDouble(arguments, "scale");
                                                                            double min = DoubleArgumentType.getDouble(arguments, "min");
                                                                            double max = DoubleArgumentType.getDouble(arguments, "max");
                                                                            double median = DoubleArgumentType.getDouble(arguments, "median");
                                                                            int dist = IntegerArgumentType.getInteger(arguments, "dist");
                                                                            ScalingHandler.ScaleEntity(entity, min, max, median, dist, scale);
                                                                            return 0;
                                                                        })).executes(arguments -> {
                                                                    Entity entity = new Object() {
                                                                        public Entity getEntity() {
                                                                            try {
                                                                                return EntityArgument.getEntity(arguments, "targets");
                                                                            } catch (CommandSyntaxException e) {
                                                                                e.printStackTrace();
                                                                                return null;
                                                                            }
                                                                        }
                                                                    }.getEntity();
                                                                    double min = DoubleArgumentType.getDouble(arguments, "min");
                                                                    double max = DoubleArgumentType.getDouble(arguments, "max");
                                                                    double median = DoubleArgumentType.getDouble(arguments, "median");
                                                                    int dist = IntegerArgumentType.getInteger(arguments, "dist");
                                                            ScalingHandler.ScaleEntity(entity, min, max, median, dist);
                                                            return 0;
                                                        }))))
                                                .executes(arguments -> {
                                                    Entity entity = new Object() {
                                                        public Entity getEntity() {
                                                            try {
                                                                return EntityArgument.getEntity(arguments, "targets");
                                                            } catch (CommandSyntaxException e) {
                                                                e.printStackTrace();
                                                                return null;
                                                            }
                                                        }
                                                    }.getEntity();
                                                    float scale = (float)DoubleArgumentType.getDouble(arguments, "scale");
                                                    ScalingHandler.ScaleEntity(entity, scale);
                                                    return 0;
                                                }))
                                        .executes(arguments -> {
                                            Entity entity = new Object() {
                                                public Entity getEntity() {
                                                    try {
                                                        return EntityArgument.getEntity(arguments, "targets");
                                                    } catch (CommandSyntaxException e) {
                                                        e.printStackTrace();
                                                        return null;
                                                    }
                                                }
                                                    }.getEntity();
                                                    ScalingHandler.ScaleEntity(entity);
                                                    return 0;
                                        })));
    }
}
