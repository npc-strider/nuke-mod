package io.github.npc_strider.NukeMod.render.entity;

import io.github.npc_strider.NukeMod.NukeMod;
import io.github.npc_strider.NukeMod.fission.FissionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.TntMinecartEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class FissionEntityRenderer extends EntityRenderer<FissionEntity> {

	public FissionEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
		this.shadowRadius = 0.5F;
	}

	public void render(FissionEntity tntEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		matrixStack.translate(0.0D, 0.5D, 0.0D);
		if ((float)tntEntity.getFuseTimer() - g + 1.0F < 10.0F) {
			float h = 1.0F - ((float)tntEntity.getFuseTimer() - g + 1.0F) / 10.0F *  2.0F;
			h = MathHelper.clamp(h, 0.0F, 1.0F);
			h *= h;
			h *= h;
			float j = 1.0F + h * 0.3F;
			matrixStack.scale(j, j, j);
		}

		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
		matrixStack.translate(-0.5D, -0.5D, 0.5D);
		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
		// FissionEntityRenderer.renderFlashingBlock(Blocks.WHITE_WOOL.getDefaultState(), matrixStack, vertexConsumerProvider, i, tntEntity.getFuseTimer() / 5 % 2 == 0);
		TntMinecartEntityRenderer.renderFlashingBlock(NukeMod.FISSION_BLOCK.getDefaultState(), matrixStack, vertexConsumerProvider, i, tntEntity.getFuseTimer() / 5 % 2 == 0);
		matrixStack.pop();
		super.render(tntEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	public Identifier getTexture(FissionEntity tntEntity) {
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
	}
}


		// MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(Blocks.TNT.getDefaultState(), matrixStack, vertexConsumerProvider, i, (int)Math.floor(50*Math.sin(tntEntity.getFuseTimer())));
		// System.out.println((int)Math.floor(50*Math.sin(tntEntity.getFuseTimer())));
		