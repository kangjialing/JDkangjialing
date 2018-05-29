package cgg.com.threeapp.view.viewInterface;

import cgg.com.threeapp.model.bean.AddressBean;
import cgg.com.threeapp.model.bean.DefaultAddr;
import cgg.com.threeapp.model.bean.UserBean;

/**
 * author: Wanderer
 * date:   2018/2/27 23:17
 * email:  none
 */

public interface ILoginView {
    void loginSucceed(UserBean userBean);
    void loginFail(String msg);

    void getaddrSucceed(DefaultAddr defaultAddr);

    void getaddrFail(String msg);
}
