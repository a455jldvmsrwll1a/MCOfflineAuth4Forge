package balls.jl.mcofflineauth4forge.net;

import balls.jl.mcofflineauth4forge.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Tells the client to sign the given data and reply with the signature.
 */
public class LoginChallengePayload implements CustomPacketPayload {
    public static final StreamCodec STREAM_CODEC = StreamCodec.of(new Encoder(), new Decoder());

    public UUID id;
    public byte[] data;

    public LoginChallengePayload() {
    }

    public LoginChallengePayload(UUID id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public @NotNull Type<LoginChallengePayload> type() {
        return new Type<>(Constants.LOGIN_CHALLENGE_PACKET_ID);
    }

    public static class Encoder implements StreamEncoder<FriendlyByteBuf, LoginChallengePayload> {

        @Override
        public void encode(FriendlyByteBuf buf, LoginChallengePayload payload) {
            buf.writeUUID(payload.id);
            buf.writeByteArray(payload.data);
        }
    }

    public static class Decoder implements StreamDecoder<FriendlyByteBuf, LoginChallengePayload> {

        @Override
        public @NotNull LoginChallengePayload decode(FriendlyByteBuf buf) {
            return new LoginChallengePayload(buf.readUUID(), buf.readByteArray());
        }
    }
}
