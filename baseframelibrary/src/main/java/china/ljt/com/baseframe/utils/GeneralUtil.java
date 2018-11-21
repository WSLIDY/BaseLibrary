package china.ljt.com.baseframe.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 李江涛 on 2018/11/15.
 * 说明:常用工具类
 */

public class GeneralUtil {
    /**设置View大小 根据提供的 图片比例尺寸
     * @param context 上下文
     * @param iv 传入View
     * @param ivMoRen 默认图片 （本地）
     * @param wid_f 所占屏幕宽度的比例
     * */
    public static View setViewSize(Context context,View iv,int ivMoRen,float wid_f) {
        iv.setLayoutParams(getParams(context,ivMoRen,wid_f));
        return iv;
    }
    /**设置View大小 根据提供的 图片比例尺寸
     * @param context 上下文
     * @param iv 传入View
     * @param wid_num 所占屏幕宽度的比例
     * @param hei_num 高宽比
     * */
    public static View setViewSize(Context context,View iv, float wid_num, float hei_num) {
        iv.setLayoutParams(getParams(context,wid_num,hei_num));
        return iv;
    }
    /**设置View大小 根据提供的 图片比例尺寸
     * @param context 上下文
     * @param iv 传入View
     * @param bitmap 默认图片 （bitmap）
     * @param widthNum 所占屏幕宽度的比例
     * */
    public static View setViewSize(Context context, View iv, Bitmap bitmap, float widthNum) {
        iv.setLayoutParams(getParams(context,bitmap,widthNum));
        return iv;
    }
    /***
     * 根据 例图，求高度
     * @param context
     * @param IV_Id 默认图片
     * @param wid_f 所占屏幕宽度比例
     * @return params num 所占屏幕宽度 控件参数.setLayoutParams(params);
     * @author LJT
     */
    private static ViewGroup.LayoutParams getParams(Context context, int IV_Id, float wid_f) {
        //获取屏幕尺寸
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        //屏幕宽度
        int widthPixels = dm.widthPixels;

        //根据图片 设定宽高比例《首先获取图片宽高》
        Drawable drawable = context.getResources().getDrawable(
                IV_Id);
        int picWidch = drawable.getIntrinsicWidth();
        int picHeight = drawable.getIntrinsicHeight();

        //需要的宽度
        float iv_width = widthPixels * wid_f;

        //计算高度 （需要的）
        float iv_height = getHeight((int) iv_width,
                picWidch, picHeight);

        //新建params
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) iv_width, (int) iv_height);
        return params;
    }
    /**
     * @param context
     * @param wid_num 需要的图片所占屏幕比例
     * @param hei_num 图片高占宽比例
     * @return
     */
    private static ViewGroup.LayoutParams getParams(Context context, float wid_num, float hei_num) {
        //获取屏幕尺寸
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        //屏幕宽度
        int widthPixels = dm.widthPixels;

        //需要的图片占屏幕的宽度 num为百分比
        float iv_width = widthPixels * wid_num;
        //需要的图片高度 （宽高比计算）
        float iv_height = iv_width * hei_num;
        //新建params
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) iv_width, (int) iv_height);
        return params;
    }
    /***
     * @param context
     * @return params  控件参数.setLayoutParams(params);
     * @author LJT
     */
    private static ViewGroup.LayoutParams getParams(Context context, Bitmap bitmap, float widthNum) {
        //获取屏幕尺寸
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        //屏幕宽度
        int widthPixels = dm.widthPixels;

        //图片的尺寸
        int imageWidth = bitmap.getWidth();
        int imageHight = bitmap.getHeight();

        //需要的宽度\高度
        float width = widthPixels * widthNum;
        //计算高度 （需要的）
        float height = getHeight((int) width,
                imageWidth, imageHight);

        //新建params
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) width, (int) height);
        return params;
    }
    /**
     * 根据比例进行缩放  保持图片的不变行
     *
     * @param wantWidch 展现的宽度
     * @param picWidth  图片原宽度
     * @param picHeight 图片原高度
     * @return 展现的高度
     */
    private static float getHeight(int wantWidch, int picWidth, int picHeight) {
        return (int) ((float) wantWidch / picWidth * picHeight);
    }
    /**
     * 获取当前版本信息
     */
    public static String getVersion(Context context) {
        String version = "";
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return version;
    }

    /**
     * 检查字符串是否有效 并决定对View 的显示
     */
    public static String checkString(String content, View hintView) {
        String bac = "";
        if (content != null && !content.equals("")) {
            bac = content;
            hintView.setVisibility(View.VISIBLE);
        } else {
            hintView.setVisibility(View.GONE);
        }
        return bac;
    }

    /**
     * 处理字符串 空格 去掉
     */
    public static String handleStringDelTab(String msg) {
        if (msg != null) {
            if (msg.contains(" ")) {
                msg = msg.replace(" ", "");
                handleStringDelTab(msg);
            }
        } else {
            msg = "";
        }
        return msg;
    }

    /**
     * @param m 传入的double值
     * @param i 需要保留的位数
     *          为Double 函数 保留位数 四舍五入
     */
    public static String handeDoubleToStr(double m, int i) {
        String s = String.valueOf(new BigDecimal(m).setScale(i, BigDecimal.ROUND_HALF_UP));
        return handleDoubleDelZero(s);
    }

    /**
     * 处理小数点后面多余的0
     */
    public static String handleDoubleDelZero(String value) {
        while (value.contains(".") && value.endsWith("0")) {
            value = value.substring(0, value.length() - 1);
            if (value.endsWith(".")) {
                value = value.replace(".", "");
            }
        }
        return value;
    }

    /**
     * 得到剪贴板管理器
     */
    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 实现粘贴功能
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }

    /**
     * 切换软件盘 显示隐藏
     */
    public static void switchSoftInputMethod(Activity act) {
        // 方法一(如果输入法在窗口上已经显示，则隐藏，反之则显示)
        InputMethodManager imm = (InputMethodManager) act
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 验证是否手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    /**
     * 中文识别
     */
    public static boolean isChinese(String source) {
        String reg_charset = "([\\u4E00-\\u9FA5]*+)";
        Pattern p = Pattern.compile(reg_charset);
        Matcher m = p.matcher(source);
        boolean hasChinese = false;
        while (m.find()) {
            if (!"".equals(m.group(1))) {
                hasChinese = true;
            }
        }
        return hasChinese;
    }

    /**
     * 设置TextView 的前景色
     */
    public static SpannableStringBuilder setTextViewForegroundColor(String content, String select, int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(content);
        int fstart = content.indexOf(select);
        int fend = fstart + select.length();
        style.setSpan(new ForegroundColorSpan(color), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    /**
     * 设置webView
     */
    public static void setWebView(WebView webView) {
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        //适配手机
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        //优先使用缓存
        //settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //不适用缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    /***
     * 去设置界面
     */
    public static void toMobileSettingUI(Context context) {
        //go to setting view
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }
}
