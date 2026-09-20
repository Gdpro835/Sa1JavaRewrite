#!/usr/bin/env bash
# Compile the existing Sketchware Java tree, without creating a Gradle project or an APK.
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
: "${ANDROID_JAR:?Set ANDROID_JAR to a recent Android SDK platforms/android-XX/android.jar}"
JAVA="${JAVA:-java}"
JAVAC="${JAVAC:-javac}"
TMP="$(mktemp -d "${TMPDIR:-/tmp}/sa1-java-check.XXXXXX")"
trap 'rm -rf "$TMP"' EXIT
python3 - "$ROOT" "$TMP/sources.txt" <<'PY'
from pathlib import Path
import sys
files = sorted((Path(sys.argv[1]) / 'data/602/files/java').rglob('*.java'))
Path(sys.argv[2]).write_text('\n'.join('"' + str(p) + '"' for p in files))
print('Compiling %d Java sources (Sketchware layout, Java 7)...' % len(files))
PY
if [[ -n "${ECJ_JAR:-}" ]]; then
    "$JAVA" -jar "$ECJ_JAR" -1.7 -proc:none -nowarn -encoding UTF-8 -cp "$ANDROID_JAR" -d "$TMP/classes" "@$TMP/sources.txt"
else
    "$JAVAC" -source 7 -target 7 -Xlint:-options -proc:none -encoding UTF-8 -cp "$ANDROID_JAR" -d "$TMP/classes" "@$TMP/sources.txt"
fi
printf 'PASS: %s class files. This is a source check, not an APK/device test.\n' "$(find "$TMP/classes" -name '*.class' | wc -l | tr -d ' ')"
