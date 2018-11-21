package china.ljt.com.baseframe.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import china.ljt.com.baseframe.utils.DisplayUtil;
import china.ljt.com.baseframe.utils.http.HttpManager;


/**
 * Created by fdc-001 on 2016/6/7.
 */
public class MapImageView extends ImageView {
    private static final Map<String, SoftReference<Bitmap>> imageAche
            = new HashMap<String, SoftReference<Bitmap>>();

    public MapImageView(Context context) {
        super(context);
    }

    public MapImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     * @param place 字符串: 以 ， 拼接
     * 1132.2563，152233566
     * */
    public void setMapView(String place) {
        String httpurl = setUrl(place);
        SoftReference<Bitmap> reference = imageAche.get(httpurl);
        if (reference != null) {
            Bitmap bitmap = reference.get();
            if (bitmap == null) {
                showMap(httpurl);
            } else {
                setImageBitmap(bitmap);
            }
        } else {
            showMap(httpurl);
        }
    }
    private String setUrl(String place) {
        int width = DisplayUtil.getScreenWidth(getContext());
        int height =width/2;
        if (width > 1024||height>1024) {
            width = 1024;
            height =width/2;
        }
        String httpUrl = "http://api.map.baidu.com/staticimage?center=" +
                place + "&ak=" + "aU9a9AGXlUfpI9L8SYiDHougSkW5oXEq" +
                "&width=" + width +
                "&height=" + height +
                "&zoom=16&markers=" + place + "&markerStyles=l,,0xFFFF0000";
        return httpUrl;
    }

    private void showMap(final String url) {
        HttpManager.displayImage(this,url);
    }
}
