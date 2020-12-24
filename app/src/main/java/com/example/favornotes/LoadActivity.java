package com.example.favornotes;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoadActivity extends AppCompatActivity {
    private final int time = 3000;
    private boolean lag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {//延时time秒后，将运行如下代码
                if(lag){
                    finish();
                    Toast.makeText(LoadActivity.this , "wait 5s!" , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoadActivity.this , MainActivity.class);
                    startActivity(intent);
                }
            }
        } , time);

        Button button = (Button) findViewById(R.id.button);
        //给按钮添加监听事件，当点击时，直接进入主页面
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(LoadActivity.this , MainActivity.class);
                startActivity(intent);
                lag = false;
            }
        });
    }
}