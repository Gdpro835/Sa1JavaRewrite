#!/usr/bin/env bash
# Tests the production text cache/font wrapper using deterministic Android API doubles.
# No Android, EGL or actual font pixels are executed or compared by this suite.
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
TMP="$(mktemp -d "${TMPDIR:-/tmp}/sa1-text-tests.XXXXXX")"
trap 'rm -rf "$TMP"' EXIT
JAVA="${JAVA:-java}"
JAVAC="${JAVAC:-javac}"
python3 - "$ROOT" "$TMP/sources.txt" <<'PY'
from pathlib import Path
import sys
root = Path(sys.argv[1])
java = root / 'data/602/files/java'
sources = list((root / 'tests/text').rglob('*.java'))
sources += [java / 'com/sega/mobile/framework/opengl/TextTextureCache.java',
            java / 'com/sega/mobile/framework/android/Font.java']
Path(sys.argv[2]).write_text('\n'.join('"' + str(p) + '"' for p in sorted(sources)))
PY
if [[ -n "${ECJ_JAR:-}" ]]; then
    "$JAVA" -jar "$ECJ_JAR" -1.7 -proc:none -nowarn -encoding UTF-8 -d "$TMP/classes" "@$TMP/sources.txt"
else
    "$JAVAC" -source 7 -target 7 -Xlint:-options -encoding UTF-8 -d "$TMP/classes" "@$TMP/sources.txt"
fi
"$JAVA" -ea -cp "$TMP/classes" TextTextureTests
