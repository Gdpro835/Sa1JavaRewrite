package com.sega.mobile.framework.opengl;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/** GLES 2 renderer. All methods must run on the GLSurfaceView renderer thread. */
public final class SpriteBatch {
    private static final int VERTEX_FLOATS = 8;
    private static final int MAX_VERTICES = 6144;
    private static final long TEXTURE_BUDGET = 48L * 1024L * 1024L;
    private static final String VERTEX_SHADER =
            "uniform vec2 uViewport; attribute vec2 aPosition; attribute vec2 aTexCoord;"
          + "attribute vec4 aColor; varying vec2 vTexCoord; varying vec4 vColor;"
          + "void main(){gl_Position=vec4(aPosition.x*2.0/uViewport.x-1.0,"
          + "1.0-aPosition.y*2.0/uViewport.y,0.0,1.0);vTexCoord=aTexCoord;vColor=aColor;}";
    private static final String FRAGMENT_SHADER =
            "precision mediump float; uniform sampler2D uTexture; uniform vec3 uHue;"
          + "uniform float uGray; varying vec2 vTexCoord; varying vec4 vColor;"
          + "void main(){vec4 t=texture2D(uTexture,vTexCoord);"
          + "vec3 c=t.a>0.0?t.rgb/t.a:vec3(0.0);"
          + "c*=vColor.a>0.0?vColor.rgb/vColor.a:vec3(0.0);"
          + "c=clamp(c+(c+vec3(1.0/255.0))*uHue/256.0,0.0,1.0);"
          + "if(uGray>0.0)c=vec3(max(max(c.r,c.g),c.b)*uGray/256.0);"
          + "float a=t.a*vColor.a;gl_FragColor=vec4(c*a,a);}";

    private static final class Texture {
        int id;
        int generation;
        int width;
        int height;
        long usedFrame;
        boolean linear;
        long bytes() { return (long) width * height * 4L; }
    }
    private final IdentityHashMap<Bitmap, Texture> textures = new IdentityHashMap<Bitmap, Texture>();
    private final FloatBuffer vertices = ByteBuffer.allocateDirect(MAX_VERTICES * VERTEX_FLOATS * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer();
    private final int[] names = new int[1];
    private int program;
    private int positionLocation, uvLocation, colorLocation, viewportLocation, hueLocation, grayLocation;
    private int whiteTexture;
    private int currentTexture;
    private int vertexCount;
    private int width, height;
    private int clipLeft, clipTop, clipRight, clipBottom;
    private float hueR, hueG, hueB, gray;
    private long frame;
    private long textureBytes;
    private int drawCalls;
    private int maxTextureSize;

    public void create() {
        // A recreated EGL context invalidates ALL previous GL names, not the CPU bitmaps.
        textures.clear();
        textureBytes = 0;
        vertices.clear();
        vertexCount = 0;
        currentTexture = 0;
        int vs = compile(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
        int fs = compile(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vs);
        GLES20.glAttachShader(program, fs);
        GLES20.glLinkProgram(program);
        int[] status = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, status, 0);
        GLES20.glDeleteShader(vs);
        GLES20.glDeleteShader(fs);
        if (status[0] == 0) throw new IllegalStateException("OpenGL program: " + GLES20.glGetProgramInfoLog(program));
        positionLocation = GLES20.glGetAttribLocation(program, "aPosition");
        uvLocation = GLES20.glGetAttribLocation(program, "aTexCoord");
        colorLocation = GLES20.glGetAttribLocation(program, "aColor");
        viewportLocation = GLES20.glGetUniformLocation(program, "uViewport");
        hueLocation = GLES20.glGetUniformLocation(program, "uHue");
        grayLocation = GLES20.glGetUniformLocation(program, "uGray");
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, status, 0);
        maxTextureSize = status[0];
        GLES20.glGenTextures(1, names, 0);
        whiteTexture = names[0];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, whiteTexture);
        parameters(false);
        ByteBuffer white = ByteBuffer.allocateDirect(4);
        white.putInt(-1).position(0);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, 1, 1, 0,
                GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, white);
    }

    public void resize(int width, int height) {
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
    }

    public void begin() {
        frame++;
        drawCalls = 0;
        vertices.clear();
        vertexCount = 0;
        currentTexture = 0;
        GLES20.glViewport(0, 0, width, height);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        GLES20.glDisable(GLES20.GL_SCISSOR_TEST);
        GLES20.glClearColor(0f, 0f, 0f, 1f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glEnable(GLES20.GL_BLEND);
        // Android bitmaps are premultiplied; using SRC_ALPHA here darkens sprite edges.
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glUseProgram(program);
        GLES20.glUniform2f(viewportLocation, width, height);
        GLES20.glUniform1i(GLES20.glGetUniformLocation(program, "uTexture"), 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glEnableVertexAttribArray(positionLocation);
        GLES20.glEnableVertexAttribArray(uvLocation);
        GLES20.glEnableVertexAttribArray(colorLocation);
        clipLeft = clipTop = 0;
        clipRight = width;
        clipBottom = height;
        GLES20.glEnable(GLES20.GL_SCISSOR_TEST);
        GLES20.glScissor(0, 0, width, height);
        hueR = hueG = hueB = gray = 0f;
        GLES20.glUniform3f(hueLocation, 0f, 0f, 0f);
        GLES20.glUniform1f(grayLocation, 0f);
    }

    public void clip(int left, int top, int right, int bottom) {
        left = Math.max(0, Math.min(width, left));
        top = Math.max(0, Math.min(height, top));
        right = Math.max(left, Math.min(width, right));
        bottom = Math.max(top, Math.min(height, bottom));
        if (left == clipLeft && top == clipTop && right == clipRight && bottom == clipBottom) return;
        flush();
        clipLeft = left; clipTop = top; clipRight = right; clipBottom = bottom;
        GLES20.glScissor(left, height - bottom, right - left, bottom - top);
    }

    public void effect(float red, float green, float blue, float gray) {
        if (hueR == red && hueG == green && hueB == blue && this.gray == gray) return;
        flush();
        hueR = red; hueG = green; hueB = blue; this.gray = gray;
        GLES20.glUniform3f(hueLocation, red, green, blue);
        GLES20.glUniform1f(grayLocation, gray);
    }

    private int texture(Bitmap bitmap, boolean linear) {
        if (bitmap == null || bitmap.isRecycled()) return 0;
        Texture t = textures.get(bitmap);
        if (t == null) {
            if (bitmap.getWidth() > maxTextureSize || bitmap.getHeight() > maxTextureSize) {
                throw new IllegalArgumentException("Sprite exceeds GL_MAX_TEXTURE_SIZE: "
                        + bitmap.getWidth() + "x" + bitmap.getHeight());
            }
            flush();
            t = new Texture();
            GLES20.glGenTextures(1, names, 0);
            t.id = names[0]; t.width = bitmap.getWidth(); t.height = bitmap.getHeight();
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, t.id);
            parameters(linear);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            t.generation = bitmap.getGenerationId();
            t.linear = linear;
            textures.put(bitmap, t);
            textureBytes += t.bytes();
        } else if (t.generation != bitmap.getGenerationId() || t.linear != linear) {
            flush();
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, t.id);
            if (t.generation != bitmap.getGenerationId()) {
                GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, bitmap);
                t.generation = bitmap.getGenerationId();
            }
            if (t.linear != linear) parameters(linear);
            t.linear = linear;
        }
        t.usedFrame = frame;
        return t.id;
    }

    private static void parameters(boolean linear) {
        int filter = linear ? GLES20.GL_LINEAR : GLES20.GL_NEAREST;
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, filter);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, filter);
        // ES2 NPOT textures must use CLAMP_TO_EDGE and no mipmaps.
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
    }

    public void quad(Bitmap bitmap, boolean linear, float[] xy, float[] uv, int color) {
        int texture = bitmap == null ? whiteTexture : texture(bitmap, linear);
        if (texture == 0 || (color >>> 24) == 0) return;
        select(texture, 6);
        vertex(xy, uv, 0, color); vertex(xy, uv, 1, color); vertex(xy, uv, 2, color);
        vertex(xy, uv, 0, color); vertex(xy, uv, 2, color); vertex(xy, uv, 3, color);
    }

    public void triangle(float[] xy, int color) {
        select(whiteTexture, 3);
        for (int i = 0; i < 3; i++) vertex(xy, null, i, color);
    }

    private void select(int texture, int required) {
        if (currentTexture != texture || vertexCount + required > MAX_VERTICES) {
            flush();
            currentTexture = texture;
        }
    }

    private void vertex(float[] xy, float[] uv, int corner, int color) {
        float a = (color >>> 24) / 255f;
        vertices.put(xy[corner * 2]).put(xy[corner * 2 + 1]);
        vertices.put(uv == null ? 0f : uv[corner * 2]).put(uv == null ? 0f : uv[corner * 2 + 1]);
        vertices.put(((color >> 16) & 255) / 255f * a);
        vertices.put(((color >> 8) & 255) / 255f * a);
        vertices.put((color & 255) / 255f * a).put(a);
        vertexCount++;
    }

    public void flush() {
        if (vertexCount == 0) return;
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, currentTexture);
        vertices.position(0);
        GLES20.glVertexAttribPointer(positionLocation, 2, GLES20.GL_FLOAT, false, 32, vertices);
        vertices.position(2);
        GLES20.glVertexAttribPointer(uvLocation, 2, GLES20.GL_FLOAT, false, 32, vertices);
        vertices.position(4);
        GLES20.glVertexAttribPointer(colorLocation, 4, GLES20.GL_FLOAT, false, 32, vertices);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        drawCalls++;
        vertices.clear();
        vertexCount = 0;
    }

    public void end() {
        flush();
        Iterator<Map.Entry<Bitmap, Texture>> it = textures.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Bitmap, Texture> e = it.next();
            Texture t = e.getValue();
            if (e.getKey().isRecycled() || frame - t.usedFrame > 120L
                    || (textureBytes > TEXTURE_BUDGET && t.usedFrame != frame)) {
                names[0] = t.id;
                GLES20.glDeleteTextures(1, names, 0);
                textureBytes -= t.bytes();
                it.remove();
            }
        }
    }

    public void release() {
        flush();
        for (Texture t : textures.values()) {
            names[0] = t.id;
            GLES20.glDeleteTextures(1, names, 0);
        }
        textures.clear();
        textureBytes = 0;
        names[0] = whiteTexture;
        GLES20.glDeleteTextures(1, names, 0);
        GLES20.glDeleteProgram(program);
        whiteTexture = program = 0;
    }

    public int getMaxTextureSize() { return maxTextureSize; }
    public int getDrawCalls() { return drawCalls; }
    public int getTextureCount() { return textures.size(); }
    public long getTextureBytes() { return textureBytes; }

    private static int compile(int type, String source) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, source);
        GLES20.glCompileShader(shader);
        int[] status = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, status, 0);
        if (status[0] == 0) {
            String log = GLES20.glGetShaderInfoLog(shader);
            GLES20.glDeleteShader(shader);
            throw new IllegalStateException("OpenGL shader: " + log);
        }
        return shader;
    }
}
