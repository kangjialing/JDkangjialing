package cgg.com.threeapp.view.costomView;

/**
 * Created by Administrator on 2018-03-02.
 */

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ObservableScrollView extends NestedScrollView {
private static final String TAG="ObservableScrollView";

public interface IScrollViewListener {
    void onScrollChanged(int l, int t, int oldl, int oldt);

}
    IScrollViewListener mScrollViewListener;

    public void setScrollViewListener(IScrollViewListener listener) {
        this.mScrollViewListener = listener;
    }

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (mScrollViewListener != null) {
            mScrollViewListener.onScrollChanged(x, y, oldx, oldy);
        }
    }
}