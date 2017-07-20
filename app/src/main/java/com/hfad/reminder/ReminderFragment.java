package com.hfad.reminder;


import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class ReminderFragment extends ListFragment {

    private ArrayList<RmndListItem> rmndItmArryList = new ArrayList<>();

    private DBDataSource dbDataSource;

    public ReminderFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dbDataSource = new DBDataSource(getActivity());
        dbDataSource.open();
        rmndItmArryList = dbDataSource.getAllRmndRows();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ArrayAdapter<RmndListItem> adapter = new customRmndArryAdapter(
                inflater.getContext(),
                0,
                rmndItmArryList
        );

        setListAdapter(adapter);

        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
