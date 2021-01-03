package io.github.npc_strider.NukeMod.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;

public class TransformConstants {
    
    // private static final Map<Block,Block> transform = ImmutableMap.of(
    //     Blocks.STONE,       Blocks.COBBLESTONE,
    //     Blocks.DIRT,        Blocks.SAND,
    //     Blocks.GRASS_BLOCK, Blocks.SAND,
    //     Blocks.SAND,        Blocks.GLASS
    // );

    // private static final Map<BlockTags,Block> transformBlockTag = ImmutableMap.of(   //Nah this is dumb. Have to look up each tag (one block can have multiple tags) which consumes time.
    //     BlockTags.BASE_STONE_OVERWORLD,       Blocks.COBBLESTONE,
    // );

    public static Map<Material,Block> transformMaterial;   //We're using JAVA 8 so we can't use the new notation :(
    static {
        transformMaterial =  new HashMap<>();
        // transformMaterial.put(  Material.STONE,             Blocks.GRAVEL       );   //Bad idea - creates a shitton of lag due to FallingSand entities.
        // transformMaterial.put(  Material.SOLID_ORGANIC,     Blocks.SAND         );
        // transformMaterial.put(  Material.SOIL,              Blocks.SAND         );
        transformMaterial.put(  Material.AGGREGATE,         Blocks.GLASS        );
        transformMaterial.put(  Material.PORTAL,            Blocks.AIR          );
        transformMaterial.put(  Material.METAL,             Blocks.LAVA         );
        transformMaterial.put(  Material.SNOW_BLOCK,        Blocks.WATER        );
        transformMaterial.put(  Material.SNOW_LAYER,        Blocks.WATER        );
        transformMaterial.put(  Material.ICE,               Blocks.WATER        );
        transformMaterial.put(  Material.DENSE_ICE,         Blocks.WATER        );
        transformMaterial.put(  Material.WOOD,              Blocks.COAL_BLOCK   );
        transformMaterial.put(  Material.NETHER_WOOD,       Blocks.COAL_BLOCK   );
        transformMaterial.put(  Material.NETHER_SHOOTS,     Blocks.COAL_BLOCK   );
        transformMaterial.put(  Material.SOLID_ORGANIC,     Blocks.COAL_BLOCK   );
    }
    
    public static Map<Material,List<Block>> transformToFallingTile;
    static {
        transformToFallingTile = new HashMap<>();
        transformToFallingTile.put(  Material.STONE,             Arrays.asList(Blocks.GRAVEL, Blocks.COBBLESTONE)    );
        transformToFallingTile.put(  Material.SOLID_ORGANIC,     Arrays.asList(Blocks.SAND, Blocks.SANDSTONE)        );
        transformToFallingTile.put(  Material.SOIL,              Arrays.asList(Blocks.SAND, Blocks.SANDSTONE)        );
    }
    
}
