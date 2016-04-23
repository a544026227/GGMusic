package gl.com.ggmusic.activity;

import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import gl.com.ggmusic.R;
import gl.com.ggmusic.constants.Constants;
import gl.com.ggmusic.music.MusicData;
import gl.com.ggmusic.music.PlayMusicService;
import gl.com.ggmusic.widget.CircleImageView;

public class MusicInfoActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 临时存储歌词文件的名称
     */
    public String TEMPORARY_FILE_NAME = "temporary.krc";

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
        toolbar.setBackgroundColor(0x22ffffff);

        bottomMusicView.setVisibility(View.GONE);//当前界面无须显示bottomView

        View view = new View(context);
        view.setBackgroundColor(0x44000000);
        outmosterRelativeLayout.addView(view, 0);

        outmosterRelativeLayout.setBackgroundColor(0xff000000);
        outmosterRelativeLayout.setPadding
                (0, Constants.statusHeight, 0, Constants.navigationBarheight);//设置上边距，显示状态栏

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


//        rx.Observable
//                .just(1)
//                .observeOn(Schedulers.io())
//                .map(new Func1<Integer, HttpResponse>() {
//
//                    @Override
//                    public HttpResponse call(Integer integer) {
//                        GGHttp ggHttp = new GGHttp(URL.KUGOU_KRC, String.class);
//                        ggHttp.setMethodType("GET");
//                        ggHttp.add("keyword", musicData.getSongNameEncoder());
//                        ggHttp.add("timelength", musicData.getDurtion() + "000");
//                        ggHttp.add("type", "1");
//                        ggHttp.add("cmd", "200");
//                        ggHttp.add("hash", musicData.getHash());
//                        return ggHttp.getHttpResponse();
//                    }
//
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<HttpResponse>() {
//                    @Override
//                    public void call(HttpResponse response) {
//                        System.out.println(response.getResponseCode());
//                        InputStream is = response.getInputStream();
//                        System.out.println(FileUtils.writeFile(Constants.DOWNLOAD_PATH+TEMPORARY_FILE_NAME,is));
//
//                    }
//                });


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
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 初始化各种动画
     */
    private void initAnimation() {
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
        circulationAnimation = new RotateAnimation(0, -360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        circulationAnimation2 = new RotateAnimation(0, -360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


        upAnimation.setDuration(1000);
        upAnimation_0ms.setDuration(0);
        downAnimation.setDuration(1000);
        circulationAnimation.setDuration(15000);
        circulationAnimation.setRepeatCount(1000000);
        circulationAnimation2.setDuration(15000);
        circulationAnimation2.setRepeatCount(1000000);

        upAnimation.setFillAfter(true);
        upAnimation_0ms.setFillAfter(true);
        downAnimation.setFillAfter(true);
    }
}
