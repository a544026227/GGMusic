package gl.com.ggmusic.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by guilinlin on 16/4/18 23:17.
 * dese:传递一个播放音乐的操作
 */
public class MusicData implements Parcelable {

    /**
     * 开始
     */
    public static final int START = 1;
    /**
     * 暂停
     */
    public static final int PAUSE = 2;

    /**
     * 播放音乐的状态，START和PAUSE
     */
    private int status;
    /**
     * 播放网址url
     */
    private String url ;
    /**
     * 播放本地Url
     */
    private String localUrl ;

    public MusicData( int status,String url) {
        this.url = url;
        this.status = status;
    }

    public MusicData(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.url);
        dest.writeString(this.localUrl);
    }

    protected MusicData(Parcel in) {
        this.status = in.readInt();
        this.url = in.readString();
        this.localUrl = in.readString();
    }

    public static final Parcelable.Creator<MusicData> CREATOR = new Parcelable.Creator<MusicData>() {
        @Override
        public MusicData createFromParcel(Parcel source) {
            return new MusicData(source);
        }

        @Override
        public MusicData[] newArray(int size) {
            return new MusicData[size];
        }
    };
}
