package gl.com.ggmusic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import gl.com.ggmusic.music.MusicData;
import gl.com.ggmusic.music.PlayMusicTool;

public class PlayMusicService extends Service {

    private MusicBinder musicBinder;
    private PlayMusicTool playMusicTool;

    @Override
    public void onCreate() {
        super.onCreate();
        musicBinder = new MusicBinder();
        playMusicTool = new PlayMusicTool();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        playMusicTool.play(MusicData.getInstance());//音乐工具类播放音乐
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 绑定service的操作
     *
     * @param intent
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    /**
     * 绑定Service成功后将当前Service返回，便于进行交互
     */
    public class MusicBinder extends Binder {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 开启并绑定播放音乐的Servie,同时向Servie发送一个请求，播放音乐
     */
    public static void startService(Context context ) {
        Intent service = new Intent(context.getApplicationContext(), PlayMusicService.class);
        context.getApplicationContext().startService(service);
    }

}
