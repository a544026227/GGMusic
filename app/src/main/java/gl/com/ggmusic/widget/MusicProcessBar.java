package gl.com.ggmusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gl.com.ggmusic.R;
import gl.com.ggmusic.music.MusicData;
import gl.com.ggmusic.music.MusicUtil;
import gl.com.ggmusic.util.DensityUtils;

/**
 * Created by guilinlin on 16/5/5 22:11.
 */
public class MusicProcessBar extends RelativeLayout implements View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {

    private android.widget.TextView startTimeTextView;
    private android.widget.TextView endTimeTextView;
    /**
     * 背景进度条
     */
    private android.view.View dstView;
    /**
     * 前景进度条，用于显示下载进度
     */
    private android.view.View cacheView;
    /**
     * 播放进度条，用于播放进度
     */
    private android.view.View playedView;
    /**
     * 音乐游标，标记歌曲播放到哪儿
     */
    private android.widget.ImageView vernierImageView;

    /**
     * 测量进度条的宽度,-1表示未测量到结果
     */
    private int processBarWidth = -1;
    private int leftMargin = 0;

    /**
     * 游标是否正在被拖动，true的时候不能被其他Event修改
     */
    private boolean isSwiping = false;

    public MusicProcessBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);

        vernierImageView.setOnTouchListener(this);
        getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_music_process_bar, this, true);
        this.vernierImageView = (ImageView) findViewById(R.id.vernierImageView);
        this.cacheView = findViewById(R.id.cacheView);
        this.dstView = findViewById(R.id.dstView);
        this.playedView = findViewById(R.id.playedView);
        this.endTimeTextView = (TextView) findViewById(R.id.endTimeTextView);
        this.startTimeTextView = (TextView) findViewById(R.id.startTimeTextView);

        leftMargin = DensityUtils.dp2px(context, -10);
    }

    private float startX;

    /**
     * 点击音乐游标的事件
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //第一次按下时记录x坐标，移动时减去这个坐标，确保滑动正确
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            startX = motionEvent.getX();
            isSwiping = true;
        }
        //滑动或者抬起
        else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE
                || motionEvent.getAction() == MotionEvent.ACTION_UP) {
            LayoutParams lp = (LayoutParams) vernierImageView.getLayoutParams();
            int lm = lp.leftMargin;//避免滑动过快，导致拖动异常
            lm += motionEvent.getX() - startX;
            //判断如果滑动的位置在规定范围之内才可以滑动
            if (lm >= leftMargin && lm <= processBarWidth + leftMargin) {
                float percent = (lm - leftMargin) / (float) processBarWidth;
                setVernierLocation(percent);
                setStartTimeTextView(percent);
                setPlayedViewByPercent(percent);
                if (listener != null) {
                    listener.onSlide(motionEvent.getAction(), percent);
                }
            }

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                isSwiping = false;
            }
        }


        return true;
    }

    @Override
    public void onGlobalLayout() {
        processBarWidth = dstView.getWidth();
    }

    /**
     * 根据百分比设置游标位置
     *
     * @param percent
     */
    public void setVernierLocation(float percent) {
        LayoutParams lp = (LayoutParams) vernierImageView.getLayoutParams();
        lp.leftMargin = (int) (processBarWidth * percent + leftMargin);
        vernierImageView.setLayoutParams(lp);
    }


    /**
     * 根据百分比设置表示缓存的进度条
     *
     * @param percent
     */
    public void setCacheViewByPercent(float percent) {
        if (percent < 0 || percent > 1 || processBarWidth == -1) {
            return;
        }
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) cacheView.getLayoutParams();
        lp.width = (int) (processBarWidth * percent);
        cacheView.setLayoutParams(lp);

    }

    /**
     * 根据百分比设置播放进度条
     *
     * @param percent
     */
    public void setPlayedViewByPercent(float percent) {
        if (percent < 0 || percent > 1 || processBarWidth == -1) {
            return;
        }
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) playedView.getLayoutParams();
        lp.width = (int) (processBarWidth * percent);
        playedView.setLayoutParams(lp);

    }


    public void setStartTimeTextView(float percent) {
        String s = MusicUtil.getTimeBySecond((int) (MusicData.getInstance().getDurtion()
                * percent));
        startTimeTextView.setText(s);
    }

    public void setEndTimeTextView(String text) {
        endTimeTextView.setText(text);
    }

    public boolean isSwiping() {
        return isSwiping;
    }


    /**
     * 游标滚动时的接口
     */
    public interface OnVernierSlideListener {
        /**
         * 拖动游标时触发的事件
         *
         * @param action
         *         表示哪一种操作，EventActon.ACTION_MOVE等等
         * @param percent
         *         拖动到的百分比位置
         */
        void onSlide(int action, float percent);
    }

    private OnVernierSlideListener listener;

    public void setOnVernierSlideListener(OnVernierSlideListener l) {
        listener = l;
    }
}
