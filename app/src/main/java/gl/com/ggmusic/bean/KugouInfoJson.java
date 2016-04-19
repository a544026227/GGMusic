package gl.com.ggmusic.bean;

/**
 * Created by guilinlin on 16/4/19 17:17.
 * desc:
 */
public class KugouInfoJson {

    /**
     * fileName : 李夏怡 - 1,2,3,4
     * url : http://fs.web.kugou.com/06e96bcb40f8ad013c6203225688fad0/5715fd7a/G006/M09/1D/07
     * /Rg0DAFUI60WIGBCFAA3IL0DG914AABVwwDtn5QADchH760.m4a
     * fileSize : 903215
     * status : 1
     * extName : m4a
     * bitRate : 33692
     * timeLength : 214
     */

    private String fileName;
    private String url;
    private int fileSize;
    private int status;
    private String extName;
    private int bitRate;
    private int timeLength;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public int getBitRate() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public int getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(int timeLength) {
        this.timeLength = timeLength;
    }
}
