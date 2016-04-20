package gl.com.ggmusic.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import gl.com.ggmusic.R;
import gl.com.ggmusic.music.MusicData;
import gl.com.ggmusic.util.MyUtil;
import gl.com.ggmusic.widget.BottomMusicView;

/**
 * Created by guilinlin on 16/4/13.<p/>
 * 注意事项:
 * 1.构造方法中需调用setContentView(),否则抛异常
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * 统一toolBar
     */
    protected android.support.v7.widget.Toolbar toolbar;
    /**
     * 最外层的容器
     */
    protected RelativeLayout outmosterRelativeLayout;
    /**
     * 显示子控件的viewStud,轻量级
     */
    protected ViewStub containerViewStub;
    /**
     * 每个界面的bottomMusic
     */
    protected BottomMusicView bottomMusicView;

    /**
     * 继承自baseActiviy需要引用的布局ID，构造函数中传入
     */
    protected int containerLayoutId = 0;

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //调用父类的setContentView，否则会调用重写的setContentView
        super.setContentView(R.layout.activity_base);

        this.context = this;
        this.outmosterRelativeLayout = (RelativeLayout) findViewById(R.id.outmosterRelativeLayout);
        this.containerViewStub = (ViewStub) findViewById(R.id.containerViewStub);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.bottomMusicView = (BottomMusicView) findViewById(R.id.bottomMusicView);

        bottomMusicView.update();

        EventBus.getDefault().register(this);//每一个界面都注册更新底部音乐空间的EventBus

        setContainerLayout();


        init();

        initView();

        setListener();
    }

    /**
     * 设置子类继承自BaseActivity需要引用的布局id
     */
    private void setContainerLayout() {
        if (containerLayoutId == 0) {
            try {
                throw new Exception("子Activity布局ID未初始化，请在构造方法中调用setContentView()");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        containerViewStub.setLayoutResource(containerLayoutId);
        containerViewStub.inflate();
    }

    protected void initToolBar(String title) {
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.mipmap.actionbar_back);
        toolbar.setNavigationOnClickListener(this);
    }

    /**
     * 为了方便使用preety自动生成view的插件，所以将次方法命令成setContentView，
     * 在BaseActivity中应使用super().setContentView代替setContentView
     *
     * @param containerLayoutId
     */
    public void setContentView(int containerLayoutId) {
        this.containerLayoutId = containerLayoutId;
    }

    /**
     * 设置真正的setContentViewReal
     *
     * @param layoutID
     */
    public void setContentViewReal(int layoutID) {
        super.setContentView(layoutID);
    }

    abstract void init();

    abstract void initView();

    abstract void setListener();

    /**
     * 监听底部音乐状态更新
     *
     * @param musicData
     */
    @Subscribe
    public void onEventMainThrad(MusicData musicData) {
        bottomMusicView.update();
    }


    /**
     * 去下一个Activitiy的快捷方法
     *
     * @param activityClass
     * @param bundle
     */
    protected void startActivity(Class activityClass, Bundle bundle) {
        Intent intent = new Intent(context, activityClass);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);

    }

    protected void startActivity(Class activityClass) {
        startActivity(activityClass, null);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    protected void showToast(Object msg) {
        MyUtil.t(context, msg);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
