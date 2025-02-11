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
 * Client's response to the server, providing the signature for the challenge data.
 */
public class LoginResponsePayload implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, LoginResponsePayload> STREAM_CODEC = StreamCodec.of(new LoginResponsePayload.Encoder(), new LoginResponsePayload.Decoder());

    public UUID id;
    public String user;
    public byte[] signature;

    public LoginResponsePayload() {
    }

    public LoginResponsePayload(UUID id, String user, byte[] signature) {
        this.id = id;
        this.signature = signature;
        this.user = user;
    }

    @Override
    public @NotNull Type<LoginResponsePayload> type() {
        return new Type<>(Constants.LOGIN_RESPONSE_PACKET_ID);
    }

    public static class Encoder implements StreamEncoder<FriendlyByteBuf, LoginResponsePayload> {

        @Override
        public void encode(FriendlyByteBuf buf, LoginResponsePayload payload) {
            buf.writeUUID(payload.id);
            buf.writeUtf(payload.user);
            buf.writeByteArray(payload.signature);
        }
    }

    public static class Decoder implements StreamDecoder<FriendlyByteBuf, LoginResponsePayload> {

        @Override
        public @NotNull LoginResponsePayload decode(FriendlyByteBuf buf) {
            return new LoginResponsePayload(buf.readUUID(), buf.readUtf(), buf.readByteArray());
        }
    }
}

