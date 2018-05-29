package cgg.com.threeapp.view.costomView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * author: Wanderer
 * date:   2018/3/28 13:55
 * email:  none
 */

public class CustomGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public CustomGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {
        //return super.canScrollVertically();
        return isScrollEnabled && super.canScrollVertically();
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }
}
