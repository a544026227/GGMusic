package gl.com.ggmusic.music;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * Created by guilinlin on 16/4/16 19:07.
 * email 973635949@qq.com<br/>
 * desc：
 */
public class PlayMusicTool implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer
        .OnBufferingUpdateListener {


    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private UpdateProcessTask updateProcessTask;

    public PlayMusicTool() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);//设置准备完成的监听
        mediaPlayer.setOnBufferingUpdateListener(this);//获取网络音乐的监听
        mediaPlayer.setOnCompletionListener(this);
        updateProcessTask = new UpdateProcessTask(mediaPlayer, handler);
    }

    /**
     * 暴露一个播放音乐的方法，需要传入一个MusicData对象
     * 同时更新所有界面的底部
     *
     * @param musicData
     */
    public void play(MusicData musicData) {


        switch (musicData.getFlag()) {
            case MusicData.START:
                playUrlMusic(musicData.getUrl());
                musicData.setPlaying(true);
                updateProcessTask.run();
                break;

            case MusicData.PAUSE://停止播放音乐
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    musicData.setPlaying(false);
                }
                break;
            case MusicData.INIT:
                break;
            case MusicData.RESTART:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    musicData.setPlaying(true);
                }
                updateProcessTask.run();
                break;
            default:
                break;
        }
        EventBus.getDefault().post(MusicData.getInstance());//一旦播放音乐什么的通知所有界面改变
    }

    /**
     * 播放网络音乐
     *
     * @param url
     *         音乐网址，后缀必须为MP3
     */
    private void playUrlMusic(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        MusicData.getInstance().setPlaying(false);
        EventBus.getDefault().post(MusicData.getInstance());
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        System.out.println("onPrepared");
        mediaPlayer.start();
        MusicData.getInstance().setTotalSize(mediaPlayer.getDuration());
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        System.out.println("onBufferingUpdate:"+i);
    }


}
