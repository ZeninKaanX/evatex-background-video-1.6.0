#!/usr/bin/env bash
# Downloads the static FFmpeg builds bundled into the mod jar.
# Windows: gyan.dev essentials build (https://www.gyan.dev/ffmpeg/builds/)
# Linux:   BtbN FFmpeg-Builds         (https://github.com/BtbN/FFmpeg-Builds)
set -euo pipefail

DEST="$(cd "$(dirname "$0")/.." && pwd)/assets/videomenu/ffmpeg"
mkdir -p "$DEST/win64" "$DEST/linux64"

echo "[1/2] Windows ffmpeg.exe (gyan.dev essentials)..."
curl -sL -o /tmp/ffmpeg-win.zip "https://www.gyan.dev/ffmpeg/builds/ffmpeg-release-essentials.zip"
unzip -o -j /tmp/ffmpeg-win.zip "*/bin/ffmpeg.exe" -d "$DEST/win64" >/dev/null
rm -f /tmp/ffmpeg-win.zip

echo "[2/2] Linux ffmpeg (BtbN)..."
curl -sL -o /tmp/ffmpeg-linux.tar.xz "https://github.com/BtbN/FFmpeg-Builds/releases/latest/download/ffmpeg-master-latest-linux64-gpl.tar.xz"
tar xf /tmp/ffmpeg-linux.tar.xz -C /tmp "ffmpeg-master-latest-linux64-gpl/bin/ffmpeg"
mv /tmp/ffmpeg-master-latest-linux64-gpl/bin/ffmpeg "$DEST/linux64/ffmpeg"
chmod +x "$DEST/linux64/ffmpeg"
rm -f /tmp/ffmpeg-linux.tar.xz
rm -rf /tmp/ffmpeg-master-latest-linux64-gpl

echo "Done. Bundled FFmpeg binaries ready:"
du -sh "$DEST/win64" "$DEST/linux64"
