package PyxEditor;

import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;
import com.sega.mobile.framework.device.MFDevice;

public class PyxWriter {

    /**
     * Lê JSON de um recurso via MFDevice.getResourceAsStream(fileName)
     * Converte para ByteArrayInputStream no formato PYX.
     */
    public static ByteArrayInputStream jsonToPyx(InputStream is) throws IOException {
        // Abre JSON via recurso
        try {
        if (is == null) {
            throw new FileNotFoundException("Resource not found");
        }

        // Lê tudo
        String jsonText = readAll(is);
        JSONObject json = new JSONObject(jsonText);

        // Constrói PYX binário
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);

        JSONArray nodes = json.getJSONArray("nodes");
        out.writeByte(nodes.length());
        for (int i = 0; i < nodes.length(); i++) {
            writeNode(nodes.getJSONObject(i), out);
        }

        out.writeByte(json.getInt("root_node_id"));
        out.writeByte(json.getInt("root_point_id"));

        JSONArray actions = json.getJSONArray("actions");
        out.writeByte(actions.length());
        for (int i = 0; i < actions.length(); i++) {
            writeAction(actions.getJSONObject(i), out);
        }

        out.flush();
        return new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
        return null;
        }
    }

    private static void writeNode(JSONObject node, DataOutputStream out) throws IOException {
        // 4 shorts reservados
        try {
        for (int i = 0; i < 4; i++) {
            out.writeShort(0);
        }

        JSONArray cps = node.getJSONArray("connect_points");
        out.writeByte(cps.length());
        for (int i = 0; i < cps.length(); i++) {
            JSONObject cp = cps.getJSONObject(i);
            out.writeShort(cp.getInt("x"));
            out.writeShort(cp.getInt("y"));
            out.writeByte(cp.getInt("link_node_id"));
            out.writeByte(cp.getInt("link_point_id"));
        }

        writeString(node.getString("label"), out);
        out.writeByte(node.getInt("animation_id"));
        out.writeByte(node.getInt("action_id"));
        out.writeByte(node.getInt("animation_x"));
        out.writeByte(node.getInt("animation_y"));
        } catch (Exception e) {
        }
    }

    private static void writeAction(JSONObject action, DataOutputStream out) throws IOException {
        try {
        JSONArray tracks = action.getJSONArray("tracks");
        out.writeByte(tracks.length());
        for (int i = 0; i < tracks.length(); i++) {
            JSONObject track = tracks.getJSONObject(i);
            out.writeByte(track.getInt("node_id"));

            JSONArray keyframes = track.getJSONArray("keyframes");
            out.writeByte(keyframes.length());
            for (int k = 0; k < keyframes.length(); k++) {
                JSONObject kf = keyframes.getJSONObject(k);
                out.writeShort(kf.getInt("frame_position"));
                out.writeShort(kf.getInt("degree"));
            }
        }
        writeString(action.getString("label"), out);
        out.writeShort(action.getInt("time_limit"));
        } catch (Exception e) {
        }
    }

    private static void writeString(String s, DataOutputStream out) throws IOException {
        try {
        byte[] utf8 = s.getBytes("UTF-8");
        if (utf8.length > 65535) throw new IllegalArgumentException("String too long");
        out.writeShort(utf8.length);
        out.write(utf8);
        } catch (Exception e) {
        }
    }

    private static String readAll(InputStream is) throws IOException {
        try {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int len;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        return baos.toString("UTF-8");
        } catch (Exception e) {
        return "";
        }
    }
}