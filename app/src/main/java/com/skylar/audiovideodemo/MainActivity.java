package com.skylar.audiovideodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.skylar.audiovideodemo.Tester.AudioRecordTester;
import com.skylar.audiovideodemo.Tester.MediaRecorderTester;
import com.skylar.audiovideodemo.Tester.Tester;

public class MainActivity extends AppCompatActivity {

    private Tester mTester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        mTester = new MediaRecorderTester();
                        break;
                    case 1:
                        mTester = new AudioRecordTester();
                        break;

                    default:
                            break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.button1:
                mTester.startTest();
                break;
            case R.id.button2:
                mTester.stopTest();
                break;
            default:
                    break;
        }
    }
}
