package gl.com.ggmusic.network;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 * Created by guilinlin on 16/4/17 10:48.
 * desc:
 */
public class HttpRequest {

    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求类型，POST或者GET
     */
    private String requestMethod;
    /**
     * 请求参数的键值对
     */
    private Map<String, String> body;
    /**
     * 请求头
     */
    private Map<String, String> header;
    /**
     * 网络请求的主体
     */
    private HttpURLConnection connection;

    /**
     * @param url
     *         网络请求地址
     * @param requestMethod
     *         请求方式
     * @param body
     *         请求参数，null表示不传
     * @param header
     *         请求头，null表示不传
     */
    public HttpRequest(@NonNull String url, @NonNull String requestMethod, Map<String, String> body, Map<String, String> header) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.body = body;
        this.header = header;
    }

    /**
     * 初始化网络请求
     */
    private void initConnection() {
        PrintWriter out = null;
        try {
            String params = Util.jointStringFromMap(body);//将参数键值对转化成字符串
            if (requestMethod.equals("GET") && body != null) {
                url += "?" + params;
            }
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(4000);
            connection.setRequestMethod(requestMethod);

            //如果是post请求并且body不为空
            if (requestMethod.equals("POST") && body != null) {
                out = new PrintWriter(connection.getOutputStream());//将参数写入输出流
                out.print(params);
                out.flush();
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    public HttpResponse getHttpResponse() {

        HttpResponse httpResponse = new HttpResponse();

        initConnection();

        InputStream is = null;
        PrintWriter out = null;
        try {
            System.out.println(connection.getURL());
            connection.connect();

            //获取网络请求结果，并保存至httpResponse对象中
            httpResponse.setResponseCode(connection.getResponseCode());
            httpResponse.setResponseMessage(connection.getResponseMessage());
            if (httpResponse.getResponseCode() == 200) {
                is = connection.getInputStream();
                httpResponse.setInputStream(is);
                httpResponse.setResponseContent(Util.inputSteamToString(is));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
        }
        System.out.println(httpResponse.toString());
        return httpResponse;
    }

    public HttpResponse getHttpResponseInputSream() {

        HttpResponse httpResponse = new HttpResponse();

        initConnection();

        InputStream is = null;
        try {
            System.out.println(connection.getURL());
            connection.connect();

            //获取网络请求结果，并保存至httpResponse对象中
            httpResponse.setResponseCode(connection.getResponseCode());
            httpResponse.setResponseMessage(connection.getResponseMessage());
            if (httpResponse.getResponseCode() == 200) {
                is = connection.getInputStream();
                httpResponse.setInputStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }
}
