package io.github.npc_strider.NukeMod.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;

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
        // transformMaterial.put(  Material.AGGREGATE,         Blocks.GLASS        );   //Using custom code to accurately transform according to material color.
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

    public static Map<MaterialColor,Block> glassColorMap;
    static {
        glassColorMap = new HashMap<>();
        glassColorMap.put(MaterialColor.WHITE, Blocks.GLASS);
        glassColorMap.put(MaterialColor.ORANGE, Blocks.ORANGE_STAINED_GLASS);
        glassColorMap.put(MaterialColor.MAGENTA, Blocks.MAGENTA_STAINED_GLASS);
        glassColorMap.put(MaterialColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_STAINED_GLASS);
        glassColorMap.put(MaterialColor.YELLOW, Blocks.YELLOW_STAINED_GLASS);
        glassColorMap.put(MaterialColor.LIME, Blocks.LIME_STAINED_GLASS);
        glassColorMap.put(MaterialColor.PINK, Blocks.PINK_STAINED_GLASS);
        glassColorMap.put(MaterialColor.GRAY, Blocks.GRAY_STAINED_GLASS);
        glassColorMap.put(MaterialColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_STAINED_GLASS);
        glassColorMap.put(MaterialColor.CYAN, Blocks.CYAN_STAINED_GLASS);
        glassColorMap.put(MaterialColor.PURPLE, Blocks.PURPLE_STAINED_GLASS);
        glassColorMap.put(MaterialColor.BLUE, Blocks.BLUE_STAINED_GLASS);
        glassColorMap.put(MaterialColor.BROWN, Blocks.BROWN_STAINED_GLASS);
        glassColorMap.put(MaterialColor.GREEN, Blocks.GREEN_STAINED_GLASS);
        glassColorMap.put(MaterialColor.RED, Blocks.RED_STAINED_GLASS);
        glassColorMap.put(MaterialColor.BLACK, Blocks.BLACK_STAINED_GLASS);
    }

    public static Block[] gravitize = { //kind of a misnomer, this should be named the above but this was added later so :/
        Blocks.ANCIENT_DEBRIS
    };
    
}
