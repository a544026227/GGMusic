package gl.com.ggmusic.adapter;

import android.content.Context;

import gl.com.ggmusic.R;
import gl.com.ggmusic.bean.KugouSearchHintJson;

/**
 * Created by guilinlin on 16/4/19 09:45.
 * desc:
 */
public class SearchHintAdapter extends CommonAdapter<KugouSearchHintJson.DataBean> {

    public SearchHintAdapter(Context context) {
        super(context, R.layout.item_search_hint);
    }

    @Override
    public void convert(ViewHolder viewHolder, KugouSearchHintJson.DataBean data, int position) {
        viewHolder.setText(R.id.textView3, data.getKeyword());
    }
}
