package dev.brokenstudio.brokenapi.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import dev.brokenstudio.brokenapi.reflections.Reflections;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public enum Skull
{
    ARROW_LEFT("MHF_ArrowLeft"),
    ARROW_RIGHT("MHF_ArrowRight"),
    ARROW_UP("MHF_ArrowUp"),
    ARROW_DOWN("MHF_ArrowDown"),
    QUESTION("MHF_Question"),
    EXCLAMATION("MHF_Exclamation"),
    CAMERA("FHG_Cam"),

    ZOMBIE_PIGMAN("MHF_PigZombie"),
    PIG("MHF_Pig"),
    SHEEP("MHF_Sheep"),
    BLAZE("MHF_Blaze"),
    CHICKEN("MHF_Chicken"),
    COW("MHF_Cow"),
    SLIME("MHF_Slime"),
    SPIDER("MHF_Spider"),
    SQUID("MHF_Squid"),
    VILLAGER("MHF_Villager"),
    OCELOT("MHF_Ocelot"),
    HEROBRINE("MHF_Herobrine"),
    LAVA_SLIME("MHF_LavaSlime"),
    MOOSHROOM("MHF_MushroomCow"),
    GOLEM("MHF_Golem"),
    GHAST("MHF_Ghast"),
    ENDERMAN("MHF_Enderman"),
    CAVE_SPIDER("MHF_CaveSpider"),

    CACTUS("MHF_Cactus"),
    CAKE("MHF_Cake"),
    CHEST("MHF_Chest"),
    MELON("MHF_Melon"),
    LOG("MHF_OakLog"),
    PUMPKIN("MHF_Pumpkin"),
    TNT("MHF_TNT"),
    DYNAMITE("MHF_TNT2"); private static final Base64 base64;
    static  {
        base64 = new Base64();
    }
    private String id;

    Skull(String id) { this.id = id; }








    public static ItemStack getCustomSkull(String url) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();
        if (propertyMap == null) {
            throw new IllegalStateException("Profile doesn't contain a property map");
        }
        byte[] encodedData = base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { url }).getBytes());
        propertyMap.put("textures", new Property("textures", new String(encodedData)));
        ItemStack head = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short)3);
        ItemMeta headMeta = head.getItemMeta();
        Class<?> headMetaClass = headMeta.getClass();
        Reflections.getField(headMetaClass, "profile", GameProfile.class).set(headMeta, profile);
        head.setItemMeta(headMeta);
        return head;
    }


    public static String getTextureUrlByUuid(String uuid) throws IOException {
        URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.replace("-",""));
        InputStreamReader read = new InputStreamReader(url.openStream());
        JsonObject textureProperty = new JsonParser().parse(read).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
        String textureDecoded = textureProperty.get("value").getAsString();
        JsonObject textureSkin = new JsonParser().parse(new String(base64.decode(textureDecoded))).getAsJsonObject().get("textures").getAsJsonObject().get("SKIN").getAsJsonObject();
        String urlString = textureSkin.get("url").getAsString();

        return urlString;
    }

    public static ItemStack getPlayerSkull(Player player) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta)itemStack.getItemMeta();
        meta.setOwnerProfile(player.getPlayerProfile());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public String getId() { return this.id; }

    public static ItemStack getSkullFromUUID(UUID uuid) throws IOException{
        return getCustomSkull(getTextureUrlByUuid(uuid.toString()));
    }

    public static void setSkullBlockTexture(Block block, String url) throws EncoderException {
        org.bukkit.block.Skull skullData = (org.bukkit.block.Skull)block.getState();
        skullData.setSkullType(SkullType.PLAYER);

        TileEntitySkull skullTile = (TileEntitySkull) ((CraftWorld) block.getWorld()).getHandle().getBlockEntity(new BlockPosition(block.getX(),block.getY(), block.getZ()), false);
        skullTile.a(getNonPlayerGameProfile(url));
        block.getState().update(true);

    }

    private static GameProfile getNonPlayerGameProfile(String url) throws EncoderException {
        GameProfile newGameProfile = new GameProfile(UUID.randomUUID(), null);
        newGameProfile.getProperties().put("textures",new Property("textures", new String(base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}",new Object[] { url }).getBytes()))));
        return newGameProfile;
    }




    public ItemStack getSkull() {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta)itemStack.getItemMeta();
        meta.setOwner(this.id);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
