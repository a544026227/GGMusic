package gl.com.ggmusic.bean;

import java.util.List;

/**
 * Created by guilinlin on 16/4/19 14:02.
 * desc:酷狗搜索结果的Json实体类
 */
public class KugouSearchHintJson {

    /**
     * recordcount : 10
     * data : [{"songcount":480,"searchcount":0,"keyword":"嘻嘻"},{"songcount":3,"searchcount":0,"keyword":"嘻嘻哈哈闯世界"},{"songcount":44,
     * "searchcount":0,"keyword":"嘻嘻哈哈过新年"},{"songcount":31,"searchcount":0,"keyword":"嘻嘻哈哈过新年纯音乐"},{"songcount":6,"searchcount":0,
     * "keyword":"嘻嘻哈哈接财神"},{"songcount":480,"searchcount":0,"keyword":"嘻嘻哈哈"},{"songcount":480,"searchcount":0,"keyword":"嘻嘻嘻"},
     * {"songcount":86,"searchcount":0,"keyword":"嘻嘻哈哈 七公主"},{"songcount":11,"searchcount":0,"keyword":"嘻嘻酒吧"},{"songcount":46,
     * "searchcount":0,"keyword":"嘻嘻刷刷花儿乐队"}]
     * status : 1
     * error :
     * errcode : 0
     */

    private int recordcount;
    private int status;
    private String error;
    private int errcode;
    /**
     * songcount : 480
     * searchcount : 0
     * keyword : 嘻嘻
     */

    private List<DataBean> data;

    public int getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(int recordcount) {
        this.recordcount = recordcount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int songcount;
        private int searchcount;
        private String keyword;

        public int getSongcount() {
            return songcount;
        }

        public void setSongcount(int songcount) {
            this.songcount = songcount;
        }

        public int getSearchcount() {
            return searchcount;
        }

        public void setSearchcount(int searchcount) {
            this.searchcount = searchcount;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
