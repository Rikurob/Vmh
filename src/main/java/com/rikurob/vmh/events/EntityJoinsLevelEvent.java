package com.rikurob.vmh.events;

import com.rikurob.vmh.config.VmhConfig;
import com.rikurob.vmh.util.RealisticSpidersCompatibility;
import com.rikurob.vmh.util.ScalingHandler;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class EntityJoinsLevelEvent {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onEntitySpawned(EntityJoinLevelEvent event) {
        execute(event, event.getEntity());
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity == null)
            return;
        if(entity instanceof Player)
            return;
        if (entity.getPersistentData().getBoolean("vmhSpawned"))
            return;
        if (RealisticSpidersCompatibility.INSTANCE.rsEnabled && (entity.getPersistentData().getBoolean("rsSpawned")))
            return;
        if(entity.getPersistentData().getBoolean("vmhChickenFromEgg"))
            return;
        if(!VmhConfig.doJockeysSpawn){
            if(!entity.getPassengers().isEmpty()){
                for(Entity passenger:entity.getPassengers()){
                    passenger.dismountTo(entity.getX(),entity.getY(),entity.getZ());
                }
            }
        }
        ScalingHandler.ScaleEntity(entity);
    }
}