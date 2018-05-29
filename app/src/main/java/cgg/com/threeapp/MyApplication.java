package cgg.com.threeapp;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.dash.zxinglibrary.activity.ZXingLibrary;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * author: Wanderer
 * date:   2018/2/23 18:59
 * email:  none
 */

public class MyApplication extends Application{

    {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }


    private static int myTid;
    private static Handler myHandler;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        myTid = Process.myTid();
        myHandler = new Handler();
        context = this;
        ZXingLibrary.initDisplayOpinion(this);
    }

    public static int getMyTid() {
        return myTid;
    }

    public static Handler getMyHandler() {
        return myHandler;
    }

    public static Context getContext() {
        return context;
    }
}
