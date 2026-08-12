package com.videomenu;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public final class VideoMenuConfig {
    public static final String FILE_NAME = "evatexbackgroundvideo.json";
    public String videoPath = "";
    public int fps = 30;
    public double scale = 0.5;
    public double darkening = 0.25;
    public boolean showVignette = true;

    public static VideoMenuConfig load() {
        VideoMenuConfig config = new VideoMenuConfig();
        Path file = FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
        if (!Files.isRegularFile(file, new LinkOption[0])) {
            config.save();
            return config;
        }
        try {
            JsonObject obj = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
            config.videoPath = VideoMenuConfig.getString(obj, "videoPath", config.videoPath);
            config.fps = VideoMenuConfig.getInt(obj, "fps", config.fps);
            config.scale = VideoMenuConfig.getDouble(obj, "scale", config.scale);
            config.darkening = VideoMenuConfig.getDouble(obj, "darkening", config.darkening);
            config.showVignette = VideoMenuConfig.getBoolean(obj, "showVignette", config.showVignette);
        } catch (Exception e) {
            VideoMenuClient.LOGGER.error("[VideoMenu] evatexbackgroundvideo.json okunamadı, varsayılan ayarlar kullanılıyor.", e);
        }
        return config;
    }

    public void save() {
        try {
            Path dir = FabricLoader.getInstance().getConfigDir();
            Files.createDirectories(dir);
            Files.writeString(dir.resolve(FILE_NAME), this.toJson(), StandardCharsets.UTF_8, new OpenOption[0]);
        } catch (Exception e) {
            VideoMenuClient.LOGGER.error("[VideoMenu] evatexbackgroundvideo.json yazılamadı.", e);
        }
    }

    private String toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("_HELP", "Bu dosya EVATEX-Background Video modunun ayarlarıdır (EverVerity tarafından yapılmıştır). Genelde bu dosyayı elle düzenlemenize gerek yoktur; ana menüdeki 'Video Menüsü' butonuyla video ve ayarları oyun içinden değiştirebilirsiniz. videoPath: ana menüde oynatılacak video dosyasının yolu. Videoları oyun klasöründeki 'videolar' klasörüne atarsanız otomatik listelenir. Not: Video, mod ile birlikte gelen FFmpeg ile çözülür; ek kurulum gerekmez.");
        obj.addProperty("videoPath", this.videoPath);
        obj.addProperty("fps", this.fps);
        obj.addProperty("scale", this.scale);
        obj.addProperty("darkening", this.darkening);
        obj.addProperty("showVignette", this.showVignette);
        return new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement) obj);
    }

    private static String getString(JsonObject obj, String key, String def) {
        return obj.has(key) && obj.get(key).isJsonPrimitive() ? obj.get(key).getAsString() : def;
    }

    private static int getInt(JsonObject obj, String key, int def) {
        try {
            return obj.has(key) ? obj.get(key).getAsInt() : def;
        } catch (Exception e) {
            return def;
        }
    }

    private static double getDouble(JsonObject obj, String key, double def) {
        try {
            return obj.has(key) ? obj.get(key).getAsDouble() : def;
        } catch (Exception e) {
            return def;
        }
    }

    private static boolean getBoolean(JsonObject obj, String key, boolean def) {
        try {
            return obj.has(key) ? obj.get(key).getAsBoolean() : def;
        } catch (Exception e) {
            return def;
        }
    }
}
