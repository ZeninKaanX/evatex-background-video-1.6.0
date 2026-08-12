package com.videomenu;

import com.videomenu.video.VideoBackground;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoMenuClient implements ClientModInitializer {
    public static final String MOD_ID = "evatexbackgroundvideo";
    public static final String MIN_VERSION = "1.21.8";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitializeClient() {
        if (!isSupportedVersion()) {
            LOGGER.warn("[EVATEX-Background Video] Bu mod Minecraft {} ve üzeri sürümler için tasarlanmıştır; bu sürümde video arka planı devre dışı bırakıldı (oyun etkilenmez).", MIN_VERSION);
            return;
        }
        this.createVideosFolder();
        VideoMenuConfig config = VideoMenuConfig.load();
        LOGGER.info("[EVATEX-Background Video] Config yüklendi. videoPath: '{}'", config.videoPath);
        Thread init = new Thread(() -> VideoBackground.INSTANCE.init(config), "VideoMenu-Init");
        init.setDaemon(true);
        init.start();
    }

    private static boolean isSupportedVersion() {
        try {
            String version = FabricLoader.getInstance().getModContainer("minecraft")
                    .orElseThrow(() -> new IllegalStateException("minecraft mod container bulunamadı"))
                    .getMetadata().getVersion().getFriendlyString();
            String[] parts = version.split("[+\\-]")[0].split("\\.");
            int major = Integer.parseInt(parts[0]);
            int minor = Integer.parseInt(parts[1]);
            if (major != 1) {
                return major > 1;
            }
            if (minor > 21) {
                return true;
            }
            if (minor < 21) {
                return false;
            }
            int patch = parts.length > 2 ? Integer.parseInt(parts[2].split("\\D")[0]) : 0;
            return patch >= 8;
        } catch (Throwable t) {
            return false;
        }
    }

    private void createVideosFolder() {
        Path dir = FabricLoader.getInstance().getGameDir().resolve("videolar");
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            LOGGER.warn("[EVATEX-Background Video] 'videolar' klasörü oluşturulamadı: '{}'", dir, e);
        }
    }
}
