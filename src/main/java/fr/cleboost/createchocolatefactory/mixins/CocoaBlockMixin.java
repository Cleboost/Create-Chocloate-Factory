package fr.cleboost.createchocolatefactory.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CocoaBlock.class)
public class CocoaBlockMixin {
    private static final VoxelShape[][][] SHAPES = new VoxelShape[3][4][];

    static {
        // Stage 0
        SHAPES[0][0] = new VoxelShape[] { Block.box(6.0, 5.0, -2.05, 10.0, 15.8, 4.21) }; // NORTH
        SHAPES[0][1] = new VoxelShape[] { Block.box(6.0, 5.0, 11.79, 10.0, 15.8, 18.05) }; // SOUTH
        SHAPES[0][2] = new VoxelShape[] { Block.box(0.0, 5.0, 6.0, 4.0, 15.8, 10.0) }; // WEST
        SHAPES[0][3] = new VoxelShape[] { Block.box(12.0, 5.0, 6.0, 16.0, 15.8, 10.0) }; // EAST

        // Stage 1
        SHAPES[1][0] = new VoxelShape[] { Block.box(6.0, 3.0, -2.05, 10.0, 15.8, 4.21) }; // NORTH
        SHAPES[1][1] = new VoxelShape[] { Block.box(6.0, 3.0, 11.79, 10.0, 15.8, 18.05) }; // SOUTH
        SHAPES[1][2] = new VoxelShape[] { Block.box(0.0, 3.0, 6.0, 4.0, 15.8, 10.0) }; // WEST
        SHAPES[1][3] = new VoxelShape[] { Block.box(12.0, 3.0, 6.0, 16.0, 15.8, 10.0) }; // EAST

        // Stage 2
        SHAPES[2][0] = new VoxelShape[] { Block.box(6.0, 1.0, -2.05, 10.0, 15.8, 4.21) }; // NORTH
        SHAPES[2][1] = new VoxelShape[] { Block.box(6.0, 1.0, 11.79, 10.0, 15.8, 18.05) }; // SOUTH
        SHAPES[2][2] = new VoxelShape[] { Block.box(0.0, 1.0, 6.0, 4.0, 15.8, 10.0) }; // WEST
        SHAPES[2][3] = new VoxelShape[] { Block.box(12.0, 1.0, 6.0, 16.0, 15.8, 10.0) }; // EAST
    }

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void createchocolatefactory$getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        int age = state.getValue(CocoaBlock.AGE);
        Direction facing = state.getValue(CocoaBlock.FACING);

        int facingIndex = switch (facing) {
            case NORTH -> 0;
            case SOUTH -> 1;
            case WEST -> 2;
            case EAST -> 3;
            default -> 0;
        };

        cir.setReturnValue(SHAPES[age][facingIndex][0]);
    }
}