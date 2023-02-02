package com.rikurob.vmh.util;

import com.rikurob.vmh.config.VmhConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.*;

public class ScalingHandler {

    //Scale Entity from Entity Config Values
    public static void ScaleEntity(Entity entity){
        String entityType = entity.getEncodeId();
        //Check Blacklist for Entity Type
        if (VmhConfig.entityBlacklist.contains(entityType))
            return;
        //Get Entity Data
        Object[]entityData=getEntityData(entity);
        //Set Min, Max, Mean, Median and Distribution based by config values or default
        double min = (double) entityData[0],max = (double) entityData[1], median = (double) entityData[2];
        int dist = (int) entityData[3];
        //Set Scaling Values
        ScaleEntity(entity,min,max,median,dist);
    }

    //Rescale Entity from Min, Max, Median, and Dist Values
    public static void ScaleEntity(Entity entity, double min, double max, double median, int dist){
        float scale=setScalingValue(min,max,median,dist);
        setScaleToEntity(entity, scale, min, max, median, dist);
    }

    //Rescale Entity from Scale Value (Need to set Min, Max, Median, and Dist still for set Entity NBT Data)
    public static void ScaleEntity(Entity entity, double min, double max, double median, int dist, float scale){
        setScaleToEntity(entity, scale, min, max, median, dist);
    }

    //Rescale Entity from Scale Value (Min, Max, Median, and Dist are set to 1,1,1,0 respectively for set NBT Data)
    public static void ScaleEntity(Entity entity, float scale){
        setScaleToEntity(entity, scale, 1, 1, 1, 0);
    }

    //Set Scaling Value
    public static float setScalingValueOld(double min, double max, double median, int dist){
        //***Set Size Value based on Distribution Types
        return (float) switch (dist) {
            case 1 -> min + (max - min) * (0.5 + 4 * Math.pow((Math.random() - 0.5), 3)); //Based on Max, Min, and Mean. Leans heavy towards the mean. The bigger the Difference between max and min, the less it leans towards the mean
            case 2 -> min + (max - min) * (0.5 + 1.74 * Math.pow((Math.random() - 0.5), 1.8));//Based on Max, Min, and Mean. Leans slightly towards the mean. The bigger the Difference between max and min, the less it leans towards the mean
            case 3 -> min + (max - min) * (0.5 + 1.74 * (0.5 + Math.pow(((Math.random() - 0.5) / 4), (1f / 3f))));//Based on Max, Min, and Mean. Leans heavy towards the min/max.
            case 4 -> min + (max - min) * (0.5 + 1.74 * (0.5 + Math.pow(((Math.random() - 0.5) / 1.74), (1 / 1.8))));//Based on Max, Min, and Mean. Leans slightly towards the min/max.
            default -> min + (max - min) * (Math.random()); //Default. Based on Max and Min, and Mean. Even distribution.
        };
    }

    public static float setScalingValue(double min, double max, double median, int dist){
        float scale;
        double stdDev;
        double mean = (max + min) / 2;

        //***Set Size Value based on Distribution Types
       switch (dist) {
           case 1 -> {//Guassian Distribution based on the mean - Trends towards mean
               stdDev = (max - min) / (2 * 2.576);
               scale=(float)randomGaussianDistScale(mean, min, max, stdDev);
           }
           case 2 -> {//Guassian Distribution based on the median - Trends towards median
               stdDev = (max - min) / (2 * 2.576);
               scale=(float)randomGaussianDistScale(median, min, max, stdDev);
           }
           case 3 -> {//Guassian Distribution based on the mean using 1/2*stdDev - Trends Heavily towards mean
               stdDev = (max - min) / (2 * 1.28);
               scale=(float)randomGaussianDistScale(mean, min, max, stdDev);
           }
           case 4 -> {//Guassian Distribution based on the median using 1/2*stdDev - Trends Heavily towards median
               stdDev = (max - min) / (2 * 1.28);
               scale=(float)randomGaussianDistScale(median, min, max, stdDev);
           }
           case 5 -> {//Guassian Distribution based on the mean using 2*stdDev - Trends Slightly towards median
               stdDev = (max - min) / (2 * 5.152);
               scale=(float)randomGaussianDistScale(mean, min, max, stdDev);
           }
           case 6 -> {//Guassian Distribution based on the median using 2*stdDev - Trends Slightly towards median
               stdDev = (max - min) / (2 * 5.152);
               scale=(float)randomGaussianDistScale(median, min, max, stdDev);
           }
           case 7 -> {//Exponential Distributions Below
               stdDev = (max - min) / (2 * 2.576);
               scale=(float)randomExponentialDistScale(mean, min, max, stdDev);
           }
           case 8 -> {
               stdDev = (max - min) / (2 * 2.576);
               scale=(float)randomExponentialDistScale(median, min, max, stdDev);
           }
           case 9 -> {
               stdDev = (max - min) / (2 * 1.28);
               scale=(float)randomExponentialDistScale(mean, min, max, stdDev);
           }
           case 10 -> {
               stdDev = (max - min) / (2 * 1.28);
               scale=(float)randomExponentialDistScale(median, min, max, stdDev);
           }
           case 11 -> {
               stdDev = (max - min) / (2 * 5.152);
               scale=(float)randomExponentialDistScale(mean, min, max, stdDev);
           }
           case 12 -> {
               stdDev = (max - min) / (2 * 5.152);
               scale=(float)randomExponentialDistScale(median, min, max, stdDev);
           }
            case 13 ->//ORIGINAL DISTRIBUTIONS
                scale=(float)(min + (max - min) * (0.5 + 4 * Math.pow((Math.random() - 0.5), 3)));//Based on Max, Min, and Mean. Leans heavy towards the mean. The bigger the Difference between max and min, the less it leans towards the mean
            case 14 ->//ORIGINAL DISTRIBUTIONS
                scale = (float) (min + (max - min) * (0.5 + 1.74 * Math.pow((Math.random() - 0.5), 1.8)));//Based on Max, Min, and Mean. Leans slightly towards the mean. The bigger the Difference between max and min, the less it leans towards the mean
            case 15 ->//ORIGINAL DISTRIBUTIONS
                scale = (float) (min + (max - min) * (0.5 + 1.74 * (0.5 + Math.pow(((Math.random() - 0.5) / 4), (1f / 3f)))));//Based on Max, Min, and Mean. Leans heavy towards the min/max.
            case 16 ->//ORIGINAL DISTRIBUTIONS
               scale = (float) (min + (max - min) * (0.5 + 1.74 * (0.5 + Math.pow(((Math.random() - 0.5) / 1.74), (1 / 1.8)))));//Based on Max, Min, and Mean. Leans slightly towards the min/max.
            default ->
                scale = (float) (min + (max - min) * (Math.random())); //Default. Based on Max and Min, and Mean. Even distribution.
        }
        return scale;
    }

    //Set Scaling to Entity
    public static void setScaleToEntity(Entity entity, float scale, double min, double max, double median, int dist){
        float scaleInverse = (1/scale);
        double mean = (max + min) / 2;
        String biome=entity.getLevel().getBiome(new BlockPos(entity.getX(), entity.getY(), entity.getZ())).unwrapKey().orElseThrow().location().toString();
        //Set entity NBT data
        entity.getPersistentData().putBoolean("vmhSpawned", (true));
        entity.getPersistentData().putDouble("vmhScale", scale);
        entity.getPersistentData().putDouble("vmhScaleMin", min);
        entity.getPersistentData().putDouble("vmhScaleMax", max);
        entity.getPersistentData().putDouble("vmhScaleMean", mean);
        entity.getPersistentData().putDouble("vmhScaleMedian", median);
        entity.getPersistentData().putDouble("vmhScaleDistribution", dist);
        entity.getPersistentData().putString("vmhBiomeSpawnedIn", biome);

        //Declared True Values(Only used if inverted config is on for certain things)
        float scaleTrue = scale;
        float scaleTrueSpeed = scale;
        float scaleTrueHealth = scale;
        float scaleTrueSize = scale;
        //Will Only Invert Values if they are in the Invert Whitelist
        if (VmhConfig.entityInvertedWhitelist.contains(entity.getEncodeId())) {
            if (VmhConfig.isInverseSpeed)
                scaleTrueSpeed = scaleInverse; //Set Speed Value to Inverse Value
            if (VmhConfig.isInverseHealth)
                scaleTrueHealth = scaleInverse; //Set Speed Value to Inverse Value
            if (VmhConfig.isInverseSize)
                scaleTrueSize = scaleInverse; //Set Speed Value to Inverse Value
            if (VmhConfig.isInverseScale)
                scaleTrue = scaleInverse; //Set Speed Value to Inverse Value
        }
        //Set Basic Size Scaling
        ScaleTypes.BASE.getScaleData(entity).setScaleTickDelay(0);  //Don't Set Base so other values can be set specifically. Still sets Base Tick Value.
        ScaleTypes.HEIGHT.getScaleData(entity).setScaleTickDelay(0);
        ScaleTypes.WIDTH.getScaleData(entity).setScaleTickDelay(0);
        ScaleTypes.HEIGHT.getScaleData(entity).setTargetScale(scaleTrueSize); //Set Height Scaling
        ScaleTypes.WIDTH.getScaleData(entity).setTargetScale(scaleTrueSize); //Set Width Scaling

        if (VmhConfig.isBuffAttackDamage) {
            ScaleTypes.ATTACK.getScaleData(entity).setScaleTickDelay(0);
            ScaleTypes.ATTACK.getScaleData(entity).setTargetScale(scaleTrue);//Set Attack Scaling
        }
        if (VmhConfig.isBuffAttackSpeed) {
            ScaleTypes.ATTACK_SPEED.getScaleData(entity).setScaleTickDelay(0);
            ScaleTypes.ATTACK_SPEED.getScaleData(entity).setTargetScale(scaleTrueSpeed);//Set Speed Scaling
        }
        if (VmhConfig.isBuffAttackKnockback) {
            ScaleTypes.KNOCKBACK.getScaleData(entity).setScaleTickDelay(0);
            ScaleTypes.KNOCKBACK.getScaleData(entity).setTargetScale(scaleTrue);//Set Knockback Scaling
        }
        if (VmhConfig.isBuffDefense) {
            ScaleTypes.DEFENSE.getScaleData(entity).setScaleTickDelay(0);
            ScaleTypes.DEFENSE.getScaleData(entity).setTargetScale(scaleTrue);//Set Defense Scaling
        }
        if (VmhConfig.isBuffSpeed&&(entity instanceof LivingEntity)) { //Uses built in MOVEMENT_SPEED attribute instead of pehkui health for better compatibility.
            Objects.requireNonNull(((LivingEntity) entity).getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue((Objects.requireNonNull(((LivingEntity) entity).getAttribute(Attributes.MOVEMENT_SPEED)).getBaseValue() * scaleTrueSpeed));//Set Movement Speed Scaling
        }
        if (VmhConfig.isBuffJumpHeightHorse&&(entity instanceof Horse)) {
            ScaleTypes.JUMP_HEIGHT.getScaleData(entity).setScaleTickDelay(0);
            ScaleTypes.STEP_HEIGHT.getScaleData(entity).setScaleTickDelay(0);
            ScaleTypes.JUMP_HEIGHT.getScaleData(entity).setTargetScale(scaleTrue);//Set Horse Jump Height Scaling
            ScaleTypes.STEP_HEIGHT.getScaleData(entity).setTargetScale(scaleTrue);//Set Horse Step Height Scaling
            Objects.requireNonNull(((LivingEntity) entity).getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue( //Set Horse Speed Scaling
                    (Objects.requireNonNull(((LivingEntity) entity).getAttribute(Attributes.MOVEMENT_SPEED)).getBaseValue() * scaleTrueSpeed)); //Set Horse Speed Scaling
        }
        else if (VmhConfig.isBuffJumpHeight) {
            ScaleTypes.JUMP_HEIGHT.getScaleData(entity).setScaleTickDelay(0);
            ScaleTypes.STEP_HEIGHT.getScaleData(entity).setScaleTickDelay(0);
            ScaleTypes.JUMP_HEIGHT.getScaleData(entity).setTargetScale(scaleTrueSpeed); //Set Jump Height Scaling
            ScaleTypes.STEP_HEIGHT.getScaleData(entity).setTargetScale(scaleTrueSpeed);//Set Step Height Scaling
        }
        if (VmhConfig.isBuffHealth&&(entity instanceof LivingEntity)) {
            LivingEntity _livEnt = (LivingEntity) entity;
            double maxHealth = _livEnt.getMaxHealth(); //Uses built in MAX_HEALTH attribute instead of pehkui health for better compatibility.
            Objects.requireNonNull(((LivingEntity) entity).getAttribute(Attributes.MAX_HEALTH)).setBaseValue((maxHealth * scaleTrueHealth)); //Set Health Scaling
            if (_livEnt.getHealth() != (float) maxHealth * scale)
                _livEnt.setHealth((float) maxHealth * scale);
        }
    }

    //Splits and Returns the Entity Data for Min,Max,Median,Dist
    public static Object[] splitData(String data){
        String[] splitData = data.split(";");
        return new Object[]{Double.parseDouble(splitData[0]), Double.parseDouble(splitData[1]), Double.parseDouble(splitData[2]), Integer.parseInt(splitData[3])};
    }

    public static Object[] getEntityData(Entity entity){
        String entityType = entity.getEncodeId();
        String biomeType=entity.getLevel().getBiome(new BlockPos(entity.getX(), entity.getY(), entity.getZ())).unwrapKey().orElseThrow().location().toString();
        if (VmhConfig.entityBlacklist.contains(entityType))
            return new Object[]{1D, 1D, 1D, 0};
        if (VmhConfig.entityData.containsKey(biomeType+";"+entityType))
            return splitData(VmhConfig.entityData.get(biomeType + ";" + entityType));
        else if (VmhConfig.entityData.containsKey(entityType))
            return splitData(VmhConfig.entityData.get(entityType));
        else if (VmhConfig.isDoAutoCompat&&entity instanceof LivingEntity)
            return new Object[]{VmhConfig.defaultMin, VmhConfig.defaultMax, VmhConfig.defaultMedian, VmhConfig.defaultDistribution};
        return new Object[]{1D, 1D, 1D, 0};
    }


    public static Object[] getEntityDataFromNBTData(Entity entity){
        return new Object[]{entity.getPersistentData().getDouble("vmhScaleMin"), entity.getPersistentData().getDouble("vmhScaleMax"), entity.getPersistentData().getDouble("vmhScaleMedian"), entity.getPersistentData().getInt("vmhScaleDist")};
    }

    public static float getScale(Entity entity){
        return (float)entity.getPersistentData().getDouble("vmhScale");
    }

    public static float getScale(ItemStack itemStack){
        return (float)itemStack.getOrCreateTag().getDouble("vmhScale");
    }

    private static double randomGaussianDistScale(double medianOrMean, double min, double max, double stdDev) {
        double num;
        do {
            num = new Random().nextGaussian(medianOrMean,stdDev);
        } while (num < min || num > max);
        return num;
    }

    private static double randomExponentialDistScale(double medianOrMean, double min, double max, double stdDev) {
        double num;
        do {
            num = medianOrMean + stdDev * new Random().nextExponential();
        } while (num < min || num > max);
        return num;
    }


}
