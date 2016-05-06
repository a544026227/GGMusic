package gl.com.ggmusic.constants;

import android.os.Environment;

import gl.com.ggmusic.R;

/**
 * Created by guilinlin on 16/4/16 22:53.
 * email 973635949@qq.com<br/>
 * desc： 常量全部放在这儿
 */
public class Constants {

    private static Constants constants;

    public static int screenWidth = 0;
    public static int statusHeight = 0;
    public static int navigationBarheight = 0;


    public static final String DOWNLOAD_PATH =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                    + "/GGMusic/download/";

    /**
     * 默认加载期间显示的图片
     */
    public static final int LOADING_IMAGE = R.drawable.icon_no_data;

    /**
     * 加载图片错误显示的图片
     */
    public static final int ERR_IMAGE = R.drawable.icon_no_data;

    private Constants() {

    }

    public static Constants getInstance() {
        if (constants == null) {
            synchronized (Constants.class) {
                if (constants == null) {
                    constants = new Constants();
                }
            }
        }
        return constants;
    }

}
