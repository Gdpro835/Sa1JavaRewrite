#!/usr/bin/env bash
# Headless tests; no Gradle, Android SDK or test framework is required.
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
TMP="$(mktemp -d "${TMPDIR:-/tmp}/sa1-engine-tests.XXXXXX")"
trap 'rm -rf "$TMP"' EXIT
JAVA="${JAVA:-java}"
JAVAC="${JAVAC:-javac}"
python3 - "$ROOT" "$TMP/sources.txt" <<'PY'
from pathlib import Path
import sys
root = Path(sys.argv[1])
java = root / 'data/602/files/java'
sources = list((java / 'GameEngine/time').glob('*.java'))
sources += list((java / 'com/sega/engine/action').glob('*.java'))
sources += list((java / 'com/sega/engine/lib').glob('*.java'))
sources += [java / 'com/sega/mobile/define/MDPhone.java',
            java / 'com/sega/mobile/framework/opengl/SpriteTransform.java', root / 'tests/EngineTests.java']
Path(sys.argv[2]).write_text('\n'.join('"' + str(p) + '"' for p in sorted(sources)))
PY
if [[ -n "${ECJ_JAR:-}" ]]; then
    "$JAVA" -jar "$ECJ_JAR" -1.7 -proc:none -nowarn -encoding UTF-8 -d "$TMP/classes" "@$TMP/sources.txt"
else
    "$JAVAC" -source 7 -target 7 -Xlint:-options -encoding UTF-8 -d "$TMP/classes" "@$TMP/sources.txt"
fi
"$JAVA" -ea -cp "$TMP/classes" EngineTests
python3 "$ROOT/tools/check_engine_structure.py"
