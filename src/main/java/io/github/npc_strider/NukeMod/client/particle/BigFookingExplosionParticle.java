package io.github.npc_strider.NukeMod.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class BigFookingExplosionParticle extends SpriteBillboardParticle {
	private final SpriteProvider spriteProvider;
	private final float scale_;

	private BigFookingExplosionParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, SpriteProvider spriteProvider) {
		super(world, x, y, z, vx, vy, vz);
		// this.maxAge = 6 + this.random.nextInt(4);
		this.maxAge = 10 + this.random.nextInt(10);
		float f = this.random.nextFloat() * 0.6F + 0.4F;
		this.colorRed = f;
		this.colorGreen = f;
		this.colorBlue = f;
		this.velocityX = vx;
		this.velocityY = vy;
		this.velocityZ = vz;
		this.prevAngle = this.angle = this.random.nextFloat()*2*(float)Math.PI;
		// this.move((float)new Vec3d(vx, vy, vz).length());
		// this.move(100.0F);
		this.scale = this.scale_ = 15.0F*this.random.nextFloat()+20.0F; //* (1.0F - (float)d * 0.5F);	//Change maximum scale factor from 2.0F to 10.0F
		this.collidesWithWorld = false;
		this.spriteProvider = spriteProvider;
		this.setSpriteForAge(spriteProvider);
	}

	public int getColorMultiplier(float tint) {
		return 15728880;
	}

	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.age++ >= this.maxAge) {
			this.markDead();
		} else {
			float factor = (float)age/(float)maxAge;
			// this.scale = scale_*(float)(1.0F-factor);
			// this.setColorAlpha(1.0F-factor*factor);
			this.move(this.velocityX, this.velocityY, this.velocityZ);
			// this.setColor(
			// 	// this.random.nextFloat(), this.random.nextFloat(), this.random.nextFloat()
			// 	(float)(1.0F-factor),
			// 	(float)(1.0F-factor),
			// 	(float)(1.0F-factor)
			// );
			this.setSpriteForAge(this.spriteProvider);
		}
	}

	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_LIT;
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			return new BigFookingExplosionParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
		}
	}
}
