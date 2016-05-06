package gl.com.ggmusic.presenter;

import gl.com.ggmusic.bean.KugouInfoJson;
import gl.com.ggmusic.bean.KugouSearchHintJson;
import gl.com.ggmusic.bean.KugouSearchListJson;
import gl.com.ggmusic.constants.URL;
import gl.com.ggmusic.network.GGHttp;
import gl.com.ggmusic.util.MyUtil;
import gl.com.ggmusic.view.ISearchActivity;
import rx.functions.Action1;

/**
 * Created by guilinlin on 16/5/3 21:38.
 */
public class SearchPresenter {

    private ISearchActivity searchActivity;

    public SearchPresenter(ISearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }

    /**
     * 设置热门搜索文字
     */
    public void addHotText() {
        searchActivity.addTextViewToFlowLayout("许嵩");
        searchActivity.addTextViewToFlowLayout("母亲");
        searchActivity.addTextViewToFlowLayout("寂寞沙洲冷");
        searchActivity.addTextViewToFlowLayout("暖春");
        searchActivity.addTextViewToFlowLayout("米店");
        searchActivity.addTextViewToFlowLayout("Adele");
        searchActivity.addTextViewToFlowLayout("不为谁而作的歌");
        searchActivity.addTextViewToFlowLayout("You Are My EveryThing");
        searchActivity.addTextViewToFlowLayout("Adele");
        searchActivity.addTextViewToFlowLayout("有心人");
    }

    /**
     * 根据关键字获取搜索结果列表
     *
     * @param key
     */
    public void setSearchList(String key) {
        GGHttp<KugouSearchListJson> ggHttpSearchList = new GGHttp<KugouSearchListJson>(URL.KUGOU_SEARCH_LIST, KugouSearchListJson.class);
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
                searchActivity.setSearchListAdapter(kugouSearchListJson);
            }
        });
    }

    /**
     * 获取提供建议的提示
     *
     * @param key
     */
    public void setSearchHintList(String key) {
        GGHttp<KugouSearchHintJson> ggHttpSearchHint = new GGHttp<>(URL.KUGOU_SEARCH, KugouSearchHintJson.class);
        ggHttpSearchHint.setMethodType("GET");
        ggHttpSearchHint.add("cmd", "302");
        ggHttpSearchHint.add("keyword", MyUtil.getURLEncoder(key));
        ggHttpSearchHint.send(new Action1<KugouSearchHintJson>() {
            @Override
            public void call(KugouSearchHintJson kugouSearchHintJson) {
                searchActivity.setSearchHintAdapter(kugouSearchHintJson);
            }
        });
    }

    /**
     * 根据歌曲名获取详细数据，然后通知音乐播放
     *
     * @param infoBean
     */
    public void getMusicInfo(final KugouSearchListJson.DataBean.InfoBean infoBean) {
        GGHttp<KugouInfoJson> ggHttpInfo   = new GGHttp<>(URL.KUGOU_INFO, KugouInfoJson.class);
        ggHttpInfo.setMethodType("GET");
        ggHttpInfo.add("acceptMp3", "1");
        ggHttpInfo.add("key", MyUtil.getMD5(infoBean.getHash() + "kgcloud").toLowerCase());
        ggHttpInfo.add("cmd", "3");
        ggHttpInfo.add("pid", "6");
        ggHttpInfo.add("hash", infoBean.getHash());
        ggHttpInfo.send(new Action1<KugouInfoJson>() {
            @Override
            public void call(KugouInfoJson kugouInfoJson) {
                searchActivity.playMusic(infoBean,kugouInfoJson.getUrl());
            }
        });
    }
}
