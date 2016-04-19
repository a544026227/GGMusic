package gl.com.ggmusic.adapter;

import android.content.Context;
import android.text.TextUtils;

import gl.com.ggmusic.R;
import gl.com.ggmusic.bean.KugouSearchListJson;

/**
 * Created by guilinlin on 16/4/19 20:01.
 * desc:
 */
public class SearchListAdapter extends CommonAdapter<KugouSearchListJson.DataBean.InfoBean> {

    public SearchListAdapter(Context context) {
        super(context, R.layout.item_search_list);
    }

    @Override
    public void convert(ViewHolder viewHolder, KugouSearchListJson.DataBean.InfoBean data, int position) {
        viewHolder.setText(R.id.fileNameTextView, data.getFilename());
        viewHolder.setText(R.id.singerNameTextView, data.getSingername());
        if (!TextUtils.isEmpty(data.getOthername()))
            viewHolder.setText(R.id.otherNameTextView, " - " + data.getOthername());
    }
}
