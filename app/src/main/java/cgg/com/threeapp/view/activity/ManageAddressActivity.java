package cgg.com.threeapp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.AddressBean;
import cgg.com.threeapp.presenter.presenterImpl.AddressPresenterImpl;
import cgg.com.threeapp.presenter.presenterInterface.IAddressPresenter;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.CommonUtil;
import cgg.com.threeapp.view.adapter.ManageAdapter;
import cgg.com.threeapp.view.viewInterface.IAddressView;

public class ManageAddressActivity extends AppCompatActivity implements View.OnClickListener, IAddressView {
    private TextView address_add;
    private RecyclerView address_recycler;
    private IAddressPresenter iAddressPresenter;
    private Map<String, String> map;
    private ImageView address_finish;
    private ProgressBar prograssBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        initView();
    }

    private void initView() {
        address_add = findViewById(R.id.address_add);
        address_recycler = findViewById(R.id.address_recycler);
        address_finish = findViewById(R.id.address_finish);
        prograssBar = findViewById(R.id.prograssBar);

        address_finish.setOnClickListener(this);
        address_add.setOnClickListener(this);

        map = new HashMap<>();
        address_recycler.setLayoutManager(new LinearLayoutManager(this));
        iAddressPresenter = new AddressPresenterImpl(this);
        getAddressList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_add:
                Intent intent = new Intent(this, AddAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.address_finish:
                finish();
                break;
        }
    }

    @Override
    public void getSucceed(AddressBean addressBean) {
        setPrograssBarIsShow(false);
        ManageAdapter manageAdapter = new ManageAdapter(addressBean.getData(), this, this);
        address_recycler.setAdapter(manageAdapter);
    }

    @Override
    public void updateSucceed(String string) {
        getAddressList();
    }

    @Override
    public void getDefaultSucceed(AddressBean.DataBean dataBean) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }

    public void getAddressList() {
        map.clear();
        map.put("uid", CommonUtil.getIntValue("uid") + "");
        iAddressPresenter.getAddressList(API.GET_ALL_ADDR_URL, map);
    }

    public void setDefaultAddress(int addressId) {
        map.clear();
        map.put("uid", CommonUtil.getIntValue("uid") + "");
        map.put("addrid", addressId + "");
        map.put("status", "1");
        iAddressPresenter.updateAddress(API.SET_DEFAULT_ADDR_URL, map);
    }


    public void setPrograssBarIsShow(boolean f) {
        if (f) {
            prograssBar.setVisibility(View.VISIBLE);
        } else {
            prograssBar.setVisibility(View.GONE);
        }
    }
}
