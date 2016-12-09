package com.example.a5androidintern1.p0151_contextmenu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
 TextView tvColor, tvSize;
    final int MENU_COLOR_RED = 1;
    final int MENU_COLOR_GREEN = 2;
    final int MENU_COLOR_BLUE = 3;

    final int MENU_SIZE_22 = 4;
    final int MENU_SIZE_26 = 5;
    final int MENU_SIZE_30 = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvColor = (TextView) findViewById(R.id.tvColor);
        tvSize = (TextView)findViewById(R.id.tvSize);

        registerForContextMenu(tvColor);
        registerForContextMenu(tvSize);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()){
            case R.id.tvColor:
                menu.add(0,MENU_COLOR_RED,0,"RED" );
                menu.add(0,MENU_COLOR_GREEN,0,"GREEN" );
                menu.add(0,MENU_COLOR_BLUE,0,"BLUE" );
                break;
            case R.id.tvSize:
                menu.add(0,MENU_SIZE_22 ,0,"22" );
                menu.add(0,MENU_SIZE_26 ,0,"26" );
                menu.add(0,MENU_SIZE_30 ,0,"30" );
                break;

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MENU_COLOR_RED: tvColor.setTextColor(Color.RED);
                tvColor.setText("text color is RED");
                break;
            case MENU_COLOR_GREEN: tvColor.setTextColor(Color.GREEN);
                tvColor.setText("text color is GREEN");
                break;
            case MENU_COLOR_BLUE: tvColor.setTextColor(Color.BLUE);
                tvColor.setText("text color is BLUE");
                break;
            case MENU_SIZE_22: tvSize.setTextSize(22);
                tvSize.setText("text size = 22");
                break;
            case MENU_SIZE_26: tvSize.setTextSize(26);
                tvSize.setText("text size = 26");
                break;
            case MENU_SIZE_30: tvSize.setTextSize(30);
                tvSize.setText("text size = 30");
                break;
        }



        return super.onContextItemSelected(item);
    }
}
