package gl.com.ggmusic.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import gl.com.ggmusic.R;
import gl.com.ggmusic.bean.BottomMusicEvent;
import gl.com.ggmusic.bean.MusicData;
import gl.com.ggmusic.constants.Constants;
import gl.com.ggmusic.service.PlayMusicService;

/**
 * 底部音乐播放的控件，一个很核心的类
 * Created by guilinlin on 16/4/15.
 * 1.此处注册了EventBus,在需要修改的地方使用:
 * EventBus.getDefault().post(new BottomMusicEvent(BottomMusicEvent.GONE));
 */
public class BottomMusicView implements View.OnClickListener {

    public static final String TAG_START_MUSIC = "tag_start_music";

    private Context context;

    private View bottomMusicView;
    private Application application;

    private android.widget.ImageView headImageView;
    private TextView musicNameTextView;
    private TextView singerNameTextView;
    private android.widget.ImageView listImageView;
    private android.widget.ImageView playImageView;
    private android.widget.ImageView nextImageView;
    private android.widget.LinearLayout playerLayout;

    /**
     * 表示音乐是否正在播放,只根据这个值判断就好
     */
    private boolean isPlaying = false;


    /**
     * 初始化底部音乐悬浮穿
     */
    public void show(Application application, final Context context) {
        this.context = context;
        this.application = application;

        init(application, context);

        setListener();

        setImageResource();
        EventBus.getDefault().register(this);//注册Eventbus
    }


    private void setListener() {
        listImageView.setOnClickListener(this);
        playImageView.setOnClickListener(this);
        nextImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.listImageView:
                break;
            case R.id.playImageView:
                bindService();
                isPlaying = !isPlaying;
                setImageResource();
                break;
            case R.id.nextImageView:
                break;

            default:
                break;
        }
    }

    /**
     * 接收EventBus，传递的对象类型需是BottomMusicEvent
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThrad(BottomMusicEvent event) {
        switch (event.getCode()) {
            case BottomMusicEvent.GONE:
                bottomMusicView.animate().alpha(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        bottomMusicView.setClickable(false);//看不见的状态下不可点击
                    }
                });
                break;
            case BottomMusicEvent.VISABLE:
                bottomMusicView.animate().alpha(1).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        bottomMusicView.setClickable(true);
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 将底部音乐控件从屏幕上移除
     */
    public void remove() {
        WindowManager windowManager = (WindowManager) application.getSystemService(Application.WINDOW_SERVICE);
        windowManager.removeView(bottomMusicView);
        EventBus.getDefault().unregister(this);
    }

    /**
     * 开启并绑定播放音乐的Servie,同时向Servie发送一个请求，播放音乐
     */
    private void bindService() {
        //如果音乐正在播放，告诉Servie暂停，如果没播放，告诉Servie播放
        MusicData musicData = new MusicData(Constants.SIMPLE_MUSIC);
        musicData.setStatus(isPlaying ? MusicData.PAUSE : MusicData.START);


        Intent service = new Intent(context.getApplicationContext(), PlayMusicService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG_START_MUSIC, musicData);
        service.putExtras(bundle);
        //同时start和bindService，不然后台音乐会自动退出
        context.getApplicationContext().startService(service);
        context.getApplicationContext().bindService
                (service, connection, Activity.BIND_AUTO_CREATE);

    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    /**
     * 初始化，设置布局，findView等
     *
     * @param application
     * @param context
     */
    private void init(Application application, Context context) {
        bottomMusicView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_music, null);
        this.nextImageView = (ImageView) bottomMusicView.findViewById(R.id.nextImageView);
        this.playImageView = (ImageView) bottomMusicView.findViewById(R.id.playImageView);
        this.listImageView = (ImageView) bottomMusicView.findViewById(R.id.listImageView);
        this.singerNameTextView = (TextView) bottomMusicView.findViewById(R.id.singerNameTextView);
        this.musicNameTextView = (TextView) bottomMusicView.findViewById(R.id.musicNameTextView);
        this.headImageView = (ImageView) bottomMusicView.findViewById(R.id.headImageView);
        //TYPE_TOAST 是关键,这样就不需要悬浮窗权限了
        WindowManager.LayoutParams params = new WindowManager.LayoutParams
                (WindowManager.LayoutParams.TYPE_TOAST);
        //禁止获得焦点，不然下面的界面无法接收到触摸点击事件  无法接收onBack
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.gravity = Gravity.BOTTOM;
        WindowManager windowManager = (WindowManager) application.
                getSystemService(Application.WINDOW_SERVICE);
        windowManager.addView(bottomMusicView, params);
    }

    /**
     * 根据音乐是否正在播放设置图片
     */
    private void setImageResource() {
        playImageView.setImageResource(isPlaying ?
                R.mipmap.playbar_btn_pause : R.mipmap.playbar_btn_play);
    }

}
