package balls.jl.mcofflineauth4forge.mixin;

import balls.jl.mcofflineauth4forge.MCOfflineAuth;
import balls.jl.mcofflineauth4forge.screen.ModScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Component component) {
        super(component);
    }

    @Inject(at = @At("RETURN"), method = "init")
    protected void init(CallbackInfo ci) {
        if (MCOfflineAuth.SHOW_HELP_TOAST) {
            MCOfflineAuth.SHOW_HELP_TOAST = false;
            assert this.minecraft != null;
            SystemToast.add(this.minecraft.getToastManager(), new SystemToast.SystemToastId(), Component.literal("Created a new key-pair!"), Component.literal("Click the OA button for more info."));
        }
    }

    @Inject(at = @At("RETURN"), method = "createNormalMenuOptions")
    private void createNormalMenuOptions(int y, int spacingY, CallbackInfoReturnable<Integer> ci) {
        this.addRenderableWidget(Button.builder(Component.literal("OA"), button -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(new ModScreen(null));
        }).bounds(this.width / 2 - 100 - spacingY, y, 20, 20).build());
    }
}
