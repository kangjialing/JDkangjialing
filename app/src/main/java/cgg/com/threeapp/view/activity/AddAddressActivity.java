package cgg.com.threeapp.view.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cgg.com.threeapp.model.bean.AddressBean;
import cgg.com.threeapp.model.bean.SQLAddrBean;
import cgg.com.threeapp.model.db.AddrDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cgg.com.threeapp.R;
import cgg.com.threeapp.presenter.presenterImpl.AddressPresenterImpl;
import cgg.com.threeapp.presenter.presenterInterface.IAddressPresenter;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.CommonUtil;
import cgg.com.threeapp.view.viewInterface.IAddressView;

public class AddAddressActivity extends AppCompatActivity implements IAddressView {
    private Spinner spinner1, spinner2, spinner3;
    private TextView fullAdd, consignee, row, tel, postcode, activity_title, updateText;
    private IAddressPresenter iAddressPresenter;
    private Map<String,String> map;
    private boolean isUpdate; // 标识是修改还新增
    private int addrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
        initSpinner();
        // 判断有木有传递的过来的值,有代表是要修改地址,没有代表新增地址
        AddressBean.DataBean dataBean = (AddressBean.DataBean) getIntent().getSerializableExtra("dataBean");
        if (dataBean == null) {
            activity_title.setText("新增收货地址");
            updateText.setText("添加收货地址");
            isUpdate = false;
        } else {
            activity_title.setText("修改收货地址");
            updateText.setText("修改收货地址");
            isUpdate = true;
            String[] split = dataBean.getAddr().split(" ");
            addrid = dataBean.getAddrid();
            String mobile = dataBean.getMobile();
            String name = dataBean.getName();

            fullAdd.setText(split[4]);
            row.setText(split[3]);
            tel.setText(mobile);
            consignee.setText(name);
        }

        // public container
        map = new HashMap<>();

        // init IAddressPresenter
        iAddressPresenter = new AddressPresenterImpl(this);
    }

    private void initSpinner() {
        // get SQLite Data
        final List<SQLAddrBean> addr = getAddr(0);
        String[] names = getNames(addr);

        // set adapter and selectedListener
        spinner1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names));

        // Level 3 linkage
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int cityId = addr.get(position).getId();
                Log.e("getId", addr.get(position).getId() + "");
                Log.e("getName", addr.get(position).getName());
                final List<SQLAddrBean> addr1 = getAddr(cityId);
                String[] names1 = getNames(addr1);
                spinner2.setAdapter(new ArrayAdapter<String>(AddAddressActivity.this, android.R.layout.simple_spinner_item, names1));
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int districtId = addr1.get(position).getId();
                        Log.e("AAA", addr1.get(position).getName());
                        Log.e("getId", addr1.get(position).getId() + "");
                        List<SQLAddrBean> districts = getAddr(districtId);
                        String[] districtNames = getNames(districts);
                        spinner3.setAdapter(new ArrayAdapter<String>(AddAddressActivity.this, android.R.layout.simple_spinner_item, districtNames));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initView() {
        // init view
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        fullAdd = findViewById(R.id.add_address_fullAdd);
        consignee = findViewById(R.id.add_address_consignee);
        activity_title = findViewById(R.id.activity_title);
        postcode = findViewById(R.id.add_address_postcode);
        fullAdd = findViewById(R.id.add_address_fullAdd);
        row = findViewById(R.id.add_address_row);
        tel = findViewById(R.id.add_address_tel);
        updateText = findViewById(R.id.updateText);
    }

    private List<SQLAddrBean> getAddr(int id) {
        SQLiteDatabase sqLiteDatabase = new AddrDao(this).initAddrDao();
        String str = "parentid = " + id;
        List<SQLAddrBean> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("bma_regions", null, "parentid = " + id, null, null, null, null);
        while (cursor.moveToNext()) {//能移动到下一个证明有数据

            int regionid = cursor.getInt(cursor.getColumnIndex("regionid"));//获取数据的方法里面的参数是列的角标...根据列名去获取列的角标
            String name = cursor.getString(cursor.getColumnIndex("name"));
            list.add(new SQLAddrBean(name, regionid));
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }

    public String[] getNames(List<SQLAddrBean> list) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            strings.add(list.get(i).getName());
        }
        return strings.toArray(new String[list.size()]);
    }

    public void addAddress(View view) {
        String userProvince = spinner1.getSelectedItem().toString().trim();
        String userCity = spinner2.getSelectedItem().toString().trim();
        String userDistrict = spinner3.getSelectedItem().toString().trim();
        String fullAddStr = fullAdd.getText().toString();
        String consigneeStr = consignee.getText().toString();
        String rowStr = row.getText().toString();
        String telStr = tel.getText().toString();
        String postcodeStr = postcode.getText().toString();

        if (consigneeStr == null || "".equals(consigneeStr)) {
            Toast.makeText(this, "收货人不能为空", Toast.LENGTH_SHORT).show();
        } else if (telStr == null || "".equals(telStr)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
        } else if (telStr.length() != 11 || !telStr.matches("[0-9]+")) {
            Toast.makeText(this, "手机号为11位纯数字", Toast.LENGTH_SHORT).show();
        } else if (rowStr == null || "".equals(rowStr)) {
            Toast.makeText(this, "街道信息不能为空", Toast.LENGTH_SHORT).show();
        } else if (fullAddStr == null || "".equals(fullAddStr)) {
            Toast.makeText(this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
        } else {

            // remove black
            fullAddStr = fullAddStr.trim();
            rowStr = rowStr.trim();
            telStr = telStr.trim();
            consigneeStr = consigneeStr.trim();

            // user's address
            String userAddress = userProvince + " " + userCity + " " + userDistrict + " " + rowStr + " " + fullAddStr;
            // Log.e("userAddress",consigneeStr+ " " + userAddress + telStr);

            // padding data
            map.clear();
            map.put("addr", userAddress);
            map.put("mobile", telStr+"");
            map.put("name", consigneeStr);
            map.put("uid", CommonUtil.getIntValue("uid") + "");

            if (isUpdate) {
                map.remove("mobile");
                map.put("addrid", addrid + "");

                iAddressPresenter.updateAddress(API.UPDATE_ADDR_URL, map);
            } else {
                // new address
                iAddressPresenter.updateAddress(API.ADD_NEW_ADDR_URL, map);
            }

        }


    }

    public void rollback(View view) {
        finish();
    }

    @Override
    public void getSucceed(AddressBean addressBean) {

    }

    @Override
    public void updateSucceed(String string) {
        // toast callback result
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

        // goto ManageAddressActivity
        Intent intent = new Intent(this, ManageAddressActivity.class);
        startActivity(intent);
    }


    @Override
    public void getDefaultSucceed(AddressBean.DataBean dataBean) {

    }


}
