package balls.jl.mcofflineauth4forge.net;

import balls.jl.mcofflineauth4forge.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Tells the server to bind the given public key with the user.
 */
public class PubkeyBindPayload implements CustomPacketPayload {
    public static final StreamCodec STREAM_CODEC = StreamCodec.of(new PubkeyBindPayload.Encoder(), new PubkeyBindPayload.Decoder());

    public String user;
    public PublicKey publicKey;

    public PubkeyBindPayload() {
    }

    public PubkeyBindPayload(String user, PublicKey publicKey) {
        this.user = user;
        this.publicKey = publicKey;
    }

    @Override
    public @NotNull Type<PubkeyBindPayload> type() {
        return new Type<>(Constants.PUBKEY_BIND_PACKET_ID);
    }

    public static class Encoder implements StreamEncoder<FriendlyByteBuf, PubkeyBindPayload> {

        @Override
        public void encode(FriendlyByteBuf buf, PubkeyBindPayload payload) {
            buf.writeByteArray(payload.publicKey.getEncoded());
            buf.writeUtf(payload.user);
        }
    }

    public static class Decoder implements StreamDecoder<FriendlyByteBuf, PubkeyBindPayload> {

        @Override
        public @NotNull PubkeyBindPayload decode(FriendlyByteBuf buf) {
            byte[] keyBytes = buf.readByteArray();
            String user = buf.readUtf();
            try {
                KeyFactory kf = KeyFactory.getInstance(Constants.ALGORITHM);
                PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(keyBytes));

                return new PubkeyBindPayload(user, publicKey);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
