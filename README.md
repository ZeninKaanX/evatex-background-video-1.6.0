# 🎬 EVATEX-Background Video

**Replace Minecraft's boring main menu panorama with your own video!** 🎮✨

Every time you reach the main menu, your video plays in the background — your favorite clip, an animation, a looping cinematic, a stream intro... whatever you want. All you have to do is drop a video into the `videolar` folder and the mod does the rest. **Made by EverVerity.** 💜

---

## ✨ Features

- 🖼️ **Your video, your main menu** — plays MP4, MOV, WebM, MKV, AVI and more instead of the panorama
- 📦 **Zero setup, no installs:** FFmpeg is bundled inside the mod — just drop the jar into `mods` and play
- 🎛️ **Full control:** pick your video, adjust scale, FPS, darkening and vignette
- 📂 **Easy management:** everything is configured in-game via the "Video Menüsü" button
- 🔄 **Works on Minecraft 1.21.8 and above** (1.21.8 / 1.21.10 / 1.21.11 ...)
- 🛡️ **Safe:** on an unsupported version the mod silently disables itself — the game never crashes
- ⚡ **Lightweight:** only active on the main menu, never interferes with gameplay

---

## 📥 Installation

1. Install **Fabric Loader** (no other dependencies required)
2. Drop the mod's `.jar` file into the `mods` folder of your game directory
3. Launch the game — **that's it!** 🎉

> ✅ **Nothing to install.** The mod ships with its own FFmpeg build for **Windows** and **Linux** — no FFmpeg, no dependencies, no command line. Just drop the jar into `mods` and run.

---

## 🚀 How to Use

### 1️⃣ Add Videos
Drop your videos into the `videolar` folder inside your game directory:

- 📁 Locate your game folder (`.../.minecraft/` or your launcher's folder)
- 📽️ Copy your videos into the `videolar` folder
- ✅ Supported formats: **MP4, MOV, WebM, MKV, AVI, M4V** and any other format FFmpeg supports
- 💡 **Tip:** looping videos or short showcase clips give the best results

### 2️⃣ Select a Video
- Click the **"Video Menüsü"** button in the top-right corner of the main menu
- Pick the video you want — the background updates instantly

### 3️⃣ Adjust Settings
From the Video Menu you can tweak the following:

| Setting | What it does |
|---------|--------------|
| 🎞️ **Video** | Switch between videos in the `videolar` folder |
| 🔍 **Scale** | Fitting mode for the video (fit, stretch, etc.) |
| ⏱️ **FPS** | Frame rate — lower FPS means less CPU usage |
| 🌑 **Darkening** | Darkens the background so buttons are easier to read |
| 🍇 **Vignette** | Toggles the edge darkening effect |
| 📁 **Videoları Aç** | Opens the `videolar` folder in your file manager |

Your settings are saved automatically — they are applied on the next launch as well. 🔁

---

## 🛠️ Requirements

- 🎮 Minecraft **1.21.8 and above** (client)
- 🧵 Fabric Loader (`>= 0.16.0`)
- ☕ Java 21
- 🎥 **FFmpeg** — already bundled with the mod (Windows & Linux). No installation needed.

---

## 🔧 Troubleshooting

| Problem | Solution |
|---------|----------|
| Video not playing | Make sure the file in `videolar` is a supported format (MP4/H.264 recommended) |
| Video too fast/slow | Change the FPS setting from the Video Menu |
| Buttons hard to read | Turn on the Darkening setting |
| Mod not visible | Check that the jar is in `mods` and you launched the game with Fabric |

---

## 📜 License

This mod is licensed under the **MIT** license. EverVerity © 2026. 💜

The mod bundles statically-linked builds of **FFmpeg** (GPL v3) for Windows and Linux. FFmpeg is a free software project created by the FFmpeg developers (https://ffmpeg.org); the source code is available from the build maintainers we used (https://github.com/BtbN/FFmpeg-Builds and https://www.gyan.dev/ffmpeg/builds/). A copy of the GNU GPL v3 is included inside the jar (`assets/videomenu/ffmpeg/COPYING.GPLv3`).

---

*Make the main menu yours again — your video is right there, waiting for you. 🎬✨*

---

## 🛠️ Building from Source

This project uses plain `javac` (no Gradle). Compile against Fabric Loader, Sponge Mixin, Gson and the Minecraft client jars (remapped to Fabric's intermediary names, e.g. 1.21.11).

```bash
# 1. Fetch the bundled FFmpeg binaries (they are not tracked in git)
bash scripts/fetch-ffmpeg.sh

# 2. Compile (Java 21)
javac -proc:none --release 21 -nowarn \
  -cp "<loader>:<mixin>:<gson>:<minecraft-intermediary>..." \
  -d out $(find src -name "*.java")

# 3. Package (jar root = fabric.mod.json + videomenu.mixins.json + assets + com + LICENSE + README)
jar cfm evatex-background-video.jar META-INF/MANIFEST.MF \
  fabric.mod.json videomenu.mixins.json LICENSE README.md assets com
```

The runtime version gate is `VideoMenuClient.MIN_VERSION = "1.21.8"` and mixins are `required: true`, so the mod enables itself on Minecraft **1.21.8+** (where the render pipeline API exists) and silently disables on anything older. A separate build targets Minecraft 1.20.x (`>=1.20 <1.21`) using the legacy render API.

# 🎬 EVATEX-Background Video

**Replace Minecraft's boring main menu panorama with your own video!** 🎮✨

Every time you reach the main menu, your video plays in the background — your favorite clip, an animation, a looping cinematic, a stream intro... whatever you want. All you have to do is drop a video into the `videos` folder and the mod does the rest. **Made by EverVerity.** 💜

---

## ✨ Features

- 🖼️ **Your video, your main menu** — plays MP4, MOV, WebM, MKV, AVI and more instead of the panorama
- 📦 **Zero setup, no installs:** FFmpeg is bundled inside the mod — just drop the jar into `mods` and play
- 🎛️ **Full control:** pick your video, adjust scale, FPS, darkening and vignette
- 📂 **Easy management:** everything is configured in-game via the "Video Menu" button
- 🔄 **Multi-version support:** works on Minecraft **1.20 and above**
- 🛡️ **Safe:** on an unsupported version the mod silently disables itself — the game never crashes
- ⚡ **Lightweight:** only active on the main menu, never interferes with gameplay

---

## 📥 Installation

1. Install **Fabric Loader** and **Fabric API** (easy with Fabric's own installer)
2. Drop the mod's `.jar` file into the `mods` folder of your game directory
3. Launch the game — **that's it!** 🎉

> ✅ **Nothing to install.** The mod ships with its own FFmpeg build for **Windows** and **Linux** — no FFmpeg, no dependencies, no command line. Just drop the jar into `mods` and run.

---

## 🚀 How to Use

### 1️⃣ Add Videos
Drop your videos into the `videos` folder inside your game directory:

- 📁 Locate your game folder (`.../.minecraft/` or your launcher's folder)
- 📽️ Copy your videos into the `videos` folder
- ✅ Supported formats: **MP4, MOV, WebM, MKV, AVI, M4V** and any other format FFmpeg supports
- 💡 **Tip:** looping videos or short showcase clips give the best results

### 2️⃣ Select a Video
- Click the **"Video Menu"** button in the top-right corner of the main menu
- Pick the video you want — the background updates instantly

### 3️⃣ Adjust Settings
From the Video Menu you can tweak the following:

| Setting | What it does |
|---------|--------------|
| 🎞️ **Video** | Switch between videos in the `videos` folder |
| 🔍 **Scale** | Fitting mode for the video (fit, stretch, etc.) |
| ⏱️ **FPS** | Frame rate — lower FPS means less CPU usage |
| 🌑 **Darkening** | Darkens the background so buttons are easier to read |
| 🍇 **Vignette** | Toggles the edge darkening effect |
| 📁 **Open Videos** | Opens the `videos` folder in your file manager |

Your settings are saved automatically — they are applied on the next launch as well. 🔁

---

## 🛠️ Requirements

- 🎮 Minecraft **1.20 and above** (client)
- 🧵 Fabric Loader (`>= 0.16.0`)
- 📦 Fabric API
- ☕ Java 21
- 🎥 **FFmpeg** — already bundled with the mod (Windows & Linux). No installation needed.

---

## 🔧 Troubleshooting

| Problem | Solution |
|---------|----------|
| Video not playing | Make sure the file in `videos` is a supported format (MP4/H.264 recommended) |
| Video too fast/slow | Change the FPS setting from the Video Menu |
| Buttons hard to read | Turn on the Darkening setting |
| Mod not visible | Check that the jar is in `mods` and you launched the game with Fabric |

---

## 📜 License

This mod is licensed under the **MIT** license. EverVerity © 2026. 💜

The mod bundles statically-linked builds of **FFmpeg** (GPL v3) for Windows and Linux. FFmpeg is a free software project created by the FFmpeg developers (https://ffmpeg.org); the source code is available from the build maintainers we used (https://github.com/BtbN/FFmpeg-Builds and https://www.gyan.dev/ffmpeg/builds/). A copy of the GNU GPL v3 is included inside the jar (`assets/videomenu/ffmpeg/COPYING.GPLv3`).

---

*Make the main menu yours again — your video is right there, waiting for you. 🎬✨*

---

## 🛠️ Building from Source

This project uses plain `javac` (no Gradle). Compile against Fabric Loader, Sponge Mixin, Gson and the Minecraft client jars (deobfuscated with Fabric's intermediary names, e.g. 1.21.11).

```bash
# 1. Fetch the bundled FFmpeg binaries (they are not tracked in git)
bash scripts/fetch-ffmpeg.sh

# 2. Compile
javac -proc:none --release 21 -nowarn \
  -cp "<loader>:<mixin>:<gson>:<minecraft-intermediary>:<fabric-api>..." \
  -d out $(find src -name "*.java")

# 3. Package (jar root = fabric.mod.json + videomenu.mixins.json + assets + com + LICENSE + README)
jar cfm evatex-background-video.jar META-INF/MANIFEST.MF \
  fabric.mod.json videomenu.mixins.json LICENSE README.md assets com
```

The runtime version gate is `VideoMenuClient.MIN_VERSION = "1.20"` and mixins are `required: false`, so the mod enables itself on every Minecraft **1.20+** version and silently disables on anything else.
