package cgg.com.threeapp.model.modelImpl;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import cgg.com.threeapp.model.bean.OrderBean;
import cgg.com.threeapp.model.modelInterface.IOrderModel;
import cgg.com.threeapp.presenter.presenterInterface.IOrderPresenter;
import cgg.com.threeapp.utils.CommonUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018-03-11.
 */

public class OrderModelImpl implements IOrderModel {
    private IOrderPresenter iOrderPresenter;

    public OrderModelImpl(IOrderPresenter iOrderPresenter) {
        this.iOrderPresenter = iOrderPresenter;
    }

    @Override
    public void createOrder(String url, Map<String, String> map) {
        OkHttpUtil.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String string = response.body().string();
                    try {
                        final String msg = new JSONObject(string).getString("msg");
                        CommonUtil.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iOrderPresenter.createSucceed(msg);
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
    public void queryOrder(String url, Map<String, String> par) {
        OkHttpUtil.doPost(url, par, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String string = response.body().string();
                    final OrderBean orderBean = new Gson().fromJson(string, OrderBean.class);
                    CommonUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iOrderPresenter.querySucceed(orderBean);
                        }
                    });

                }
            }
        });
    }

    @Override
    public void delectCart(String url, Map<String, String> map) {
        OkHttpUtil.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String string = response.body().string();
                    try {
                        final String msg = new JSONObject(string).getString("msg");
                        CommonUtil.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iOrderPresenter.deleteSucceed(msg);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
