package gl.com.ggmusic.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import gl.com.ggmusic.bean.MusicData;
import gl.com.ggmusic.music.PlayMusicTool;
import gl.com.ggmusic.widget.BottomMusicView;

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
        MusicData musicData = intent.getParcelableExtra(BottomMusicView.TAG_START_MUSIC);
        playMusicTool.play(musicData);
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

}
