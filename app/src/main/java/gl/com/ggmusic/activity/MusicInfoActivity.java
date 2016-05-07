package gl.com.ggmusic.activity;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.MotionEvent;
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

import org.greenrobot.eventbus.Subscribe;

import gl.com.ggmusic.R;
import gl.com.ggmusic.bean.EventBusMusicInfo;
import gl.com.ggmusic.constants.Constants;
import gl.com.ggmusic.music.MusicData;
import gl.com.ggmusic.music.MusicUtil;
import gl.com.ggmusic.music.PlayMusicService;
import gl.com.ggmusic.presenter.MusicInfoPresenter;
import gl.com.ggmusic.util.BitmapUtil;
import gl.com.ggmusic.util.MyUtil;
import gl.com.ggmusic.view.IMusicInfoActivity;
import gl.com.ggmusic.widget.CircleImageView;
import gl.com.ggmusic.widget.MusicProcessBar;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MusicInfoActivity extends BaseActivity implements IMusicInfoActivity, View.OnClickListener, MusicProcessBar
        .OnVernierSlideListener {

    private MusicInfoPresenter presenter;
    private MusicData musicData;
    private android.widget.ImageView needleImageView;

    private Animation upAnimation;
    private Animation upAnimation_0ms;
    private Animation downAnimation;
    /**
     * 头像的旋转属性动画
     */
    private ValueAnimator rotationAnimatior;
    /**
     * 标记停止时转到了多少度，实现停止之后继续转动的效果,每当暂停时记录位置
     */
    private float currentRotation = 0;
    /**
     * 光碟图片
     */
    private ImageView diskImageView;
    /**
     * 头像圆形图片
     */
    private gl.com.ggmusic.widget.CircleImageView songLogoImageView;
    private ImageView circulationImageView;
    private ImageView prevImageView;
    private ImageView startImageView;
    private ImageView nextImageView;
    private ImageView menuImageView;
    /**
     * 音乐进度条的自定义控件
     */
    private MusicProcessBar musicProcessBar;
    private View centerView;
    private RelativeLayout titleRelativeLayut;
    private ImageView loveImageView;
    private ImageView downLoadImageView;
    private ImageView commentImageView;
    private ImageView moreImageView;

    public MusicInfoActivity() {
        setContentView(R.layout.activity_music_info);
    }

    @Override
    void init() {

        presenter = new MusicInfoPresenter(this);

        musicData = MusicData.getInstance();

        findView();

        initAnimation();


    }


    @Override
    void initView() {
        initToolBar();

        setViewStatus(musicData.isPlaying());

        //当前界面无须显示bottomView
        bottomMusicView.setVisibility(View.GONE);

        //获取歌手图片
        presenter.setSingerHeadImage(musicData.getSinger());
        //获取歌词
        presenter.setMusicLrc(musicData);
        //设置结束时间
        setEndTime();

        //给当前界面加上一层灰色背景
        View view = new View(context);
        view.setBackgroundColor(0x88000000);
        view.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        outmosterRelativeLayout.addView(view, 0);
        //设置上边距，显示状态栏
        outmosterRelativeLayout.setPadding
                (0, Constants.statusHeight, 0, Constants.navigationBarheight);

        displayHeadImage(musicData.getSongLogo());


    }

    /**
     * 设置结束时间
     */
    private void setEndTime() {
        musicProcessBar.setEndTimeTextView(MusicUtil.getTimeBySecond(musicData.getDurtion()));
    }

    /**
     * 初始化toolBar
     */
    private void initToolBar() {
        initToolBar(musicData.getSongName());
        toolbar.setSubtitle(musicData.getSinger());
        toolbar.inflateMenu(R.menu.menu_music_info_share);
        toolbar.setBackgroundColor(0x00000000);
    }

    @Override
    void setListener() {
        startImageView.setOnClickListener(this);
        musicProcessBar.setOnVernierSlideListener(this);
        downLoadImageView.setOnClickListener(this);
    }

    /**
     * 拖动游标时触发的事件
     */
    @Override
    public void onSlide(int action, float percent) {
        if (action == MotionEvent.ACTION_UP) {//手指抬起才更新进度
            musicData.setFlag(MusicData.SELECT);
            musicData.setSelectTimePercent(percent);
            PlayMusicService.startService(context);
        }
    }

    /**
     * 监听音乐播放进度，修改进度条和时间
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(EventBusMusicInfo event) {
        if (musicProcessBar.isSwiping()) {
            return;
        }
        musicProcessBar.setVernierLocation(event.percent);
        musicProcessBar.setStartTimeTextView(event.percent);
        musicProcessBar.setPlayedViewByPercent(event.percent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startImageView:
                if (TextUtils.isEmpty(musicData.getUrl())) {
                    return;
                }
                //如果音乐正在播放，暂停；如果已暂停，开始播放
                musicData.setFlag(musicData.isPlaying() ? MusicData.PAUSE : MusicData.RESTART);
                setViewStatus(!musicData.isPlaying());
                PlayMusicService.startService(context);

                break;
            case R.id.downLoadImageView:


                break;
            default:
                break;
        }
    }

    /**
     * 根据播放音乐的状态修改界面
     *
     * @param isPlaying
     *         true表示正在播放
     */
    private void setViewStatus(boolean isPlaying) {
        if (isPlaying) {
            rotationAnimatior.start();
            startImageView.setImageResource(R.mipmap.play_fm_btn_pause_prs);
            needleImageView.startAnimation(downAnimation);//放下播放杆
        } else {
            //记录旋转到的位置
            if (rotationAnimatior.getAnimatedValue() != null) {
                currentRotation = currentRotation + (float) rotationAnimatior.getAnimatedValue();
            }
            //停止属性动画
            rotationAnimatior.cancel();
            rotationAnimatior.end();
            //保存当前旋转的角度
            diskImageView.setRotation(currentRotation);
            songLogoImageView.setRotation(currentRotation);
            startImageView.setImageResource(R.mipmap.play_fm_btn_play_prs);
            needleImageView.startAnimation(upAnimation);//抬起播放杆
        }
    }

    /**
     * 透明状态栏
     */
    @Override
    protected void superInit() {
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
        rotationAnimatior = new ValueAnimator();
        rotationAnimatior.setDuration(25000);
        rotationAnimatior.setObjectValues(0f, 360f);
        rotationAnimatior.setRepeatCount(1000000);
        rotationAnimatior.setInterpolator(new LinearInterpolator());
        rotationAnimatior.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float r = (float) valueAnimator.getAnimatedValue();
                songLogoImageView.setRotation(r + currentRotation);
                diskImageView.setRotation(r + currentRotation);

            }
        });

        upAnimation = new RotateAnimation(0, -30,
                Animation.RELATIVE_TO_SELF, 0.167f,
                Animation.RELATIVE_TO_SELF, 0.111f);
        upAnimation_0ms = new RotateAnimation(0, -30,
                Animation.RELATIVE_TO_SELF, 0.167f,
                Animation.RELATIVE_TO_SELF, 0.111f);
        downAnimation = new RotateAnimation(-30, 0,
                Animation.RELATIVE_TO_SELF, 0.167f,
                Animation.RELATIVE_TO_SELF, 0.111f);


        upAnimation.setDuration(1000);
        upAnimation_0ms.setDuration(0);
        downAnimation.setDuration(1000);

        upAnimation.setFillAfter(true);
        upAnimation_0ms.setFillAfter(true);
        downAnimation.setFillAfter(true);
    }

    @Override
    public void displayHeadImage(String url) {
        ImageLoader.getInstance().displayImage(url, songLogoImageView,
                MyUtil.getImageLoaderOptions(R.mipmap.about_logo, R.mipmap.about_logo)
                , new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
                        Observable
                                .just(1)
                                .observeOn(Schedulers.io())
                                .map(new Func1<Integer, Drawable>() {
                                    @Override
                                    public Drawable call(Integer integer) {
                                        if (loadedImage == null) {
                                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.music_info_background);
                                            bitmap = BitmapUtil.blur(context, bitmap, 25);
                                            return new BitmapDrawable(bitmap);
                                        }
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
                        //获取到图片并且显示后加载动画
                        if (musicData.isPlaying()) {
                            rotationAnimatior.start();
                        }
                    }
                });
    }


    private void findView() {
        this.moreImageView = (ImageView) findViewById(R.id.moreImageView);
        this.commentImageView = (ImageView) findViewById(R.id.commentImageView);
        this.downLoadImageView = (ImageView) findViewById(R.id.downLoadImageView);
        this.loveImageView = (ImageView) findViewById(R.id.loveImageView);
        this.musicProcessBar = (MusicProcessBar) findViewById(R.id.musicProcessBar);
        this.titleRelativeLayut = (RelativeLayout) findViewById(R.id.titleRelativeLayut);
        this.menuImageView = (ImageView) findViewById(R.id.menuImageView);
        this.nextImageView = (ImageView) findViewById(R.id.nextImageView);
        this.startImageView = (ImageView) findViewById(R.id.startImageView);
        this.prevImageView = (ImageView) findViewById(R.id.prevImageView);
        this.circulationImageView = (ImageView) findViewById(R.id.circulationImageView);
        this.needleImageView = (ImageView) findViewById(R.id.needleImageView);
        this.songLogoImageView = (CircleImageView) findViewById(R.id.songLogoImageView);
        this.diskImageView = (ImageView) findViewById(R.id.diskImageView);
        this.centerView =  findViewById(R.id.centerView);
    }


}
