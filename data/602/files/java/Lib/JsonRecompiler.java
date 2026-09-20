package Lib;

import java.io.*;
import java.util.*;
import org.json.*;
import com.sega.mobile.framework.device.MFDevice;

public final class JsonRecompiler {

    private static void writeByte(DataOutputStream out, int val) throws IOException {
        out.writeByte(val);
    }

    private static void writeUByte(DataOutputStream out, int val) throws IOException {
        //out.writeByte(val & 0xFF);
        if (val < 0 || val > 255) throw new IllegalArgumentException("UByte inválido: " + val);
            out.write(val);
    }
    
    private static void writeUByte2(DataOutputStream out, int val) throws IOException {
        out.write(val & 0xFF);
    }

    private static void writeShort(DataOutputStream out, int val) throws IOException {
        out.writeShort(val);
    }

    private static void writeShortReverse(DataOutputStream out, int value) throws IOException {
        out.writeShort(Short.reverseBytes((short) value));
    }

    private static void writeUShort(DataOutputStream out, int val) throws IOException {
        out.writeShort(val & 0xFFFF);
    }

    private static void writeInt(DataOutputStream out, int val) throws IOException {
        out.writeInt(val);
    }

    private static void writeUTF(DataOutputStream out, String s) throws IOException {
        byte[] data = s.getBytes("UTF-8");
        writeUShort(out, data.length);
        out.write(data);
    }

    public static InputStream recompileJson(InputStream jsonIn) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] by = new byte[1024];
        int nRead;
        while ((nRead = jsonIn.read(by, 0, by.length)) != -1) {
            buffer.write(by, 0, nRead);
        }
        buffer.flush();
        String jsonText = buffer.toString("UTF-8");
        JSONObject data = new JSONObject(jsonText);

        if (data.has("animations") && data.has("images")) {
            compile_qi_dat(data, out);
        } else if (data.has("frames") && data.has("actions")) {
            compile_old_animation(data, out);
        } else if (data.has("objects")) {
            compile_animationdata_dat(data, out);
        } else {
            throw new IllegalArgumentException("Unrecognized JSON format.");
        }

        out.flush();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    private static void writeShort2(DataOutputStream out, int value) throws IOException {
        out.writeByte(value & 0xFF);
        out.writeByte((value >> 8) & 0xFF);
    }

private static void compile_old_animation(JSONObject data, DataOutputStream out) throws IOException, JSONException {
    JSONArray clips = data.getJSONArray("images").getJSONObject(0).getJSONArray("clips");
    writeUByte(out, clips.length());
    for (int i = 0; i < clips.length(); i++) {
        JSONObject clip = clips.getJSONObject(i);
        writeShort2(out, clip.getInt("x"));
        writeShort2(out, clip.getInt("y"));
        writeShort2(out, clip.getInt("w"));
        writeShort2(out, clip.getInt("h"));
    }

    JSONArray frames = data.getJSONArray("frames");
    writeUByte(out, frames.length());
    for (int i = 0; i < frames.length(); i++) {
        JSONObject frame = frames.getJSONObject(i);

        JSONObject rect1 = frame.getJSONObject("rect1");
        for (int j = 0; j < 4; j++) {
            writeUByte(out, rect1.getInt("b" + j));
        }

        JSONObject rect2 = frame.getJSONObject("rect2");
        for (int j = 0; j < 4; j++) {
            writeUByte(out, rect2.getInt("b" + j));
        }

        JSONArray frameClips = frame.getJSONArray("clips");
        writeUByte(out, frameClips.length());
        for (int j = 0; j < frameClips.length(); j++) {
            JSONObject c = frameClips.getJSONObject(j);
            writeByte(out, c.getInt("x"));
            writeByte(out, c.getInt("y"));
            writeByte(out, c.getInt("id"));
            writeByte(out, c.getInt("unknown"));
        }
    }

    JSONArray actions = data.getJSONArray("actions");
    writeUByte(out, actions.length());
    for (int i = 0; i < actions.length(); i++) {
        JSONArray action = actions.getJSONArray(i);
        writeUByte(out, action.length());
        for (int j = 0; j < action.length(); j++) {
            JSONObject f = action.getJSONObject(j);
            writeUByte(out, f.getInt("frame_id"));
            writeUByte(out, f.getInt("duration"));
        }
    }
}

    private static void compile_qi_dat(JSONObject data, DataOutputStream out) throws IOException, JSONException {
        JSONArray animations = data.getJSONArray("animations");
        writeUByte(out, animations.length());

        for (int i = 0; i < animations.length(); i++) {
            JSONObject anim = animations.getJSONObject(i);
            writeInt(out, anim.getInt("metadata_int"));

            JSONArray frames = anim.getJSONArray("frames");
            writeUByte(out, frames.length());
            for (int j = 0; j < frames.length(); j++) {
                JSONObject frame = frames.getJSONObject(j);

                JSONArray clips = frame.getJSONArray("clips");
                writeUByte(out, clips.length());
                for (int k = 0; k < clips.length(); k++) {
                    JSONObject clip = clips.getJSONObject(k);
                    int fid = clip.getInt("function_id");
                    writeUByte(out, fid);

                    if (fid == 0) {
                        writeUByte(out, clip.getInt("clip_flags"));
                        writeUByte(out, clip.getInt("sheet_index"));
                        writeUByte(out, clip.getInt("offset_x") >> 4);
                        writeShort(out, clip.getInt("pos_x"));
                        writeShort(out, clip.getInt("pos_y"));
                    } else if (fid == 1 || fid == 2) {
                        writeShort(out, clip.getInt("pos_x"));
                        writeShort(out, clip.getInt("pos_y"));
                        JSONObject color = clip.getJSONObject("color");
                        writeUByte(out, color.getInt("r"));
                        writeUByte(out, color.getInt("g"));
                        writeUByte(out, color.getInt("b"));
                        writeShort(out, clip.getInt("width"));
                        writeShort(out, clip.getInt("height"));
                    } else if (fid == 6) {
                        writeUByte(out, clip.getInt("extra_flag"));
                        writeUByte(out, clip.getInt("extra_val"));
                        writeShort(out, clip.getInt("pos_x"));
                        writeShort(out, clip.getInt("pos_y"));
                    }
                }

                JSONArray rects = frame.getJSONArray("rectarrays");
                writeUByte(out, rects.length());
                for (int r = 0; r < rects.length(); r++) {
                    JSONArray inner = rects.getJSONArray(r);
                    writeUByte(out, inner.length());
                    for (int s = 0; s < inner.length(); s++) {
                        JSONObject rect = inner.getJSONObject(s);
                        writeShort(out, rect.getInt("x"));
                        writeShort(out, rect.getInt("y"));
                        writeShort(out, rect.getInt("w"));
                        writeShort(out, rect.getInt("h"));
                    }
                }

                JSONArray crosses = frame.getJSONArray("crossarrays");
                writeUByte(out, crosses.length());
                for (int c = 0; c < crosses.length(); c++) {
                    JSONArray inner = crosses.getJSONArray(c);
                    writeUByte(out, inner.length());
                    for (int d = 0; d < inner.length(); d++) {
                        JSONObject cross = inner.getJSONObject(d);
                        writeShort(out, cross.getInt("x"));
                        writeShort(out, cross.getInt("y"));
                    }
                }
            }

            JSONArray actions = anim.getJSONArray("actions");
            writeUByte(out, actions.length());
            for (int a = 0; a < actions.length(); a++) {
                JSONArray acts = actions.getJSONArray(a);
                writeUByte(out, acts.length());
                for (int b = 0; b < acts.length(); b++) {
                    JSONObject act = acts.getJSONObject(b);
                    writeUByte(out, act.getInt("frame_id"));
                    writeUByte(out, act.getInt("duration"));
                    writeShort(out, act.getInt("pos_x"));
                    writeShort(out, act.getInt("pos_y"));
                }
            }
        }

        JSONArray images = data.getJSONArray("images");
        writeUByte(out, images.length());
        for (int i = 0; i < images.length(); i++) {
            JSONArray clips = images.getJSONObject(i).getJSONArray("clips");
            writeUByte(out, clips.length());
            for (int j = 0; j < clips.length(); j++) {
                JSONObject clip = clips.getJSONObject(j);
                writeShort(out, clip.getInt("x"));
                writeShort(out, clip.getInt("y"));
                writeShort(out, clip.getInt("w"));
                writeShort(out, clip.getInt("h"));
            }
        }

        for (int i = 0; i < images.length(); i++) {
            writeUTF(out, images.getJSONObject(i).getString("full_path"));
        }

        out.writeByte(0);
    }

    private static void compile_animationdata_dat(JSONObject data, DataOutputStream out) throws IOException, JSONException {
        JSONArray objects = data.getJSONArray("objects");
        writeUByte(out, objects.length());

        for (int i = 0; i < objects.length(); i++) {
            JSONObject obj = objects.getJSONObject(i);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            DataOutputStream temp = new DataOutputStream(buffer);

            JSONArray frames = obj.getJSONArray("frames");
            writeUByte(temp, frames.length());
            for (int j = 0; j < frames.length(); j++) {
                JSONObject frame = frames.getJSONObject(j);
                JSONArray layers = frame.getJSONArray("layers");
                writeUByte(temp, layers.length());

                for (int k = 0; k < layers.length(); k++) {
                    JSONObject layer = layers.getJSONObject(k);
                    int tid = layer.getInt("type");
                    writeByte(temp, tid);

                    switch (tid) {
                        case 0:
                            writeUByte(temp, layer.getInt("flags"));
                            writeUByte(temp, layer.getInt("sheet_index"));
                            writeUByte(temp, layer.getInt("offset_x") >> 4);
                            writeShort(temp, layer.getInt("x"));
                            writeShort(temp, layer.getInt("y"));
                            break;
                        case 1:
                        case 2:
                            JSONObject color = layer.getJSONObject("color");
                            writeUByte(temp, color.getInt("r"));
                            writeUByte(temp, color.getInt("g"));
                            writeUByte(temp, color.getInt("b"));
                            writeShort(temp, layer.getInt("flag0"));
                            writeShort(temp, layer.getInt("flag1"));
                            writeShort(temp, layer.getInt("flag2"));
                            writeShort(temp, layer.getInt("flag3"));
                            break;
                        case 3:
                            writeByte(temp, layer.getInt("flags"));
                            writeShort(temp, layer.getInt("x"));
                            writeShort(temp, layer.getInt("y"));
                            break;
                        case 4:
                            writeShort(temp, layer.getInt("x"));
                            writeShort(temp, layer.getInt("y"));
                            writeShort(temp, layer.getInt("w"));
                            writeShort(temp, layer.getInt("h"));
                            break;
                        case 5:
                            writeShort(temp, layer.getInt("x"));
                            writeShort(temp, layer.getInt("y"));
                            break;
                        case 6:
                            writeUByte(temp, layer.getInt("param1"));
                            writeUByte(temp, layer.getInt("param2"));
                            writeShort(temp, layer.getInt("x"));
                            writeShort(temp, layer.getInt("y"));
                            break;
                    }
                }

                JSONArray rects = frame.getJSONArray("rectarrays");
                writeUByte(temp, rects.length());
                for (int r = 0; r < rects.length(); r++) {
                    JSONArray inner = rects.getJSONArray(r);
                    writeUByte(temp, inner.length());
                    for (int s = 0; s < inner.length(); s++) {
                        JSONObject rect = inner.getJSONObject(s);
                        writeShort2(temp, rect.getInt("x"));
                        writeShort2(temp, rect.getInt("y"));
                        writeShort2(temp, rect.getInt("w"));
                        writeShort2(temp, rect.getInt("h"));
                    }
                }

                JSONArray crosses = frame.getJSONArray("crossarrays");
                writeUByte(temp, crosses.length());
                for (int c = 0; c < crosses.length(); c++) {
                    JSONArray inner = crosses.getJSONArray(c);
                    writeUByte(temp, inner.length());
                    for (int d = 0; d < inner.length(); d++) {
                        JSONObject cross = inner.getJSONObject(d);
                        writeShort(temp, cross.getInt("x"));
                        writeShort(temp, cross.getInt("y"));
                    }
                }
            }

            JSONArray actions = obj.getJSONArray("actions");
            writeUByte(temp, actions.length());
            for (int a = 0; a < actions.length(); a++) {
                JSONArray acts = actions.getJSONArray(a);
                writeUByte(temp, acts.length());
                for (int b = 0; b < acts.length(); b++) {
                    JSONObject p = acts.getJSONObject(b);
                    writeUByte(temp, p.getInt("frame_id"));
                    writeUByte(temp, p.getInt("duration"));
                    writeShort(temp, p.getInt("x"));
                    writeShort(temp, p.getInt("y"));
                }
            }

            temp.flush();
            byte[] bufferBytes = buffer.toByteArray();
            writeInt(out, bufferBytes.length);
            out.write(bufferBytes);
        }

        JSONArray images = data.getJSONArray("images");
        writeUByte(out, images.length());
        for (int i = 0; i < images.length(); i++) {
            JSONObject image = images.getJSONObject(i);
            JSONArray clips = image.getJSONArray("clips");
            writeUByte(out, clips.length());
            for (int j = 0; j < clips.length(); j++) {
                JSONObject clip = clips.getJSONObject(j);
                writeShort(out, clip.getInt("x"));
                writeShort(out, clip.getInt("y"));
                writeShort(out, clip.getInt("w"));
                writeShort(out, clip.getInt("h"));
            }
            writeUTF(out, image.getString("name"));
        }

        JSONArray strings = data.getJSONArray("strings");
        writeUByte(out, strings.length());
        for (int i = 0; i < strings.length(); i++) {
            JSONArray group = strings.getJSONArray(i);
            writeUByte(out, group.length());
            for (int j = 0; j < group.length(); j++) {
                writeUTF(out, group.getString(j));
            }
        }

        out.writeByte(0);
    }
}