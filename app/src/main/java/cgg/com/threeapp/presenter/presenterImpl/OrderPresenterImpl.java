package cgg.com.threeapp.presenter.presenterImpl;

import android.util.Log;

import java.util.Map;

import cgg.com.threeapp.model.bean.OrderBean;
import cgg.com.threeapp.model.modelImpl.OrderModelImpl;
import cgg.com.threeapp.model.modelInterface.IOrderModel;
import cgg.com.threeapp.presenter.presenterInterface.IOrderPresenter;
import cgg.com.threeapp.view.viewInterface.IOrderView;

/**
 * Created by Administrator on 2018-03-11.
 */

public class OrderPresenterImpl implements IOrderPresenter {
    private IOrderModel iOrderModel;
    private IOrderView iOrderView;

    public OrderPresenterImpl(IOrderView iOrderView) {
        iOrderModel = new OrderModelImpl(this);
        this.iOrderView = iOrderView;
    }

    @Override
    public void deleteAllCart(String url, Map<String, String> par) {
        iOrderModel.delectCart(url, par);
    }

    @Override
    public void createOrder(String url, Map<String, String> par) {
        iOrderModel.createOrder(url, par);
    }


    @Override
    public void queryOrder(String url, Map<String, String> par) {
        iOrderModel.queryOrder(url,par);
    }

    @Override
    public void querySucceed(OrderBean orderBean) {
        iOrderView.querySucceed(orderBean);
    }

    @Override
    public void deleteSucceed(String string) {
        iOrderView.deleteSucceed(string);
    }

    @Override
    public void createSucceed(String string) {
        iOrderView.createSucceed(string);
    }

}
