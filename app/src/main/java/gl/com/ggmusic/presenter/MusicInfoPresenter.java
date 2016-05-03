package gl.com.ggmusic.presenter;

import gl.com.ggmusic.bean.KugouImageHeadJson;
import gl.com.ggmusic.constants.Constants;
import gl.com.ggmusic.constants.URL;
import gl.com.ggmusic.music.MusicData;
import gl.com.ggmusic.network.GGHttp;
import gl.com.ggmusic.network.HttpResponse;
import gl.com.ggmusic.util.FileUtils;
import gl.com.ggmusic.util.KrcUtil;
import gl.com.ggmusic.util.MyUtil;
import gl.com.ggmusic.view.IMusicInfoActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by guilinlin on 16/5/3 22:38.
 */
public class MusicInfoPresenter {

    private IMusicInfoActivity musicInfoActivity;

    public MusicInfoPresenter(IMusicInfoActivity musicInfoActivity) {
        this.musicInfoActivity = musicInfoActivity;
    }

    /**
     * 获取歌手图片
     *
     * @param singer
     */
    public void setSingerHeadImage(String singer) {
        GGHttp<KugouImageHeadJson> ggHttpInfo = new GGHttp<>(URL.KUGOU_HEAD_IMAGE, KugouImageHeadJson.class);
        ggHttpInfo.setMethodType("GET");
        ggHttpInfo.add("size", "240");
        ggHttpInfo.add("cmd", "104");
        ggHttpInfo.add("type", "softhead");
        ggHttpInfo.add("singer", MyUtil.getURLEncoder(singer));
        ggHttpInfo.send(new Action1<KugouImageHeadJson>() {
            @Override
            public void call(KugouImageHeadJson kugouImageHeadJson) {
                if (kugouImageHeadJson.getStatus() == 1) {
                    musicInfoActivity.displayHeadImage(kugouImageHeadJson.getUrl());
                }
            }
        });
    }

    /**
     * 获取歌词
     *
     * @param musicData
     */
    public void setMusicLrc(final MusicData musicData) {
        Observable
                .just(1)
                .observeOn(Schedulers.io())
                .map(new Func1<Integer, HttpResponse>() {
                    @Override
                    public HttpResponse call(Integer integer) {
                        GGHttp ggHttp = new GGHttp(URL.KUGOU_KRC, String.class);
                        ggHttp.setMethodType("GET");
                        ggHttp.add("keyword", musicData.getSongNameEncoder());
                        ggHttp.add("timelength", musicData.getDurtion() + "000");
                        ggHttp.add("type", "1");
                        ggHttp.add("cmd", "200");
                        ggHttp.add("hash", musicData.getHash());
                        return ggHttp.getHttpResponseStream();
                    }

                })
                .observeOn(Schedulers.io())
                .map(new Func1<HttpResponse, String>() {
                    @Override
                    public String call(HttpResponse response) {
                        return KrcUtil.convt(response.getInputStream());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        FileUtils.writeFile(Constants.DOWNLOAD_PATH + musicData.getSongName() + ".lrc", s);
                    }
                });
    }
}
