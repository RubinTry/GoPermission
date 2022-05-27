package cn.rubintry.demo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.rubintry.gopermission.core.GoPermission;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void requestPermission(View view) {
        GoPermission
                .isIntervalMode(false)
                .permissions()
                .beforeRequest(() -> new DemoDialog.Builder(SecondActivity.this).create())
                .request((allGrant, grantedPermissions, deniedPermissions) -> {

                });
    }
}
