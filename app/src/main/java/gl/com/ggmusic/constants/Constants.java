package gl.com.ggmusic.constants;

/**
 * Created by guilinlin on 16/4/16 22:53.
 * email 973635949@qq.com<br/>
 * desc： 常量全部放在这儿
 */
public class Constants {

    private static Constants constants;

    public static int screenWidth = 0;

    public static final String TEST_GET_URL = "http://app-api-test.51youdian.com:8081/saledianMerchant/storeManager/updateCashierRealName";

    public static final String DOWNLOAD_PATH = "GGMusicCache";
    public static final String SIMPLE_MUSIC = "http://7xq4kg.com1.z0.glb.clouddn.com/test.mp3";
    public static final String SIMPLE_MUSIC2 = "http://7xq4kg.com1.z0.glb.clouddn" +
            ".com/%E6%9D%8E%E8%8D%A3%E6%B5%A9%20-%20%E8%80%81%E8%A1%97.mp3";


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
