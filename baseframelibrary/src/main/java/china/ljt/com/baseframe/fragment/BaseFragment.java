package china.ljt.com.baseframe.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import china.ljt.com.baseframe.activity.BaseActivity;


/**
 * Created by 李江涛 on 2017/6/29.
 * 说明:
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    private long lastClick = 0;
    protected Resources res;
    protected BaseActivity holdActivity;
    /**
     * 根view
     */
    protected View mRootView;

    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**回复当前状态*/
        if (savedInstanceState != null) {
            try {
                boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (isSupportHidden) {
                    ft.hide(this);
                } else {
                    ft.show(this);
                }
                ft.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(getLayoutId(), container, false);
        this.holdActivity = (BaseActivity) getActivity();
        res =holdActivity.getApplicationContext().getResources();
        mIsPrepare = true;
        initView(mRootView);
        setListener();
        loadData();
        return mRootView;
    }


    @Override
    public void onClick(View v) {
        if (fastClick())
            widgetClick(v);
    }
    /**
     * 设置ContentView
     * @return R.line_single.xxx
     */
    protected abstract int getLayoutId();
    /**
     * 初始化view
     * */
    protected abstract void initView(View rootView);
    /**
     * add Listener
     */
    protected abstract void setListener();
    /**
     * 下载数据
     */
    protected abstract void loadData();
    /**
     * view点击
     * @param v
     */
    public abstract void widgetClick(View v);
    /**
     * 防止快速点击
     * @return
     */
    private boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
    /**
     * findViewById
     * @param resId
     * @param <T>
     * @return
     */
    public <T extends View> T $findViewById(int resId) {
        return (T) mRootView.findViewById(resId);
    }

    /**
     * startActivity
     * @param cls 需要启动的Activity
     */
    protected void $startActivity(Class<?> cls) {
        $startActivity(cls, null);
    }

    /**
     * startActivity 存在Bundle
     * @param cls 需要启动的Activity
     * @param bundle Bundle数据
     */
    protected void $startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(holdActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     * @param cls 需要启动的Activity
     * @param requestCode 请求码
     */
    protected void $startActivityForResult(Class<?> cls, int requestCode) {
        $startActivityForResult(cls, null, requestCode);
    }

    /**
     * startActivityForResult 存在Bundle
     * @param cls 需要启动的Activity
     * @param bundle Bundle数据
     * @param requestCode 请求码
     */
    protected void $startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(holdActivity, cls);
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
        Intent intent = holdActivity.getIntent();
        Bundle bundle = null;
        if (null != intent)
            bundle = intent.getExtras();
        return bundle;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /**保存意外销时状态*/
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisible = isVisibleToUser;
        if (isVisibleToUser)
        {
            if (mIsPrepare )
            {
                onVisibleToUser();
            }
        }
    }

    public void onVisibleToUser() {

    }
}
