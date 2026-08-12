package com.videomenu;

import com.videomenu.video.VideoBackground;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;

public final class VideoSelectScreen extends class_437 {
    private static final String[] EXTENSIONS = new String[]{".mp4", ".mov", ".webm", ".mkv", ".avi", ".m4v"};
    private static final double[] SCALE_OPTIONS = new double[]{0.25, 0.5, 0.75, 1.0};
    private static final int[] FPS_OPTIONS = new int[]{15, 24, 30, 45, 60, 80, 100};
    private static final double[] DARKEN_OPTIONS = new double[]{0.0, 0.15, 0.25, 0.4, 0.6};
    private static final int VIDEOS_PER_PAGE = 10;

    private final class_437 parent;
    private final int initialPage;
    private VideoMenuConfig config;
    private List<Path> videos = new ArrayList<>();
    private int page;

    public VideoSelectScreen(class_437 parent, int page) {
        super(class_2561.method_43470("Video Menüsü"));
        this.parent = parent;
        this.initialPage = page;
    }

    protected void method_25426() {
        this.config = VideoMenuConfig.load();
        this.videos = this.discoverVideos();
        int maxPages = Math.max(1, (this.videos.size() + VIDEOS_PER_PAGE - 1) / VIDEOS_PER_PAGE);
        this.page = Math.min(this.initialPage, maxPages - 1);
        int centerX = this.field_22789 / 2;
        int listY = 58;
        int listX = centerX - 250;
        int col2X = centerX + 10;
        int from = this.page * VIDEOS_PER_PAGE;
        int to = Math.min(this.videos.size(), from + VIDEOS_PER_PAGE);
        Path current = this.resolveConfigPath(this.config.videoPath);
        int y = listY;
        for (int i = from; i < to; i++) {
            Path video = this.videos.get(i);
            boolean selected = video.equals(current);
            String label = (selected ? "> " : "  ") + String.valueOf(video.getFileName());
            Path chosen = video;
            this.method_37063(class_4185.method_46430(class_2561.method_43470(label), button -> this.selectVideo(chosen)).method_46434(listX, y, 240, 20).method_46431());
            y += 22;
        }
        if (this.videos.size() > VIDEOS_PER_PAGE) {
            class_4185 prev = class_4185.method_46430(class_2561.method_43470("<"), button -> this.openPage(this.page - 1)).method_46434(listX, y + 2, 60, 20).method_46431();
            class_4185 next = class_4185.method_46430(class_2561.method_43470(">"), button -> this.openPage(this.page + 1)).method_46434(listX + 180, y + 2, 60, 20).method_46431();
            prev.field_22763 = this.page > 0;
            next.field_22763 = this.page < maxPages - 1;
            this.method_37063(prev);
            this.method_37063(next);
        }
        this.method_37063(class_4185.method_46430(class_2561.method_43470("Geri"), button -> this.field_22787.method_1507(this.parent)).method_46434(listX, this.field_22790 - 30, 240, 20).method_46431());
        class_4185 scaleButton = class_4185.method_46430(class_2561.method_43470("Ölçek: " + VideoSelectScreen.percent(this.config.scale)), button -> {
            this.config.scale = VideoSelectScreen.cycle(SCALE_OPTIONS, this.config.scale);
            this.apply();
        }).method_46434(col2X, 58, 240, 20).method_46431();
        this.method_37063(scaleButton);
        class_4185 fpsButton = class_4185.method_46430(class_2561.method_43470("FPS: " + this.config.fps), button -> {
            this.config.fps = VideoSelectScreen.cycleInt(FPS_OPTIONS, this.config.fps);
            this.apply();
        }).method_46434(col2X, 84, 240, 20).method_46431();
        this.method_37063(fpsButton);
        class_4185 darkenButton = class_4185.method_46430(class_2561.method_43470("Karartma: " + VideoSelectScreen.percent(this.config.darkening)), button -> {
            this.config.darkening = VideoSelectScreen.cycle(DARKEN_OPTIONS, this.config.darkening);
            this.apply();
        }).method_46434(col2X, 110, 240, 20).method_46431();
        this.method_37063(darkenButton);
        class_4185 vignetteButton = class_4185.method_46430(class_2561.method_43470("Vinyet: " + (this.config.showVignette ? "Açık" : "Kapalı")), button -> {
            this.config.showVignette = !this.config.showVignette;
            this.apply();
        }).method_46434(col2X, 136, 240, 20).method_46431();
        this.method_37063(vignetteButton);
        this.method_37063(class_4185.method_46430(class_2561.method_43470("Videoları Aç (Klasör)"), button -> this.openVideosFolder()).method_46434(col2X, 162, 240, 20).method_46431());
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float deltaTicks) {
        super.method_25394(context, mouseX, mouseY, deltaTicks);
        context.method_25300(this.field_22793, "Video Menüsü", this.field_22789 / 2, 16, 0xFFFFFF);
        Path dir = this.videosDir();
        context.method_25303(this.field_22793, "Videoları şuraya koyun: " + String.valueOf(dir.toAbsolutePath()), this.field_22789 / 2 - 250, 44, 0xAAAAAA);
        int centerX = this.field_22789 / 2;
        context.method_25303(this.field_22793, "Videolar:", centerX - 250, 48, 0xFFFFFF);
        context.method_25303(this.field_22793, "Ayarlar:", centerX + 10, 48, 0xFFFFFF);
        context.method_25303(this.field_22793, "Made by EverVerity | EVATEX-Background Video", 8, this.field_22790 - 12, 0x8A8A8A);
    }

    public boolean method_25421() {
        return false;
    }

    public boolean method_25422() {
        return false;
    }

    private void selectVideo(Path video) {
        this.config.videoPath = video.toAbsolutePath().toString();
        this.apply();
    }

    private void apply() {
        this.config.save();
        VideoBackground.INSTANCE.reload(this.config);
        this.field_22787.method_1507(new VideoSelectScreen(this.parent, this.page));
    }

    private void openPage(int newPage) {
        this.field_22787.method_1507(new VideoSelectScreen(this.parent, newPage));
    }

    private void openVideosFolder() {
        Path dir = this.videosDir();
        try {
            Files.createDirectories(dir);
            ProcessBuilder pb = new ProcessBuilder("xdg-open", dir.toAbsolutePath().toString());
            pb.start();
        } catch (IOException e) {
            VideoMenuClient.LOGGER.warn("[VideoMenu] Klasör açılamadı: '{}'", dir, e);
        }
    }

    private List<Path> discoverVideos() {
        ArrayList<Path> list = new ArrayList<>();
        Path dir = this.videosDir();
        if (Files.isDirectory(dir, new LinkOption[0])) {
            try (Stream<Path> s = Files.list(dir)) {
                s.filter(VideoSelectScreen::isVideoFile).sorted(Comparator.comparing(p -> p.getFileName().toString())).forEach(list::add);
            } catch (IOException e) {
                VideoMenuClient.LOGGER.warn("[VideoMenu] video klasörü okunamadı.", e);
            }
        }
        Path current = this.resolveConfigPath(this.config != null ? this.config.videoPath : "");
        if (current != null && !list.contains(current)) {
            list.add(current);
        }
        return list;
    }

    private Path resolveConfigPath(String path) {
        if (path == null || path.isBlank()) {
            return null;
        }
        Path p = Path.of(path);
        if (!p.isAbsolute()) {
            p = FabricLoader.getInstance().getGameDir().resolve(p);
        }
        if (Files.isRegularFile(p, new LinkOption[0])) {
            return p.toAbsolutePath().normalize();
        }
        return null;
    }

    private Path videosDir() {
        return FabricLoader.getInstance().getGameDir().resolve("videolar");
    }

    private static boolean isVideoFile(Path p) {
        String name = p.getFileName().toString().toLowerCase();
        for (String ext : EXTENSIONS) {
            if (name.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    private static double cycle(double[] options, double current) {
        int idx = 0;
        for (int i = 0; i < options.length; i++) {
            if (options[i] == current) {
                idx = i;
                break;
            }
        }
        return options[(idx + 1) % options.length];
    }

    private static int cycleInt(int[] options, int current) {
        int idx = 0;
        for (int i = 0; i < options.length; i++) {
            if (options[i] == current) {
                idx = i;
                break;
            }
        }
        return options[(idx + 1) % options.length];
    }

    private static String percent(double value) {
        return Math.round(value * 100.0) + "%";
    }
}
