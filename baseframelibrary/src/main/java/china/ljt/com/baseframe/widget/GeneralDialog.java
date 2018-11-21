package china.ljt.com.baseframe.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by 李江涛 on 2017/7/14.
 * 说明:通用dialog
 */

public class GeneralDialog extends AlertDialog {
    private IGeneralDialogListener listener;
    private CardView view;
    private float textSize = 14f;
    private String title, content, left, right;
    private String color = "#505050";
    private String lineColor = "#18000000";
    private TextView tvTitle, tvContent, tvLeft, tvRight;
    public static GeneralDialog instence;

    public static GeneralDialog instance(Context context,String content) {
        if (null == instence) {
            instence = new GeneralDialog(context,content,"确定");
        }
        return instence;
    }
    public static GeneralDialog instance(Context context,String content,IGeneralDialogListener listener) {
        if (null == instence) {
            instence = new GeneralDialog(context,content,"确定");
            instence.listener = listener;
        }
        return instence;
    }
    public static GeneralDialog instance(Context context,String content,String button,IGeneralDialogListener listener) {
        if (null == instence) {
            instence = new GeneralDialog(context,content,button);
            instence.listener = listener;

        }
        return instence;
    }
    public static GeneralDialog instance(Context context,String content,String left,String right,IGeneralDialogListener listener) {
        if (null == instence) {
            instence = new GeneralDialog(context,content,left,right);
            instence.listener = listener;
        }
        return instence;
    }
    private GeneralDialog(Context context) {
        super(context);
    }

    private GeneralDialog(Context context, String content, String button) {
        super(context);
        this.content = content;
        this.right = button;
        initView();
    }

    private GeneralDialog(Context context, String content, String left, String right) {
        super(context);
        this.content = content;
        this.left = left;
        this.right = right;
        initView();
    }

    private GeneralDialog(Context context, String title, String content, String left, String right) {
        super(context);
        this.title = title;
        this.content = content;
        this.left = left;
        this.right = right;
        initView();
    }

    protected GeneralDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initView() {
        view = new CardView(getContext());
        view.setCardBackgroundColor(Color.WHITE);
        view.setRadius(13);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        if (!TextUtils.isEmpty(title)) {
            ll.addView(getTitleView());
            ll.addView(getLineH());
        }
        ll.addView(getContentView());
        ll.addView(getLineH());
        ll.addView(getButtonView());


        //成功添加view
        view.addView(ll, lp);
    }

    /**
     * 横线
     */
    private View getLineH() {
        View view = new View(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        view.setBackgroundColor(Color.parseColor(lineColor));
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * 标题
     */
    private View getTitleView() {
        tvTitle = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        tvTitle.setLayoutParams(lp);
        tvTitle.setText(title);
        tvTitle.setPadding(0, 15, 0, 15);
        tvTitle.setTextSize(textSize);
        tvTitle.setTextColor(Color.parseColor(color));
        return tvTitle;
    }

    /**
     * 内容
     */
    private View getContentView() {
        tvContent = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        tvContent.setLayoutParams(lp);
        tvContent.setPadding(15, 30, 15, 30);
        tvContent.setText(content);
        tvContent.setTextSize(14f);
        tvContent.setTextColor(Color.parseColor(color));
        return tvContent;
    }

    /**
     * 两个按钮
     */
    private View getButtonView() {
        LinearLayout ll = new LinearLayout(getContext());
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        if (!TextUtils.isEmpty(left)) {
            ll.addView(getLeftView());
        }
        ll.setPadding(15, 15, 15, 15);
        ll.addView(getRightView());
        ll.setLayoutParams(lp);
        return ll;
    }

    /**
     * 左侧按钮
     */
    private View getLeftView() {
        tvLeft = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.weight = 1;
        tvLeft.setGravity(Gravity.CENTER);
        tvLeft.setLayoutParams(lp);
        tvLeft.setText(left);
        tvLeft.setTextSize(textSize);
        tvLeft.setTextColor(Color.parseColor(color));
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDialogCancle();
                } else {
                    cancel();
                }
            }
        });
        return tvLeft;
    }

    /**
     * 右侧按钮
     */
    private View getRightView() {
        tvRight = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.weight = 1;
        tvRight.setLayoutParams(lp);
        tvRight.setGravity(Gravity.CENTER);
        tvRight.setText(right);
        tvRight.setTextSize(textSize);
        tvRight.setTextColor(Color.parseColor(color));
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDialogSure();
                } else {
                    cancel();
                }
            }
        });
        return tvRight;
    }

    @Override
    public void show() {
        getWindow().setBackgroundDrawable(new ColorDrawable());
        super.show();
        setContentView(view);
        getWindow().setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(false);
        getWindow()
                .setLayout(
                        scanForActivity(getContext()).getWindowManager().getDefaultDisplay()
                                .getWidth() * 4 / 5,
                        android.view.WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }

    public GeneralDialog setTextColor(int title, int content, int left, int right) {
        if (title != 0) {
            tvTitle.setTextColor(title);
        }
        if (content != 0) {
            tvContent.setTextColor(content);
        }
        if (left != 0) {
            tvLeft.setTextColor(left);
        }
        if (right != 0) {
            tvRight.setTextColor(right);
        }
        return this;
    }
    public interface IGeneralDialogListener{
        void onDialogSure();

        void onDialogCancle();
    }

    @Override
    public void cancel() {
        if (instence != null) {
            super.cancel();
        }
    }
}
