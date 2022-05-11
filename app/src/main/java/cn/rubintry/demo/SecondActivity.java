package cn.rubintry.demo;

import android.Manifest;
import android.os.Bundle;
;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.rubintry.gopermission.core.BeforeRequestCallback;
import cn.rubintry.gopermission.core.Callback;
import cn.rubintry.gopermission.core.GoPermission;
import cn.rubintry.gopermission.widget.IPermissionDialogInterface;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void requestPermission(View view) {
        GoPermission
                .permissions()
                .beforeRequest(new BeforeRequestCallback() {
                    @Override
                    public IPermissionDialogInterface onBefore() {
                        return new DemoDialog.Builder(SecondActivity.this)
                                .create();
                    }
                })
                .request(new Callback() {
                    @Override
                    public void onResult(boolean allGrant, @NonNull String[] grantedPermissions, @NonNull String[] deniedPermissions) {

                    }
                });
    }
}
