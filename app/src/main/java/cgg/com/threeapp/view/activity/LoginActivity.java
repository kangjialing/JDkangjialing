package cgg.com.threeapp.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.DefaultAddr;
import cgg.com.threeapp.model.bean.UserBean;
import cgg.com.threeapp.presenter.presenterImpl.LoginPresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.CommonUtil;
import cgg.com.threeapp.view.viewInterface.ILoginView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {
    private ImageView mine_cha, qqLogin;
    private EditText login_user_mobile, login_user_pwd;
    private TextView login_mobile_signIn;
    private Button login_button;
    private LoginPresenterImpl loginPresenter;
    private UMAuthListener umAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initPremiss();
        qqLogin = findViewById(R.id.qqLogin);
        mine_cha = findViewById(R.id.mine_cha);
        login_user_mobile = findViewById(R.id.login_user_mobile);
        login_user_pwd = findViewById(R.id.login_user_pwd);
        login_button = findViewById(R.id.login_button);
        login_mobile_signIn = findViewById(R.id.login_mobile_signIn);
        mine_cha = findViewById(R.id.mine_cha);

        mine_cha.setOnClickListener(this);
        login_mobile_signIn.setOnClickListener(this);
        login_button.setOnClickListener(this);
        qqLogin.setOnClickListener(this);

        loginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_cha:
                finish();
                break;
            case R.id.login_mobile_signIn:
                Intent intent = new Intent(this, SIgnInActivity.class);
                startActivity(intent);
                break;
            case R.id.login_button:
                Toast.makeText(this, "login_button", Toast.LENGTH_SHORT).show();
                HashMap<String, String> map = new HashMap<>();
                map.put("mobile", login_user_mobile.getText().toString());
                map.put("password", login_user_pwd.getText().toString());
                loginPresenter.checkUserInfo(API.LOGIN_URL, map);
                break;
            case R.id.qqLogin:
                getLogin();
                break;
        }
    }

    @Override
    public void loginSucceed(UserBean userBean) {
        Toast.makeText(this, userBean.getMsg(), Toast.LENGTH_SHORT).show();
        UserBean.DataBean data = userBean.getData();
        CommonUtil.setSharedBoolean("isLogin", true); // 存储状态
        CommonUtil.setSharedInt("uid", data.getUid()); // 存储uid
        String iconUrl = data.getIcon();
        String userName = data.getUsername();
        if (null == iconUrl && "".equals(iconUrl)) {
            iconUrl = "isNull";
        }
        if (null == userName && "".equals(userName)) {
            iconUrl = "isNull";
        }
        CommonUtil.setSharedString("icon", iconUrl);
        CommonUtil.setSharedString("userName", userName);

        // 登录成功后 获取默认的收货地址和收货人
        HashMap<String, String> m = new HashMap<>();
        m.put("uid", data.getUid() + "");
        loginPresenter.getAddrData(API.GET_DEFAULT_ADDR_URL, m);
        finish();
    }

    @Override
    public void loginFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getaddrSucceed(DefaultAddr defaultAddr) {
        // 收货地址
        CommonUtil.setSharedString("userDefaultAddress", defaultAddr.getData().getAddr());
        // 收货人
        CommonUtil.setSharedString("userConsignee", defaultAddr.getData().getName());
    }

    @Override
    public void getaddrFail(String msg) {

    }

    // 授权登陆
    private void getLogin() {
        UMShareAPI mShareAPI = UMShareAPI.get(this);
        mShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void initPremiss() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }

        //授权成功了。map里面就封装了一些qq信息
        umAuthListener = new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.e("onStart", "onStart");
            }

            //授权成功了。map里面就封装了一些qq信息
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                StringBuffer sb = new StringBuffer();
                for (String key : map.keySet()) {
                    sb.append(map.get(key) + ",");
                    Log.e(key, map.get(key));
                }
                Toast.makeText(LoginActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.e("onError", "onError");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.e("onCancel", "onCancel");
            }
        };
    }
}
