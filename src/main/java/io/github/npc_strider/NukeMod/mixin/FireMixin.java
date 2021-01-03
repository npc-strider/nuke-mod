package io.github.npc_strider.NukeMod.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.npc_strider.NukeMod.fission.FissionBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.block.TntBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(FireBlock.class)
public class FireMixin {
	@Shadow
	private int getSpreadChance(BlockState state) {
		return 0;	//I think this works??
	};

	//private void trySpreadingFire(World world, BlockPos pos, int spreadFactor, Random rand, int currentAge) {

	@Inject(
		method = "trySpreadingFire(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;ILjava/util/Random;I)V",
		cancellable = true,
		// remap = false,
		at = @At("HEAD")
	)
	private void trySpreadingFire(World world, BlockPos pos, int spreadFactor, Random rand, int currentAge, CallbackInfo callbackInfo) {
		BlockState blockState = world.getBlockState(pos);
		Block block = blockState.getBlock();
		if (block instanceof FissionBlock) {
			int i = this.getSpreadChance(world.getBlockState(pos));
			if (rand.nextInt(spreadFactor) < i) {
				TntBlock.primeTnt(world, pos);	//Temporary
				world.removeBlock(pos, false);
			}
			callbackInfo.cancel();	//Cannot just return when using inject mixin. Have to cancel first!
			return;
		}
	};
}
// 
// 
// 
// @Mixin(ItemStack.class) //Why the fuck does this not compile??? It's copypasted from the fucking example documentation. https://fabricmc.net/wiki/tutorial:mixin_redirectors_methods
// 						//y'know what, IIIIIIIIIIII give up goodbye ! -Rick Astley
// abstract class ExampleMixin {
//     @Redirect(method = "readTags",
//               at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;fromTag(Lnet/minecraft/nbt/ListTag;)Lnet/minecraft/item/ItemStack;"))
//     private static ItemStack returnNull(ListTag tag) {
//         return null;
//     }
// }