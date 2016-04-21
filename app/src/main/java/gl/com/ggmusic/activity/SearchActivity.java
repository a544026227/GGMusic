package gl.com.ggmusic.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import gl.com.ggmusic.R;
import gl.com.ggmusic.adapter.SearchHintAdapter;
import gl.com.ggmusic.adapter.SearchListAdapter;
import gl.com.ggmusic.bean.KugouInfoJson;
import gl.com.ggmusic.bean.KugouSearchHintJson;
import gl.com.ggmusic.bean.KugouSearchListJson;
import gl.com.ggmusic.constants.URL;
import gl.com.ggmusic.music.MusicData;
import gl.com.ggmusic.network.GGHttp;
import gl.com.ggmusic.music.PlayMusicService;
import gl.com.ggmusic.util.DensityUtils;
import gl.com.ggmusic.util.MyUtil;
import gl.com.ggmusic.widget.FlowLayout;
import rx.functions.Action1;

public class SearchActivity extends BaseActivity {

    private MaterialEditText searchEditText;
    private ImageView deleteImageView;

    private SearchHintAdapter searchHintAdapter;
    private SearchListAdapter searchListAdapter;

    private ListView searchHintListView;
    private ListView searchListListView;
    private gl.com.ggmusic.widget.FlowLayout hotSearchFlowLayout;


    public SearchActivity() {
        setContentView(R.layout.activity_search);
    }

    @Override
    void init() {

        initToolBar("");
        View view = LayoutInflater.from(context).inflate(R.layout.layout_search_top, toolbar);

        this.hotSearchFlowLayout = (FlowLayout) findViewById(R.id.hotSearchFlowLayout);
        this.searchListListView = (ListView) findViewById(R.id.resultListView);
        this.searchHintListView = (ListView) findViewById(R.id.hintListView);
        this.searchEditText = (MaterialEditText) view.findViewById(R.id.searchEditText);
        this.deleteImageView = (ImageView) view.findViewById(R.id.deleteImageView);

        searchHintAdapter = new SearchHintAdapter(context);
        searchListAdapter = new SearchListAdapter(context);
    }

    @Override
    void initView() {

        searchHintListView.setAdapter(searchHintAdapter);
        searchListListView.setAdapter(searchListAdapter);

        addTextViewToFlowLayout("许嵩");
        addTextViewToFlowLayout("我是歌手");
        addTextViewToFlowLayout("寂寞沙洲冷");
        addTextViewToFlowLayout("暖春");
        addTextViewToFlowLayout("米店");
        addTextViewToFlowLayout("Adele");
        addTextViewToFlowLayout("不为谁而作的歌");
        addTextViewToFlowLayout("You Are My EveryThing");
        addTextViewToFlowLayout("Adele");
        addTextViewToFlowLayout("有心人");


    }

    /**
     * 增加文字到流式布局中，这段代码可以优化
     *
     * @param name
     */
    private void addTextViewToFlowLayout(final String name) {
        TextView tv = new TextView(context);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
        int pad = DensityUtils.dp2px(context, 7);
        tv.setPadding(pad, pad, pad, pad);
        tv.setTextSize(19);
        tv.setTextColor(getResources().getColor(R.color.colorDefaultBlack));
        tv.setText(name);
        lp.bottomMargin = DensityUtils.dp2px(context, 13);
        lp.rightMargin = DensityUtils.dp2px(context, 13);
        tv.setBackgroundResource(R.drawable.bg_grey_stoke_1px_corrner_2dp);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSearchList(name);
            }
        });
        hotSearchFlowLayout.addView(tv, lp);
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
                hotSearchFlowLayout.setVisibility(View.GONE);
                searchHintAdapter.getList().clear();
                searchHintAdapter.notifyDataSetChanged();
                searchListAdapter.getList().clear();
                searchListAdapter.getList().addAll(kugouSearchListJson.getData().getInfo());
                searchListAdapter.notifyDataSetChanged();
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
                searchHintAdapter.getList().clear();
                searchHintAdapter.getList().addAll(kugouSearchHintJson.getData());
                searchHintAdapter.notifyDataSetChanged();
            }
        });
    }

    private GGHttp<KugouInfoJson> ggHttpInfo;

    /**
     * 根据hash值获取详细数据
     *
     * @param infoBean
     */
    private void getInfo(final KugouSearchListJson.DataBean.InfoBean infoBean) {
        ggHttpInfo = new GGHttp<>(URL.KUGOU_INFO, KugouInfoJson.class);
        ggHttpInfo.setMethodType("GET");
        ggHttpInfo.add("acceptMp3", "1");
        ggHttpInfo.add("key", MyUtil.getMD5(infoBean.getHash() + "kgcloud").toLowerCase());
        ggHttpInfo.add("cmd", "3");
        ggHttpInfo.add("pid", "6");
        ggHttpInfo.add("hash", infoBean.getHash());
        ggHttpInfo.send(new Action1<KugouInfoJson>() {
            @Override
            public void call(KugouInfoJson kugouInfoJson) {
                MusicData musicData = MusicData.getInstance();
                musicData.setFlag(MusicData.START);
                musicData.setUrl(kugouInfoJson.getUrl());
                musicData.setSinger(infoBean.getSingername());
                musicData.setSongName(infoBean.getFilename());
                musicData.setSongLogo("");
                PlayMusicService.startService(context);
                showToast("准备开始播放：" + infoBean.getSingername());
            }
        });
    }


    @Override
    void setListener() {
        searchHintListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getSearchList(searchHintAdapter.getList().get(i).getKeyword());


            }
        });

        searchListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getInfo(searchListAdapter.getList().get(i));
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
