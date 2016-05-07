package gl.com.ggmusic.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import gl.com.ggmusic.constants.Constants;

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
                    (context.getApplicationContext(), msg.toString(), Toast.LENGTH_SHORT);
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
            for (byte temp : mdByte) {
                str[k++] = hexDigits[temp >>> 4 & 0xf];
                str[k++] = hexDigits[temp & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串转为utf-8编码
     *
     * @param str
     */
    public static String getURLEncoder(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "getURLEncoder_error";
        }
    }

    /**
     * 设置加载图片时的默认图片
     *
     * @param loadingRes
     *         下载期间显示的图片
     * @param errRes
     *         图片加载错误显示的图片,传空表示使用默认的
     */
    public static DisplayImageOptions getImageLoaderOptions(@NonNull Integer loadingRes, Integer errRes) {
        return new DisplayImageOptions.Builder()
                // 设置图片下载期间显示的图片
                .showImageOnLoading(loadingRes)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(errRes == null ? Constants.ERR_IMAGE : errRes)
                .cacheInMemory(true) // default  设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // default  设置下载的图片是否缓存在SD卡中
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default 设置图片的解码类型
                .build();
    }
}
