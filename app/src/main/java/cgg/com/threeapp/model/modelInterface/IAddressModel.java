package cgg.com.threeapp.model.modelInterface;

import java.util.Map;

/**
 * Created by Administrator on 2018-03-15.
 */

public interface IAddressModel {
    void getAddressList(String url, Map<String,String>map);
    void updateAddress(String url, Map<String,String>map);
    void getDefaultAddress(String url, Map<String,String>map);
}
