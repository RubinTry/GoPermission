package cn.rubintry.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;

import cn.rubintry.gopermission.Callback;
import cn.rubintry.gopermission.GoPermission;


public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        GoPermission
                .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request(new Callback() {
                    @Override
                    public void onResult(boolean allGrant, @NotNull String[] grantedPermissions, @NotNull String[] deniedPermissions) {

                    }
                });
    }
}