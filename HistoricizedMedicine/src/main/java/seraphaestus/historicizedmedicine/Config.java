package seraphaestus.historicizedmedicine;

import net.minecraftforge.common.config.Configuration;
import scala.Int;

public class Config {

	public static final String categoryGeneral = "general";
	public static final String categoryCompat = "compat";
	public static final String categoryKnowledge = "knowledge";
	public static final String categoryClient = "client";

	// This values below you can access elsewhere in your mod:
	public static boolean implementHoney = true;

	public static boolean canViewEffectsInTooltip = true;
	public static boolean canViewHealAmountInTooltip = true;
	public static boolean knowledgeSheetConsumed = true;
	public static boolean registerRecipes = true;
	public static boolean enableCreativeTab = true;
	public static boolean enableRecipes = true;
	public static boolean enablePlague = true;
	public static boolean plagueCarriers = true;
	public static float plagueRange = 2f;
	public static int plagueDuration = 20 * 60 * 5;    //5 minutes
	public static int bleedTotalAmount = 10;
	public static int statueParticleAmount = 10;
	public static int statueCooldown = 60 * 20;
	public static boolean disableUseOfMilkBuckets = true;
	public static String plagueName = "The Block Death";
	public static float plagueChance = 40f;

	public static boolean fullKnowledgeRequired = true;
	public static boolean craftingConsumesKnowledge = false;
	public static boolean requireExactTier = true;

	public static String commandRunOnEbersUse = null;
	public static String commandRunOnCorpusUse = null;
	public static String commandRunOnCanonUse = null;
	public static String commandRunOnMagnaUse = null;

	// Call this from CommonProxy.preInit(). It will create our config if it doesn't
	// exist yet and read the values if it does exist.
	public static void readConfig() {
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
			initCompatConfig(cfg);
			initKnowledgeConfig(cfg);
			initClientConfig(cfg);
		} catch (Exception e1) {

		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}

	private static void initGeneralConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(categoryGeneral, "General configuration");
		canViewEffectsInTooltip = cfg.getBoolean("tooltipEffects", categoryGeneral, canViewEffectsInTooltip, "Set to false if you don't want players to be able to see what medical items do re: potion effects");
		canViewHealAmountInTooltip = cfg.getBoolean("tooltipHealAmount", categoryGeneral, canViewHealAmountInTooltip, "Set to false if you don't want players to be able to see what medical items do re: health");
		registerRecipes = cfg.getBoolean("registerRecipes", categoryGeneral, registerRecipes, "Set to false to disable all recipes for this mod");
		enableCreativeTab = cfg.getBoolean("enableCreativeTab", categoryGeneral, enableCreativeTab, "Set to false to disable the creative tab for this mod");
		enableRecipes = cfg.getBoolean("enableRecipes", categoryGeneral, enableRecipes, "Set to false to disable all recipes implemented by this mod");
		bleedTotalAmount = cfg.getInt("bleedTotalAmount", categoryGeneral, bleedTotalAmount, 0, Int.MaxValue(), "The amount of half-hearts that Blood Loss will take off over the course of its effect, per level of the effect");
		statueCooldown = cfg.getInt("statueCooldown", categoryGeneral, statueCooldown, 0, Int.MaxValue(), "The duration in ticks of the cooldown of a statue after using it");
		disableUseOfMilkBuckets = cfg.getBoolean("disableUseOfMilkBuckets", categoryGeneral, disableUseOfMilkBuckets, "Disable to restore milk bucket use.");
		enablePlague = cfg.getBoolean("enablePlague", categoryGeneral, enablePlague, "Set to false to disable the effects of plague.");
		plagueCarriers = cfg.getBoolean("plagueCarriers", categoryGeneral, plagueCarriers, "Whether rats carry plague.");
		plagueRange = cfg.getFloat("plagueRange", categoryGeneral, plagueRange, 0, 16, "The range within which entites will catch the plague from the player.");
		plagueDuration = cfg.getInt("plagueDuration", categoryGeneral, plagueDuration, 1, Int.MaxValue(), "How many ticks it takes until an entity with the plague dies.");
		plagueName = cfg.getString("plagueName", categoryGeneral, plagueName, "Display name for the plague effect.");
		plagueChance = cfg.getFloat("plagueChance", categoryGeneral, plagueChance, 0, 100, "The chance (percentage) that a carrier will carry the plague.");
	}

	private static void initCompatConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(categoryCompat, "Configuration of common items that may be duplicates");
		implementHoney = cfg.getBoolean("implementHoney", categoryCompat, implementHoney, "Set to false if you don't want this mod to add honey");

	}

	private static void initKnowledgeConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(categoryKnowledge, "Configuration of knowledge sheets (e.g. the ebers papyrus)");
		knowledgeSheetConsumed = cfg.getBoolean("knSheetConsumed", categoryKnowledge, knowledgeSheetConsumed, "Set to false if you want players to keep their knowledge sheet after right clicking it");
		fullKnowledgeRequired = cfg.getBoolean("fullKnowledgeRequired", categoryKnowledge, fullKnowledgeRequired, "Whether the sheet needs to be filled before it can be used in the crafting table");
		craftingConsumesKnowledge = cfg.getBoolean("craftingConsumesKnowledge", categoryKnowledge, craftingConsumesKnowledge, "If enabled, crafting an item reduces the stored knowledge of the knowledge sheet by 1");
		requireExactTier = cfg.getBoolean("requireExactTier", categoryKnowledge, requireExactTier, "Whether crafting requries the exact type of knowledge sheet, or any greater than or equal to it in terms of advancement");

		commandRunOnEbersUse = cfg.getString("commandEbers", categoryKnowledge, commandRunOnEbersUse, "The command that should be run when a player uses a complete version of this sheet. (useful with game stages)");
		commandRunOnCorpusUse = cfg.getString("commandCorpus", categoryKnowledge, commandRunOnCorpusUse, "The command that should be run when a player uses a complete version of this sheet. (useful with game stages)");
		commandRunOnCanonUse = cfg.getString("commandCanon", categoryKnowledge, commandRunOnCanonUse, "The command that should be run when a player uses a complete version of this sheet. (useful with game stages)");
		commandRunOnMagnaUse = cfg.getString("commandMagna", categoryKnowledge, commandRunOnMagnaUse, "The command that should be run when a player uses a complete version of this sheet. (useful with game stages)");
	}

	private static void initClientConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(categoryClient, "Configuration of client-side things");
		statueParticleAmount = cfg.getInt("statueParticleAmount", categoryClient, statueParticleAmount, 0, 255, "The amount of particles that should be spawned when a statue is used");
	}
}
