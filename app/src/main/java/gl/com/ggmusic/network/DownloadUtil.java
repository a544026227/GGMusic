package gl.com.ggmusic.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import gl.com.ggmusic.constants.Constants;
import gl.com.ggmusic.util.MyUtil;

/**
 * Created by guilinlin on 16/4/18 20:47.
 * email 973635949@qq.com
 */
public class DownloadUtil {

    /**
     * 下载文件
     *
     * @param downloadUrl
     *         下载地址
     * @param extension
     *         文件的后缀名，null表示没有
     */
    public void download(final String downloadUrl, final String extension, final DownloadListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startDownload(downloadUrl, extension, listener);
            }
        }).start();

    }

    private void startDownload(String downloadUrl, String extension, DownloadListener listener) {
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
                os = new FileOutputStream(getFile(downloadUrl, extension));
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
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

    public File getFile(String url, String extension) {
        File dir = new File("/a/a");
        System.out.println("dir:" + Constants.DOWNLOAD_PATH);
        if (!dir.isDirectory() || !dir.exists()) {
            System.out.println(dir.mkdirs());
        }

        StringBuffer sb = new StringBuffer(Constants.DOWNLOAD_PATH).append("/");
        sb.append(MyUtil.getMD5(url));
        if (extension != null)

        {
            sb.append('.').append(extension);
        }

        File file = new File(sb.toString());
        System.out.println("Filepath:" + sb.toString());
        if (!file.exists())

        {
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
        public void process(int hasDownloadSize, int totalSize, float percent);
    }
}
