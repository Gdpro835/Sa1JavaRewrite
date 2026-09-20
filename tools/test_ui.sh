#!/usr/bin/env bash
# Production menu/animation integration tests with headless host/audio/graphics doubles.
# This does not execute Android, EGL, GLES shaders or audio drivers.
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
: "${ANDROID_JAR:?Set ANDROID_JAR to an Android SDK android.jar (compile-only)}"
: "${JSON_JAR:?Set JSON_JAR to the org.json:json JVM library (e.g. 20240303)}"
JAVA="${JAVA:-java}"
JAVAC="${JAVAC:-javac}"
TMP="$(mktemp -d "${TMPDIR:-/tmp}/sa1-ui-tests.XXXXXX")"
trap 'rm -rf "$TMP"' EXIT
python3 - "$ROOT" "$TMP" <<'PY'
from pathlib import Path
import sys
root, tmp = map(Path, sys.argv[1:])
for name, sources in [('production', (root / 'data/602/files/java').rglob('*.java')),
                      ('tests', (root / 'tests/ui').rglob('*.java'))]:
    (tmp / (name + '.txt')).write_text('\n'.join('"' + str(p) + '"' for p in sorted(sources)))
PY
compile() {
    if [[ -n "${ECJ_JAR:-}" ]]; then
        "$JAVA" -jar "$ECJ_JAR" -1.7 -proc:none -nowarn -encoding UTF-8 "$@"
    else
        "$JAVAC" -source 7 -target 7 -Xlint:-options -proc:none -encoding UTF-8 "$@"
    fi
}
compile -cp "$ANDROID_JAR" -d "$TMP/production" "@$TMP/production.txt"
compile -cp "$TMP/production:$ANDROID_JAR:$JSON_JAR" -d "$TMP/tests" "@$TMP/tests.txt"
for suite in ${UI_TEST_CLASS:-MenuRegressionTests OtherMenuRegressionTests}; do
    "$JAVA" -ea -Dsa1.assets="$ROOT/data/602/files/assets" \
        -cp "$TMP/tests:$TMP/production:$JSON_JAR:$ANDROID_JAR" "$suite" "$@"
done
