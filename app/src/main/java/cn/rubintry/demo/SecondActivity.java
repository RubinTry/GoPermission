package cn.rubintry.demo;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import cn.rubintry.gopermission.Callback;
import cn.rubintry.gopermission.GoPermission;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void requestPermission(View view) {
        GoPermission.permissions(Manifest.permission.BLUETOOTH)
                .callback(new Callback() {
                    @Override
                    public void onAllGrant() {
                        Log.d("TAG", "onAllGrant: ");
                    }

                    @Override
                    public void onDenied(@NotNull String[] permissions) {
                        Log.d("TAG", "onDenied: ");
                    }
                }).request();
    }
}
