package china.ljt.com.ljtbaseframe;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import china.ljt.com.baseframe.activity.BaseActivity;
import china.ljt.com.baseframe.utils.StatusBarUtil;

public class Main2Activity extends BaseActivity {
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView tvTitle;
    AppBarLayout appBarLayout;

    @Override
    protected int getLayoutId() {
        StatusBarUtil.setStatusBarTranslucent(this, false);
        return R.layout.activity_main2;
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        initToolBar();
    }

    private void initToolBar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        collapsingToolbarLayout.setTitle("");
        toolbar.setTitle("");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent));//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.transparent));//设置收缩后Toolbar上字体的颜色
        tvTitle.setText(getString(R.string.app_name) + " (hehehehe)");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBar, int offset) {
                tvTitle.setAlpha(Math.abs(offset * 1f / appBar.getTotalScrollRange()));
            }
        });
    }
    @Override
    protected void addListener() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void widgetClick(View v) {

    }
}
