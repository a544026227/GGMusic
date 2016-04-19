package gl.com.ggmusic.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import gl.com.ggmusic.R;
import gl.com.ggmusic.adapter.SearchHintAdapter;
import gl.com.ggmusic.bean.KugouInfoJson;
import gl.com.ggmusic.bean.KugouSearchHintJson;
import gl.com.ggmusic.bean.KugouSearchListJson;
import gl.com.ggmusic.constants.URL;
import gl.com.ggmusic.network.GGHttp;
import gl.com.ggmusic.util.MyUtil;
import rx.functions.Action1;

public class SearchActivity extends BaseActivity {

    private android.widget.ListView listView;
    private android.widget.EditText searchEditText;

    private SearchHintAdapter adapter;


    public SearchActivity() {
        setContentView(R.layout.activity_search);
    }

    @Override
    void init() {
        this.listView = (ListView) findViewById(R.id.listView);
        this.searchEditText = (EditText) findViewById(R.id.searchEditText);

        adapter = new SearchHintAdapter(context);
    }

    @Override
    void initView() {
        initToolBar("设置");

        listView.setAdapter(adapter);

    }

    private GGHttp<KugouSearchListJson> ggHttpSearchList;

    /**
     * 根据具体名称获取歌曲信息列表
     *
     * @param key
     */
    private void getSearchList(String key) {
        ggHttpSearchList = new GGHttp(URL.KUGOU_SEARCH_LIST, KugouSearchListJson.class);
        ggHttpSearchList.setMethodType("GET");
        ggHttpSearchList.add("page", "1");
        ggHttpSearchList.add("pagesize", "30");
        ggHttpSearchList.add("showtype", "10");
        ggHttpSearchList.add("plat", "2");
        ggHttpSearchList.add("version", "7980");
        ggHttpSearchList.add("tag", "1");
        ggHttpSearchList.add("correct", "1");
        ggHttpSearchList.add("privilege", "1");
        ggHttpSearchList.add("sver", "5");
        ggHttpSearchList.add("keyword", MyUtil.getURLEncoder(key));
        ggHttpSearchList.send(new Action1<KugouSearchListJson>() {
            @Override
            public void call(KugouSearchListJson kugouSearchListJson) {
                getInfo(kugouSearchListJson.getData().getInfo().get(0).getHash());
            }
        });
    }

    private GGHttp<KugouSearchHintJson> ggHttpSearchHint;

    /**
     * 获取提供建议的搜索列表
     *
     * @param key
     */
    private void getSearchHint(String key) {
        ggHttpSearchHint = new GGHttp<>(URL.KUGOU_SEARCH, KugouSearchHintJson.class);
        ggHttpSearchHint.setMethodType("GET");
        ggHttpSearchHint.add("cmd", "302");
        ggHttpSearchHint.add("keyword", MyUtil.getURLEncoder(key));
        ggHttpSearchHint.send(new Action1<KugouSearchHintJson>() {
            @Override
            public void call(KugouSearchHintJson kugouSearchHintJson) {
                adapter.getList().clear();
                adapter.getList().addAll(kugouSearchHintJson.getData());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private GGHttp<KugouInfoJson> ggHttpInfo;

    /**
     * 根据hash值获取详细数据
     *
     * @param hash
     */
    private void getInfo(String hash) {
        ggHttpInfo = new GGHttp<>(URL.KUGOU_INFO, KugouInfoJson.class);
        ggHttpInfo.setMethodType("GET");
        ggHttpInfo.add("acceptMp3", "1");
        ggHttpInfo.add("key", MyUtil.getMD5(hash + "kgcloud").toLowerCase());
        ggHttpInfo.add("cmd", "3");
        ggHttpInfo.add("pid", "6");
        ggHttpInfo.add("hash", hash);
        ggHttpInfo.send(new Action1<KugouInfoJson>() {
            @Override
            public void call(KugouInfoJson kugouInfoJson) {
            }
        });
    }


    @Override
    void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getSearchList(adapter.getList().get(i).getKeyword());

            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchString = editable.toString();
                if (!TextUtils.isEmpty(searchString)) {
                    getSearchHint(searchString);
                }
            }
        });
    }
}
