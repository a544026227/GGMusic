package gl.com.ggmusic.bean;

/**
 * 通知音乐播放主界面更新
 * Created by guilinlin on 16/5/6 16:24.
 */
public class EventBusMusicInfo {

    /**
     * 音乐播放到的百分比位置
     */
    public float percent;

    public EventBusMusicInfo(float percent) {
        this.percent = percent;
    }
}
