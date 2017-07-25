package com.hfad.reminder;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

public class TempActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);


        SharedPreferences prefs = getSharedPreferences("toDoitms",MODE_PRIVATE);;
        int  size = prefs.getInt("size", 0);

        ArrayList<String> list = new ArrayList<String>();

        for (int i=0;i<size;i++){
            list.add(prefs.getString("value"+i,null));
        }
        System.out.println("List is"+list);
    }
}
