package balls.jl.mcofflineauth4forge;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static final String MOD_ID = "mcofflineauth";

    public static final String ALGORITHM = "Ed25519";

    public static final Path GAME_DIR = Minecraft.getInstance().gameDirectory.toPath();
    public static final Path MOD_DIR = Paths.get(GAME_DIR.toString(), ".offline-auth");
    public static final Path SEC_PATH = Paths.get(MOD_DIR.toString(), "secret-key");
    public static final Path PUB_PATH = Paths.get(MOD_DIR.toString(), "public-key");

    public static final ResourceLocation PUBKEY_QUERY_PACKET_ID = ResourceLocation.fromNamespaceAndPath("mc-offline-auth", "pubkey-query");
    public static final ResourceLocation PUBKEY_BIND_PACKET_ID = ResourceLocation.fromNamespaceAndPath("mc-offline-auth", "pubkey-bind");
    public static final ResourceLocation LOGIN_CHALLENGE_PACKET_ID = ResourceLocation.fromNamespaceAndPath("mc-offline-auth", "login-challenge");
    public static final ResourceLocation LOGIN_RESPONSE_PACKET_ID = ResourceLocation.fromNamespaceAndPath("mc-offline-auth", "login-response");

    public static final ResourceLocation LOGIN_TASK = ResourceLocation.fromNamespaceAndPath("mc-offline-auth", "login_task");
}
