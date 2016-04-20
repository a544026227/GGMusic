package gl.com.ggmusic;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import gl.com.ggmusic.constants.Constants;
import gl.com.ggmusic.music.MusicData;
import gl.com.ggmusic.service.PlayMusicService;
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

        Constants.screenWidth = ScreenUtils.getScreenWidth(this);//初始化计算高度
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
