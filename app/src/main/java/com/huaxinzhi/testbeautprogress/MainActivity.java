package com.huaxinzhi.testbeautprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.dragon.preloadprogressbar.preloadRingProgress;

public class MainActivity extends AppCompatActivity {
    //创建界面加载进度显示的dialog
    private preloadRingProgress progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (preloadRingProgress) findViewById(R.id.progress);
        progress.reSetViewSize(R.color.load_progress, this, new preloadRingProgress.callBackAty() {
            @Override
            public void handleAty() {
                Toast.makeText(MainActivity.this, "进度圆满", Toast.LENGTH_SHORT).show();
            }
        });
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
