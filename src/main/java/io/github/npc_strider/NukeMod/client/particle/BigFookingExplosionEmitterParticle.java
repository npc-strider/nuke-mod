package io.github.npc_strider.NukeMod.client.particle;

import io.github.npc_strider.NukeMod.NukeModClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.block.Material;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class BigFookingExplosionEmitterParticle extends NoRenderParticle {
	private int age_;
	private final int maxAge_;

	private BigFookingExplosionEmitterParticle(ClientWorld world, double x, double y, double z) {
		super(world, x, y, z, 0.0D, 0.0D, 0.0D);
		this.maxAge_ = 10;//80;
	}

	public void tick() {
		double r = 40.0D;
		// for (int i = 0; i < (int)Math.floor(32*(float)this.age_/(float)this.maxAge_); ++i) {
		for (int i = 0; i < (int)Math.floor(64/(age_+1)); ++i) {
			// double d = this.x + (this.random.nextDouble() - this.random.nextDouble()) * r;
			// double e = this.y + (this.random.nextDouble() - this.random.nextDouble()) * r;
			// double f = this.z + (this.random.nextDouble() - this.random.nextDouble()) * r;
			float alpha = this.random.nextFloat() * 360;
			float beta = this.random.nextFloat() * 360;
			// Vec3d position = Vec3d.fromPolar(alpha, beta).multiply((this.random.nextDouble() - this.random.nextDouble()) * r).add(this.x, this.y, this.z);	//Keeps our explosion spherical, rather than in a 3d box as with the vanilla method.
			double r_ = Math.sqrt(this.random.nextDouble()) * r;
			Vec3d position = Vec3d.fromPolar(alpha, beta).multiply(r_).add(this.x, this.y, this.z);	//Keeps our explosion spherical, rather than in a 3d box as with the vanilla method.
			if (world.getBlockState(new BlockPos(position)).getMaterial() == Material.AIR) {
				Vec3d v = Vec3d.fromPolar(alpha, beta).normalize().multiply(r_*0.05F+0.05F*this.random.nextFloat());
				this.world.addParticle(NukeModClient.BIG_FOOKING_EXPLOSION, true, position.getX(), position.getY(), position.getZ(), v.getX(), v.getY(), v.getZ());
			}
		}

		++this.age_;
		if (this.age_ == this.maxAge_) {
			this.markDead();
		}

	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final FabricSpriteProvider sprites;

		public Factory(FabricSpriteProvider sprites) {
			this.sprites = sprites;
		}

		@Override
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			return new BigFookingExplosionEmitterParticle(clientWorld, d, e, f);
		}
	}
}