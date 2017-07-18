package com.hfad.reminder;


import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class ReminderFragment extends ListFragment {


    public ReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.reminders)
        );

        setListAdapter(adapter);

        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
