package cgg.com.threeapp.presenter.presenterInterface;

/**
 * author: ShiChenguang
 * date : 2018/4/2 13:18
 */
public interface IVideoPresenter {
    void getDataFrmoNet(String url);
    void succeed();
    void error(String err);
}
