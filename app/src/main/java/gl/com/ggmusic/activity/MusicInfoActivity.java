package gl.com.ggmusic.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import gl.com.ggmusic.R;
import gl.com.ggmusic.constants.Constants;
import gl.com.ggmusic.music.MusicData;
import gl.com.ggmusic.music.PlayMusicService;
import gl.com.ggmusic.presenter.MusicInfoPresenter;
import gl.com.ggmusic.util.BitmapUtil;
import gl.com.ggmusic.view.IMusicInfoActivity;
import gl.com.ggmusic.widget.CircleImageView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MusicInfoActivity extends BaseActivity implements IMusicInfoActivity, View.OnClickListener {

    /**
     * 临时存储歌词文件的名称
     */
    public String TEMPORARY_FILE_NAME = "temporary.krc";

    private MusicInfoPresenter presenter;
    private MusicData musicData;
    private android.widget.ImageView needleImageView;

    private Animation upAnimation;
    private Animation upAnimation_0ms;
    private Animation downAnimation;
    private Animation circulationAnimation;//循环转动的动画
    private Animation circulationAnimation2;//循环转动的动画
    private ImageView diskImageView;
    private ImageView circulationImageView;
    private ImageView prevImageView;
    private ImageView startImageView;
    private ImageView nextImageView;
    private ImageView menuImageView;
    private android.widget.RelativeLayout titleRelativeLayut;
    private View centerView;
    private gl.com.ggmusic.widget.CircleImageView songLogoImageView;

    private int currentRotation = 0;


    public MusicInfoActivity() {
        setContentView(R.layout.activity_music_info);
    }

    @Override
    void init() {

        presenter = new MusicInfoPresenter(this);

        this.needleImageView = (ImageView) findViewById(R.id.needleImageView);
        this.titleRelativeLayut = (RelativeLayout) findViewById(R.id.titleRelativeLayut);
        this.menuImageView = (ImageView) findViewById(R.id.menuImageView);
        this.nextImageView = (ImageView) findViewById(R.id.nextImageView);
        this.startImageView = (ImageView) findViewById(R.id.startImageView);
        this.prevImageView = (ImageView) findViewById(R.id.prevImageView);
        this.circulationImageView = (ImageView) findViewById(R.id.circulationImageView);
        this.diskImageView = (ImageView) findViewById(R.id.diskImageView);
        this.songLogoImageView = (CircleImageView) findViewById(R.id.songLogoImageView);

        initAnimation();
    }

    @Override
    void initView() {
        //初始化toolBar
        initToolBar(musicData.getSongName());
        toolbar.setSubtitle(musicData.getSinger());
        toolbar.inflateMenu(R.menu.menu_music_info_share);
        toolbar.setBackgroundColor(0x00000000);
        //当前界面无须显示bottomView
        bottomMusicView.setVisibility(View.GONE);

        //获取歌手图片
        presenter.setSingerHeadImage(musicData.getSinger());
        //获取歌词
        presenter.setMusicLrc(musicData);

        View view = new View(context);
        view.setBackgroundColor(0x88000000);
        view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        outmosterRelativeLayout.addView(view, 0);

        //设置上边距，显示状态栏
        outmosterRelativeLayout.setPadding
                (0, Constants.statusHeight, 0, Constants.navigationBarheight);

        songLogoImageView.startAnimation(circulationAnimation2);
        diskImageView.startAnimation(circulationAnimation);

        if (!musicData.isPlaying()) {//如果音乐是暂停状态
            diskImageView.clearAnimation();
            songLogoImageView.clearAnimation();
            needleImageView.startAnimation(upAnimation_0ms);//抬起播放杆
            startImageView.setImageResource(R.mipmap.play_fm_btn_play_prs);
        } else {
            startImageView.setImageResource(R.mipmap.play_fm_btn_pause_prs);
        }


    }

    @Override
    void setListener() {
        startImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startImageView:
                if (TextUtils.isEmpty(musicData.getUrl())) {
                    return;
                }
                if (musicData.isPlaying()) {//点击的时候正在播放
                    songLogoImageView.clearAnimation();
                    diskImageView.clearAnimation();
                    startImageView.setImageResource(R.mipmap.play_fm_btn_play_prs);
                    musicData.setFlag(MusicData.PAUSE);
                    needleImageView.startAnimation(upAnimation);
                } else {
                    songLogoImageView.startAnimation(circulationAnimation2);
                    diskImageView.startAnimation(circulationAnimation);
                    startImageView.setImageResource(R.mipmap.play_fm_btn_pause_prs);
                    musicData.setFlag(MusicData.RESTART);
                    needleImageView.startAnimation(downAnimation);
                }
                PlayMusicService.startService(context);

                break;

            default:
                break;
        }
    }

    @Override
    protected void superInit() {
        //透明状态栏
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0x88000000);
            window.setNavigationBarColor(0x88000000);
        }

    }

    /**
     * 初始化各种动画
     */
    @Override
    public void initAnimation() {
        musicData = MusicData.getInstance();
        upAnimation = new RotateAnimation(0, -30,
                Animation.RELATIVE_TO_SELF, 0.167f,
                Animation.RELATIVE_TO_SELF, 0.111f);
        upAnimation_0ms = new RotateAnimation(0, -30,
                Animation.RELATIVE_TO_SELF, 0.167f,
                Animation.RELATIVE_TO_SELF, 0.111f);
        downAnimation = new RotateAnimation(-30, 0,
                Animation.RELATIVE_TO_SELF, 0.167f,
                Animation.RELATIVE_TO_SELF, 0.111f);
        circulationAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        circulationAnimation2 = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


        upAnimation.setDuration(1000);
        upAnimation_0ms.setDuration(0);
        downAnimation.setDuration(1000);
        circulationAnimation.setDuration(20000);
        circulationAnimation.setRepeatCount(1000000);
        circulationAnimation.setInterpolator(new LinearInterpolator());
        circulationAnimation2.setDuration(20000);
        circulationAnimation2.setRepeatCount(1000000);
        circulationAnimation2.setInterpolator(new LinearInterpolator());

        upAnimation.setFillAfter(true);
        upAnimation_0ms.setFillAfter(true);
        downAnimation.setFillAfter(true);
    }

    @Override
    public void setBackGround() {

    }

    @Override
    public void displayHeadImage(String url) {
        ImageLoader.getInstance().displayImage(url, songLogoImageView, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
                Observable
                        .just(1)
                        .observeOn(Schedulers.io())
                        .map(new Func1<Integer, Drawable>() {
                            @Override
                            public Drawable call(Integer integer) {
                                Bitmap bitmap = BitmapUtil.blur(context, loadedImage, 25);
                                return new BitmapDrawable(bitmap);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Drawable>() {
                            @Override
                            public void call(Drawable drawable) {
                                outmosterRelativeLayout.setBackgroundDrawable(drawable);
                            }
                        });
            }
        });
    }
}
