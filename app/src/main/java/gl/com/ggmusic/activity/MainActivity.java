package gl.com.ggmusic.activity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gl.com.ggmusic.R;
import gl.com.ggmusic.activity.main.MainDiscoverView;
import gl.com.ggmusic.activity.main.MainFriendsView;
import gl.com.ggmusic.activity.main.MainMusicView;
import gl.com.ggmusic.adapter.CommonUseViewPagerAdapter;
import gl.com.ggmusic.constants.Constants;
import gl.com.ggmusic.presenter.MainPresenter;
import gl.com.ggmusic.view.IMainActivity;
import gl.com.ggmusic.widget.BottomMusicView;


/**
 * Created by guilinlin on 16/4/13.<p/>
 * 注意事项:
 * 1.构造方法中需调用setContentView()；
 */
public class MainActivity extends BaseActivity implements IMainActivity,View.OnClickListener {


    /**
     * 菜单图标
     */
    private ImageView menuImageView;
    /**
     * 发现页图标
     */
    private ImageView discoverImageView;
    /**
     * 我的音乐图标
     */
    private ImageView musicImageView;
    /**
     * 朋友图标
     */
    private ImageView friendsImageView;
    /**
     * 搜索图标
     */
    private ImageView searchImageView;
    /**
     * 首页的ViewPager
     */
    private android.support.v4.view.ViewPager contentViewPager;
    /**
     * 官方提供的侧滑菜单
     */
    private DrawerLayout drawerLayout;
    /**
     * presenter
     */
    private MainPresenter mainPresenter;

    public MainActivity() {
        setContentView(R.layout.activity_main);
    }

    @Override
    void init() {

        //移除baseActivity的view,使用自定义的
        outmosterRelativeLayout.removeAllViews();
        setContentViewReal(R.layout.activity_main);

        makeMusicCacheDir();

        mainPresenter = new MainPresenter(this);


        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        this.contentViewPager = (ViewPager) findViewById(R.id.contentViewPager);
        this.searchImageView = (ImageView) findViewById(R.id.searchImageView);
        this.friendsImageView = (ImageView) findViewById(R.id.friendsImageView);
        this.musicImageView = (ImageView) findViewById(R.id.musicImageView);
        this.discoverImageView = (ImageView) findViewById(R.id.discoverImageView);
        this.menuImageView = (ImageView) findViewById(R.id.menuImageView);
        super.bottomMusicView = (BottomMusicView) findViewById(R.id.bottomMusicView);

    }

    @Override
    void initView() {
        bottomMusicView.update();

        initViewPager();

    }

    @Override
    void setListener() {

        this.searchImageView.setOnClickListener(this);
        this.friendsImageView.setOnClickListener(this);
        this.musicImageView.setOnClickListener(this);
        this.discoverImageView.setOnClickListener(this);
        this.menuImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.menuImageView:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.discoverImageView:
                showViewPager(0);
                break;
            case R.id.musicImageView:
                showViewPager(1);
                break;
            case R.id.friendsImageView:
                showViewPager(2);
                break;
            case R.id.searchImageView:
                startActivity(SearchActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化首页3个ViewPager
     */
    @Override
    public void initViewPager() {
        List<View> list = new ArrayList<>();
        list.add(new MainDiscoverView(context));
        list.add(new MainMusicView(context));
        list.add(new MainFriendsView(context));
        CommonUseViewPagerAdapter adapter = new CommonUseViewPagerAdapter(list);
        contentViewPager.setAdapter(adapter);
        contentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                showViewPager(position);
            }

            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showViewPager(int position) {
        contentViewPager.setCurrentItem(position);
        if (position == 0) {
            discoverImageView.setImageResource(R.mipmap.actionbar_discover_selected);
            musicImageView.setImageResource(R.mipmap.actionbar_music_prs);
            friendsImageView.setImageResource(R.mipmap.actionbar_friends_prs);
        } else if (position == 1) {
            discoverImageView.setImageResource(R.mipmap.actionbar_discover_prs);
            musicImageView.setImageResource(R.mipmap.actionbar_music_selected);
            friendsImageView.setImageResource(R.mipmap.actionbar_friends_prs);
        } else if (position == 2) {
            discoverImageView.setImageResource(R.mipmap.actionbar_discover_prs);
            musicImageView.setImageResource(R.mipmap.actionbar_music_prs);
            friendsImageView.setImageResource(R.mipmap.actionbar_friends_selected);
        }

    }

    @Override
    public void makeMusicCacheDir() {
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            mkDir();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mkDir();
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                showToast("拒绝文件读写权限音乐将无法下载！");
            }
        }
    }

    private void mkDir() {
        File dir = new File(Constants.DOWNLOAD_PATH);
        if (!dir.exists()) {
            System.out.println(dir.mkdirs());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
