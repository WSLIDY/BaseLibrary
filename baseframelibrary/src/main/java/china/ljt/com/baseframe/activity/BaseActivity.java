package china.ljt.com.baseframe.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by 李江涛 on 2017/6/29.
 * 说明:
 */

public abstract class BaseActivity extends AppCompatActivity implements  View.OnClickListener {
    private long lastClick = 0;
    protected Resources res;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getResources();
        setContentView(getLayoutId());
        //第一次进入加在数据
        initView();
        addListener();
        loadData();
    }

    /**
     * 设置ContentView
     *
     * @return R.line_single.xxx
     */
    protected abstract int getLayoutId();
    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 监听
     */
    protected abstract void addListener();
    /**
     * 下载数据
     * //第一次进入加在数据
     */
    protected abstract void loadData();


    /**
     * view点击
     *
     * @param v
     */
    public abstract void widgetClick(View v);


    /**
     * 防止快速点击
     *
     * @return
     */
    private boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (fastClick()) {
            widgetClick(view);
        }
    }
    /**
     * findViewById
     *
     * @param resId
     * @param <T>
     * @return
     */
    public <T extends View> T $findViewById(int resId) {
        return (T) findViewById(resId);
    }
    /**
     * startActivity 存在Bundle
     * @param cls 需要启动的Activity
     * @param bundle Bundle数据
     */
    protected void $startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    /**
     * startActivityForResult 存在Bundle
     *
     * @param cls         需要启动的Activity
     * @param bundle      Bundle数据
     * @param requestCode 请求码
     */
    protected void $startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
    /**
     * 获取 传入 Bundle
     * @return
     */
    protected Bundle $getIntentExtra() {
        Intent intent =getIntent();
        Bundle bundle = null;
        if (null != intent)
            bundle = intent.getExtras();
        return bundle;
    }
}
