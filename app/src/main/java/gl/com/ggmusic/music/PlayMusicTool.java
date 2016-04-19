package gl.com.ggmusic.music;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

import gl.com.ggmusic.bean.MusicData;

/**
 * Created by guilinlin on 16/4/16 19:07.
 * email 973635949@qq.com<br/>
 * desc：
 */
public class PlayMusicTool implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer
        .OnBufferingUpdateListener {


    private MediaPlayer mediaPlayer;

    /**
     * 这个值记录音乐是否已经播放了但是被暂停播放
     */
    private boolean isPaused = false;

    public PlayMusicTool() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);//设置准备完成的监听
        mediaPlayer.setOnBufferingUpdateListener(this);//获取网络音乐的监听

    }

    /**
     * 暴露一个播放音乐的方法，需要传入一个MusicData对象，
     * 由于告知播放什么音乐，以及进行什么操作
     *
     * @param musicData
     */
    public void play(MusicData musicData) {
        if (musicData.getStatus() == MusicData.START) {
            playUrlMusic(musicData.getUrl());
        } else if (musicData.getStatus() == MusicData.PAUSE) {//停止播放音乐
            if(mediaPlayer.isPlaying()){
                pause();
            }

        }
    }

    /**
     * 播放网络音乐
     *
     * @param url
     *         音乐网址，后缀必须为MP3
     */
    public void playUrlMusic(final String url) {
        if (isPaused) {//如果已暂停，重新播放
            mediaPlayer.start();
        } else {//否则准备并开始播放
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
    }

    public void pause() {
        isPaused = true;//标记已暂停
        mediaPlayer.pause();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        System.out.println("onPrepared");
        mediaPlayer.start();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        System.out.println(i);
    }
}
