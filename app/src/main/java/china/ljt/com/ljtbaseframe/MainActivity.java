package china.ljt.com.ljtbaseframe;


import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import china.ljt.com.baseframe.activity.BaseActivity;
import china.ljt.com.baseframe.utils.PhotoSelectTool;
import china.ljt.com.baseframe.utils.permission.PermissionGen;
import china.ljt.com.baseframe.widget.GeneralDialog;

public class MainActivity extends BaseActivity {
    private ImageView imageView,imageView2;
    @Override
    protected int getLayoutId() {

        return R.layout.activity_main;
    }

    private void needPermissaon() {
        //PermissionGen.needPermission(this, 100, permissions);
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void initView() {
        needPermissaon();
        imageView = $findViewById(R.id.main_iv_01);
        imageView2 = $findViewById(R.id.main_iv_02);

    }

    @Override
    protected void addListener() {
        $findViewById(R.id.main_01).setOnClickListener(this);
        $findViewById(R.id.main_02).setOnClickListener(this);
        $findViewById(R.id.main_03).setOnClickListener(this);
        $findViewById(R.id.main_04).setOnClickListener(this);
        $findViewById(R.id.main_05).setOnClickListener(this);
    }




    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.main_01:
               // PermissionGen.with(this).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).addRequestCode(100).request();
                $startActivity(Main2Activity.class,null);
//                new GeneralDialog(this, "发现新版本，是否更新？", "取消", "确定").setDialogListener(new GeneralDialog.ICustomDialogListener() {
//                    @Override
//                    public void dialogClick(View view) {
//                        String name = (String) view.getTag();
//                        if (name.equals("确定")) {
//                            UpdateAppUtils.from(MainActivity.this)
//                                    .serverVersionCode(100)
//                                    .serverVersionName("1.3.2")
//                                    .apkPath("http://www.cbrex.com/upload/android/cbrex_android.apk")
//                                    .updateInfo("hehehhe")
//                                    .isForce(true)
//                                    .setSmallIcon(R.mipmap.ic_er)
//                                    .update();
//                        }
//                    }
//                }).show();
               // HttpManager.postAsyn("",new Test(),new HttpManager.Param("ss","ss"));
                break;
            case R.id.main_02:
                PermissionGen.with(this).permissions(Manifest.permission.READ_EXTERNAL_STORAGE).addRequestCode(100).request();
                GeneralDialog.instance(this, "hehehe", "取消", "确认", new GeneralDialog.IGeneralDialogListener() {
                    @Override
                    public void onDialogSure() {
                        GeneralDialog.instence.cancel();
                    }

                    @Override
                    public void onDialogCancle() {
                        GeneralDialog.instence.cancel();
                    }
                }).show();
                break;
            case R.id.main_04:
                PhotoSelectTool.selectOnActivity(this,null,imageView);
                break;
            case R.id.main_05:
              // GlideUtils.getInstance(this).loadCircleImage("http://guolin.tech/book.png",imageView2);
               //GlideUtils.getInstance(this,R.mipmap.ic_er,R.mipmap.ic_launcher).loadImage("http://p1.pstatp.com/large/166200019850062839d3",imageView2);
              //  Glide.with(this).load("http://guolin.tech/book.png").into(imageView2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoSelectTool.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhotoSelectTool.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}

