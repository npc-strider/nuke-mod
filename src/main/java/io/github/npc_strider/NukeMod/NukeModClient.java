package io.github.npc_strider.NukeMod;


import io.github.npc_strider.NukeMod.client.particle.BigFookingExplosionEmitterParticle;
import io.github.npc_strider.NukeMod.client.particle.BigFookingExplosionParticle;
import io.github.npc_strider.NukeMod.client.particle.BigFookingSmokeEmitterParticle;
import io.github.npc_strider.NukeMod.client.particle.BigFookingSmokeParticle;
import io.github.npc_strider.NukeMod.client.particle.FissioningParticle;
import io.github.npc_strider.NukeMod.render.entity.FissionEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class NukeModClient implements ClientModInitializer {
    public static final DefaultParticleType FISSION_PARTICLE = FabricParticleTypes.simple(false);
    public static final DefaultParticleType BIG_FOOKING_EXPLOSION = FabricParticleTypes.simple(false);
    public static final DefaultParticleType BIG_FOOKING_EXPLOSION_EMITTER = FabricParticleTypes.simple(false);
    public static final DefaultParticleType BIG_FOOKING_SMOKE = FabricParticleTypes.simple(false);
    public static final DefaultParticleType BIG_FOOKING_SMOKE_EMITTER = FabricParticleTypes.simple(false);

    private static final String MOD_ID = NukeMod.MOD_ID;

    @Override
	public void onInitializeClient() {
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, "fission-emission"), FISSION_PARTICLE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, "big-explosion"), BIG_FOOKING_EXPLOSION);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, "big-explosion-emitter"), BIG_FOOKING_EXPLOSION_EMITTER);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, "big-smoke"), BIG_FOOKING_SMOKE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, "big-smoke-emitter"), BIG_FOOKING_SMOKE_EMITTER);
		ParticleFactoryRegistry.getInstance().register(FISSION_PARTICLE, FissioningParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(BIG_FOOKING_EXPLOSION, BigFookingExplosionParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(BIG_FOOKING_EXPLOSION_EMITTER, BigFookingExplosionEmitterParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(BIG_FOOKING_SMOKE, BigFookingSmokeParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(BIG_FOOKING_SMOKE_EMITTER, BigFookingSmokeEmitterParticle.Factory::new);

        EntityRendererRegistry.INSTANCE.register(NukeMod.FISSION_ENTITY, (dispatcher, context) -> new FissionEntityRenderer(dispatcher));
	}
}
