package com.hfad.reminder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by DEVANG on 7/21/2017.
 */

public class customRmndArryAdapter extends ArrayAdapter<RmndListItem>{

    private Context context;
    private List<RmndListItem> itemList;

    //constructor, call on creation
    public customRmndArryAdapter(Context context, int resource, ArrayList<RmndListItem> itemList) {
        super(context, resource, itemList);

        this.context = context;
        this.itemList = itemList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
        RmndListItem item = itemList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_row_rmnd, null);



        ImageView image = (ImageView) view.findViewById(R.id.rmndImage);
        TextView listRmndTitle = (TextView) view.findViewById(R.id.rmndTitle);
        TextView listRmndDate = (TextView) view.findViewById(R.id.rmndDate);
        TextView listRmndTime = (TextView) view.findViewById(R.id.rmndTime);

        //set address and description
        String title = item.getTitle() ;
        listRmndTitle.setText(title);

        long bDateTime = item.getDateTime();

        DateFormat formattter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(bDateTime);
        listRmndDate.setText(formattter.format(calendar.getTime()));

        DateFormat format = new SimpleDateFormat("HH:mm:ss.SS");
        listRmndTime.setText(format.format(calendar.getTime()));
        //get the image associated with this property
        int imageID = item.getIcon();
        image.setImageResource(imageID);

        return view;
    }
}
