package gl.com.ggmusic.network;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by guilinlin on 16/4/16 22:50.
 * email 973635949@qq.com<br/>
 * desc：网络请求库
 */
public class GGHttp<T extends Object> {

    /**
     * 请求地址
     */
    private String url = "";
    /**
     * 参数的键值对类型
     */
    private Map<String, String> body;
    /**
     * 发送的请求的对象
     */
    private HttpRequest request;
    /**
     * json映射的实体对象
     */
    private Class className;
    /**
     * 请求方式，默认post请求
     */
    private String methodType = "POST";

    public GGHttp(String url, Class className) {
        this.url = url;
        this.className = className;
        this.body = new HashMap<>();
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public Observable<T> send() {
        return Observable
                .just(1)
                .observeOn(Schedulers.io())
                .map(new Func1<Integer, T>() {
                    @Override
                    public T call(Integer httpRequest) {
                        HttpResponse response = getHttpResponse();
                        T t = (T) new Gson().fromJson(response.getResponseContent(), className);
                        return t;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void send(Action1<T> action1) {
        send().subscribe(action1);
    }


    /**
     * 直接连接网络并获取返回数据，注意，该方法是一个耗时方法
     */
    public HttpResponse getHttpResponse() {
        request = new HttpRequest(url, methodType, body, null);
        return request.getHttpResponse();
    }

    /**
     * 对外提供一个增加参数的方法
     *
     * @param key
     * @param value
     */
    public void add(String key, String value) {
        body.put(key, value);
    }


}
