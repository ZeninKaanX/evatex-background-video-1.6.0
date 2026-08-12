package com.videomenu.video;

import com.videomenu.VideoMenuClient;
import com.videomenu.VideoMenuConfig;
import com.videomenu.mixin.NativeImageAccessor;
import java.nio.IntBuffer;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1044;
import net.minecraft.class_10799;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import org.lwjgl.system.MemoryUtil;

public final class VideoBackground {
    public static final VideoBackground INSTANCE = new VideoBackground();
    public static final class_2960 TEXTURE_ID = class_2960.method_60655("evatexbackgroundvideo", "video");
    public static final class_2960 OVERLAY_TEXTURE = class_2960.method_60656("textures/gui/title/background/panorama_overlay.png");

    private volatile VideoMenuConfig config;
    private VideoDecoder decoder;
    private volatile boolean active;
    private volatile int[] latestFrame;
    private long lastUploadedIndex;
    private volatile int frameW;
    private volatile int frameH;
    private volatile class_1043 texture;
    private volatile IntBuffer pixelBuffer;

    private VideoBackground() {
    }

    public synchronized void init(VideoMenuConfig config) {
        if (this.config != null) {
            return;
        }
        this.config = config;
        this.decoder = new VideoDecoder(config, this::onFrame);
        this.active = this.decoder.start();
    }

    public boolean isActive() {
        return this.active;
    }

    public synchronized void pause() {
        if (!this.active) {
            return;
        }
        VideoMenuClient.LOGGER.info("[VideoMenu] Ana menü kapatıldı, video durduruluyor.");
        if (this.decoder != null) {
            this.decoder.close();
            this.decoder = null;
        }
        this.active = false;
        this.latestFrame = null;
    }

    public synchronized void resume() {
        if (this.config == null || this.active) {
            return;
        }
        VideoMenuClient.LOGGER.info("[VideoMenu] Ana menü açıldı, video yeniden başlatılıyor.");
        if (this.decoder != null) {
            this.decoder.close();
            this.decoder = null;
        }
        if (this.texture != null) {
            try {
                this.texture.close();
            } catch (Throwable t) {
            }
            this.texture = null;
            this.pixelBuffer = null;
        }
        this.latestFrame = null;
        this.frameW = 0;
        this.frameH = 0;
        this.lastUploadedIndex = -1L;
        this.decoder = new VideoDecoder(this.config, this::onFrame);
        this.active = this.decoder.start();
    }

    public synchronized void reload(VideoMenuConfig newConfig) {
        VideoMenuClient.LOGGER.info("[VideoMenu] Ayarlar uygulanıyor, video yeniden başlatılıyor.");
        if (this.decoder != null) {
            this.decoder.close();
            this.decoder = null;
        }
        if (this.texture != null) {
            try {
                this.texture.close();
            } catch (Throwable t) {
            }
            this.texture = null;
            this.pixelBuffer = null;
        }
        this.config = newConfig;
        this.latestFrame = null;
        this.frameW = 0;
        this.frameH = 0;
        this.lastUploadedIndex = -1L;
        this.decoder = new VideoDecoder(this.config, this::onFrame);
        this.active = this.decoder.start();
    }

    private void onFrame(int[] abgrPixels, int width, int height) {
        this.frameW = width;
        this.frameH = height;
        this.latestFrame = abgrPixels;
    }
    public boolean render(class_332 context, int width, int height) {
        if (!this.active) {
            return false;
        }
        if (this.latestFrame == null) {
            return false;
        }
        this.ensureTexture();
        if (this.texture == null) {
            return false;
        }
        long published = this.decoder.publishedFrames();
        if (published != this.lastUploadedIndex) {
            synchronized (this) {
                int[] latest = this.latestFrame;
                if (latest != null) {
                    this.pixelBuffer.clear();
                    this.pixelBuffer.put(latest);
                    this.texture.method_4524();
                }
            }
            this.lastUploadedIndex = published;
        }
        int vw = this.frameW;
        int vh = this.frameH;
        double fit = Math.min((double) width / (double) vw, (double) height / (double) vh);
        int dw = (int) ((double) vw * fit);
        int dh = (int) ((double) vh * fit);
        int dx = (width - dw) / 2;
        int dy = (height - dh) / 2;
        context.method_25302(class_10799.field_56883, TEXTURE_ID, dx, dy, 0.0f, 0.0f, dw, dh, vw, vh, vw, vh);
        if (this.config.darkening > 0.0) {
            int alpha = (int) (this.config.darkening * 255.0);
            if (alpha > 255) {
                alpha = 255;
            }
            context.method_25294(0, 0, width, height, alpha << 24);
        }
        if (this.config.showVignette) {
            context.method_25302(class_10799.field_56883, OVERLAY_TEXTURE, 0, 0, 0.0f, 0.0f, width, height, 16, 128, 16, 128);
        }
        return true;
    }

    private void ensureTexture() {
        if (this.texture != null) {
            return;
        }
        int w = this.frameW;
        int h = this.frameH;
        if (w <= 0 || h <= 0) {
            return;
        }
        try {
            class_1011 image = new class_1011(class_1011.class_1012.field_4997, w, h, false);
            long pointer = ((NativeImageAccessor) (Object) image).videomenu$getPointer();
            this.pixelBuffer = MemoryUtil.memIntBuffer(pointer, w * h);
            this.texture = new class_1043(() -> "videomenu:video", image);
            class_310.method_1551().method_1531().method_4616(TEXTURE_ID, (class_1044) this.texture);
            this.texture.method_4524();
        } catch (Throwable t) {
            VideoMenuClient.LOGGER.error("[VideoMenu] Video dokusu oluşturulamadı.", t);
            this.texture = null;
        }
    }
}
