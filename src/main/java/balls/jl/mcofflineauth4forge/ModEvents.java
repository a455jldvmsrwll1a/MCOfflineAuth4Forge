package balls.jl.mcofflineauth4forge;

import balls.jl.mcofflineauth4forge.net.LoginChallengePayload;
import balls.jl.mcofflineauth4forge.net.LoginResponsePayload;
import balls.jl.mcofflineauth4forge.net.PubkeyBindPayload;
import balls.jl.mcofflineauth4forge.net.PubkeyQueryPayload;
import com.mojang.logging.LogUtils;
import lol.bai.badpackets.api.PacketReceiver;
import lol.bai.badpackets.api.config.ClientConfigContext;
import lol.bai.badpackets.api.config.ConfigPackets;
import lol.bai.badpackets.api.play.ClientPlayContext;
import lol.bai.badpackets.api.play.PlayPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        LOGGER.info("Initialising MCOfflineAuth::Client. (on Forge)");

        registerPacketPayloads();
        registerEventCallbacks();
        ClientKeyPair.loadOrCreate();
    }

    private static void registerPacketPayloads() {
        ConfigPackets.registerClientChannel(new LoginChallengePayload().type(), LoginChallengePayload.STREAM_CODEC);
        ConfigPackets.registerServerChannel(new LoginResponsePayload().type(), LoginResponsePayload.STREAM_CODEC);

        PlayPackets.registerClientChannel(new PubkeyQueryPayload().type(), PubkeyQueryPayload.STREAM_CODEC);
        PlayPackets.registerServerChannel(new PubkeyBindPayload().type(), PubkeyBindPayload.STREAM_CODEC);
    }

    private static void registerEventCallbacks() {
         ConfigPackets.registerClientReceiver(new LoginChallengePayload().type(), new LoginChallengeReceiver());
         PlayPackets.registerClientReceiver(new PubkeyQueryPayload().type(), new PubkeyQueryReceiver());
    }

    static class LoginChallengeReceiver implements PacketReceiver<ClientConfigContext, LoginChallengePayload> {

        @Override
        public void receive(ClientConfigContext context, LoginChallengePayload payload) {
            context.client().execute(() -> {
                String name = context.client().getGameProfile().getName();
                if (name == null) {
                    LOGGER.error("Could not retrieve the username.");
                    return;
                }

                try {
                    Signature sig = Signature.getInstance(Constants.ALGORITHM);
                    sig.initSign(ClientKeyPair.KEY_PAIR.getPrivate());
                    sig.update(payload.data);

                    context.send(new LoginResponsePayload(payload.id, name, sig.sign()));
                } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
                    throw new RuntimeException(e);
                }

            });
        }
    }

    static class PubkeyQueryReceiver implements PacketReceiver<ClientPlayContext, PubkeyQueryPayload> {

        @Override
        public void receive(ClientPlayContext context, PubkeyQueryPayload payload) {
            context.client().execute(() -> {
                if (context.client().player != null) {
                    String user = context.client().player.getName().getString();
                    context.send(new PubkeyBindPayload(user, ClientKeyPair.KEY_PAIR.getPublic()));
                } else {
                    LOGGER.error("Failed to send public key to the server.");
                }
            });
        }
    }
}
