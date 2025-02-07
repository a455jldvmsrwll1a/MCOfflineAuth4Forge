package balls.jl.mcofflineauth4forge;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Constants.MOD_ID)
public class MCOfflineAuth {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static boolean SHOW_HELP_TOAST = false;

    public MCOfflineAuth(FMLJavaModLoadingContext context) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("Initialising MCOfflineAuth::Client. (on Forge)");

            ClientKeyPair.loadOrCreate();
        }
    }
}
