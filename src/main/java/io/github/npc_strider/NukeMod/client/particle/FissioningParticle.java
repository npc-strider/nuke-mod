package io.github.npc_strider.NukeMod.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class FissioningParticle extends SpriteBillboardParticle {

    protected FissioningParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider sprites) {
        super(clientWorld, d, e, f, g, h, i);
        setSprite(sprites.getSprite(world.random));
        float j = this.random.nextFloat() * 0.3F + 0.7F;
        scale(2.0F * j);
        this.setMaxAge((int)(10.0F / (this.random.nextFloat() * 0.9F + 0.1F)));	//Just increasing the maxAge by increasing the max from 4.0F to 10.0F
        this.colorRed = 1.0F * j;
        this.colorGreen = 0.0F;
        this.colorBlue = 0.0F;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final FabricSpriteProvider sprites;

        public Factory(FabricSpriteProvider sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(DefaultParticleType type, ClientWorld world, double x, double y, double z, double vX, double vY, double vZ) {
            return new FissioningParticle(world, x, y, z, vX, vY, vZ, sprites);
        }
    }
}