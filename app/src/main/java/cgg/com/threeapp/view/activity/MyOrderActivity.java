package cgg.com.threeapp.view.activity;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.OrderBean;
import cgg.com.threeapp.presenter.presenterImpl.OrderPresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.CommonUtil;
import cgg.com.threeapp.view.adapter.OrderAdapter;
import cgg.com.threeapp.view.viewInterface.IOrderView;

public class MyOrderActivity extends AppCompatActivity implements View.OnClickListener, IOrderView {
    private RadioButton all, dzf, yzf, yqx;
    private ImageView orderBack, select;
    private View pView;
    private PopupWindow popupWindow;
    private RadioGroup radio_group;
    private RecyclerView dd_listViwe;
    private OrderPresenterImpl orderPresenter;
    private Map<String, String> map;
    private final int DAIZHIFU = 0;
    private final int YIZHIFU = 1;
    private final int YIQUXIAO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initView();

        map = new HashMap<>();
        orderPresenter = new OrderPresenterImpl(this);
        dd_listViwe.setLayoutManager(new LinearLayoutManager(this));
        //添加Android自带的分割线
        dd_listViwe.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        getShowType();

    }

    private void initView() {
        all = findViewById(R.id.DD_all);
        dzf = findViewById(R.id.DD_DZF);
        yqx = findViewById(R.id.DD_YQX);
        yzf = findViewById(R.id.DD_YZF);
        select = findViewById(R.id.DD_selected);
        orderBack = findViewById(R.id.orderBack);
        radio_group = findViewById(R.id.radio_group);
        dd_listViwe = findViewById(R.id.dd_listViwe);

        pView = View.inflate(this, R.layout.order_select_layout, null);

        TextView yizhifu = pView.findViewById(R.id.yizhifu);
        TextView daizhifu = pView.findViewById(R.id.daizhifu);
        TextView yiquxiao = pView.findViewById(R.id.yiquxiao);
        TextView wodedingdan = pView.findViewById(R.id.wodedingdan);

        select.setOnClickListener(this);
        orderBack.setOnClickListener(this);

        yizhifu.setOnClickListener(this);
        daizhifu.setOnClickListener(this);
        yiquxiao.setOnClickListener(this);
        wodedingdan.setOnClickListener(this);

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.DD_all:
                        loadData(-1);
                        break;
                    case R.id.DD_DZF:
                        loadData(0);
                        break;
                    case R.id.DD_YQX:
                        loadData(2);
                        break;
                    case R.id.DD_YZF:
                        loadData(1);
                        break;
                }
            }
        });

        popupWindow = new PopupWindow(pView, 220, 350);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DD_selected:
                popupWindow.showAsDropDown(v);
                break;
            case R.id.wodedingdan:
                loadData(-11);
            case R.id.daizhifu:
                loadData(0);
                popupWindow.dismiss();
                break;
            case R.id.yiquxiao:
                loadData(2);
                popupWindow.dismiss();
                break;
            case R.id.yizhifu:
                loadData(1);
                popupWindow.dismiss();
                break;
            case R.id.orderBack:
                finish();
                break;
        }
    }

    @Override
    public void deleteSucceed(String string) {

    }

    @Override
    public void createSucceed(String string) {

    }

    @Override
    public void querySucceed(OrderBean orderBean) {
        dd_listViwe.setAdapter(new OrderAdapter(this, orderBean));
    }

    public void getShowType() {
        map.put("uid", CommonUtil.getIntValue("uid") + "");
        map.put("page", "1");
        int type = getIntent().getIntExtra("type", -2);
        loadData(type);
    }

    public void loadData(int type) {
        if (type == DAIZHIFU) {
            map.put("status", DAIZHIFU + "");
            dzf.setChecked(true);
        } else if (type == YIZHIFU) {
            map.put("status", "" + YIZHIFU);
            yzf.setChecked(true);
        } else if (type == YIQUXIAO) {
            map.put("status", "" + YIQUXIAO);
            yqx.setChecked(true);
        } else {
            map.remove("status");
            all.setChecked(true);
        }
        orderPresenter.queryOrder(API.ORDER_LIST_URL, map);
    }
}
