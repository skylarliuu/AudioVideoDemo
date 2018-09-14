package com.skylar.audiovideodemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void click(View view){
        switch (view.getId()){
            case R.id.button1:
                startActivity(new Intent(MainActivity.this,AudioTestActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(MainActivity.this,DrawPictureActivity.class));
                break;

            default:
                    break;
        }
    }
}
