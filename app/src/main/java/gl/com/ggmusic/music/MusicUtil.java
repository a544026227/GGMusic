package gl.com.ggmusic.music;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import gl.com.ggmusic.bean.KugouImageHeadJson;
import gl.com.ggmusic.constants.URL;
import gl.com.ggmusic.network.GGHttp;
import gl.com.ggmusic.util.MyUtil;
import rx.functions.Action1;

/**
 * Created by guilinlin on 16/5/6 15:44.
 * email 973635949@qq.com
 */
public class MusicUtil {

    /**
     * 播放音乐工具类，只要播放音乐都要用这个！！！
     *
     * @param url
     * @param singer
     * @param songName
     * @param durtion
     * @param hash
     * @param context
     */
    public static void playMusic(String url, String singer, String songName,
                                 int durtion, String hash, Context context) {
        final MusicData musicData = MusicData.getInstance();
        musicData.setFlag(MusicData.START);
        musicData.setUrl(url);
        musicData.setSinger(singer);
        musicData.setSongName(songName);
        musicData.setDurtion(durtion);
        musicData.setHash(hash);
        musicData.setDownloaded(false);
        PlayMusicService.startService(context);

        getSongLogo(singer, musicData);
    }

    /**
     * 获取歌手头像，获取成功后发消息更新底部音乐栏
     *
     * @param singer
     * @param musicData
     */
    private static void getSongLogo(String singer, final MusicData musicData) {
        GGHttp<KugouImageHeadJson> ggHttpInfo = new GGHttp<>(URL.KUGOU_HEAD_IMAGE, KugouImageHeadJson.class);
        ggHttpInfo.setMethodType("GET");
        ggHttpInfo.add("size", "240");
        ggHttpInfo.add("cmd", "104");
        ggHttpInfo.add("type", "softhead");
        ggHttpInfo.add("singer", MyUtil.getURLEncoder(singer));
        ggHttpInfo.send(new Action1<KugouImageHeadJson>() {
            @Override
            public void call(KugouImageHeadJson kugouImageHeadJson) {
                if (kugouImageHeadJson.getStatus() == 1) {
                    musicData.setSongLogo(kugouImageHeadJson.getUrl());
                    EventBus.getDefault().post(musicData);
                }else{
                    musicData.setSongLogo("");
                    EventBus.getDefault().post(musicData);
                }
            }
        });
    }

    /**
     * 根据传入的秒返回01：22格式的字符串
     */
    public static final String getTimeBySecond(int time) {
        int minute = time / 60;
        int second = time % 60;
        StringBuffer sb = new StringBuffer();
        if (minute < 10) {
            sb.append('0');
        }
        sb.append(minute).append(':');
        if (second < 10) {
            sb.append('0');
        }
        sb.append(second);
        return sb.toString();
    }

}
