package cgg.com.threeapp.presenter.presenterImpl;

import java.util.Map;

import cgg.com.threeapp.model.bean.AddressBean;
import cgg.com.threeapp.model.modelImpl.AddressModelImpl;
import cgg.com.threeapp.model.modelInterface.IAddressModel;
import cgg.com.threeapp.presenter.presenterInterface.IAddressPresenter;
import cgg.com.threeapp.view.viewInterface.IAddressView;

/**
 * Created by Administrator on 2018-03-15.
 */

public class AddressPresenterImpl implements IAddressPresenter {
    private IAddressModel iAddressModel;
    private IAddressView iAddressView;

    public AddressPresenterImpl(IAddressView iAddressView) {
        this.iAddressView = iAddressView;
        iAddressModel = new AddressModelImpl(this);
    }

    @Override
    public void getAddressList(String url, Map<String, String> map) {
        iAddressModel.getAddressList(url,map);
    }

    @Override
    public void updateAddress(String url, Map<String, String> map) {
        iAddressModel.updateAddress(url,map);
    }

    @Override
    public void getDefaultAddress(String url, Map<String, String> map) {
        iAddressModel.getDefaultAddress(url,map);
    }

    @Override
    public void updateSucceed(String string) {
        iAddressView.updateSucceed(string);
    }

    @Override
    public void getAddressListSucceed(AddressBean addressBean) {
        iAddressView.getSucceed(addressBean);
    }

    @Override
    public void getDefaultSucceed(AddressBean.DataBean dataBean) {
        iAddressView.getDefaultSucceed(dataBean);
    }
}
