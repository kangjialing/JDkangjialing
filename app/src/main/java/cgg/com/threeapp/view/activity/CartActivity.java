package cgg.com.threeapp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.CartBean;
import cgg.com.threeapp.model.bean.HomeBean;
import cgg.com.threeapp.presenter.presenterImpl.CartPresenterImpl;
import cgg.com.threeapp.presenter.presenterImpl.HomePresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.CommonUtil;
import cgg.com.threeapp.view.adapter.CartActivityExpandableAdapter;
import cgg.com.threeapp.view.adapter.HomeRecommendAdapter;
import cgg.com.threeapp.view.costomView.CustomGridLayoutManager;
import cgg.com.threeapp.view.costomView.MyExpandableListView;
import cgg.com.threeapp.view.viewInterface.ICartView;
import cgg.com.threeapp.view.viewInterface.IHomeView;

public class CartActivity extends AppCompatActivity implements View.OnClickListener, IHomeView, ICartView {
    private ImageView cartBack;
    private TextView cartEdit, cartIsNull,tuijiantitle;
    private RecyclerView cart_activity_tuijian;
    private MyExpandableListView cart_activity_expand;
    private CartActivityExpandableAdapter myExpandableAdapter;
    private CheckBox cart_allSelect;
    private static CartPresenterImpl cartPresenter;
    private static TextView cart_activity_jieSuan,cart_activity_heji;
    private LinearLayout cartActivity_EditView,cartActivity_noEditView;
    private boolean isEdit =false;
    private ArrayList<CartBean.DataBean.ListBean> orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        orderList = new ArrayList<>();
        cartBack = findViewById(R.id.aCartBack);
        cartEdit = findViewById(R.id.aCartEdit);
        tuijiantitle = findViewById(R.id.tuijiantitle);
        cart_activity_tuijian = findViewById(R.id.cart_activity_tuijian);
        cartIsNull = findViewById(R.id.cartIsNull);
        cart_activity_expand = findViewById(R.id.cart_activity_expand);
        cart_allSelect = findViewById(R.id.cart_activity_allSelect);
        cart_activity_jieSuan = findViewById(R.id.cart_activity_jieSuan);
        cart_activity_heji = findViewById(R.id.cart_activity_heji);
        cartActivity_EditView = findViewById(R.id.cartActivity_EditView);
        cartActivity_noEditView = findViewById(R.id.cartActivity_noEditView);


        // 设置监听
        cartBack.setOnClickListener(this);
        cartEdit.setOnClickListener(this);

        // 不能上下滑动的Recycler
        CustomGridLayoutManager customGridLayoutManager = new CustomGridLayoutManager(this, 2);
        customGridLayoutManager.setScrollEnabled(false);
        cart_activity_tuijian.setLayoutManager(customGridLayoutManager);

        // 请求推荐数据
        new HomePresenterImpl(this).getHomeBean(API.INDEX_URL, new HashMap<String, String>());
        cartPresenter = new CartPresenterImpl(this);
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aCartBack:
                finish();
                break;
            case R.id.aCartEdit:
                myEdit();
                break;
        }
    }

    @Override
    public void setHomeBean(HomeBean homeBean) {
        cart_activity_tuijian.setAdapter(new HomeRecommendAdapter(homeBean.getTuijian(), this));
    }

    @Override
    public void setCartBean(final CartBean cartBean) {
        if (null == cartBean) {
            cartIsNull.setVisibility(View.VISIBLE);
            cartEdit.setVisibility(View.GONE);
            cart_activity_expand.setVisibility(View.GONE);
        } else {
            if (cartBean.getData().size() > 0) {
                cartIsNull.setVisibility(View.GONE);
                cartEdit.setVisibility(View.VISIBLE);
                cart_activity_expand.setVisibility(View.VISIBLE);
                myExpandableAdapter = new CartActivityExpandableAdapter(cartBean, this);
                myExpandableAdapter.jiesuanPrice();
                List<CartBean.DataBean> data = cartBean.getData();
                for (int i = 0; i < data.size(); i++) {
                    boolean b = myExpandableAdapter.setParentChecked(data.get(i));
                    data.get(i).setCheck(b);
                }
                cart_activity_expand.setAdapter(myExpandableAdapter);
                // 默认展开所有的条目
                for (int i = 0; i < myExpandableAdapter.getGroupCount(); i++) {
                    cart_activity_expand.expandGroup(i);
                }

                // 设置子条目不可以收缩
                cart_activity_expand.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        // 返回true 设置不可以收缩
                        return true;
                    }
                });

                cart_allSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox v1 = (CheckBox) v;
                        myExpandableAdapter.setAllChildChecked(v1.isChecked());
                    }
                });

                // 是否全选
                boolean b = myExpandableAdapter.checkAllGoodsISelected();
                cart_allSelect.setChecked(b);

                cart_activity_jieSuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int mNum = 0;
                        orderList.clear();
                        for (int i = 0; i < cartBean.getData().size(); i++) {
                            CartBean.DataBean dataBean = cartBean.getData().get(i);
                            for (int j = 0; j < dataBean.getList().size(); j++) {
                                CartBean.DataBean.ListBean listBean = dataBean.getList().get(j);
                                if (listBean.getSelected() == 1) {
                                    orderList.add(listBean);
                                    mNum += listBean.getNum();
                                    Log.e("mNum",""+mNum);
                                }
                            }
                        }
                        if (mNum < 1) {
                            Toast.makeText(CartActivity.this, "您还没中商品", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent(CartActivity.this, CompleteOrderActivity.class);
                        intent.putExtra("orderList", orderList);
                        startActivity(intent);
                    }
                });
            }else{
                cartIsNull.setVisibility(View.VISIBLE);
                cartEdit.setVisibility(View.GONE);
                cart_activity_expand.setVisibility(View.GONE);
            }
        }

    }

    // 请求数据的方法
    public static void getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", CommonUtil.getIntValue("uid") + "");
        cartPresenter.getCartBean(API.SELECT_CART, map);
    }

    public static void setCount(int count) {
        cart_activity_jieSuan.setText("去结算(" + count + ")");
    }

    public static void setTotalPrice(double totalPric) {
        cart_activity_heji.setText("总价:￥ " + totalPric);
    }

    private void myEdit(){
        isEdit = !isEdit;
        if(isEdit){
            cartEdit.setText("完成");
            cartActivity_noEditView.setVisibility(View.GONE);
            cart_activity_tuijian.setVisibility(View.GONE);
            tuijiantitle.setVisibility(View.GONE);
            cartActivity_EditView.setVisibility(View.VISIBLE);
        }else{
            cartEdit.setText("编辑");
            cartActivity_noEditView.setVisibility(View.VISIBLE);
            cart_activity_tuijian.setVisibility(View.VISIBLE);
            tuijiantitle.setVisibility(View.VISIBLE);
            cartActivity_EditView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
