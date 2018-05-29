package cgg.com.threeapp.view.viewInterface;

import cgg.com.threeapp.model.bean.AddressBean;

/**
 * Created by Administrator on 2018-03-15.
 */

public interface IAddressView {
    void getSucceed(AddressBean addressBean);
    void updateSucceed(String string);
    void getDefaultSucceed(AddressBean.DataBean dataBean);
}
