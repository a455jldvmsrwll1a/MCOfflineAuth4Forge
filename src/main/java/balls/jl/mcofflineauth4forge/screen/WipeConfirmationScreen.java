package balls.jl.mcofflineauth4forge.screen;

import balls.jl.mcofflineauth4forge.ClientKeyPair;
import balls.jl.mcofflineauth4forge.MCOfflineAuth;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class WipeConfirmationScreen extends Screen {
    public final Screen parent;

    public WipeConfirmationScreen(Screen parent) {
        super(Component.empty());
        this.parent = parent;
    }

    @Override
    protected void init() {
        assert this.minecraft != null;
        int width = this.minecraft.getWindow().getGuiScaledWidth();
        int height = this.minecraft.getWindow().getGuiScaledHeight();
        Button cancelBtnWidget = Button.builder(Component.literal("Cancel"), (btn) -> close()).bounds(20, height - this.font.lineHeight - 20, 80, 20).build();

        Button confirmBtnWidget = Button.builder(Component.literal("Confirm").withStyle(ChatFormatting.RED, ChatFormatting.BOLD), (btn) -> {
            ClientKeyPair.generate();
            MCOfflineAuth.SHOW_HELP_TOAST = false;

            SystemToast.add(this.minecraft.getToastManager(), new SystemToast.SystemToastId(), Component.literal("New key-pair created."), Component.literal("The old key-pair will no longer work!"));
            close();
        }).tooltip(Tooltip.create(Component.literal("OLD KEY-PAIR WILL NO LONGER WORK!").withStyle(ChatFormatting.RED, ChatFormatting.BOLD))).bounds(width - 80 - 20, height - this.font.lineHeight - 20, 80, 20).build();

        addRenderableWidget(cancelBtnWidget);
        addRenderableWidget(confirmBtnWidget);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        super.render(gfx, mouseX, mouseY, delta);

        gfx.drawString(this.font, "MC Offline Auth 4 Forge Configuration", 15, 40 - this.font.lineHeight - 10, 0xFFA719FF, true);
        gfx.drawString(this.font, "§lConfirm deletion of the key-pair?§r", 15, 60 - this.font.lineHeight - 10, 0xFF0000, true);
        gfx.drawString(this.font, "After deleting, the key-pair will no longer be usable.", 15, 80 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
        gfx.drawString(this.font, "Servers with your old key §nwill reject you until you bind the new public key§r.", 15, 90 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
        gfx.drawString(this.font, "You can achieve this it two ways:", 15, 100 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
        gfx.drawString(this.font, "§oBefore deleting§r, unbind your user in the server(s): [recommended]", 15, 120 - this.font.lineHeight - 10, 0xFF38FF74, true);
        gfx.drawString(this.font, "Before you create a new key-pair, do \"§i/offauth unbind§r\" then leave,", 15, 130 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
        gfx.drawString(this.font, "then come back to this screen and §oconfirm§r delete the old key-pair,", 15, 140 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
        gfx.drawString(this.font, "then rejoin and bind your new public key using \"§i/offauth bind§r\".", 15, 150 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
        gfx.drawString(this.font, "Otherwise, tell an admin to do it for you:", 15, 170 - this.font.lineHeight - 10, 0xFF9BC2A7, true);
        gfx.drawString(this.font, "An §nadmin§r can run \"§i/offauth bind <user> <pubkey>§r\" to rebind your new key.", 15, 180 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
        gfx.drawString(this.font, "You should provide them with your §onew§r public key.", 15, 190 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
    }

    public void close() {
        assert this.minecraft != null;
        this.minecraft.setScreen(parent);
    }
}