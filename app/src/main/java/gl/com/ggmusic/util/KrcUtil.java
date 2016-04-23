package gl.com.ggmusic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KrcUtil {
    private static final char[] miarry = {'@', 'G', 'a', 'w', '^', '2', 't',
            'G', 'Q', '6', '1', '-', 'Î', 'Ò', 'n', 'i'};

    public String convt(String filenm)
            throws IOException {
        File krcfile = new File(filenm);
        byte[] zip_byte = new byte[(int) krcfile.length()];
        FileInputStream fileinstrm = new FileInputStream(krcfile);
        byte[] top = new byte[4];
        fileinstrm.read(top);
        fileinstrm.read(zip_byte);
        int j = zip_byte.length;
        for (int k = 0; k < j; k++) {
            int l = k % 16;
            int tmp67_65 = k;
            byte[] tmp67_64 = zip_byte;
            tmp67_64[tmp67_65] = ((byte) (tmp67_64[tmp67_65] ^ miarry[l]));
        }
        String lrc_str;
        try {
            lrc_str = new String(ZLibUtils.decompress(zip_byte), "utf-8");
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
            return "";
        }
        String final_lrc = lrc_str.replaceAll("<([^>]*)>", "").replaceAll(
                ",([^]]*)]", "] ");

        Pattern p = Pattern.compile("\\[\\d+?\\]");
        Matcher m = p.matcher(final_lrc);
        while (m.find()) {
            final_lrc = m.replaceFirst(toTime(m.group()));
            m = p.matcher(final_lrc);
        }

        return final_lrc;
    }

    private static String toTime(String num) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        long time = Long.parseLong(num.substring(1, num.length() - 1));
        return "[" + sdf.format(Long.valueOf(time)) + "." + time % 1000L / 10L + "]";
    }
}