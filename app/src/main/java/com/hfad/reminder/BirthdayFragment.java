package com.hfad.reminder;


import android.app.ListFragment;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class BirthdayFragment extends ListFragment {

    private ArrayList<BdayListItem> bdayItmArryList = new ArrayList<>();
    public BirthdayFragment() {
        // Required empty public constructor

        bdayItmArryList.add(new BdayListItem(R.mipmap.ic_launcher,"Devang","Bhatt",123145621));
        bdayItmArryList.add(new BdayListItem(R.mipmap.ic_launcher,"Suresh","Bhatt",123145621));
        bdayItmArryList.add(new BdayListItem(R.mipmap.ic_launcher,"Hemlata","Bhatt",123145621));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ArrayAdapter<BdayListItem> adapter = new customBdayArryAdapter(inflater.getContext(),0,bdayItmArryList);


        setListAdapter(adapter);

        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
