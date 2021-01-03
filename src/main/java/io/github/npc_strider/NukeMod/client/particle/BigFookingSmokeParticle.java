package io.github.npc_strider.NukeMod.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AscendingParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class BigFookingSmokeParticle extends AscendingParticle {
   protected BigFookingSmokeParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
      // super(world, x, y, z, velocityX, velocityY, velocityZ, 2.5F, spriteProvider);
      super(world, x, y, z, 0.1F, 0.1F, 0.1F, velocityX, velocityY, velocityZ, 1.0F, spriteProvider, 0.3F, 20*60, 0.0005D, false);
      this.scale = 10.0F;
      this.age = random.nextInt(160);
   }

   @Environment(EnvType.CLIENT)
   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
         return new BigFookingSmokeParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
      }
   }
}
