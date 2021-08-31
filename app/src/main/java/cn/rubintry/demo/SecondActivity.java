package cn.rubintry.demo;

import android.Manifest;
import android.os.Bundle;
;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import cn.rubintry.gopermission.core.Callback;
import cn.rubintry.gopermission.core.GoPermission;
import cn.rubintry.gopermission.utils.LogUtils;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void requestPermission(View view) {

    }
}
