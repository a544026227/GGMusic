package gl.com.ggmusic.music;

/**
 * Created by guilinlin on 16/4/18 23:17.
 * dese:该类是一个全局单例
 */
public class MusicData  {

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
     * 播放音乐的状态,暂停播放还是停止什么的
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
    private String  songName ="网易云音乐";
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
    private boolean isPlaying ;

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

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
