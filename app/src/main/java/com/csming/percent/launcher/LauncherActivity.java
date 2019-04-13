package com.csming.percent.launcher;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.csming.percent.R;
import com.csming.percent.main.MainActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Created by csming on 2019/4/7.
 */
public class LauncherActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        initView();
        gotoNextActivityDelay();
    }

    private void initView() {
//        int ran = (int) (Math.random() * SlogonEntity.SLOGON_VALUES.length);
//        TextView tvSlogon = findViewById(R.id.tv_slogon);
//        tvSlogon.setText(SlogonEntity.SLOGON_VALUES[ran]);
    }

    private void gotoNextActivityDelay() {
        handler = new Handler(getMainLooper());
        handler.postDelayed(this::gotoNextActivity, 1000);
    }

    private void gotoNextActivity() {
        startActivity(MainActivity.getIntent(this));
        overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        finish();
    }
}
