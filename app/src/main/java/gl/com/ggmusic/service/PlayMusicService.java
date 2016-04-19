package gl.com.ggmusic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import gl.com.ggmusic.bean.MusicData;
import gl.com.ggmusic.music.PlayMusicTool;

public class PlayMusicService extends Service {


    public static final String TAG_START_MUSIC = "tag_start_music";

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
        MusicData musicData = intent.getParcelableExtra(TAG_START_MUSIC);
        playMusicTool.play(musicData);//音乐工具类播放音乐
        EventBus.getDefault().post(musicData);//通知底部音乐控件更新状态
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


        public PlayMusicService getService() {
            return PlayMusicService.this;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 开启并绑定播放音乐的Servie,同时向Servie发送一个请求，播放音乐
     */
    public static void startService(Context context , MusicData musicData) {

        Intent service = new Intent(context.getApplicationContext(), PlayMusicService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(PlayMusicService.TAG_START_MUSIC, musicData);
        service.putExtras(bundle);
        context.getApplicationContext().startService(service);

    }

}
