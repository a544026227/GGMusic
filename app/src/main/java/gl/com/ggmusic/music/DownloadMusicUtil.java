package gl.com.ggmusic.music;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gl.com.ggmusic.bean.EventBusMusicInfoDownLoad;
import gl.com.ggmusic.network.DownloadUtil;

/**
 * 下载歌曲的操作，该类是单例模式，并且只能同时下载一首歌曲
 * Created by guilinlin on 16/5/7 18:10.
 */
public class DownloadMusicUtil {

    private static DownloadMusicUtil downloadMusicUtil;

    /**
     * 单一线程池，歌曲只能同时下载一首
     */
    private ExecutorService singleService;

    public DownloadMusicUtil() {
        singleService = Executors.newSingleThreadExecutor();
    }

    public static DownloadMusicUtil getInstance() {
        if (downloadMusicUtil == null) {
            synchronized (DownloadMusicUtil.class) {
                if (downloadMusicUtil == null) {
                    downloadMusicUtil = new DownloadMusicUtil();
                }
            }
        }
        return downloadMusicUtil;
    }

    /**
     * 开始下载歌曲
     *
     * @param musicUrl
     *         歌曲地址
     * @param fileName
     *         下载的文件名
     */
    public void start(final String musicUrl, final String fileName) {
        singleService.execute(new Runnable() {
            @Override
            public void run() {
                DownloadUtil util = new DownloadUtil(musicUrl, fileName, "mp3");
                util.start(new DownloadUtil.DownloadListener() {
                    @Override
                    public void process(int hasDownloadSize, int totalSize, float percent) {
                        if(percent>=1){//下载完成
                            MusicData.getInstance().setDownloaded(true);
                            EventBus.getDefault().post(new EventBusMusicInfoDownLoad());
                        }
                    }
                });
            }
        });
    }

}
