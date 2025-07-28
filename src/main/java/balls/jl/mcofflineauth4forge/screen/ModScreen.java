package balls.jl.mcofflineauth4forge.screen;

import balls.jl.mcofflineauth4forge.ClientKeyPair;
import balls.jl.mcofflineauth4forge.Constants;
import balls.jl.mcofflineauth4forge.util.KeyEncode;
import com.mojang.blaze3d.platform.ClipboardManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModScreen extends Screen {
    private final Screen parent;

    public ModScreen(Screen parent) {
        super(Component.empty());
        this.parent = parent;
    }

    @Override
    protected void init() {
        assert this.minecraft != null;
        int width = this.minecraft.getWindow().getGuiScaledWidth();
        int height = this.minecraft.getWindow().getGuiScaledHeight();
        String keyString = KeyEncode.encodePublic(ClientKeyPair.KEY_PAIR.getPublic());
        Button pkBtnWidget = Button.builder(Component.literal(keyString), (btn) -> {
            ClipboardManager clipboard = new ClipboardManager();
            clipboard.setClipboard(this.minecraft.getWindow().getWindow(), keyString);
            SystemToast.add(this.minecraft.getToastManager(), new SystemToast.SystemToastId(), Component.literal("Public key copied to clipboard!"), Component.literal("You can share this key with others."));
        }).tooltip(Tooltip.create(Component.literal("Copy to clipboard."))).bounds(75, 150 - this.font.lineHeight - 15, width - 100 - 20, 20).build();

        Button resetBtnWidget = Button.builder(Component.literal("§cRegenerate§r"), (btn) -> this.minecraft.setScreen(new WipeConfirmationScreen(this))).tooltip(Tooltip.create(Component.literal("§lWipe§r the key-pair & make a new one."))).bounds(15, height - this.font.lineHeight - 20, 80, 20).build();

        Button reloadBtnWidget = Button.builder(Component.literal("Reload"), (btn) -> {
            try {
                ClientKeyPair.load();
            } catch (RuntimeException e) {
                SystemToast.add(this.minecraft.getToastManager(), new SystemToast.SystemToastId(), Component.literal("Failed to load key-pair!"), Component.literal(e.toString()));
            }
            this.minecraft.setScreen(new ModScreen(this.parent));
        }).tooltip(Tooltip.create(Component.literal("Load the key from disk again. (e.g. if you changed the files while Minecraft was open.)"))).bounds(15 + 80 + 10, height - this.font.lineHeight - 20, 80, 20).build();

        Button explorerBtnWidget = Button.builder(Component.literal("Open Folder"), (btn) -> net.minecraft.Util.getPlatform().openPath(Constants.MOD_DIR)).tooltip(Tooltip.create(Component.literal("Open the mod's directory with the native file manager."))).bounds(15 + 80 + 80 + 10 + 10, height - this.font.lineHeight - 20, 100, 20).build();
        Button cancelBtnWidget = Button.builder(Component.literal("Back"), (btn) -> close()).bounds(width - 80 - 15, height - this.font.lineHeight - 20, 80, 20).build();
        Button modPageWidget = Button.builder(Component.literal("Mod Page"), (btn) -> net.minecraft.Util.getPlatform().openUri("https://github.com/a455jldvmsrwll1a/MCOfflineAuth?tab=readme-ov-file#usage-players")).tooltip(Tooltip.create(Component.literal("Visit mod page on Github."))).bounds(15, 130 - this.font.lineHeight - 20, 80, 20).build();

        this.addRenderableWidget(pkBtnWidget);
        this.addRenderableWidget(resetBtnWidget);
        this.addRenderableWidget(reloadBtnWidget);
        this.addRenderableWidget(explorerBtnWidget);
        this.addRenderableWidget(cancelBtnWidget);
        this.addRenderableWidget(modPageWidget);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        super.render(gfx, mouseX, mouseY, delta);

        gfx.drawString(this.font, "MC Offline Auth 4 Forge Configuration", 15, 40 - this.font.lineHeight - 10, 0xFFA719FF, true);
        gfx.drawString(this.font, "By jldmw1a", 15, 60 - this.font.lineHeight - 10, 0xFF85F1FF, true);
        gfx.drawString(this.font, "Allows servers to have some form of authentication without using", 15, 80 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
        gfx.drawString(this.font, "external authentication systems, lending itself to offline servers.", 15, 90 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
        gfx.drawString(this.font, "THIS MOD IS EXPERIMENTAL SOFTWARE. See webpage below for usage guide.", 15, 105 - this.font.lineHeight - 10, 0xFFEB9B34, true);
        gfx.drawString(this.font, "Public key: ", 15, 150 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
    }

    public void close() {
        assert this.minecraft != null;
        this.minecraft.setScreen(parent);
    }
}
