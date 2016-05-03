package gl.com.ggmusic.view;

/**
 * Created by guilinlin on 16/5/3 21:21.
 */
public interface IMainActivity {

    void initViewPager();

    void showViewPager(int position);

    /**
     * 检查权限，生成缓存音乐的文件夹
     */
    void makeMusicCacheDir();
}
