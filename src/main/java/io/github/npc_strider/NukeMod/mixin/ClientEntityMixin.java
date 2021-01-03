// package io.github.npc_strider.NukeMod.mixin;
// import net.minecraft.client.network.ClientPlayNetworkHandler;
// import net.minecraft.entity.Entity;
// import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
// import org.spongepowered.asm.mixin.Mixin;
// import org.spongepowered.asm.mixin.injection.At;
// import org.spongepowered.asm.mixin.injection.Inject;
// import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// import io.github.npc_strider.NukeMod.NukeMod;
// import io.github.npc_strider.NukeMod.fission.FissionEntity;


// @Mixin(ClientPlayNetworkHandler.class)
// public class Client {

//     @Inject(at = @At("TAIL"), method="onEntitySpawn")
//     public void onEntitySpawn(EntitySpawnS2CPacket packet, CallbackInfo cbi) {
//         System.out.l
//         ClientPlayNetworkHandler cpnh = (ClientPlayNetworkHandler) (Object) this;

//         if (packet.getEntityTypeId() == NukeMod.FISSION_ENTITY_TYPE) {
//             Entity e = new FissionEntity(null, cpnh.getWorld());

//             int i = packet.getId();
//             e.updateTrackedPosition(packet.getX(), packet.getY(), packet.getZ());
//             e.pitch = (float)(packet.getPitch() * 360) / 256.0F;
//             e.yaw = (float)(packet.getYaw() * 360) / 256.0F;
//             e.setEntityId(i);
//             e.setUuid(packet.getUuid());
//             cpnh.getWorld().addEntity(i, e);
//         }
//     }

// }

package io.github.npc_strider.NukeMod.mixin;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import io.github.npc_strider.NukeMod.NukeMod;
import io.github.npc_strider.NukeMod.fission.FissionEntity;

/*  
    Absolute bruh moment, wasted hours searching for the solution (why my non-living entity wasn't rendering/appearing on the clientside, including smoke and other effects...)
    Found this solution here https://www.reddit.com/r/fabricmc/comments/g4xpm9/fabric_modding_custom_entity_rendering_on/.
    The solution here https://www.reddit.com/r/fabricmc/comments/ft7ds1/entity_not_rendering/ works but it has a small (noticeable) delay until the client renders the entity,
    so I'm using the first option.
*/
@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientEntityMixin {
	@Shadow
	private ClientWorld world;

	@Inject(
			method = "onEntitySpawn(Lnet/minecraft/network/packet/s2c/play/EntitySpawnS2CPacket;)V",
			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/network/packet/s2c/play/EntitySpawnS2CPacket;getEntityTypeId()Lnet/minecraft/entity/EntityType;"),
			cancellable = true,
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void onEntitySpawn(EntitySpawnS2CPacket packet, CallbackInfo ci, double x, double y, double z, EntityType<?> type) {
		Entity entity = null;
		if (packet.getEntityTypeId() == NukeMod.FISSION_ENTITY_TYPE) {
			entity = new FissionEntity(null, world);
		}
		if (entity != null) {
			int entityId = packet.getId();
			entity.setVelocity(Vec3d.ZERO);
			entity.updatePosition(x, y, z);
			entity.updateTrackedPosition(x, y, z);
			entity.pitch = (float) (packet.getPitch() * 360) / 256.0F;
			entity.yaw = (float) (packet.getYaw() * 360) / 256.0F;
			entity.setEntityId(entityId);
			entity.setUuid(packet.getUuid());
			this.world.addEntity(entityId, entity);
			ci.cancel();
		}
	}

}