package destiny.penumbra_phantasm.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import destiny.penumbra_phantasm.server.block.entity.DarkMarbleDiceBlockEntity;
import destiny.penumbra_phantasm.server.block.entity.ScarletMarbleDiceBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;

public class ScarletMarbleDiceBlockEntityRenderer implements BlockEntityRenderer<ScarletMarbleDiceBlockEntity> {
    public ScarletMarbleDiceBlockEntityRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
    }

    @Override
    public void render(ScarletMarbleDiceBlockEntity scarletMarbleDiceBlock, float v, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState state = scarletMarbleDiceBlock.getBlockState();
        BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);

        RenderType renderType = RenderType.solid();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);

        poseStack.pushPose();

        poseStack.translate(0.5, 0.5, 0.5);

        poseStack.mulPose(Axis.XP.rotationDegrees(scarletMarbleDiceBlock.rotationX));
        poseStack.mulPose(Axis.YP.rotationDegrees(scarletMarbleDiceBlock.rotationY));
        poseStack.mulPose(Axis.ZP.rotationDegrees(scarletMarbleDiceBlock.rotationZ));

        poseStack.translate(-0.5, -0.5, -0.5);

        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), vertexConsumer, state, model,
                1.0f, 1.0f, 1.0f, packedLight, packedOverlay);

        poseStack.popPose();
    }
}
