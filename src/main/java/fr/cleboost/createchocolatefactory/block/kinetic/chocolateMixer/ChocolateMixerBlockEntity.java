package fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ChocolateMixerBlockEntity extends KineticBlockEntity {
    public int runningTicks;
	public int processingTicks;
	public boolean running;

    public ChocolateMixerBlockEntity(BlockEntityType<? extends ChocolateMixerBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		// inputInv = new ItemStackHandler(1);
		// outputInv = new ItemStackHandler(9);
		// capability = LazyOptional.of(ChocolateMixerInventoryHandler::new);
	}

    @Override
    public void tick() {
        super.tick();

        // Vérifie si le bloc reçoit de l'énergie kinétique
        boolean wasRunning = running;
        running = Math.abs(getSpeed()) > 0.5f; // Le mixer tourne si la vitesse est supérieure à 0.5 RPM
        
        if (running) {
            runningTicks++;
            
            // Marque le bloc entity comme modifié pour sauvegarder l'état
            if (!wasRunning) {
                setChanged();
                sendData(); // Synchronise avec le client pour l'animation
            }
        } else {
            // Remet à zéro les ticks si le mixer s'arrête
            if (wasRunning) {
                runningTicks = 0;
                setChanged();
                sendData(); // Synchronise avec le client
            }
        }
    }
}
