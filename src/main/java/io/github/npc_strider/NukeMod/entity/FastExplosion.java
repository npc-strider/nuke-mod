package io.github.npc_strider.NukeMod.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.npc_strider.NukeMod.NukeMod;
import io.github.npc_strider.NukeMod.NukeModClient;
import io.github.npc_strider.NukeMod.constant.TransformConstants;
import io.github.npc_strider.NukeMod.constant.TransformConstants.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FastExplosion {
    private final Vec3d radius;
    private final Vec3d effectRadius;
    private final World world;
    private final Vec3d origin;
    private final Entity entity;

    public FastExplosion(Entity entity, Vec3d radius, Vec3d effectRadius, Vec3d origin, World world) {
        this.radius = radius;
        this.effectRadius = effectRadius;
        this.origin = origin;
        this.world = world;
        this.entity = entity;
    }

    private void removeTiles() {
        double radiusX = this.radius.getX();
        double radiusY = this.radius.getY();
        double radiusZ = this.radius.getZ();
        World wld = this.world;
        for (double z = -radiusZ; z <= radiusZ; ++z) { //Note to self - don't try and parallize things...
            for (double y = -radiusY; y <= radiusY; ++y) {
                for (double x = -radiusX; x <= radiusX; ++x) {
                    Vec3d d = new Vec3d(x, y, z);
                    Vec3d ellipse_ =  d.multiply(1/radiusZ, 1/radiusY, 1/radiusX);
                    double ellipseFactor = ellipse_.dotProduct(ellipse_);
                    BlockPos pos = new BlockPos(d.add(this.origin));
                    BlockState blockState = wld.getBlockState(pos);
                    Block block = blockState.getBlock();
                    // System.out.println("Position "+x+","+y+","+z+"  "+ellipseFactor );
                    if (blockState.getMaterial() != Material.AIR && ellipseFactor < 1 && block.getBlastResistance() < 1e6) {   //Barrier and Adminium has a blast resistance of 3.6e+6 -- So this is OK!
                        // System.out.println("Removed "+block.getName().asString() );
                        if (!wld.removeBlock(pos, false)) {
                            // System.out.println("Fluid");
                            wld.setBlockState(pos, Blocks.AIR.getDefaultState(), 3); //This handles fluid.
                        }
                    }
                }
            }
        }
    }

    private static Map<Material,Block> transformMaterial = TransformConstants.transformMaterial;
    private static Map<Material,List<Block>> transformToFallingTile = TransformConstants.transformToFallingTile;

    private void effectTiles() {
        double radiusX = this.effectRadius.getX();
        double radiusY = this.effectRadius.getY();
        double radiusZ = this.effectRadius.getZ();
        World wld = this.world;
        for (double z = -radiusZ; z <= radiusZ; ++z) { //Note to self - don't try and parallize things...
            for (double y = -radiusY; y <= radiusY; ++y) {
                for (double x = -radiusX; x <= radiusX; ++x) {
                    Vec3d d = new Vec3d(x, y, z);
                    Vec3d ellipse_ =  d.multiply(1/radiusZ, 1/radiusY, 1/radiusX);
                    double ellipseFactor = ellipse_.dotProduct(ellipse_);
                    BlockPos pos = new BlockPos(d.add(this.origin));
                    BlockState blockState = wld.getBlockState(pos);
                    Block block = blockState.getBlock();
                    Material mat = blockState.getMaterial();
                    if (mat != Material.AIR && block.getBlastResistance() < 1e6 && ellipseFactor < wld.random.nextDouble() && ellipseFactor < 1) {
                        if (transformMaterial.containsKey(mat)) {
                            wld.setBlockState(pos, transformMaterial.get(mat).getDefaultState());
                        } else if (transformToFallingTile.containsKey(mat)) {           //Prevent massive lag from creating falling entities - ensures gravity affected tiles have a solid tile beneath.
                            Material matBelow = wld.getBlockState(pos.down()).getMaterial();
                            if (matBelow != Material.AIR && matBelow != Material.AGGREGATE) {
                                wld.setBlockState(pos, transformToFallingTile.get(mat).get(0).getDefaultState());
                            } else {
                                wld.setBlockState(pos, transformToFallingTile.get(mat).get(1).getDefaultState());
                            }
                        } else if (mat.isBurnable() && wld.getBlockState(pos.up()).getMaterial() == Material.AIR) {
                            wld.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
                        }
                        
                        //else if (transform.containsKey(block)) {
                        //     wld.setBlockState(pos, transform.get(block).getDefaultState());
                        // }
                    }
                }
            }
        }
    }

    private void damageEntities() {
        List<Entity> list = this.world.getOtherEntities(this.entity, new Box(this.origin.subtract(effectRadius), this.origin.add(effectRadius)));
        for (int x = 0; x < list.size(); ++x) {
            Entity entity = (Entity)list.get(x);
            Double distance = entity.getPos().distanceTo(this.origin);
            Double effectFactor = Math.exp(-(0.01*distance)*(0.01*distance));   //IMPORTANT NOTE: We're treating all distances with the same effect falloff, but we need to use the ellipse formulae to calculate different falloffs that follow the ellipse shape.
            entity.setVelocity(entity.getVelocity().add(entity.getPos().subtract(this.origin).add(0,0.2,0)).normalize().multiply(20*effectFactor)); //Knockback
            if (!entity.isImmuneToExplosion() && entity.isLiving()) {
                LivingEntity liveEntity = (LivingEntity)entity;
                StatusEffects.POISON.applyUpdateEffect((LivingEntity)entity, 5);
                // System.out.println(effectFactor);
                liveEntity.damage(DamageSource.GENERIC, (float)(liveEntity.getMaxHealth()*effectFactor+2));   //Yeah, probably should use explosion damage source but whatever.
                if (liveEntity.getHealth() <= 0) {
                    liveEntity.kill();
                } else {
                    liveEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, (int)Math.floor(120*20*effectFactor), (int)Math.ceil(5*effectFactor)));
                    if (!liveEntity.isFireImmune()) {
                        liveEntity.setFireTicks(20);
                    }
                }
            } else if (entity.getType() == EntityType.ITEM) {
                int rand = this.world.random.nextInt(11);
                if (rand < 8) { //20% chance of item surviving
                    entity.remove();
                }
            }
        }
        // List<? extends PlayerEntity> players = this.world.getPlayers();  //Was going to try some stuff with rendering a bright flash, but that seems way too hard...
        // for (int x = 0; x < list.size(); ++x) {
        //     PlayerEntity player = players.get(x);
        // }
    }

    public void damageBlocksAndEntities() {
        if (this.world.isClient) {
            //this.world.playSound(this.origin.getX(), this.origin.getY(), this.origin.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 25.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F, false);
            this.world.playSound(this.origin.getX(), this.origin.getY(), this.origin.getZ(), NukeMod.FISSION_EXPLOSION, SoundCategory.BLOCKS, 25.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F, false);
            this.world.addParticle(NukeModClient.BIG_FOOKING_EXPLOSION_EMITTER, true, this.origin.getX(), this.origin.getY(), this.origin.getZ(), 1.0D, 0.0D, 0.0D);
            this.world.addParticle(NukeModClient.BIG_FOOKING_SMOKE_EMITTER, true, this.origin.getX(), this.origin.getY(), this.origin.getZ(), 1.0D, 0.0D, 0.0D);
        } else {
            removeTiles();
            effectTiles();
            damageEntities();   //Apply damage to items created to reduce lag.
        }  
    }
}
