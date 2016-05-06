package gl.com.ggmusic.music;

import android.media.MediaPlayer;
import android.os.Handler;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by guilinlin on 16/4/19 17:46.
 * desc:轮询更新播放进度,更新MusicData中的播放进度
 */
public class UpdateProcessTask implements Runnable {

    private MediaPlayer mediaPlayer;
    private Handler handler;

    public UpdateProcessTask(MediaPlayer mediaPlayer, Handler handler) {
        this.mediaPlayer = mediaPlayer;
        this.handler = handler;
    }

    @Override
    public void run() {
        MusicData musicData = MusicData.getInstance();
        float p = mediaPlayer.getCurrentPosition() / (float) MusicData.getInstance().getTotalSize();
        musicData.setPercent(p);
        EventBus.getDefault().post(musicData);
        if (musicData.isPlaying()) {//如果正在播放，才执行轮询操作
            handler.postDelayed(this, 200);
        }

    }
}
