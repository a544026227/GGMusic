package gl.com.ggmusic.music;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import gl.com.ggmusic.bean.MusicData;
import gl.com.ggmusic.service.PlayMusicService;

/**
 * Created by guilinlin on 16/4/19 17:46.
 * desc:
 */
public class MusicUtil {

    public static void startMusic(Context context ,MusicData musicData) {
        Intent service = new Intent(context.getApplicationContext(), PlayMusicService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG_START_MUSIC, musicData);
        service.putExtras(bundle);
        //同时start和bindService，不然后台音乐会自动退出
        context.getApplicationContext().startService(service);
        context.getApplicationContext().bindService
                (service, connection, Activity.BIND_AUTO_CREATE);
    }
}
