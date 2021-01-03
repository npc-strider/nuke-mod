package io.github.npc_strider.NukeMod;

import io.github.npc_strider.NukeMod.fission.FissionBlock;
import io.github.npc_strider.NukeMod.fission.FissionEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NukeMod implements ModInitializer {

	public static final String MOD_ID = "nuke-mod";
	// public static final EntityType<FissionEntity> FISSION_ENTITY;

	public static final Identifier FISSION_BLOCK_ID = new Identifier(MOD_ID, "fission-device");
	public static final FissionBlock FISSION_BLOCK = new FissionBlock(FabricBlockSettings.copy(Blocks.TNT));
	
	public static final EntityType FISSION_ENTITY_TYPE = FabricEntityTypeBuilder.create(SpawnGroup.MISC, FissionEntity::new).dimensions(new EntityDimensions(0.98F, 0.98F, false)).trackRangeBlocks(10).trackedUpdateRate(10).fireImmune().build();
	public static final EntityType<FissionEntity> FISSION_ENTITY = FISSION_ENTITY_TYPE;

	public static final Identifier FISSION_ALARM_ID = new Identifier(MOD_ID, "fission-alarm");
	public static final Identifier FISSION_EXPLOSION_ID = new Identifier(MOD_ID, "fission-explosion");
	public static SoundEvent FISSION_ALARM = new SoundEvent(FISSION_ALARM_ID);
	public static SoundEvent FISSION_EXPLOSION = new SoundEvent(FISSION_EXPLOSION_ID);

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, FISSION_BLOCK_ID, FISSION_BLOCK);
		Registry.register(Registry.ITEM, FISSION_BLOCK_ID, new BlockItem(FISSION_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));
		FlammableBlockRegistry.getDefaultInstance().add(FISSION_BLOCK, 15, 100);	//Copy TNT settings

		Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, "fission-entity"), FISSION_ENTITY);

		Registry.register(Registry.SOUND_EVENT, FISSION_ALARM_ID, FISSION_ALARM);
		Registry.register(Registry.SOUND_EVENT, FISSION_EXPLOSION_ID, FISSION_EXPLOSION);
	}
}
