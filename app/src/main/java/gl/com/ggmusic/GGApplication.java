package gl.com.ggmusic;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import gl.com.ggmusic.constants.Constants;
import gl.com.ggmusic.music.MusicData;
import gl.com.ggmusic.music.PlayMusicService;
import gl.com.ggmusic.util.ScreenUtils;

/**
 * Created by guilinlin on 16/4/19 17:57.
 * desc:
 */
public class GGApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        bindService();

        measureScreen();

        initImageLoader();



    }

    public void initImageLoader(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }

    /**
     * 计算一些屏幕尺寸
     */
    private void measureScreen() {
        Constants.screenWidth = ScreenUtils.getScreenWidth(this);//初始化计算高度
        Constants.statusHeight = ScreenUtils.getStatusHeight(this);//初始化状态栏高度
        Constants.navigationBarheight = ScreenUtils.getNavigationBarHeight(this);//初始化navigation高度
    }


    /**
     * 开启并绑定播放音乐的Servie,同时向Servie发送一个请求，初始化服务
     */
    private void bindService() {
        //如果音乐正在播放，告诉Servie暂停，如果没播放，告诉Servie播放
        MusicData musicData = MusicData.getInstance();
        musicData.setFlag(MusicData.INIT);

        Intent service = new Intent(this, PlayMusicService.class);
        //同时start和bindService，不然后台音乐会自动退出
        startService(service);
        bindService(service, connection, Activity.BIND_AUTO_CREATE);

    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
