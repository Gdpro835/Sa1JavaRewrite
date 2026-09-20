#!/usr/bin/env python3
"""Guard architectural invariants; not a substitute for Android gameplay/render tests."""
import json
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
JAVA = ROOT / 'data/602/files/java'


def text(path):
    return (JAVA / path).read_text(encoding='utf-8')


def require(condition, message):
    if not condition:
        raise SystemExit('FAIL: ' + message)


canvas = text('com/sega/mobile/framework/android/Canvas.java')
device = text('com/sega/mobile/framework/device/MFDevice.java')
main = text('com/sega/mobile/framework/MFMain.java')
batch = text('com/sega/mobile/framework/opengl/SpriteBatch.java')
player = text('SonicGBA/PlayerObject.java')
world = text('SonicGBA/GameObject.java')
clock = text('GameEngine/time/FrameClock.java')
require('extends GLSurfaceView' in canvas and 'GLSurfaceView.Renderer' in canvas, 'screen must be a GLES view')
require('setEGLContextClientVersion(2)' in canvas, 'explicit GLES 2 context')
require('setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY)' in canvas, 'render every available display frame')
for source in JAVA.rglob('*.java'):
    require(not re.search(r'\bFRAME_SKIP\b', source.read_text(encoding='utf-8')),
            'legacy FPS limiter must not return: ' + str(source.relative_to(JAVA)))
for path, source in [('Canvas', canvas), ('MFDevice', device), ('MainState', text('MFLib/MainState.java'))]:
    require(not re.search(r'\b(?:sleep|parkNanos|parkUntil|postDelayed)\s*\(', source),
            'no artificial frame wait in ' + path)
require('System.nanoTime()' in canvas and 'clock.reset()' in canvas, 'monotonic lifecycle-aware clock')
require('MotionEvent.ACTION_CANCEL' in canvas and 'queueEvent' in canvas, 'queued, cancelable input')
require('lockCanvas' not in canvas and 'drawBitmap' not in canvas, 'no software screen/frame upload bridge')
require('Bitmap.createBitmap' not in device and 'getCanvas()' not in device, 'no CPU screen buffers')
require('Thread.sleep' not in device and 'mainRunnable' not in device, 'no legacy busy-spin scheduler')
require(device.count('currentState.onUpdate(delta)') == 1, 'one variable update per render callback')
require('while' not in clock and 'MAX_DELTA_SECONDS' in clock, 'no hidden fixed-step catch-up in the clock')
require('mCreated' not in main and 'MFDevice.attach(this)' in main, 'Activity recreation must attach a new view')
require('System.exit(' not in main, 'lifecycle must not terminate the VM')
require('new Thread' not in world and 'Thread.sleep' not in world, 'no companion/network-copy game mutation threads')
require('glDrawArrays' in batch and 'GL_ONE_MINUS_SRC_ALPHA' in batch, 'scene geometry and premultiplied alpha')
require('getGenerationId()' in batch and 'textures.clear()' in batch, 'mutable bitmap uploads and EGL recreation')
require('worldCal.actionLogic(var1 - var7, var2 - var4)' in player, 'absolute collision correction must remain unscaled')
require('worldCal.moveVelocity(this.velX, this.velY, this.totalVelocity)' in player, 'player velocity must be integrated')
require('this.velX = this.footPointX - footPointX;' not in player and 'railDisplacement(' in player, 'rail motion and exit velocity must use time units')
require('timeCount += 60' not in player and 'GameTime.milliseconds(' in player, 'score time must be real elapsed time')
require('GameTime.event(this, "fire_cnt", "initialShot"' in text('SonicGBA/Crab.java'), 'initial attack must not repeat on fractional frames')
require('GameTime.steps(this, "attack_cn"' in text('SonicGBA/Boss4.java'), 'crossed switch timeline events must not be skipped')
require('void onUpdate(double deltaSeconds)' in text('com/sega/mobile/framework/MFGameState.java'), 'explicit delta state interface')
require('Lib.Animation.updateAll()' in text('MFLib/MainState.java'), 'direct animation playback must advance from update')
require('AnimationDrawer.updateAll()' in text('MFLib/MainState.java'), 'animation time belongs to the update phase')
require('startTime = System.currentTimeMillis()' not in text('Lib/AnimationDrawer.java'), 'animations must pause with simulation time')
require(int(json.loads((ROOT / 'data/602/project_config').read_text())['min_sdk']) >= 14, 'renderer needs Android API 14+')
require(json.loads((ROOT / 'data/602/build_config').read_text())['java_ver'] == '1.7', 'retain Sketchware Java 7 format')
require(not (ROOT / 'build.gradle').exists(), 'do not silently replace the Sketchware project with Gradle')
print('PASS: engine structure guards (GLES, delta, lifecycle, input, unscaled corrections, Sketchware).')
