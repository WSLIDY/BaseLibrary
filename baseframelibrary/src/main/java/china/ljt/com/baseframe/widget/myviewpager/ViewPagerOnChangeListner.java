package china.ljt.com.baseframe.widget.myviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

import china.ljt.com.baseframe.R;


/*
* count -->totleNum
* pointLayout-->point content
* */
public class ViewPagerOnChangeListner implements ViewPager.OnPageChangeListener {
    private int index = 0;
    private int count;
    private Context context;
    private LinearLayout pointLayout;
    List<ImageView> points;
    private boolean isShow;

    public ViewPagerOnChangeListner(Context context, int count, LinearLayout pointLayout, boolean isShow) {
        this.count = count;
        this.context = context;
        this.pointLayout = pointLayout;
        points = new LinkedList<ImageView>();
        this.isShow=isShow;
        initPoints();
    }

    private void initPoints() {
        pointLayout.removeAllViews();
        //添加圆点
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8, 15, 8, 15);
        if(isShow){
            if (count > 1) {
                //数量大于1 再添加圆点
                for (int i = 0; i < count; i++) {
                    // 添加标记点
                    ImageView point = new ImageView(context);

                    if (i == index % count) {
                        point.setBackgroundResource(R.drawable.shape_blue_circle);
                    } else {
                        point.setBackgroundResource(R.drawable.shape_white_circle);

                    }
                    point.setLayoutParams(lp);

                    points.add(point);
                    pointLayout.addView(point);
                }
            }
        }


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(isShow){
            index = position;
            for (int i = 0; i < count; i++) {
                points.get(i).setBackgroundResource(R.drawable.shape_white_circle);
            }
            points.get(position % count).setBackgroundResource(R.drawable.shape_blue_circle);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
