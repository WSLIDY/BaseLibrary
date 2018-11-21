package china.ljt.com.baseframe.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import china.ljt.com.baseframe.R;

/**
 * Created by 李江涛 on 2017/11/8.
 * 说明:头像上传工具类  相册请求 相机请求
 * 1、权限
 * <p>
 * 2、xml 文件 file_paths
 * <p>
 * <paths>
 * <external-path
 * name="camera_photos"
 * path="" />
 * </paths>
 * <p>
 * 3、manifests 中配置
 * <provider
 * android:name="android.support.v4.content.FileProvider"
 * android:authorities="${applicationId}.fileprovider"
 * android:exported="false"
 * android:grantUriPermissions="true">
 * <meta-data
 * android:name="android.support.FILE_PROVIDER_PATHS"
 * android:resource="@xml/file_paths"/>
 * </provider>
 * <p>
 * 4、调用方法：
 * PhotoSelectTool.showDialogOnActivity(Activity, IPhotoImageCommit,imageView);
 *
 * @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 * super.onActivityResult(requestCode, resultCode, data);
 * PhotoSelectTool.onActivityResult(requestCode, resultCode, data);
 * }
 * @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
 * super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * PhotoSelectTool.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * }
 */

public class PhotoSelectTool {
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    public static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private static File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private static File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private static Uri imageUri;
    private static Uri cropImageUri;
    private static ImageView imageView;
    private static AlertDialog userPhotoDialog;
    private static Context context;
    private static Fragment currentFragment;
    private static Activity currentActivity;
    private static IPhotoImageCommit iPhotoImageCommit;

    public static void selectOnFragment(final Fragment currentFragmen, IPhotoImageCommit model, ImageView iv) {
        imageView = iv;
        currentFragment = currentFragmen;
        currentActivity = null;
        context = currentFragmen.getContext();
        iPhotoImageCommit = model;
        setSelectDialog();
    }

    public static void selectOnActivity(final Context sontext, IPhotoImageCommit model, ImageView iv) {
        imageView = iv;
        context = sontext;
        currentActivity = (Activity) context;
        currentFragment = null;
        iPhotoImageCommit = model;
        setSelectDialog();
    }

    private static void setSelectDialog() {
        userPhotoDialog = new AlertDialog.Builder(context).create();
        userPhotoDialog.setCanceledOnTouchOutside(true);

        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_headimg_select, null);
        userPhotoDialog.show();

        userPhotoDialog.setContentView(view);
        userPhotoDialog.getWindow().setGravity(Gravity.BOTTOM);
        userPhotoDialog.getWindow().setWindowAnimations(R.style.popuWindowAnimal);
        userPhotoDialog.getWindow()
                .setLayout(
                        ((Activity) context).getWindowManager().getDefaultDisplay()
                                .getWidth() - 30,
                        android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        TextView btn_cancel = (TextView) view.findViewById(R.id.head_tv_cancel);
        TextView tv_take_photo = (TextView) view
                .findViewById(R.id.tv_take_photo);
        TextView tv_phone_photo = (TextView) view
                .findViewById(R.id.tv_phone_photo);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userPhotoDialog.dismiss();
            }
        });

        tv_take_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                userPhotoDialog.dismiss();
                fromCamera();
            }
        });
        tv_phone_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                userPhotoDialog.dismiss();
                gallery();
            }
        });
    }

    private static void gallery() {
        autoObtainStoragePermission();
    }

    private static void fromCamera() {
        autoObtainCameraPermission();
    }

    //请求SD权限
    private static void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            if (currentFragment != null && currentActivity == null) {
                PhotoUtils.openPic(currentFragment, CODE_GALLERY_REQUEST);
            }
            if (currentActivity != null && currentFragment == null) {
                PhotoUtils.openPic(currentActivity, CODE_GALLERY_REQUEST);
            }
        }
    }

    //请求相机权限
    private static void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                Toast.makeText(context, "您已经拒绝过一次", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                if (currentFragment != null && currentActivity == null) {
                    PhotoUtils.takePicture(currentFragment, imageUri, CODE_CAMERA_REQUEST);
                }
                if (currentActivity != null && currentFragment == null) {
                    PhotoUtils.takePicture(currentActivity, imageUri, CODE_CAMERA_REQUEST);
                }
            } else {
                Toast.makeText(context, "设备没有SD卡", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static final int output_X = 480;
    private static final int output_Y = 480;

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    if (currentFragment != null && currentActivity == null) {
                        PhotoUtils.cropImageUri(currentFragment, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    }
                    if (currentActivity != null && currentFragment == null) {
                        PhotoUtils.cropImageUri(currentActivity, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    }
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(context, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", new File(newUri.getPath()));

                        if (currentFragment != null) {
                            PhotoUtils.cropImageUri(currentFragment, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                        }
                        if (currentActivity != null) {
                            PhotoUtils.cropImageUri(currentActivity, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                        }
                    } else {
                        Toast.makeText(context, "设备没有SD卡", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, context);
                    if (bitmap != null) {
                        showImages(bitmap);
                        commitPhoto(bitmap);
                    }
                    break;
            }
        }
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri

                        if (currentFragment != null && currentActivity == null) {
                            PhotoUtils.takePicture(currentFragment, imageUri, CODE_CAMERA_REQUEST);
                        }
                        if (currentActivity != null && currentFragment == null) {
                            PhotoUtils.takePicture(currentActivity, imageUri, CODE_CAMERA_REQUEST);
                        }
                    } else {
                        Toast.makeText(context, "设备没有SD卡", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(context, "请允许打开相机", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (currentFragment != null && currentActivity == null) {
                        PhotoUtils.openPic(currentFragment, CODE_GALLERY_REQUEST);
                    }
                    if (currentActivity != null && currentFragment == null) {
                        PhotoUtils.openPic(currentActivity, CODE_GALLERY_REQUEST);
                    }
                } else {
                    Toast.makeText(context, "请允许打操作SDCard", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private static void commitPhoto(Bitmap bitmap) {
        //上传图片
        if (iPhotoImageCommit != null) {
            iPhotoImageCommit.commitImage(ImageUtils.imgToBase64(bitmap));
        }
    }

    private static void showImages(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public interface IPhotoImageCommit {
        void commitImage(String base64);
    }
}
