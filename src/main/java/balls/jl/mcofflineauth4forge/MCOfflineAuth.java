package balls.jl.mcofflineauth4forge;

import balls.jl.mcofflineauth4forge.screen.ModScreen;
import com.mojang.logging.LogUtils;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Constants.MOD_ID)
public class MCOfflineAuth {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static boolean SHOW_HELP_TOAST = false;

    public MCOfflineAuth(FMLJavaModLoadingContext context) {
        ServerStartingEvent.BUS.addListener(MCOfflineAuth::onServerStarting);

        context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(((minecraft, screen) -> new ModScreen(screen)))
        );
    }

    private static void onServerStarting(ServerStartingEvent event) {
        LOGGER.warn("MC Offline Auth 4 Forge has no server support; only client-side functionality is currently implemented.");
    }
}
