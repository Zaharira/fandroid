package com.example.a5androidintern1.p0301activityresult;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textView;
    Button btnColor;
    Button btnAlign;
    final int REQUEST_CODE_COLOR = 1;
    final int REQUEST_CODE_ALIGN = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.tvMain);
        btnColor = (Button) findViewById(R.id.buttonColor);
        btnAlign = (Button) findViewById(R.id.buttonAlign);
        btnAlign.setOnClickListener(this);
        btnColor.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent ;

        switch (view.getId()){
            case R.id.buttonColor:
                intent = new Intent(this, ColorActivity.class);
                startActivityForResult(intent,REQUEST_CODE_COLOR);
                break;

            case R.id.buttonAlign:
                intent = new Intent(this, AlignActivity.class);
                startActivityForResult(intent,REQUEST_CODE_ALIGN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("myLogs", "requestCode = " + requestCode + " resultCode = " + resultCode );
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_COLOR:
                    int color = data.getIntExtra("color", Color.WHITE);
                    textView.setTextColor(color);
                    break;
                case REQUEST_CODE_ALIGN:
                    int align = data.getIntExtra("alignment", Gravity.LEFT);
                    textView.setGravity(align);
                    break;
            }



        }else {
            Toast.makeText(MainActivity.this,"Wrong Result",Toast.LENGTH_SHORT).show();
        }


    }
}
