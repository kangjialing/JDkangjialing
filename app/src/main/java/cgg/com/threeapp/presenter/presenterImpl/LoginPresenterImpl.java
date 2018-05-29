package cgg.com.threeapp.presenter.presenterImpl;

import java.util.Map;

import cgg.com.threeapp.model.bean.DefaultAddr;
import cgg.com.threeapp.model.bean.UserBean;
import cgg.com.threeapp.model.modelImpl.LoginModelImpl;
import cgg.com.threeapp.model.modelInterface.ILoginModel;
import cgg.com.threeapp.presenter.presenterInterface.ILoginPresenter;
import cgg.com.threeapp.view.viewInterface.ILoginView;

/**
 * author: Wanderer
 * date:   2018/2/27 23:24
 * email:  none
 */

public class LoginPresenterImpl implements ILoginPresenter {
    private ILoginModel iLoginModel;
    private ILoginView iLoginView;

    public LoginPresenterImpl(ILoginView iLoginView) {
        iLoginModel = new LoginModelImpl(this);
        this.iLoginView = iLoginView;
    }

    @Override
    public void checkUserInfo(String url, Map<String, String> par) {
        iLoginModel.checkUserInfo(url,par);
    }

    @Override
    public void loginFail(String msg) {
        iLoginView.loginFail(msg);
    }

    @Override
    public void loginSucceed(UserBean userBean) {
        iLoginView.loginSucceed(userBean);
    }

    @Override
    public void getAddrData(String url, Map<String, String> par) {
        iLoginModel.getUserAddr(url,par);
    }

    @Override
    public void getaddrSucceed(DefaultAddr defaultAddr) {
        iLoginView.getaddrSucceed(defaultAddr);
    }


    @Override
    public void getaddrFail(String msg) {
        iLoginView.getaddrFail(msg);
    }
}
