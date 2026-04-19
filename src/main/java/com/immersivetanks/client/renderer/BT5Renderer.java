package com.immersivetanks.client.renderer;

import com.immersivetanks.client.model.bt_5;
import com.immersivetanks.entity.custom.BT5;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class BT5Renderer extends EntityRenderer<BT5> {
    private final ResourceLocation TEXTURE = new ResourceLocation("immersivetanks", "textures/entity/bt5.png");
    private final bt_5<BT5> model;

    public BT5Renderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new bt_5<>((context.bakeLayer(bt_5.LAYER_LOCATION)));

    }

    @Override
    public void render(BT5 entity, float entity_yaw, float partial_trick, PoseStack poses_stack, MultiBufferSource buffer, int packed_light) {
        poses_stack.pushPose();
        poses_stack.mulPose(Axis.YP.rotationDegrees(entity_yaw));
        poses_stack.mulPose(Axis.ZP.rotationDegrees(180.0f));
        poses_stack.translate(0.0D, -1.5D, 0.0D);
        VertexConsumer consumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
        this.model.renderToBuffer(poses_stack, consumer, packed_light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        poses_stack.popPose();
        this.model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partial_trick, entity.getViewYRot(partial_trick), entity.getViewXRot(partial_trick));

        super.render(entity, entity_yaw, partial_trick, poses_stack, buffer, packed_light);
    }

    @Override
    public ResourceLocation getTextureLocation(BT5 p_114482_) {
        return TEXTURE;
    }
}

