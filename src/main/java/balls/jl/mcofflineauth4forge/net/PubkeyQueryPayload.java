package balls.jl.mcofflineauth4forge.net;

import balls.jl.mcofflineauth4forge.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

/**
 * Tells the client to send over its public key.
 */
public class PubkeyQueryPayload implements CustomPacketPayload {
    public static final StreamCodec STREAM_CODEC = StreamCodec.of(new PubkeyQueryPayload.Encoder(), new PubkeyQueryPayload.Decoder());

    public PubkeyQueryPayload() {
    }

    @Override
    public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<PubkeyQueryPayload>(Constants.PUBKEY_QUERY_PACKET_ID);
    }

    static class Encoder implements StreamEncoder<FriendlyByteBuf, PubkeyQueryPayload> {

        @Override
        public void encode(@NotNull FriendlyByteBuf buf, @NotNull PubkeyQueryPayload payload) {
        }
    }

    static class Decoder implements StreamDecoder<FriendlyByteBuf, PubkeyQueryPayload> {

        @Override
        public @NotNull PubkeyQueryPayload decode(@NotNull FriendlyByteBuf buf) {
            return new PubkeyQueryPayload();
        }
    }
}
