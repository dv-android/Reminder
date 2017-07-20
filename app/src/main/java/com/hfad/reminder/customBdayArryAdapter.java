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
 * Created by DEVANG on 7/18/2017.
 */

public class customBdayArryAdapter extends ArrayAdapter<BdayListItem> {

    private Context context;
    private List<BdayListItem> itemList;

    //constructor, call on creation
    public customBdayArryAdapter(Context context, int resource, ArrayList<BdayListItem> itemList) {
        super(context, resource, itemList);

        this.context = context;
        this.itemList = itemList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
        BdayListItem item = itemList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_row_bday, null);



        ImageView image = (ImageView) view.findViewById(R.id.bdayImage);
        TextView listBdayName = (TextView) view.findViewById(R.id.badyName);
        TextView listBdayDate = (TextView) view.findViewById(R.id.badyDate);

        //set address and description
        String fName = item.getFirstName() ;
        String lName = item.getLastName();
        String fullName = fName + "  "+ lName;
        listBdayName.setText(fullName);

        long bDate = item.getBdayDate();
        DateFormat formattter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(bDate);
        formattter.format(calendar.getTime());
        listBdayDate.setText(formattter.format(calendar.getTime()));

        //get the image associated with this property
        int imageID = item.getIcon();
        image.setImageResource(imageID);

        return view;
    }
}
