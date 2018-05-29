package cgg.com.threeapp.presenter.presenterInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.AddressBean;

/**
 * Created by Administrator on 2018-03-15.
 */

public interface IAddressPresenter {

    void getAddressList(String url, Map<String,String>map);
    void updateAddress(String url, Map<String,String>map);
    void getDefaultAddress(String url, Map<String,String>map);

    void updateSucceed(String string);
    void getAddressListSucceed(AddressBean addressBean);
    void getDefaultSucceed(AddressBean.DataBean dataBean);
}
