package cgg.com.threeapp.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.DetailBean;
import cgg.com.threeapp.presenter.presenterImpl.DetailPresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.CommonUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import cgg.com.threeapp.view.viewInterface.IDetailView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity implements IDetailView, View.OnClickListener {
    TextView detail_bargainPrice, detail_price, detail_title, lookCart, addCart;
    ImageView detail_icon;
    private int pid = -1;
    private ArrayList<String> imageUrls;
    private UMShareListener umShareListener;
    private String iconUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initPremiss();
        String pid = getIntent().getStringExtra("pid");

        DetailPresenterImpl detailPresenter = new DetailPresenterImpl(this);
        HashMap<String, String> map = new HashMap<>();
        map.put("pid", pid);
        detailPresenter.getDetailBean(API.INDEX_DETAIL_URL, map);

        detail_bargainPrice = findViewById(R.id.detail_bargainPrice);
        detail_icon = findViewById(R.id.detail_icon);
        detail_price = findViewById(R.id.detail_price);
        detail_title = findViewById(R.id.detail_title);
        addCart = findViewById(R.id.addCart);
        lookCart = findViewById(R.id.lookCart);

        addCart.setOnClickListener(this);
        lookCart.setOnClickListener(this);
        detail_icon.setOnClickListener(this);
    }

    @Override
    public void setDetailBean(DetailBean detailBean) {
        DetailBean.DataBean data = detailBean.getData();
        detail_bargainPrice.setText("折扣价: " + data.getBargainPrice() + "元");
        detail_price.setText("原价: " + data.getPrice() + "元");
        detail_title.setText(data.getTitle());
        pid = data.getPid();
        String[] split = data.getImages().split("\\|");
        iconUrl = split[0];
        Glide.with(this).load(iconUrl).into(detail_icon);

        imageUrls = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            imageUrls.add(split[i]);
        }
    }

    @Override
    public void onClick(View v) {

        if (CommonUtil.getBooleanValue("isLogin")) {
            switch (v.getId()) {
                case R.id.lookCart:
                    Intent intent = new Intent(this, CartActivity.class);
                    startActivity(intent);
                    break;
                case R.id.addCart:
                    Toast.makeText(this, "添加购物车成功", Toast.LENGTH_SHORT).show();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("uid", CommonUtil.getIntValue("uid") + "");
                    map.put("pid", pid + "");
                    OkHttpUtil.doPost(API.ADD_CART_URL, map, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                    break;
            }
        } else {
            new AlertDialog.Builder(DetailActivity.this)
                    .setTitle("消息提示")
                    .setMessage("您还没登录,现在去登录？")
                    .setNegativeButton("稍后再去", null)
                    .setPositiveButton("现在就去", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                            DetailActivity.this.startActivity(intent);
                        }
                    }).show();
        }

        if (R.id.detail_icon == v.getId()) {
            Intent intent1 = new Intent(this, ImageScaleActivity.class);
            intent1.putStringArrayListExtra("imageUrls", imageUrls);
            intent1.putExtra("position", 0);
            startActivity(intent1);
        }
    }


    public void detail_back(View view) {
        finish();
    }

    // 带分享面板
    private void getDaiMianBan() {
        new ShareAction(this)
                .withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener)
                .withMedia(new UMImage(this, iconUrl))
                .open();
    }

    private void initPremiss() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }

        umShareListener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.e("onStart", "onStart");
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Log.e("onResult", "onResult");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Log.e("onError", "onError");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Log.e("onCancel", "onCancel");
            }
        };
    }


    public void mShared(View view) {
        getDaiMianBan();
    }
}
