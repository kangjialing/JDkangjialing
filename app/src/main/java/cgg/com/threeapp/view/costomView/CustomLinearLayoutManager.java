package cgg.com.threeapp.view.costomView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * author: Wanderer
 * date:   2018/3/28 13:50
 * email:  none
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
