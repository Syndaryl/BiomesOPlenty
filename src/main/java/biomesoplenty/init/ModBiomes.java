/*******************************************************************************
 * Copyright 2014-2019, the Biomes O' Plenty Team
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 *
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/
package biomesoplenty.init;

import static biomesoplenty.api.biome.BOPBiomes.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import biomesoplenty.common.biome.end.DeadReefBiome;
import biomesoplenty.common.biome.end.EtherealForestBiome;
import biomesoplenty.common.biome.nether.*;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import biomesoplenty.api.enums.BOPClimates;
import biomesoplenty.common.biome.BiomeBOP;
import biomesoplenty.common.biome.overworld.AlpsBiome;
import biomesoplenty.common.biome.overworld.AlpsFoothillsBiome;
import biomesoplenty.common.biome.overworld.BayouBiome;
import biomesoplenty.common.biome.overworld.MireBiome;
import biomesoplenty.common.biome.overworld.BorealForestBiome;
import biomesoplenty.common.biome.overworld.BrushlandBiome;
import biomesoplenty.common.biome.overworld.ChaparralBiome;
import biomesoplenty.common.biome.overworld.CherryBlossomGroveBiome;
import biomesoplenty.common.biome.overworld.ColdDesertBiome;
import biomesoplenty.common.biome.overworld.ConiferousForestBiome;
import biomesoplenty.common.biome.overworld.BogBiome;
import biomesoplenty.common.biome.overworld.DeadForestBiome;
import biomesoplenty.common.biome.overworld.DryPlainsBiome;
import biomesoplenty.common.biome.overworld.FloodplainBiome;
import biomesoplenty.common.biome.overworld.FlowerMeadowBiome;
import biomesoplenty.common.biome.overworld.GrasslandBiome;
import biomesoplenty.common.biome.overworld.GravelBeachBiome;
import biomesoplenty.common.biome.overworld.GroveBiome;
import biomesoplenty.common.biome.overworld.OrchardBiome;
import biomesoplenty.common.biome.overworld.HighlandBiome;
import biomesoplenty.common.biome.overworld.MoorBiome;
import biomesoplenty.common.biome.overworld.LavenderFieldBiome;
import biomesoplenty.common.biome.overworld.LushGrasslandBiome;
import biomesoplenty.common.biome.overworld.LushSwampBiome;
import biomesoplenty.common.biome.overworld.MangroveBiome;
import biomesoplenty.common.biome.overworld.MapleWoodsBiome;
import biomesoplenty.common.biome.overworld.MarshBiome;
import biomesoplenty.common.biome.overworld.MeadowBiome;
import biomesoplenty.common.biome.overworld.MysticGroveBiome;
import biomesoplenty.common.biome.overworld.OasisBiome;
import biomesoplenty.common.biome.overworld.OminousWoodsBiome;
import biomesoplenty.common.biome.overworld.OriginBeachBiome;
import biomesoplenty.common.biome.overworld.OriginHillsBiome;
import biomesoplenty.common.biome.overworld.OutbackBiome;
import biomesoplenty.common.biome.overworld.OvergrownCliffsBiome;
import biomesoplenty.common.biome.overworld.PastureBiome;
import biomesoplenty.common.biome.overworld.PrairieBiome;
import biomesoplenty.common.biome.overworld.PumpkinPatchBiome;
import biomesoplenty.common.biome.overworld.RainforestBiome;
import biomesoplenty.common.biome.overworld.RedwoodForestBiome;
import biomesoplenty.common.biome.overworld.RedwoodForestEdgeBiome;
import biomesoplenty.common.biome.overworld.ScrublandBiome;
import biomesoplenty.common.biome.overworld.SeasonalForestBiome;
import biomesoplenty.common.biome.overworld.ShieldBiome;
import biomesoplenty.common.biome.overworld.ShrublandBiome;
import biomesoplenty.common.biome.overworld.SilkgladeBiome;
import biomesoplenty.common.biome.overworld.SnowyConiferousForestBiome;
import biomesoplenty.common.biome.overworld.SnowyForestBiome;
import biomesoplenty.common.biome.overworld.SteppeBiome;
import biomesoplenty.common.biome.overworld.TemperateRainforestBiome;
import biomesoplenty.common.biome.overworld.TropicalRainforestBiome;
import biomesoplenty.common.biome.overworld.TropicsBiome;
import biomesoplenty.common.biome.overworld.TundraBiome;
import biomesoplenty.common.biome.overworld.VolcanoBiome;
import biomesoplenty.common.biome.overworld.VolcanoEdgeBiome;
import biomesoplenty.common.biome.overworld.WastelandBiome;
import biomesoplenty.common.biome.overworld.WetlandBiome;
import biomesoplenty.common.biome.overworld.WhiteBeachBiome;
import biomesoplenty.common.biome.overworld.WoodlandBiome;
import biomesoplenty.common.biome.overworld.XericShrublandBiome;
import biomesoplenty.common.world.WorldTypeBOP;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBiomes
{
    public static WorldTypeBOP worldType;

    public static Multimap<Integer, WeightedSubBiome> subBiomes = HashMultimap.create();
    public static List<Integer> islandBiomes = Lists.newArrayList();

    public static void setup()
    {
        worldType = new WorldTypeBOP();
        registerBiomeDictionaryTags();
    }

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event)
    {
        //Technical Biomes (Need to be registered before main biomes that use them)
        mangrove = registerBiome(new MangroveBiome(), "mangrove");
        gravel_beach = registerBiome(new GravelBeachBiome(), "gravel_beach");
        origin_beach = registerBiome(new OriginBeachBiome(), "origin_beach");
        white_beach = registerBiome(new WhiteBeachBiome(), "white_beach");
    	alps_foothills = registerBiome(new AlpsFoothillsBiome(), "alps_foothills");
        redwood_forest_edge = registerBiome(new RedwoodForestEdgeBiome(), "redwood_forest_edge");
        volcano_edge = registerBiome(new VolcanoEdgeBiome(), "volcano_edge");

        //Overworld Biomes
    	alps = registerBiome(new AlpsBiome(), "alps");
    	bayou = registerBiome(new BayouBiome(), "bayou");
        bog = registerBiome(new BogBiome(), "bog");
        boreal_forest = registerBiome(new BorealForestBiome(), "boreal_forest");
    	brushland = registerBiome(new BrushlandBiome(), "brushland");
        chaparral = registerBiome(new ChaparralBiome(), "chaparral");
    	cherry_blossom_grove = registerBiome(new CherryBlossomGroveBiome(), "cherry_blossom_grove");
    	cold_desert = registerBiome(new ColdDesertBiome(), "cold_desert");
        coniferous_forest = registerBiome(new ConiferousForestBiome(), "coniferous_forest");
        dead_forest = registerBiome(new DeadForestBiome(), "dead_forest");
        dry_plains = registerBiome(new DryPlainsBiome(), "dry_plains");
        floodplain = registerBiome(new FloodplainBiome(), "floodplain");
        flower_meadow = registerBiome(new FlowerMeadowBiome(), "flower_meadow");
        grassland = registerBiome(new GrasslandBiome(), "grassland");
        grove = registerBiome(new GroveBiome(), "grove");
        orchard = registerBiome(new OrchardBiome(), "orchard");
        highland = registerBiome(new HighlandBiome(), "highland");
        lavender_field = registerBiome(new LavenderFieldBiome(), "lavender_field");
        lush_grassland = registerBiome(new LushGrasslandBiome(), "lush_grassland");
        lush_swamp = registerBiome(new LushSwampBiome(), "lush_swamp");
        maple_woods = registerBiome(new MapleWoodsBiome(), "maple_woods");
        marsh = registerBiome(new MarshBiome(), "marsh");
        meadow = registerBiome(new MeadowBiome(), "meadow");
        mire = registerBiome(new MireBiome(), "mire");
        moor = registerBiome(new MoorBiome(), "moor");
        mystic_grove = registerBiome(new MysticGroveBiome(), "mystic_grove");
        oasis = registerBiome(new OasisBiome(), "oasis");
        ominous_woods = registerBiome(new OminousWoodsBiome(), "ominous_woods");
        origin_hills = registerBiome(new OriginHillsBiome(), "origin_hills");
        outback = registerBiome(new OutbackBiome(), "outback");
        overgrown_cliffs = registerBiome(new OvergrownCliffsBiome(), "overgrown_cliffs");
        pasture = registerBiome(new PastureBiome(), "pasture");
        prairie = registerBiome(new PrairieBiome(), "prairie");
        pumpkin_patch = registerBiome(new PumpkinPatchBiome(), "pumpkin_patch");
        rainforest = registerBiome(new RainforestBiome(), "rainforest");
        redwood_forest = registerBiome(new RedwoodForestBiome(), "redwood_forest");
        scrubland = registerBiome(new ScrublandBiome(), "scrubland");
        seasonal_forest = registerBiome(new SeasonalForestBiome(), "seasonal_forest");
        shield = registerBiome(new ShieldBiome(), "shield");
        shrubland = registerBiome(new ShrublandBiome(), "shrubland");
        silkglade = registerBiome(new SilkgladeBiome(), "silkglade");
        snowy_coniferous_forest = registerBiome(new SnowyConiferousForestBiome(), "snowy_coniferous_forest");
        snowy_forest = registerBiome(new SnowyForestBiome(), "snowy_forest");
        steppe = registerBiome(new SteppeBiome(), "steppe");
        temperate_rainforest = registerBiome(new TemperateRainforestBiome(), "temperate_rainforest");
        tropical_rainforest = registerBiome(new TropicalRainforestBiome(), "tropical_rainforest");
        tropics = registerBiome(new TropicsBiome(), "tropics");
        tundra = registerBiome(new TundraBiome(), "tundra");
        volcano = registerBiome(new VolcanoBiome(), "volcano");
        wasteland = registerBiome(new WastelandBiome(), "wasteland");
        wetland = registerBiome(new WetlandBiome(), "wetland");
        woodland = registerBiome(new WoodlandBiome(), "woodland");
        xeric_shrubland = registerBiome(new XericShrublandBiome(), "xeric_shrubland");

        //Nether Biomes
        ashen_inferno = registerBiome(new AshenInfernoBiome(), "ashen_inferno");
        fungi_forest = registerBiome(new FungiForestBiome(), "fungi_forest");
        glowstone_cavern = registerBiome(new GlowstoneCavernBiome(), "glowstone_cavern");
        undergarden = registerBiome(new UndergardenBiome(), "undergarden");
        visceral_heap = registerBiome(new VisceralHeapBiome(), "visceral_heap");

        //End Biomes
        dead_reef = registerBiome(new DeadReefBiome(), "dead_reef");
        ethereal_forest = registerBiome(new EtherealForestBiome(), "ethereal_forest");

        //Sub/Island Biomes (Note: Rarity supports two decimal places)
        registerSubBiome(Biomes.DESERT, oasis, 0.1F, 100);
        registerSubBiome(Biomes.PLAINS, orchard, 0.5F, 100);
        registerSubBiome(brushland, xeric_shrubland, 1.0F, 100);
        registerSubBiome(highland, moor, 0.75F, 100);
        registerSubBiome(meadow, flower_meadow, 0.5F, 100);
        registerSubBiome(prairie, pasture, 1.0F, 100);
        registerSubBiome(seasonal_forest, pumpkin_patch, 0.45F, 100);

        registerIslandBiome(origin_hills, BOPClimates.COOL_TEMPERATE, 50);
        registerIslandBiome(origin_hills, BOPClimates.DRY_TEMPERATE, 50);
        registerIslandBiome(origin_hills, BOPClimates.WET_TEMPERATE, 50);
        
        registerIslandBiome(volcano, BOPClimates.WARM_TEMPERATE, 50);
        registerIslandBiome(volcano, BOPClimates.MEDITERRANEAN, 50);
        registerIslandBiome(volcano, BOPClimates.SAVANNA, 50);
        
        registerIslandBiome(tropics, BOPClimates.SUBTROPICAL, 50);
        registerIslandBiome(tropics, BOPClimates.TROPICAL, 50);
        registerIslandBiome(tropics, BOPClimates.HOT_DESERT, 50);
    }
    
    private static void registerBiomeDictionaryTags()
    {
        //Overworld Biomes
        registerBiomeToDictionary(alps, Type.MOUNTAIN, Type.SNOWY, Type.COLD);
        registerBiomeToDictionary(alps_foothills, Type.MOUNTAIN, Type.SNOWY, Type.FOREST, Type.SPARSE, Type.COLD);
        registerBiomeToDictionary(bayou, Type.SWAMP, Type.HOT, Type.WET, Type.DENSE);
        registerBiomeToDictionary(bog, Type.SWAMP, Type.CONIFEROUS, Type.COLD, Type.LUSH, Type.WET);
        registerBiomeToDictionary(boreal_forest, Type.FOREST, Type.CONIFEROUS, Type.HILLS, Type.COLD, Type.DENSE);
        registerBiomeToDictionary(brushland, Type.SAVANNA, Type.HOT, Type.DRY, Type.SPARSE);
        registerBiomeToDictionary(chaparral, Type.PLAINS, Type.DRY, Type.HILLS);
        registerBiomeToDictionary(cherry_blossom_grove, Type.FOREST, Type.MAGICAL, Type.LUSH, Type.DENSE);
        registerBiomeToDictionary(cold_desert, Type.SNOWY, Type.DRY, Type.COLD);
        registerBiomeToDictionary(coniferous_forest, Type.CONIFEROUS, Type.FOREST, Type.COLD, Type.DENSE);
        registerBiomeToDictionary(dead_forest, Type.FOREST, Type.DEAD, Type.COLD, Type.DRY, Type.SPARSE);
        registerBiomeToDictionary(dry_plains, Type.PLAINS, Type.SAVANNA, Type.HOT, Type.DRY);
        registerBiomeToDictionary(floodplain, Type.JUNGLE, Type.WATER, Type.HOT, Type.WET);
        registerBiomeToDictionary(flower_meadow, Type.PLAINS, Type.LUSH);
        registerBiomeToDictionary(grassland, Type.PLAINS, Type.HILLS, Type.WET);
        registerBiomeToDictionary(gravel_beach, Type.BEACH);
        registerBiomeToDictionary(grove, Type.FOREST, Type.PLAINS, Type.LUSH, Type.SPARSE);
        registerBiomeToDictionary(highland, Type.MOUNTAIN, Type.HILLS, Type.WET);
        registerBiomeToDictionary(lavender_field, Type.PLAINS, Type.MAGICAL, Type.LUSH);
        registerBiomeToDictionary(lush_grassland, Type.JUNGLE, Type.PLAINS, Type.HILLS, Type.WET, Type.HOT, Type.LUSH);
        registerBiomeToDictionary(lush_swamp, Type.SWAMP, Type.LUSH, Type.WET, Type.DENSE);
        registerBiomeToDictionary(mangrove, Type.WATER, Type.WET, Type.DENSE, Type.LUSH);
        registerBiomeToDictionary(maple_woods, Type.FOREST, Type.CONIFEROUS, Type.COLD, Type.DENSE);
        registerBiomeToDictionary(marsh, Type.WATER, Type.WET, Type.LUSH);
        registerBiomeToDictionary(meadow, Type.PLAINS, Type.FOREST, Type.LUSH, Type.COLD);
        registerBiomeToDictionary(mire, Type.SWAMP, Type.DEAD, Type.WET);
        registerBiomeToDictionary(moor, Type.HILLS, Type.WET);
        registerBiomeToDictionary(mystic_grove, Type.MAGICAL, Type.FOREST, Type.LUSH, Type.DENSE, Type.RARE);
        registerBiomeToDictionary(oasis, Type.SANDY, Type.LUSH, Type.JUNGLE, Type.HOT, Type.SPARSE);
        registerBiomeToDictionary(ominous_woods, Type.MAGICAL, Type.FOREST, Type.SPOOKY, Type.DEAD, Type.DENSE, Type.RARE);
        registerBiomeToDictionary(orchard, Type.FOREST, Type.PLAINS, Type.DENSE, Type.LUSH);
        registerBiomeToDictionary(origin_beach, Type.BEACH, Type.RARE);
        registerBiomeToDictionary(origin_hills, Type.WATER, Type.FOREST, Type.RARE);
        registerBiomeToDictionary(outback, Type.SANDY, Type.SAVANNA, Type.HOT, Type.DRY, Type.SPARSE);
        registerBiomeToDictionary(overgrown_cliffs, Type.MOUNTAIN, Type.HILLS, Type.LUSH, Type.JUNGLE, Type.DENSE, Type.HOT);
        registerBiomeToDictionary(pasture, Type.PLAINS, Type.DRY);
        registerBiomeToDictionary(prairie, Type.PLAINS, Type.DRY, Type.SPARSE);
        registerBiomeToDictionary(pumpkin_patch, Type.FOREST, Type.COLD, Type.DENSE);
        registerBiomeToDictionary(rainforest, Type.JUNGLE, Type.FOREST, Type.LUSH, Type.HILLS, Type.WET, Type.DENSE);
        registerBiomeToDictionary(redwood_forest, Type.FOREST, Type.DENSE);
        registerBiomeToDictionary(redwood_forest_edge, Type.FOREST, Type.DENSE);
        registerBiomeToDictionary(scrubland, Type.SAVANNA, Type.SPARSE, Type.DRY);
        registerBiomeToDictionary(seasonal_forest, Type.FOREST, Type.COLD, Type.DENSE);
        registerBiomeToDictionary(shield, Type.FOREST, Type.COLD, Type.WET, Type.DENSE);
        registerBiomeToDictionary(shrubland, Type.PLAINS, Type.DRY, Type.SPARSE);
        registerBiomeToDictionary(silkglade, Type.FOREST, Type.DEAD, Type.SPOOKY, Type.DRY, Type.DENSE);
        registerBiomeToDictionary(snowy_coniferous_forest, Type.FOREST, Type.CONIFEROUS, Type.SNOWY, Type.COLD, Type.DENSE);
        registerBiomeToDictionary(snowy_forest, Type.SNOWY, Type.FOREST, Type.COLD, Type.SPARSE);
        registerBiomeToDictionary(steppe, Type.PLAINS, Type.HILLS, Type.DRY);
        registerBiomeToDictionary(temperate_rainforest, Type.FOREST, Type.LUSH, Type.WET, Type.DENSE);
        registerBiomeToDictionary(tropical_rainforest, Type.JUNGLE, Type.LUSH, Type.HOT, Type.WET, Type.DENSE);
        registerBiomeToDictionary(tropics, Type.WATER, Type.JUNGLE, Type.LUSH, Type.DENSE);
        registerBiomeToDictionary(tundra, Type.COLD, Type.WASTELAND, Type.DEAD, Type.SPARSE);
        registerBiomeToDictionary(volcano, Type.WATER, Type.DEAD, Type.WASTELAND, Type.MOUNTAIN, Type.HOT, Type.DRY);
        registerBiomeToDictionary(volcano_edge, Type.WATER, Type.DEAD, Type.WASTELAND, Type.MOUNTAIN, Type.HOT, Type.DRY);
        registerBiomeToDictionary(wasteland, Type.WASTELAND, Type.DEAD, Type.DRY, Type.SPARSE, Type.HOT);
        registerBiomeToDictionary(wetland, Type.SWAMP, Type.FOREST, Type.LUSH, Type.WET, Type.DENSE);
        registerBiomeToDictionary(white_beach, Type.BEACH);
        registerBiomeToDictionary(woodland, Type.FOREST, Type.DENSE);
        registerBiomeToDictionary(xeric_shrubland, Type.SANDY, Type.SAVANNA, Type.LUSH, Type.HOT, Type.DRY, Type.SPARSE);

        //Nether Biomes
        registerBiomeToDictionary(ashen_inferno, Type.NETHER, Type.HOT);
        registerBiomeToDictionary(fungi_forest, Type.NETHER, Type.HOT);
        registerBiomeToDictionary(glowstone_cavern, Type.NETHER, Type.HOT);
        registerBiomeToDictionary(undergarden, Type.NETHER, Type.HOT);
        registerBiomeToDictionary(visceral_heap, Type.NETHER, Type.HOT);

        //End Biomes
        registerBiomeToDictionary(dead_reef, Type.END);
        registerBiomeToDictionary(ethereal_forest, Type.END);
    }
    
    private static void registerBiomeToDictionary(Optional<Biome> biome, Type...types)
    {
        if (biome.isPresent())
        {
            BiomeDictionary.addTypes(biome.get(), types);
        }
    }

    public static Optional<Biome> registerBiome(BiomeBOP biome, String name)
    {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        
        if (biome.canSpawnInBiome)
        {
        	BiomeManager.addSpawnBiome(biome);
        }

        for (Map.Entry<BOPClimates, Integer> entry : biome.getWeightMap().entrySet())
        {
            if (entry != null)
            {
                BOPClimates climate = entry.getKey();
                int weight = entry.getValue();
                climate.addBiome(weight, biome);
            }
        }

        return Optional.of(biome);
    }

    public static void registerSubBiome(Biome parent, Optional<Biome> child, float rarity, int weight)
    {
        if (!child.isPresent())
            return;

        subBiomes.put(Registry.BIOME.getId(parent), new WeightedSubBiome(child.get(), rarity, weight));
    }
    
    public static void registerSubBiome(Optional<Biome> parent, Optional<Biome> child, float rarity, int weight)
    {
    	if (!parent.isPresent())
            return;
    	
        if (!child.isPresent())
            return;

        subBiomes.put(Registry.BIOME.getId(parent.get()), new WeightedSubBiome(child.get(), rarity, weight));
    }

    public static void registerIslandBiome(Biome biome, BOPClimates climate, int weight)
    {
        islandBiomes.add(Registry.BIOME.getId(biome));
        climate.addIslandBiome(weight, biome);
    }

    public static void registerIslandBiome(Optional<Biome> biome, BOPClimates climate, int weight)
    {
        if (!biome.isPresent())
            return;

        registerIslandBiome(biome.get(), climate, weight);
    }

    public static class WeightedSubBiome
    {
        public final Biome biome;
        public final float rarity;
        public final int weight;

        public WeightedSubBiome(Biome biome, float rarity, int weight)
        {
            this.biome = biome;
            this.rarity = rarity;
            this.weight = weight;
        }
    }
}
