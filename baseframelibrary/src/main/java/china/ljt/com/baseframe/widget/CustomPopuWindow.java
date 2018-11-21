package china.ljt.com.baseframe.widget;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;



/**
 * Created by 李江涛 on 2017/6/14.
 * 说明:从下往上弹出窗体动画
 */

public class CustomPopuWindow extends PopupWindow {
    public CustomPopuWindow(View contentView, int width, int height){
        super(contentView,width,height);
        //设置popupWindow弹出窗体的背景
        setBackgroundDrawable(new BitmapDrawable());
        //设置弹出窗体需要软键盘，
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //再设置模式，和Activity的一样，覆盖，调整大小。
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
    /**
     * 在android7.0上，如果不主动约束PopuWindow的大小，比如，设置布局大小为 MATCH_PARENT,那么PopuWindow会变得尽可能大，
     * 以至于 view下方无空间完全显示PopuWindow，而且view又无法向上滚动，此时PopuWindow会主动上移位置，直到可以显示完全。
     *　解决办法：主动约束PopuWindow的内容大小，重写showAsDropDown方法：
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }
}
