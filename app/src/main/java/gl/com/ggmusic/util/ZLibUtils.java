package gl.com.ggmusic.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 *解压缩字符串的工具类
 */
public abstract class ZLibUtils {
    public static byte[] decompress(byte[] data) {
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();

        decompresser.reset();

        decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];

            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);

                o.write(buf, 0, i);
            }

            output = o.toByteArray();
        } catch (Exception e) {
            output = data;

            e.printStackTrace();
            try {
                o.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        decompresser.end();

        return output;
    }

    public static byte[] decompress(InputStream is) {
        InflaterInputStream iis = new InflaterInputStream(is);

        ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
        try {
            int i = 1024;

            byte[] buf = new byte[i];

            while ((i = iis.read(buf, 0, i)) > 0) {
                o.write(buf, 0, i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return o.toByteArray();
    }
}