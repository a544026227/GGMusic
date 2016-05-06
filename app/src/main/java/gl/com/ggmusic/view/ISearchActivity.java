package gl.com.ggmusic.view;

import gl.com.ggmusic.bean.KugouSearchHintJson;
import gl.com.ggmusic.bean.KugouSearchListJson;

/**
 * Created by guilinlin on 16/5/3 21:35.
 */
public interface ISearchActivity {

    /**
     * 增加文字到流式布局中，热门搜索的流式布局
     *
     * @param name
     */
    void addTextViewToFlowLayout(String name);

    /**
     * 设置搜索结果列表的adapter
     *
     * @param kugouSearchListJson
     */
    void setSearchListAdapter(KugouSearchListJson kugouSearchListJson);

    /**
     * @param kugouSearchHintJson
     */
    void setSearchHintAdapter(KugouSearchHintJson kugouSearchHintJson);

    /**
     * 点击列表后播放音乐
     *
     * @param infoBean
     */
    void playMusic(final KugouSearchListJson.DataBean.InfoBean infoBean,String url);

}
