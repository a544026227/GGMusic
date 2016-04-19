package gl.com.ggmusic.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;

import gl.com.ggmusic.R;
import gl.com.ggmusic.util.MyUtil;

/**
 * Created by guilinlin on 16/4/13.<p/>
 * 注意事项:
 * 1.构造方法中需调用setContentView()；
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * 统一toolBar
     */
    protected android.support.v7.widget.Toolbar toolbar;
    /**
     * 显示子控件的viewStud,轻量级
     */
    protected ViewStub containerViewStub;

    /**
     * 继承自baseActiviy需要引用的布局ID，构造函数中传入
     */
    protected int containerLayoutId;

    protected Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        this.context = this;
        this.containerViewStub = (ViewStub) findViewById(R.id.viewStub);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);


        setContainerLayout();

        init();

        initView();

        setListener();
    }

    /**
     * 设置之类继承自BaseActivity需要引用的布局id
     */
    private void setContainerLayout() {
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

    abstract void init();

    abstract void initView();

    abstract void setListener();


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

    protected void setNavigationOnClickListener(View.OnClickListener listener) {

    }

    protected void showToast(Object msg) {
        MyUtil.t(context, msg);
    }
}
