package cgg.com.threeapp.view.viewInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.OrderBean;

/**
 * Created by Administrator on 2018-03-11.
 */

public interface IOrderView {
    void deleteSucceed(String string);
    void createSucceed(String string);
    void querySucceed(OrderBean orderBean);
}
