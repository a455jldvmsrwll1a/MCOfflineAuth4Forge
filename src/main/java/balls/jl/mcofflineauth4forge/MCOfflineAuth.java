package balls.jl.mcofflineauth4forge;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Constants.MOD_ID)
public class MCOfflineAuth {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static boolean SHOW_HELP_TOAST = false;

    public MCOfflineAuth(FMLJavaModLoadingContext context) {
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.warn("MC Offline Auth 4 Forge has no server support; only client-side functionality is currently implemented.");
    }
}
