package com.hfad.reminder;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ToDoFragment extends ListFragment {


    public ToDoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences prefs = getActivity().getSharedPreferences("todoPrefsKey",getActivity().MODE_PRIVATE);
        int  size = prefs.getInt("size", 0);

        ArrayList<String> list = new ArrayList<String>();

        for (int i=0;i<size;i++){
            list.add(prefs.getString("value"+i,null));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_expandable_list_item_1,
                list
        );

        setListAdapter(adapter);

        return super.onCreateView(inflater,container,savedInstanceState);

    }

}
