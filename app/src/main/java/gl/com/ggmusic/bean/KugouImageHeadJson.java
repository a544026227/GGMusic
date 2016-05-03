package gl.com.ggmusic.bean;

/**
 * Created by guilinlin on 16/5/3 22:43.
 */
public class KugouImageHeadJson {

    /**
     * status : 1
     * errcode : 0
     * error :
     * singer : 周杰伦
     * url : http://singerimg.kugou.com/uploadpic/pass/softhead/120/20160109/20160109182037417555.jpg
     */

    private int status;
    private int errcode;
    private String error;
    private String singer;
    private String url;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
