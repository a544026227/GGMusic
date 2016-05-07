package gl.com.ggmusic.network;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import gl.com.ggmusic.constants.Constants;
import gl.com.ggmusic.util.MyUtil;

/**
 * Created by guilinlin on 16/4/18 20:47.
 */
public class DownloadUtil {

    private String downloadUrl;
    private String extension;
    private String fileName;

    /**
     * @param downloadUrl
     *         下载地址
     * @param fileName
     *         文件名，null将使用下载地址的MD5作为文件名
     * @param extension
     *         文件后缀名,比如 mp3,mp4,null表示没有文件后缀名
     */
    public DownloadUtil(@NonNull String downloadUrl, String fileName, String extension) {
        this.downloadUrl = downloadUrl;
        this.extension = extension;
        this.fileName = fileName;
    }

    /**
     * 下载文件
     */
    public void start(DownloadListener listener) {
        InputStream is = null;
        FileOutputStream os = null;
        int responseCode;
        int totalSize;//文件总大小
        int hasDownloadSize = 0;//已下载的大小

        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(4000);
            responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                totalSize = connection.getContentLength();
                is = connection.getInputStream();
                os = new FileOutputStream(getFile());
                int len;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                    hasDownloadSize += len;
                    //把相关数据传给回调
                    listener.process(hasDownloadSize, totalSize, hasDownloadSize / (float) totalSize);
                }
                os.flush();
                is.close();
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取一个文件对象
     */
    public File getFile() {
        StringBuilder sb = new StringBuilder(Constants.DOWNLOAD_PATH).append("/");
        if (fileName == null) {//如果没有传入文件名使用网址的MD5作为文件名
            sb.append(MyUtil.getMD5(downloadUrl));
        } else {
            sb.append(fileName);
        }
        if (extension != null) {//如果传入的后缀名为空则不要后缀名
            sb.append('.').append(extension);
        }

        File file = new File(sb.toString());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    /**
     * 下载的接口
     */
    public interface DownloadListener {
        /**
         * @param hasDownloadSize
         *         已经下载的大小
         * @param totalSize
         *         文件的size
         * @param percent
         *         已下载所占的百分比
         */
        void process(int hasDownloadSize, int totalSize, float percent);
    }
}
