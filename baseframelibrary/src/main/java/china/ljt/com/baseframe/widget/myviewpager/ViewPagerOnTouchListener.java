package china.ljt.com.baseframe.widget.myviewpager;

import android.view.MotionEvent;
import android.view.View;


/**
 * Created by fdc-001 on 2016/4/13.
 */
public class ViewPagerOnTouchListener implements View.OnTouchListener {
    private AutoScrollViewPager pager;
    public ViewPagerOnTouchListener(AutoScrollViewPager pager){
        this.pager=pager;

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pager.stopAutoScroll();

                break;
            case MotionEvent.ACTION_MOVE:
                pager.startAutoScroll();

                break;
            case MotionEvent.ACTION_UP:
                pager.startAutoScroll();

                break;

            default:
                break;
        }

        return false;
    }
}
