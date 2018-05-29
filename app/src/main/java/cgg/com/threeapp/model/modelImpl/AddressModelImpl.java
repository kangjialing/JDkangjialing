package cgg.com.threeapp.model.modelImpl;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import cgg.com.threeapp.model.bean.AddressBean;
import cgg.com.threeapp.model.modelInterface.IAddressModel;
import cgg.com.threeapp.presenter.presenterInterface.IAddressPresenter;
import cgg.com.threeapp.utils.CommonUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018-03-15.
 */

public class AddressModelImpl implements IAddressModel {
    private IAddressPresenter iAddressPresenter;

    public AddressModelImpl(IAddressPresenter iAddressPresenter) {
        this.iAddressPresenter = iAddressPresenter;
    }


    @Override
    public void getAddressList(String url, Map<String, String> map) {
        OkHttpUtil.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("getAddressList","onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String json = response.body().string();
                    final AddressBean addressBean = new Gson().fromJson(json, AddressBean.class);
                    CommonUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iAddressPresenter.getAddressListSucceed(addressBean);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void updateAddress(String url, Map<String, String> map) {
        OkHttpUtil.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("updateAddress","onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.isSuccessful()){
                    String json = response.body().string();
                    Log.e("onResponse",json);
                    try {
                        final String msg = new JSONObject(json).getString("msg");
                        CommonUtil.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iAddressPresenter.updateSucceed(msg);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void getDefaultAddress(String url, Map<String, String> map) {
        OkHttpUtil.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("getDefaultAddress","onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String json = response.body().string();
                    final AddressBean.DataBean dataBean = new Gson().fromJson(json, AddressBean.DataBean.class);
                    CommonUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iAddressPresenter.getDefaultSucceed(dataBean);
                        }
                    });
                }
            }
        });
    }
}
