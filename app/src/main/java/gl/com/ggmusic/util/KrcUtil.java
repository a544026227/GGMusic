package gl.com.ggmusic.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

public class KrcUtil {
    private static final char[] miarry = {'@', 'G', 'a', 'w', '^', '2', 't',
            'G', 'Q', '6', '1', '-', 'Î', 'Ò', 'n', 'i'};

    public static String convt(InputStream is) {
        try {
            byte[] head = new byte[4];

            is.read(head);


            byte[] zip_byte = toByteArray(is);

            int j = zip_byte.length;
            for (int k = 0; k < j; k++) {
                int l = k % 16;
                int tmp67_65 = k;
                byte[] tmp67_64 = zip_byte;
                tmp67_64[tmp67_65] = ((byte) (tmp67_64[tmp67_65] ^ miarry[l]));
            }
            String lrc_str;
            lrc_str = new String(decompress(zip_byte));
            String final_lrc = lrc_str.replaceAll("<([^>]*)>", "").replaceAll(
                    ",([^]]*)]", "] ");

            Pattern p = Pattern.compile("\\[\\d+?\\]");
            Matcher m = p.matcher(final_lrc);
            while (m.find()) {
                final_lrc = m.replaceFirst(toTime(m.group()));
                m = p.matcher(final_lrc);
            }
            System.out.println(final_lrc);
            return final_lrc;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    private static String toTime(String num) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        long time = Long.parseLong(num.substring(1, num.length() - 1));
        return "[" + sdf.format(Long.valueOf(time)) + "." + time % 1000L / 10L + "]";
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

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

}