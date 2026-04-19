package com.immersivetanks.client.model;
// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.immersivetanks.entity.custom.BT5;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import static com.immersivetanks.entity.custom.TankBase.DATA_PITCH;
import static com.immersivetanks.entity.custom.TankBase.DATA_YAW;

// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class bt_5<T extends BT5> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("immersivetanks", "bt_5"), "main");
	private final ModelPart body;
	private final ModelPart Tracks;
	private final ModelPart wheels;
	private final ModelPart bone2;
	private final ModelPart bone3;
	private final ModelPart bone4;
	private final ModelPart bone6;
	private final ModelPart bone5;
	private final ModelPart bone7;
	private final ModelPart bone8;
	private final ModelPart turret;
	private final ModelPart cannon;
	private final ModelPart bone;

	public bt_5(ModelPart root) {
		this.body = root.getChild("body");
		this.Tracks = this.body.getChild("Tracks");
		this.wheels = this.body.getChild("wheels");
		this.bone2 = this.wheels.getChild("bone2");
		this.bone3 = this.wheels.getChild("bone3");
		this.bone4 = this.wheels.getChild("bone4");
		this.bone6 = this.wheels.getChild("bone6");
		this.bone5 = this.wheels.getChild("bone5");
		this.bone7 = this.wheels.getChild("bone7");
		this.bone8 = this.wheels.getChild("bone8");
		this.turret = root.getChild("turret");
		this.cannon = this.turret.getChild("cannon");
		this.bone = this.cannon.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-17.0F, -8.0F, -34.0F, 35.0F, 18.0F, 82.0F, new CubeDeformation(0.0F))
				.texOffs(0, 101).addBox(18.0F, -5.0F, -51.0F, 1.0F, 14.0F, 99.0F, new CubeDeformation(0.0F))
				.texOffs(201, 101).addBox(-18.0F, -5.0F, -51.0F, 1.0F, 14.0F, 99.0F, new CubeDeformation(0.0F))
				.texOffs(373, 399).addBox(-17.0F, -4.0F, -53.0F, 4.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(402, 99).addBox(14.0F, -4.0F, -53.0F, 4.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(353, 408).addBox(10.0F, -10.0F, 35.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(207, 412).addBox(-11.0F, -10.0F, 35.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(373, 384).addBox(-15.0F, -3.0F, 10.0F, 31.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -61.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(314, 369).addBox(-17.0F, -3.0F, 10.0F, 35.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -53.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(207, 380).addBox(-13.0F, -2.0F, -15.0F, 28.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -37.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition Tracks = body.addOrReplaceChild("Tracks", CubeListBuilder.create().texOffs(0, 215).addBox(-2.0F, -2.0F, -47.0F, 7.0F, 2.0F, 96.0F, new CubeDeformation(0.0F))
				.texOffs(0, 314).addBox(44.0F, 13.0F, -47.0F, 7.0F, 2.0F, 96.0F, new CubeDeformation(0.0F))
				.texOffs(235, 0).addBox(44.0F, -2.0F, -47.0F, 7.0F, 2.0F, 96.0F, new CubeDeformation(0.0F))
				.texOffs(207, 215).addBox(-2.0F, 13.0F, -47.0F, 7.0F, 2.0F, 96.0F, new CubeDeformation(0.0F)), PartPose.offset(-24.0F, -2.0F, 0.0F));

		PartDefinition cube_r4 = Tracks.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(402, 197).addBox(-3.0F, -1.0F, -4.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(324, 408).addBox(43.0F, -1.0F, -4.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, 50.0F, -1.1345F, 0.0F, 0.0F));

		PartDefinition cube_r5 = Tracks.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(402, 177).addBox(-3.0F, -1.0F, -4.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(295, 408).addBox(43.0F, -1.0F, -4.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -49.0F, -2.7489F, 0.0F, 0.0F));

		PartDefinition cube_r6 = Tracks.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(402, 187).addBox(-3.0F, -1.0F, -4.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(407, 369).addBox(43.0F, -1.0F, -4.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 4.0F, -51.0F, -1.4835F, 0.0F, 0.0F));

		PartDefinition cube_r7 = Tracks.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(402, 133).addBox(-7.0F, -1.0F, -4.0F, 11.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 11.0F, 49.0F, -1.6493F, 0.3054F, 1.5708F));

		PartDefinition cube_r8 = Tracks.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(262, 399).addBox(-5.0F, -1.0F, -4.0F, 9.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 11.0F, -49.0F, -1.6534F, -0.4341F, 1.6293F));

		PartDefinition cube_r9 = Tracks.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(402, 153).addBox(-5.0F, -1.0F, -4.0F, 9.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(47.0F, 11.0F, -49.0F, -1.6534F, -0.4341F, 1.6293F));

		PartDefinition cube_r10 = Tracks.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(402, 143).addBox(-7.0F, -1.0F, -4.0F, 11.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(47.0F, 11.0F, 49.0F, -1.6493F, 0.3054F, 1.5708F));

		PartDefinition wheels = body.addOrReplaceChild("wheels", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition bone2 = wheels.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(306, 314).addBox(-24.0F, -10.0F, 43.0F, 48.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone3 = wheels.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(306, 325).addBox(-24.0F, -18.0F, 42.0F, 48.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone4 = wheels.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(306, 336).addBox(-24.0F, -10.0F, 22.0F, 48.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone6 = wheels.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(207, 358).addBox(-24.0F, -10.0F, -23.0F, 48.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone5 = wheels.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(314, 358).addBox(-24.0F, -10.0F, -43.0F, 48.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone7 = wheels.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(207, 369).addBox(-24.0F, -4.0F, 6.0F, 48.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, -51.0F));

		PartDefinition bone8 = wheels.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(306, 347).addBox(-24.0F, -10.0F, -1.0F, 48.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition turret = partdefinition.addOrReplaceChild("turret", CubeListBuilder.create().texOffs(207, 314).addBox(-12.0F, -13.0F, -11.0F, 24.0F, 13.0F, 25.0F, new CubeDeformation(0.0F))
				.texOffs(299, 384).addBox(-9.0F, -13.0F, 12.0F, 18.0F, 9.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(207, 399).addBox(-12.0F, -12.0F, -12.0F, 21.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(405, 124).addBox(-9.0F, -10.0F, -13.0F, 18.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -20.0F));

		PartDefinition cannon = turret.addOrReplaceChild("cannon", CubeListBuilder.create().texOffs(257, 404).addBox(-2.0F, -2.0F, -8.0F, 3.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -12.0F));

		PartDefinition bone = cannon.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(402, 163).addBox(-1.0F, -32.0F, -50.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 31.0F, 32.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(BT5 entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float degToRad = (float)Math.PI / 180.0f;


		this.turret.yRot = (entity.current_yaw) * degToRad;

		this.cannon.xRot = entity.current_pitch * degToRad;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		turret.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}