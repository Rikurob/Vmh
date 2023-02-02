package com.rikurob.vmh.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;


public class VmhConfig {
    public static final ForgeConfigSpec GENERAL_SPEC;
	public static final ForgeConfigSpec MAIN_LIST;
	public static final ForgeConfigSpec ADVANCED_SPEC;
	public static final ForgeConfigSpec BUFFS;
	public static final ForgeConfigSpec BIOME_LIST;

	public static ConfigValue<ArrayList<String>> vmhEntities;
	public static ConfigValue<ArrayList<String>> vmhEntitiesInvertedWhitelist;
	public static ConfigValue<ArrayList<String>> vmhEntitiesBlacklist;
	public static ConfigValue<ArrayList<String>> vmhWhitelistByBiomes;

	public static IntValue vmhDistributionType;
    public static DoubleValue vmhSizeMin;
    public static DoubleValue vmhSizeMax;
    public static DoubleValue vmhSizeMedian;

	public static BooleanValue vmhDoScaleNoise;

	public static BooleanValue vmhDoAutoCompat;
	public static BooleanValue vmhBuffHealth;
    public static BooleanValue vmhBuffSpeed;
    public static BooleanValue vmhBuffAttackDamage;
    public static BooleanValue vmhBuffAttackSpeed;
    public static BooleanValue vmhBuffKnockback;
    public static BooleanValue vmhBuffDefense;
    public static BooleanValue vmhBuffJumpHeight;
    public static BooleanValue vmhBuffHorseJumpHeight;

    public static BooleanValue vmhInverseSpeedValue;
	public static BooleanValue vmhInverseValue;
	public static BooleanValue vmhInverseHealthValue;
	public static BooleanValue vmhInverseSizeValue;

	public static BooleanValue vmhAddEggValues;

	public static BooleanValue vmhDoJockeysSpawn;

	static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
		ForgeConfigSpec.Builder configBuilder2 = new ForgeConfigSpec.Builder();
		ForgeConfigSpec.Builder configBuilder3 = new ForgeConfigSpec.Builder();
		ForgeConfigSpec.Builder configBuilder4 = new ForgeConfigSpec.Builder();
		ForgeConfigSpec.Builder configBuilder5 = new ForgeConfigSpec.Builder();

		setupGeneralConfig(configBuilder);
		setupEntityConfig(configBuilder2);
		setupAdvancedConfig(configBuilder3);
		setupBuffsConfig(configBuilder4);
		setupBiomesConfig(configBuilder4);

		GENERAL_SPEC = configBuilder.build();
		MAIN_LIST = configBuilder2.build();
		ADVANCED_SPEC= configBuilder3.build();
		BUFFS= configBuilder4.build();
		BIOME_LIST=configBuilder5.build();
	}


	private static void setupGeneralConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Default Spawn Size/Distribution Values");
        vmhSizeMax = builder
                .comment("\nDetermines the default min, max, and median size values a mob can be.\n",
                        "The median value sets where the middle of the distribution is set.\n",
                        "**Default values: Min=0.8, Max=1,2, Median=1, Distribution=0. Default values will only be used if an entity does not have them set in the config.**\n",
                        "**More info on distributions can be found on the mod's page on curseforge.**\n",
                         "*WARNING: Extreme values can cause issues and lag. *\n")
                .translation("vmh.config.vmhSizeMax")
                .defineInRange("vmhSizeMax", 1.05D, 0.01D, 5D);
        vmhSizeMin = builder
                .translation("vmh.config.vmhSizeMin")
                .defineInRange("vmhSizeMin", 0.955D, 0.01D, 5D);
        vmhSizeMedian = builder
                .translation("vmh.config.vmhSizeMedian")
                .defineInRange("vmhSizeMedian", 1D, 0.01D, 5D);
        vmhDistributionType = builder
                .comment("\nDetermines the type of distribution to use on entities.\n")
                .translation("vmh.config.vmhDistributionType")
                .defineInRange("vmhDistributionType", 0, 0, 4);
        builder.pop();

        builder.push("Do Auto-Compatibility");
        vmhDoAutoCompat = builder
                .comment("\nEnables/disables auto-compatibility with mobs/creatures. This turns the Entities List into a Whitelist.\n")
                .translation("vmh.config.vmhDoAutoCompat")
                .define("vmhDoAutoCompat", true);
        builder.pop();
    }

    private static void setupBuffsConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Buff Health");
		vmhBuffHealth = builder
				.comment("\nDetermines whether or not to buff/debuff entity health by their size value.\n")
				.translation("vmh.config.vmhBuffHealth")
				.define("vmhBuffHealth", false);
		builder.pop();

		builder.push("Buff Speed");
		vmhBuffSpeed = builder
				.comment("\nDetermines whether or not to buff/debuff entity movement/flying speed by their size value.\n")
				.translation("vmh.config.vmhBuffSpeed")
				.define("vmhBuffSpeed", false);
		builder.pop();

		builder.push("Buff Attack Damage");
		vmhBuffAttackDamage = builder
				.comment("\nDetermines whether or not to buff/debuff entity attack damage by their size value.\n")
				.translation("vmh.config.vmhBuffAttackDamage")
				.define("vmhBuffAttackDamage", false);
		builder.pop();

		builder.push("Buff Attack Speed");
		vmhBuffAttackSpeed = builder
				.comment("\nDetermines whether or not to buff/debuff entity attack speed by their size value.\n")
				.translation("vmh.config.vmhBuffAttackSpeed")
				.define("vmhBuffAttackSpeed", false);
		builder.pop();

		builder.push("Buff Attack Knockback");
		vmhBuffKnockback = builder
				.comment("\nDetermines whether or not to buff/debuff entity knockback by their size value.\n")
				.translation("vmh.config.vmhBuffKnockback")
				.define("vmhBuffKnockback", false);
		builder.pop();

		builder.push("Buff Defense");
		vmhBuffDefense = builder
				.comment("\nDetermines whether Aor not to buff/debuff entity defense by their size value.\n")
				.translation("vmh.config.vmhBuffDefense")
				.define("vmhBuffDefense", false);
		builder.pop();

		builder.push("Modify Jump/Step Height");
		vmhBuffJumpHeight = builder
				.comment("\nDetermines whether or not to modify entity jump height/step height by their size value.\n",
						"WARNING: Be careful turning this on, it can cause mobs to have serious issues. It depends on the heights.\n")
				.translation("vmh.config.vmhBuffJumpHeight")
				.define("vmhBuffJumpHeight", false);
		builder.pop();

		builder.push("Buff Horse Jump Height");
		vmhBuffHorseJumpHeight = builder
				.comment("\nDetermines whether or not to modify horse jump heights.\n")
				.translation("vmh.config.vmhBuffHorseJumpHeight")
				.define("vmhBuffHorseJumpHeight", false);
		builder.pop();

	}

	private static void setupEntityConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Entities");
		vmhEntities = builder
				.comment("\nDetermines what entities have their size modified by this mod, as well as their min, max, and median sizes.\n",
						"The median only does things for certain distribution values.\n",
						"If an entity doesn't work automatically, this list also works as a whitelist. There is also a Blacklist, and an Inverted Entity Whitelist\n",
						"Inverted values can be set which invert the Min and Max values of a dataset for a specific.\n",
						"**Values must be set as follows: 'minecraft/modId:name;vmhSizeMin;vmhSizeMax;vmhSizeMedian;vmhDistributionType'.**\n")
				.translation("vmh.config.vmhEntities")
				.define("vmhEntities", new ArrayList<>(
						List.of("minecraft:allay;0.95;1.05;1;0",
								"minecraft:axolotl;0.95;1.05;1;0",
								"minecraft:bat;0.95;1.05;1;0",
								"minecraft:bee;0.95;1.05;1;0",
								"minecraft:blaze;0.95;1.05;1;0",
								"minecraft:cat;0.95;1.05;1;0",
								"minecraft:cave_spider;0.95;1.05;1;0",
								"minecraft:chicken;0.95;1.05;1;0",
								"minecraft:cod;0.95;1.05;1;0",
								"minecraft:cow;0.95;1.05;1;0",
								"minecraft:creeper;0.95;1.05;1;0",
								"minecraft:dolphin;0.95;1.05;1;0",
								"minecraft:donkey;0.95;1.05;1;0",
								"minecraft:drowned;0.95;1.05;1;0",
								"minecraft:enderman;0.95;1.05;1;0",
								"minecraft:evoker;0.95;1.05;1;0",
								"minecraft:fox;0.95;1.05;1;0",
								"minecraft:frog;0.95;1.05;1;0",
								"minecraft:ghast;0.95;1.05;1;0",
								"minecraft:glow_squid;0.95;1.05;1;0",
								"minecraft:goat;0.95;1.05;1;0",
								"minecraft:guardian;0.95;1.05;1;0",
								"minecraft:hoglin;0.95;1.05;1;0",
								"minecraft:horse;0.95;1.05;1;0",
								"minecraft:husk;0.95;1.05;1;0",
								"minecraft:illusioner;0.95;1.05;1;0",
								"minecraft:iron_golem;0.95;1.05;1;0",
								"minecraft:llama;0.95;1.05;1;0",
								"minecraft:magma_cube;0.95;1.05;1;0",
								"minecraft:mooshroom;0.95;1.05;1;0",
								"minecraft:mule;0.95;1.05;1;0",
								"minecraft:ocelot;0.95;1.05;1;0",
								"minecraft:panda;0.95;1.05;1;0",
								"minecraft:parrot;0.95;1.05;1;0",
								"minecraft:phantom;0.95;1.05;1;0",
								"minecraft:pig;0.95;1.05;1;0",
								"minecraft:piglin_brute;0.95;1.05;1;0",
								"minecraft:pillager;0.95;1.05;1;0",
								"minecraft:polar_bear;0.95;1.05;1;0",
								"minecraft:pufferfish;0.95;1.05;1;0",
								"minecraft:rabbit;0.95;1.05;1;0",
								"minecraft:ravager;0.95;1.05;1;0",
								"minecraft:salmon;0.95;1.05;1;0",
								"minecraft:sheep;0.95;1.05;1;0",
								"minecraft:shulker;0.95;1.05;1;0",
								"minecraft:silverfish;0.95;1.05;1;0",
								"minecraft:skeleton;0.95;1.05;1;0",
								"minecraft:slime;0.7;1.05;3;0",
								"minecraft:snow_golem;0.95;1.05;1;0",
								"minecraft:spider;0.8;1.2;1;0",
								"minecraft:squid;0.95;1.05;1;0",
								"minecraft:stray;0.95;1.05;1;0",
								"minecraft:strider;0.95;1.05;1;0",
								"minecraft:tadpole;0.95;1.05;1;0",
								"minecraft:trader_llama;0.95;1.05;1;0",
								"minecraft:tropical_fish;0.95;1.05;1;0",
								"minecraft:turtle;0.95;1.05;1;0",
								"minecraft:vex;0.95;1.05;1;0",
								"minecraft:villager;0.952;1.08;1;0",
								"minecraft:vindicator;0.95;1.05;1;0",
								"minecraft:wandering_trader;0.952;1.08;1;0",
								"minecraft:witch;0.95;1.05;1;0",
								"minecraft:wither_skeleton;0.95;1.05;1;0",
								"minecraft:wolf;0.95;1.05;1;0",
								"minecraft:zoglin;0.95;1.05;1;0",
								"minecraft:zombie;0.95;1.05;1;0",
								"minecraft:zombie_horse;0.95;1.05;1;0",
								"minecraft:zombie_villager;0.95;1.05;1;0",
								"minecraft:zombified_piglin;0.95;1.05;1;0")));
		builder.pop();
	}

	private static void setupAdvancedConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Do Jockeys Spawn");
		vmhDoJockeysSpawn = builder
				.comment("\nDetermines whether or not Jockeys spawn (Just splits the entities if false).\n")
				.translation("vmh.config.vmhDoJockeysSpawn")
				.define("vmhDoJockeysSpawn", true);
		builder.pop();

		builder.push("Do Scale Noise");
		vmhDoScaleNoise = builder
				.comment("\nDetermines whether or not to scale noise by the scale value.\n")
				.translation("vmh.config.vmhDoScaleNoise")
				.define("vmhDoScaleNoise", true);
		builder.pop();

		builder.push("Add Egg Values");
		vmhAddEggValues = builder
				.comment("\nDetermines whether or not to add size values to eggs.\n")
				.translation("vmh.config.vmhAddEggValues")
				.define("vmhAddEggValues", false);
		builder.pop();

		builder.push("Inverse Speed Value");
		vmhInverseSpeedValue = builder
				.comment("\nDetermines whether or not to invert only speed values based on size (True means bigger entities move slower)(Inverts Speed and Attack Speed ).\n")
				.translation("vmh.config.vmhInverseSpeedValue")
				.define("vmhInverseSpeedValue", false);
		builder.pop();

		builder.push("Inverse Health Value");
		vmhInverseHealthValue = builder
				.comment("\nDetermines whether or not to invert only health values based on size except speed (Inverts Health Only).\n")
				.translation("vmh.config.vmhInverseHealthValue")
				.define("vmhInverseHealthValue", false);
		builder.pop();

		builder.push("Inverse Size Value");
		vmhInverseSizeValue = builder
				.comment("\nDetermines whether or not to invert only base size values based on size except speed (Inverts Height and Width Only).\n")
				.translation("vmh.config.vmhInverseSizeValue")
				.define("vmhInverseSizeValue", false);
		builder.pop();

		builder.push("Inverse Value");
		vmhInverseValue = builder
				.comment("\nDetermines whether or not to invert all invertable values based on size except speed (Inverts Jump Height, Attack, Defense, and Knockback ).\n")
				.translation("vmh.config.vmhInverseValue")
				.define("vmhInverseValue", false);
		builder.pop();

		builder.push("Entities Blacklist");
		vmhEntitiesBlacklist = builder
				.comment("\nDetermines what entities are blacklist to be used by this mod.\n")
				.translation("vmh.config.vmhEntitiesBlacklist")
				.define("vmhEntitiesBlacklist", new ArrayList<>(List.of(
						"minecraft:ender_dragon",
						"minecraft:wither",
						"minecraft:warden"
				)));
		builder.pop();

		builder.push("Inverted Entities Whitelist");
		vmhEntitiesInvertedWhitelist = builder
				.comment("\nDetermines what entities are allowed to have their values inverted by this mod.\n")
				.translation("vmh.config.vmhEntitiesInvertedWhitelist")
				.define("vmhEntitiesInvertedWhitelist", new ArrayList<>(List.of("")));
		builder.pop();

	}

	private static void setupBiomesConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Whitelist Entities by Biomes");
		vmhWhitelistByBiomes = builder
				.comment("\nDetermines what entities distribution values change to when in a specific the specific biome.\n")
				.translation("vmh.config.vmhWhitelistByBiomes")
				.define("vmhWhitelistByBiomes", new ArrayList<>(
						List.of("minecraft:birch_forest;minecraft:skeleton;0.95;1.05;1;0",
								"minecraft:birch_forest;minecraft:zombie;0.95;1.05;1;0",
								"minecraft:sunflower_plains;minecraft:zombie;0.95;1.05;1;0"
						)));
		builder.pop();
	}

	//DEFAULT VALUES IF CONFIG FAILS TO LOAD
	public static double defaultMin=0.95D;
	public static double defaultMax=1.05D;
	public static double defaultMedian=1.0D;
	public static int defaultDistribution=0;

	public static boolean doScaleNoise=false;

	public static boolean isDoAutoCompat=false;

	public static boolean isBuffHealth=false;
	public static boolean isBuffSpeed=false;
	public static boolean isBuffAttackDamage=false;
	public static boolean isBuffAttackSpeed=false;
	public static boolean isBuffAttackKnockback=false;
	public static boolean isBuffDefense=false;
	public static boolean isBuffJumpHeight=false;
	public static boolean isBuffJumpHeightHorse=false;

	public static boolean isInverseSpeed=false;
	public static boolean isInverseScale=false;
	public static boolean isInverseHealth=false;
	public static boolean isInverseSize=false;

	public static boolean doJockeysSpawn=true;


	//DATA LISTS FROM CONFIG

	//Blacklist(Entities)
	public static ArrayList<String> entityBlacklist = new ArrayList<>();
	//Inverted Whitelist(Entities) - (For Inverted Scaling)
	public static ArrayList<String> entityInvertedWhitelist = new ArrayList<>();

	public static HashMap<String, String> entityData=new HashMap<>();//Entity or Biome;Entity,EntityData

	//SET SPLIT VALUES
	public static void loadForgeConfig() {
		//Blacklist
		entityBlacklist = vmhEntitiesBlacklist.get();

		//Inverted Whitelist(Entities) - (For Inverted Scaling)
		entityInvertedWhitelist = vmhEntitiesInvertedWhitelist.get();

		//Entity Data
		defaultMin= vmhSizeMin.get();
		defaultMax= vmhSizeMax.get();
		defaultMedian= vmhSizeMedian.get();
		defaultDistribution= vmhDistributionType.get();

		//Auto-Compat
		isDoAutoCompat= vmhDoAutoCompat.get();

		doScaleNoise=vmhDoScaleNoise.get();

		//Buffs
		isBuffHealth= vmhBuffHealth.get();
		isBuffSpeed= vmhBuffSpeed.get();
		isBuffAttackDamage= vmhBuffAttackDamage.get();
		isBuffAttackSpeed= vmhBuffAttackSpeed.get();
		isBuffAttackKnockback= vmhBuffKnockback.get();
		isBuffDefense= vmhBuffDefense.get();
		isBuffJumpHeight= vmhBuffJumpHeight.get();
		isBuffJumpHeightHorse= vmhBuffHorseJumpHeight.get();

		//Inverse Scaling
		isInverseSpeed= vmhInverseSpeedValue.get();
		isInverseScale= vmhInverseValue.get();
		isInverseHealth= vmhInverseHealthValue.get();
		isInverseSize= vmhInverseSizeValue.get();

		doJockeysSpawn=vmhDoJockeysSpawn.get();
		//Split Lists and add to entityData HashMap
		for (String data: vmhEntities.get()) {
			String[] splitData = data.split(";");
			entityData.put(splitData[0],splitData[1]+";"+splitData[2]+";"+splitData[3]+";"+splitData[4]);//Entity,EntityData
		}
		for (String biomeData:vmhWhitelistByBiomes.get()) {
			String[] splitBiomeData = biomeData.split(";");
			entityData.put(splitBiomeData[0]+";"+splitBiomeData[1],splitBiomeData[2]+";"+splitBiomeData[3]+";"+splitBiomeData[4]+";"+splitBiomeData[5]);//Biome;Entity,EntityData
		}
	}



}

