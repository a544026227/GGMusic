package gl.com.ggmusic.activity;

import android.widget.ListView;

import gl.com.ggmusic.R;
import gl.com.ggmusic.adapter.SearchHintAdapter;
import gl.com.ggmusic.bean.SearchHintData;

public class SettingActivity extends BaseActivity {

    private android.widget.ListView listView;

    public SettingActivity() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    void init() {
        this.listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    void initView() {
        initToolBar("设置");

        SearchHintAdapter adapter = new SearchHintAdapter(context);
        for (int i = 0; i < 100; i++) {
            adapter.add(new SearchHintData(i+""));
        }
        listView.setAdapter(adapter);
    }

    @Override
    void setListener() {

    }
}
