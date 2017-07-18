package com.hfad.reminder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DEVANG on 7/13/2017.
 */

public class customItemArrayAdapter extends ArrayAdapter<ReminderItem>{

    private Context context;
    private List<ReminderItem> itemList;

    //constructor, call on creation
    public customItemArrayAdapter(Context context, int resource, ArrayList<ReminderItem> itemList) {
        super(context, resource, itemList);

        this.context = context;
        this.itemList = itemList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
        ReminderItem item = itemList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_layout, null);

        TextView listItemName = (TextView) view.findViewById(R.id.listItemName);

        ImageView image = (ImageView) view.findViewById(R.id.image);

        //set address and description
        String itemName = item.getListItemName() ;
        listItemName.setText(itemName);


        //get the image associated with this property
        int imageID = item.getIcon();
        image.setImageResource(imageID);

        return view;
    }
}
