package china.ljt.com.baseframe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 李江涛 on 2017/12/1.
 * 说明: 横向跑马灯 使用方法
 *
 android:id="@+id/tips"
 android:visibility="gone"
 android:layout_width="match_parent"
 android:layout_height="wrap_content"
 android:paddingBottom="5dp"
 android:paddingTop="5dp"
 android:paddingRight="5dp"
 android:drawableLeft="@drawable/tips_image"
 android:gravity="center_vertical"
 android:layout_marginLeft="10dp"
 android:drawablePadding="10dp"
 android:textSize="13sp"
 android:singleLine="true"
 android:ellipsize="marquee"
 android:textColor="@color/text_gray"
 android:marqueeRepeatLimit="marquee_forever"
 android:background="@color/white"/>
 */

public class MarqueeTextView extends TextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //返回textview是否处在选中的状态
    //而只有选中的textview才能够实现跑马灯效果
    @Override
    public boolean isFocused() {
        return true;
    }
}
