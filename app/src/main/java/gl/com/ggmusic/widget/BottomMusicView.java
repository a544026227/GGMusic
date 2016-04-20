package gl.com.ggmusic.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gl.com.ggmusic.R;
import gl.com.ggmusic.constants.Constants;
import gl.com.ggmusic.music.MusicData;
import gl.com.ggmusic.service.PlayMusicService;

/**
 * 底部音乐播放的控件，一个很核心的类
 * Created by guilinlin on 16/4/15.
 */
public class BottomMusicView extends RelativeLayout implements View.OnClickListener {

    private Context context;

    private android.widget.ImageView headImageView;
    private TextView songNameTextView;
    private TextView singerNameTextView;
    private android.widget.ImageView listImageView;
    private android.widget.ImageView playImageView;
    private android.widget.ImageView nextImageView;
    private View processView;

    /**
     * 表示音乐是否正在播放,只根据这个值判断就好
     */
    private boolean isPlaying = false;

    public BottomMusicView(Context context, AttributeSet attrs) {
        super(context, attrs);


        LayoutInflater.from(context).inflate(R.layout.layout_bottom_music, this, true);

        init(context);

        setListener();

        setImageResource();
    }

    private void init(Context context) {
        this.context = context;
        this.nextImageView = (ImageView) findViewById(R.id.nextImageView);
        this.playImageView = (ImageView) findViewById(R.id.playImageView);
        this.listImageView = (ImageView) findViewById(R.id.listImageView);
        this.singerNameTextView = (TextView) findViewById(R.id.singerNameTextView);
        this.songNameTextView = (TextView) findViewById(R.id.musicNameTextView);
        this.headImageView = (ImageView) findViewById(R.id.headImageView);
        this.processView = findViewById(R.id.processView);

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
                MusicData musicData = MusicData.getInstance();
                if (TextUtils.isEmpty(musicData.getUrl())) {
                    return;//判断之前是否有音乐播放
                }
                musicData.setFlag(isPlaying ? MusicData.PAUSE : MusicData.RESTART);
                PlayMusicService.startService(context);
                //如果音乐正在播放，告诉Servie暂停，如果没播放，告诉Servie播放
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
     * 更新底部音乐状态栏
     */
    public void update() {
        MusicData musicData = MusicData.getInstance();
        isPlaying = musicData.isPlaying();
        setImageResource();
        songNameTextView.setText(musicData.getSongName());
        singerNameTextView.setText(musicData.getSinger());
        updateProcess(musicData.getPercent());
    }


    /**
     * 根据音乐是否正在播放设置图片
     */
    private void setImageResource() {
        playImageView.setImageResource(isPlaying ?
                R.mipmap.playbar_btn_pause : R.mipmap.playbar_btn_play);
    }

    public void updateProcess(float percent) {
        LayoutParams lp = (LayoutParams) processView.getLayoutParams();
        lp.width = (int) (Constants.screenWidth * percent);
        processView.requestLayout();

    }

}
