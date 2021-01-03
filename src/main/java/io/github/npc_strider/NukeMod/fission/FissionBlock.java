package io.github.npc_strider.NukeMod.fission;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
// import net.minecraft.sound.SoundCategory;
// import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class FissionBlock extends Block {   //Copying code from TntBlock because I can't override some static stuff (private static void primeTnt method)
    public FissionBlock(Settings settings) {
        super(settings);
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            if (world.isReceivingRedstonePower(pos)) {
                primeNuke(world, pos);
                world.removeBlock(pos, false);
            }

        }
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            primeNuke(world, pos);
            world.removeBlock(pos, false);
        }

    }

    public static void primeNuke(World world, BlockPos pos) {
        primeNuke(world, pos, (LivingEntity)null);
    }

    private static void primeNuke(World world, BlockPos pos, @Nullable LivingEntity igniter) {
        if (!world.isClient) {
            FissionEntity fissionEntity = new FissionEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, igniter);
            // FissionEntity fissionEntity = NukeMod.FISSION_ENTITY.create(world).updateFissionEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, igniter);
            
            world.spawnEntity(fissionEntity);
            // world.playSound((PlayerEntity)null, fissionEntity.getX(), fissionEntity.getY(), fissionEntity.getZ(), SoundEvents.BLOCK_CONDUIT_ACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.0F); //test audio
        }
    }



    // Only explosive lenses
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        if (!world.isClient) {
            spawnFizzle(
                world, 
                (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D,
                explosion.getCausingEntity()
            );
        }
    }

    // Only explosive lenses
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient && projectile.isOnFire()) {
            Entity entity = projectile.getOwner();
            BlockPos blockPos = hit.getBlockPos();
            spawnFizzle(
                world,
                (double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D,
                entity instanceof LivingEntity ? (LivingEntity)entity : null
            );
            world.removeBlock(blockPos, false);
        }
    }

    //Incomplete explosion - only explosive lenses detonate in a way that does not intiate fission
    private static void spawnFizzle(World world, Double x, Double y, Double z, LivingEntity igniter) {
        TntEntity tntEntity = new TntEntity(world, x, y, z, igniter);
        // tntEntity.setFuse((short)(world.random.nextInt(tntEntity.getFuseTimer() / 4) + tntEntity.getFuseTimer() / 8));
        tntEntity.setFuse((short)(world.random.nextInt(tntEntity.getFuseTimer() / 24) + tntEntity.getFuseTimer() / 20));
        world.spawnEntity(tntEntity);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            player.sendMessage(new LiteralText("Device ready: awaiting redstone signal . . ."), true);
        }
        return ActionResult.SUCCESS;
    }
    
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }
}
