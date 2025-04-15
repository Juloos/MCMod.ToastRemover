package juloos.toastremover.mixin;

import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ToastComponent.class})
public abstract class ToastManagerMixin {
    public ToastManagerMixin() {}

    @Inject(
            method = "addToast",
            at = @At("HEAD"),
            cancellable = true
    )
    private void hideToastInstantly(Toast toast, CallbackInfo ci) {
        if (!(toast instanceof SystemToast) && !(toast instanceof AdvancementToast)) {
            ci.cancel();
        }
    }
}