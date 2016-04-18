package gl.com.ggmusic.util;

import android.content.Context;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by guilinlin on 16/4/14.
 */
public class MyUtil {

    public static void print(Object... objectses) {
        String str = "";
        for (Object o : objectses) {
            str += o + ",";
        }
        System.out.println(str);
    }

    /**
     * 快速显示一个日志
     *
     * @param context
     * @param msg
     */
    private static Toast toast;

    public static void t(Context context, Object msg) {
        if (toast == null) {
            toast = Toast.makeText
                    (context.getApplicationContext(), msg.toString(), Toast.LENGTH_LONG);
        } else {
            toast.setText(msg.toString());
        }

        toast.show();
    }



    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F'};

    public static String getMD5(String inStr) {
        byte[] inStrBytes = inStr.getBytes();
        try {
            MessageDigest MD = MessageDigest.getInstance("MD5");
            MD.update(inStrBytes);
            byte[] mdByte = MD.digest();
            char[] str = new char[mdByte.length * 2];
            int k = 0;
            for (int i = 0; i < mdByte.length; i++) {
                byte temp = mdByte[i];
                str[k++] = hexDigits[temp >>> 4 & 0xf];
                str[k++] = hexDigits[temp & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
