package cgg.com.threeapp.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.CartBean;
import cgg.com.threeapp.model.bean.OrderBean;
import cgg.com.threeapp.presenter.presenterImpl.OrderPresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.CommonUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import cgg.com.threeapp.view.adapter.ConfirmOrderAdapter;
import cgg.com.threeapp.view.viewInterface.IOrderView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CompleteOrderActivity extends AppCompatActivity implements IOrderView {
    private TextView co_address, co_price, orderContent,shouhuoren;
    private RecyclerView order_list;
    private ArrayList<CartBean.DataBean.ListBean> orderList;
    private double totalPrice;
    private int count;
    private OrderPresenterImpl orderPresenter;
    private Map<String, String> mmap;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        orderPresenter = new OrderPresenterImpl(this);
        mmap = new HashMap<>();
        co_price = findViewById(R.id.co_price);
        order_list = findViewById(R.id.order_list);
        orderContent = findViewById(R.id.orderContent);
        co_address = findViewById(R.id.co_address);
        shouhuoren = findViewById(R.id.shouhuoren);

        getDefaultAddress();

        co_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteOrderActivity.this, ManageAddressActivity.class);
                startActivity(intent);
            }
        });
        getOrderData();
        setOrderAdapter();
        calculatePrice();

    }

    private void calculatePrice() {
        totalPrice = 0;
        for (int i = 0; i < orderList.size(); i++) {
            CartBean.DataBean.ListBean listBean = orderList.get(i);
            count += listBean.getNum();
            totalPrice += (listBean.getBargainPrice() * listBean.getNum());
        }
        orderContent.setText("共" + count + "件商品,合计:￥" + totalPrice);
        co_price.setText("合计:￥" + totalPrice);
    }

    private void setOrderAdapter() {
        order_list.setLayoutManager(new LinearLayoutManager(this));
        order_list.setAdapter(new ConfirmOrderAdapter(this, orderList));
    }

    public void orderback1(View view) {
        finish();
    }

    public void getOrderData() {
        orderList = (ArrayList<CartBean.DataBean.ListBean>) getIntent().getSerializableExtra("orderList");
    }


    public void completeOrder(View view) {
        // 1.判断有木有木有默认地址
        if("isNull".equals(CommonUtil.getStringValue("userDefaultAddress"))){
            Toast.makeText(this, "请添加默认收货地址", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2.创建订单
        for (int i = 0; i < orderList.size(); i++) {
            mmap.clear();
            mmap.put("uid", CommonUtil.getIntValue("uid") + "");
            mmap.put("price", orderList.get(i).getBargainPrice() + "");
            orderPresenter.createOrder(API.CREATE_ORDER_URL, mmap);
        }
        // 3.清空购物车
        index = 0;
        deleleCart();
    }

    private String[] names = {"支付宝", "微信", "银行卡", "钱包", "白条"};

    public void deleleCart() {
        mmap.clear();
        CartBean.DataBean.ListBean listBean = orderList.get(index);
        mmap.put("uid", CommonUtil.getIntValue("uid") + "");
        mmap.put("pid", String.valueOf(listBean.getPid()));
        OkHttpUtil.doPost(API.DELETE_CART_URL, mmap, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                index++;
                if (index < orderList.size()) {
                    deleleCart();
                } else {
                    CommonUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 删除购物车后 调用支付功能 是否选择支付
                            new AlertDialog.Builder(CompleteOrderActivity.this)
                                    .setTitle("请选择支付平台")
                                    .setSingleChoiceItems(names,0,null)
                                    .setNegativeButton("确认支付", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            index = 0;
                                            updateOrderState(1);

                                        }
                                    })
                                    .setPositiveButton("取消支付", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            index = 0;
                                            updateOrderState(0);
                                        }
                                    })
                                    .create().show();

                        }
                    });


                }
            }
        });
    }

    @Override
    public void deleteSucceed(String string) {
        Log.e("deleteSucceed", string + "");
    }

    @Override
    public void createSucceed(String string) {
        Log.e("createSucceed", string + "");
    }

    @Override
    public void querySucceed(OrderBean orderBean) {

    }

    public void updateOrderState(int state) {

        CommonUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 跳转到查看订单页面
                Intent intent = new Intent(CompleteOrderActivity.this, MyOrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void updateAddress(View view) {

    }

    private void getDefaultAddress(){
        // 获取默认的地址
        String userDefaultAddress = CommonUtil.getStringValue("userDefaultAddress");
        if("isNull".equals(userDefaultAddress)){ // 标识有木有默认的地址
            co_address.setText("请添加默认地址");
        }else{
            co_address.setText("默认地址: "+userDefaultAddress);
            String userConsignee = CommonUtil.getStringValue("userConsignee");
            shouhuoren.setText("收货人: "+userConsignee);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDefaultAddress();
    }
}
