package gl.com.ggmusic.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import gl.com.ggmusic.R;
import gl.com.ggmusic.adapter.SearchHintAdapter;
import gl.com.ggmusic.adapter.SearchListAdapter;
import gl.com.ggmusic.bean.KugouSearchHintJson;
import gl.com.ggmusic.bean.KugouSearchListJson;
import gl.com.ggmusic.music.MusicUtil;
import gl.com.ggmusic.presenter.SearchPresenter;
import gl.com.ggmusic.util.DensityUtils;
import gl.com.ggmusic.util.KeyBoardUtils;
import gl.com.ggmusic.view.ISearchActivity;
import gl.com.ggmusic.widget.FlowLayout;

public class SearchActivity extends BaseActivity implements ISearchActivity {

    private SearchPresenter searchPresenter;

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

        searchPresenter = new SearchPresenter(this);

        initToolBar("");
        View view = LayoutInflater.from(context).inflate(R.layout.layout_search_top, toolbar);

        this.hotSearchFlowLayout = (FlowLayout) findViewById(R.id.hotSearchFlowLayout);
        this.searchListListView = (ListView) findViewById(R.id.resultListView);
        this.searchHintListView = (ListView) findViewById(R.id.hintListView);
        this.searchEditText = (MaterialEditText) view.findViewById(R.id.searchEditText);
        this.deleteImageView = (ImageView) view.findViewById(R.id.deleteImageView);

        searchHintAdapter = new SearchHintAdapter(context);
        searchListAdapter = new SearchListAdapter(context);

        searchPresenter.addHotText();
    }

    @Override
    void initView() {

        searchHintListView.setAdapter(searchHintAdapter);
        searchListListView.setAdapter(searchListAdapter);


    }

    @Override
    void setListener() {
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEditText.setText("");
                searchHintListView.setVisibility(View.GONE);
            }
        });
        searchHintListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchPresenter.setSearchList(searchHintAdapter.getList().get(i).getKeyword());

            }
        });

        searchListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchPresenter.getMusicInfo(searchListAdapter.getList().get(i));
            }
        });
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    searchPresenter.setSearchList(searchEditText.getText().toString());
                    return true;
                }
                return false;
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
                    searchPresenter.setSearchHintList(searchString);
                }
            }
        });
    }

    @Override
    public void addTextViewToFlowLayout(final String name) {
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
                searchPresenter.setSearchList(name);
            }
        });
        hotSearchFlowLayout.addView(tv, lp);
    }

    @Override
    public void setSearchListAdapter(KugouSearchListJson kugouSearchListJson) {

        KeyBoardUtils.closeKeybord(searchEditText, context);
        hotSearchFlowLayout.setVisibility(View.GONE);
        searchHintAdapter.getList().clear();
        searchHintAdapter.notifyDataSetChanged();
        searchListAdapter.getList().clear();
        searchListAdapter.getList().addAll(kugouSearchListJson.getData().getInfo());
        searchListAdapter.notifyDataSetChanged();
    }

    @Override
    public void setSearchHintAdapter(KugouSearchHintJson kugouSearchHintJson) {
        searchHintAdapter.getList().clear();
        searchHintAdapter.getList().addAll(kugouSearchHintJson.getData());
        searchHintAdapter.notifyDataSetChanged();
    }


    public void playMusic(final KugouSearchListJson.DataBean.InfoBean infoBean, String url) {
        MusicUtil.playMusic(url, infoBean.getSingername(), infoBean.getFilename(),
                infoBean.getDuration(), infoBean.getHash(), context);
    }

}
