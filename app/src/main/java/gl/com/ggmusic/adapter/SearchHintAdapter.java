package gl.com.ggmusic.adapter;

import android.content.Context;

import gl.com.ggmusic.R;
import gl.com.ggmusic.bean.SearchHintData;

/**
 * Created by guilinlin on 16/4/19 09:45.
 * desc:
 */
public class SearchHintAdapter extends CommonAdapter<SearchHintData> {

    public SearchHintAdapter(Context context) {
        super(context, R.layout.item_search_hint);
    }

    @Override
    public void convert(ViewHolder viewHolder, SearchHintData data,int position) {
        viewHolder.setText(R.id.textView3,data.text);
        viewHolder.setImage(R.id.imageView,position%2==0?R.mipmap.playbar_btn_pause
        :R.mipmap.playbar_btn_play);
    }
}
