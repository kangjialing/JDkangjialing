package cgg.com.threeapp.model.modelInterface;

import java.util.Map;

/**
 * Created by Administrator on 2018-03-11.
 */

public interface IOrderModel {
    void createOrder(String url, Map<String, String> map);
    void queryOrder(String url, Map<String,String> par);
    void delectCart(String url, Map<String, String> map);
}
