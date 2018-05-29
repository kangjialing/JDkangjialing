package cgg.com.threeapp.presenter.presenterInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.OrderBean;

/**
 * Created by Administrator on 2018-03-11.
 */

public interface IOrderPresenter {
    void deleteAllCart(String url, Map<String,String> par);
    void createOrder(String url, Map<String,String> par);
    void deleteSucceed(String string);
    void createSucceed(String string);
    void queryOrder(String url, Map<String,String> par);
    void querySucceed(OrderBean orderBean);
}
