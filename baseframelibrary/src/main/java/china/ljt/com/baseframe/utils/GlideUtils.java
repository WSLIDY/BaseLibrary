package china.ljt.com.baseframe.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;


/**
 * Created by 李江涛 on 2018/11/16.
 * 说明:
 * <p>
 * 描述：Glide工具类（glide 4.x）
 * 功能包括加载图片，圆形图片，圆角图片，指定圆角图片，模糊图片，灰度图片等等。
 * 目前我只加了这几个常用功能，其他请参考glide-transformations这个开源库。
 * <p>
 * https://github.com/wasabeef/glide-transformations
 */


public class GlideUtils {
    private static GlideUtils instance;
    private Context context;
    private int placeImage;
    private int errorImage;
    private RequestOptions options;

    private GlideUtils(Context context, int placeImage, int errorImage) {
        this.context = context;
        this.placeImage = placeImage;
        this.errorImage = errorImage;
        getOptions();
    }

    public static GlideUtils getInstance(Context context) {
        return new GlideUtils(context, 0, 0);
    }


    public static GlideUtils getInstance(Context context, int placeImage, int errorImage) {
        return new GlideUtils(context, placeImage, errorImage);
    }

    private RequestOptions getOptions() {
        options = new RequestOptions()
                .centerCrop()
                .placeholder(placeImage) //占位图
                .error(errorImage)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        return options;
    }

    /**
     * 加载图片(默认)
     */


    public void loadImage(String url, ImageView imageView) {
        Glide.with(context).load(url).apply(options).transition(DrawableTransitionOptions.withCrossFade(100)).into(imageView);

    }

    /**
     * 指定图片大小;使用override()方法指定了一个图片的尺寸。
     * Glide现在只会将图片加载成width*height像素的尺寸，而不会管你的ImageView的大小是多少了。
     * 如果你想加载一张图片的原始尺寸的话，可以使用Target.SIZE_ORIGINAL关键字----override(Target.SIZE_ORIGINAL)
     *
     * @param url
     * @param imageView
     * @param width
     * @param height
     */


    public void loadImage(String url, ImageView imageView, int width, int height) {
        Glide.with(context).load(url).apply(options.override(width, height)).transition(DrawableTransitionOptions.withCrossFade(100)).into(imageView);

    }


    /**
     * 禁用内存缓存功能
     * diskCacheStrategy()方法基本上就是Glide硬盘缓存功能的一切，它可以接收五种参数：
     * <p>
     * DiskCacheStrategy.NONE： 表示不缓存任何内容。
     * DiskCacheStrategy.DATA： 表示只缓存原始图片。
     * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
     * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
     * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
     */
    public void loadImage(String url, ImageView imageView,boolean NoMemory) {
        Glide.with(context).load(url).apply(options.skipMemoryCache(NoMemory)).transition(DrawableTransitionOptions.withCrossFade(100)).into(imageView);

    }


    /**
     * 加载圆形图片
     */
    public void loadCircleImage(String url, ImageView imageView) {
        Glide.with(context).load(url).apply(options.circleCrop()).transition(DrawableTransitionOptions.withCrossFade(100)).into(imageView);
    }

    /**
     * 预先加载图片
     * 在使用图片之前，预先把图片加载到缓存，调用了预加载之后，我们以后想再去加载这张图片就会非常快了，
     * 因为Glide会直接从缓存当中去读取图片并显示出来
     */
    public static void preloadImage(Context context, String url) {
        Glide.with(context)
                .load(url)
                .preload();

    }

    private void loadGif(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeImage)
                .error(errorImage);
        Glide.with(context)
                .load(url)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade(100))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }

                })
                .into(imageView);

    }


    public static int dip2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void downloadImage(final Context context, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //String url = "http://www.guolin.tech/book.png";
                    FutureTarget<File> target = Glide.with(context)
                            .asFile()
                            .load(url)
                            .submit();
                    final File imageFile = target.get();
                    Log.d("logcat", "下载好的图片文件路径=" + imageFile.getPath());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
//                        }
//                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
