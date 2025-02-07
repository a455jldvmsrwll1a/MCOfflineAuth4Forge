package balls.jl.mcofflineauth4forge;

import net.minecraft.client.Minecraft;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static final String MOD_ID = "mcofflineauth";

    public static final int PUBKEY_SIZE = 44;
    public static final String ALGORITHM = "Ed25519";

    public static final Path GAME_DIR = Minecraft.getInstance().gameDirectory.toPath();
    public static final Path MOD_DIR = Paths.get(GAME_DIR.toString(), ".offline-auth");
    public static final Path SEC_PATH = Paths.get(MOD_DIR.toString(), "secret-key");
    public static final Path PUB_PATH = Paths.get(MOD_DIR.toString(), "public-key");

//    public static final Identifier PUBKEY_QUERY_PACKET_ID = Identifier.of("mc-offline-auth", "pubkey-query");
//    public static final Identifier PUBKEY_BIND_PACKET_ID = Identifier.of("mc-offline-auth", "pubkey-bind");
//    public static final Identifier LOGIN_CHALLENGE_PACKET_ID = Identifier.of("mc-offline-auth", "login-challenge");
//    public static final Identifier LOGIN_RESPONSE_PACKET_ID = Identifier.of("mc-offline-auth", "login-response");
//
//    public static final Identifier LOGIN_TASK = Identifier.of("mc-offline-auth", "login_task");
}
