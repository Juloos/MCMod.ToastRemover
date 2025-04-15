package juloos.toastremover.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementToast.class)
public abstract class AdvancementToastMixin {
    @Shadow private boolean playedSound;
    @Shadow @Final private Advancement advancement;

    @Inject(
            method = "render",
            at = @At("HEAD"),
            cancellable = true
    )
    private void hideToastButPlaySound(PoseStack poseStack, ToastComponent toastComponent, long l, CallbackInfoReturnable<Toast.Visibility> cir) {
        DisplayInfo displayInfo = this.advancement.getDisplay();
        if (displayInfo != null && !this.playedSound && l > 0L) {
            this.playedSound = true;
            if (displayInfo.getFrame() == FrameType.CHALLENGE) {
                toastComponent.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F));
            }
        }
        cir.setReturnValue(Toast.Visibility.HIDE);
    }
}
