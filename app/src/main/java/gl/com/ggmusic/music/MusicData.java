package gl.com.ggmusic.music;

import gl.com.ggmusic.util.MyUtil;

/**
 * Created by guilinlin on 16/4/18 23:17.
 * dese:该类是一个全局单例
 */
public class MusicData {

    /**
     * 暂停
     */
    public static final int PAUSE = 2;
    /**
     * 开始
     */
    public static final int START = 1;
    /**
     * 初始化
     */
    public static final int INIT = 3;
    /**
     * 播放之前的音乐
     */
    public static final int RESTART = 4;
    /**
     * 播放结束
     */
    public static final int END = 5;
    /**
     * 选择播放节点
     */
    public static final int SELECT = 6;


    /**
     * 这个状态很重要，直接觉得startMusicService进行什么操作,暂停播放还是停止什么的
     */
    private int flag;
    /**
     * 播放网址url
     */
    private String url;
    /**
     * 播放本地Url
     */
    private String localUrl;
    /**
     * 歌曲名称
     */
    private String songName = "网易云音乐";
    /**
     * 演唱者
     */
    private String singer;
    /**
     * 歌曲小图片
     */
    private String songLogo;
    /**
     * 记录播放状态，true表示正在播放
     */
    private boolean isPlaying;
    /**
     * 音乐播放的百分比
     */
    private float percent;
    /**
     * 音乐的尺寸
     */
    private int totalSize;
    /**
     * 歌曲时间
     */
    private int durtion = 0;
    /**
     * 歌曲hash值
     */
    private String hash ;
    /**
     * 歌曲缓存的进度
     */
    private int cachePercent;
    /**
     * 选择歌曲的时间百分比，比如总时间2：00，设置0.5，就表示跳转到1：00开始播放
     */
    private float selectTimePercent ;

    private static MusicData musicData;

    private MusicData() {
    }

    public static MusicData getInstance() {
        if (musicData == null) {
            synchronized (MusicData.class) {
                if (musicData == null) {
                    musicData = new MusicData();
                }
            }
        }
        return musicData;
    }

    public MusicData(String url) {
        this.url = url;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public float getSelectTimePercent() {
        return selectTimePercent;
    }

    public void setSelectTimePercent(float selectTimePercent) {
        this.selectTimePercent = selectTimePercent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getSongName() {
        return songName;
    }
    public String getSongNameEncoder() {
        return  MyUtil.getURLEncoder(songName);
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }


    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSongLogo() {
        return songLogo;
    }

    public void setSongLogo(String songLogo) {
        this.songLogo = songLogo;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getDurtion() {
        return durtion;
    }

    public void setDurtion(int durtion) {
        this.durtion = durtion;
    }

    public String getHash() {
        return hash;
    }

    public int getCachePercent() {
        return cachePercent;
    }

    public void setCachePercent(int cachePercent) {
        this.cachePercent = cachePercent;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
