package china.ljt.com.baseframe.utils.update;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import china.ljt.com.baseframe.R;


/**
 * Created by Teprinciple on 2016/10/13.
 */
public class ConfirmDialog extends Dialog {

    Callback callback;
    private TextView content;
    private TextView sureBtn;
    private TextView cancleBtn;
    private Context context;

    public ConfirmDialog(Context context, Callback callback) {
        super(context, R.style.CustomDialog);
        this.context = context;
        this.callback = callback;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm, null);
        sureBtn = (TextView)mView.findViewById(R.id.dialog_confirm_sure);
        cancleBtn = (TextView)mView.findViewById(R.id.dialog_confirm_cancle);
        content = (TextView) mView.findViewById(R.id.dialog_confirm_title);


        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callback(1);
                ConfirmDialog.this.cancel();
            }
        });
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callback(0);
                ConfirmDialog.this.cancel();
            }
        });
        super.setContentView(mView);
        getWindow()
                .setLayout(
                        getScreenWidth(context)*6/7,
                        android.view.WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private   int getScreenWidth(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }
    public ConfirmDialog setContent(String s){
        content.setText(s);
        return this;
    }


}
