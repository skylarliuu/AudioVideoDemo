package com.skylar.audiovideodemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class DrawPictureActivity extends AppCompatActivity {

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_picture);

        // 1. ImageView
//        ImageView imageView = findViewById(R.id.imageView);
//        bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
//                                                     + "pic.jpg");
//        imageView.setImageBitmap(bitmap);

        //SurfaceView
//        SurfaceView surfaceView = findViewById(R.id.surfaceView);
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                if(holder == null)
//                    return;
//
//                Paint paint = new Paint();
//                paint.setAntiAlias(true);
//                paint.setStyle(Paint.Style.STROKE);
//
//                Canvas canvas = holder.lockCanvas();// 先锁定当前surfaceView的画布
//                canvas.drawBitmap(bitmap,0,0,paint);//执行绘制操作
//                holder.unlockCanvasAndPost(canvas);// 解除锁定并显示在界面上
//
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//
//            }
//        });
    }


}
