package io.github.npc_strider.NukeMod.client.particle;

import io.github.npc_strider.NukeMod.NukeModClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;

public class BigFookingSmokeEmitterParticle extends NoRenderParticle {
	private int age_;
	private final int maxAge_;
	
	private double RADIUS_X = 60;
	private final float m = 1.0F;
	private final int[][] radiuses = {	//For the big fission cloud
		{(int)Math.floor(64*m),	(int)Math.floor(RADIUS_X/2)		},
		{(int)Math.floor(32*m), (int)Math.floor(RADIUS_X/3)		},
		{(int)Math.floor(16*m), (int)Math.floor(RADIUS_X/4)		},
		{(int)Math.floor(16*m), (int)Math.floor(RADIUS_X/5)		},
		{(int)Math.floor(16*m), (int)Math.floor(RADIUS_X/6)		},
		{(int)Math.floor(12*m), (int)Math.floor(RADIUS_X/7)		},
		{(int)Math.floor(8*m), 	(int)Math.floor(RADIUS_X/8)		},
		{(int)Math.floor(8*m), 	(int)Math.floor(RADIUS_X/9)		},
		{(int)Math.floor(8*m), 	(int)Math.floor(RADIUS_X/10)	},

		{(int)Math.floor(32*m), (int)Math.floor(RADIUS_X/2)		},
		{(int)Math.floor(32*m), (int)Math.floor(RADIUS_X/2)		},
		{(int)Math.floor(32*m), (int)Math.floor(RADIUS_X/2)		},
		{(int)Math.floor(32*m), (int)Math.floor(RADIUS_X/2.2)	},
		{(int)Math.floor(24*m), (int)Math.floor(RADIUS_X/2.5)	},
		{(int)Math.floor(16*m), (int)Math.floor(RADIUS_X/2.7)	},
		{(int)Math.floor(8*m), 	(int)Math.floor(RADIUS_X/3)		},
		{(int)Math.floor(8*m), 	(int)Math.floor(RADIUS_X/5.5)	},
		{(int)Math.floor(8*m), 	(int)Math.floor(RADIUS_X/8)		},
	};	// {(y+0n)=>r0,(y+1n)=>r1,(y+2n)=>r2, ... }

	private BigFookingSmokeEmitterParticle(ClientWorld world, double x, double y, double z) {
		super(world, x, y, z, 0.0D, 0.0D, 0.0D);
		this.maxAge_ = 1;//80;
	}

	public void tick() {
		double dy = 8.0D;
		for (int j = 0; j < radiuses.length; ++j) {
			for (int i = 0; i < radiuses[j][0]; ++i) {
				float theta = this.random.nextFloat() * 360;
				double r_ = Math.sqrt(this.random.nextDouble()) * radiuses[j][1];
				Vec3d position = Vec3d.fromPolar(0, theta).multiply(r_).add(this.x, this.y+(dy*(double)(j)), this.z);	//Keeps our explosion spherical, rather than in a 3d box as with the vanilla method.
				// if (world.getBlockState(new BlockPos(position)).getMaterial() == Material.AIR) {
				Vec3d v = Vec3d.fromPolar(0, theta).normalize().multiply(r_*0.05F+0.05F*this.random.nextFloat());
				this.world.addParticle(NukeModClient.BIG_FOOKING_SMOKE, true, position.getX(), position.getY(), position.getZ(), v.getX(), v.getY()-0.25F, v.getZ());
				// }
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
			return new BigFookingSmokeEmitterParticle(clientWorld, d, e, f);
		}
	}
}