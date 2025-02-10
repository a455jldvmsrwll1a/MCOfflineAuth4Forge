package balls.jl.mcofflineauth4forge;

import balls.jl.mcofflineauth4forge.net.LoginChallengePayload;
import balls.jl.mcofflineauth4forge.net.LoginResponsePayload;
import balls.jl.mcofflineauth4forge.net.PubkeyBindPayload;
import balls.jl.mcofflineauth4forge.net.PubkeyQueryPayload;
import com.mojang.logging.LogUtils;
import lol.bai.badpackets.api.config.ConfigPackets;
import lol.bai.badpackets.api.play.PlayPackets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        LOGGER.info("Initialising MCOfflineAuth::Client. (on Forge)");

        registerPacketPayloads();
        ClientKeyPair.loadOrCreate();
    }

    private static void registerPacketPayloads() {
        ConfigPackets.registerClientChannel(new LoginChallengePayload().type(), LoginChallengePayload.STREAM_CODEC);
        ConfigPackets.registerServerChannel(new LoginResponsePayload().type(), LoginResponsePayload.STREAM_CODEC);

        PlayPackets.registerClientChannel(new PubkeyQueryPayload().type(), PubkeyQueryPayload.STREAM_CODEC);
        PlayPackets.registerServerChannel(new PubkeyBindPayload().type(), PubkeyBindPayload.STREAM_CODEC);
    }
}
