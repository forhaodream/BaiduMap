package com.example.cs_android.baidumap.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.cs_android.baidumap.R;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * 主页
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RxPermissions mRxPermissions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.app_description));
        requestPermissions();
    }

    public void onclick(View v) {
        switch (v.getId()) {
            //启动定位页面
            case R.id.btn_location:
                startCls(LocationActivity.class);
                break;
            //启动地图显示页面
            case R.id.btn_map:
                startCls(MapActivity.class);
                break;
            //启动地图绘制页面
            case R.id.btn_overlay_map:
                startCls(OverlayActivity.class);
                break;
            //启动POI检索页面
            case R.id.btn_poi:
                startCls(PoiActivity.class);
                break;
            //启动线路规划
            case R.id.btn_route:
                startCls(RouteActivity.class);
                break;
            //鹰眼轨迹的上传获取显示
            case R.id.btn_trace:
                startCls(TraceActivity.class);
                break;
            //地理围栏
            case R.id.btn_rail:
                startCls(RailActivity.class);
                break;
        }
    }

    private void requestPermissions() {
        mRxPermissions = new RxPermissions(MainActivity.this);
        mRxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，弹出提示
                            showPermissions();
                        } else {
                            // 用户拒绝了该权限，弹出提示
                            showPermissions();
                        }
                    }
                });
    }

    public void showPermissions() {
        if (!mRxPermissions.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            showPermissionsDialog("此应用需要获取定位地理位置的权限,是否打开应用设置手动授予");
        } else if (!mRxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showPermissionsDialog("此应用需要获取读取手机存储的权限，是否打开应用设置手动授予");
        } else if (!mRxPermissions.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            showPermissionsDialog("此应用需要获取读取手机基本信息的权限，是否打开应用设置手动授予");
        }
    }

    public void showPermissionsDialog(String msg) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("请注意")
                .setMessage(msg)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivity(intent);
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).show();
    }


    public void startCls(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}
