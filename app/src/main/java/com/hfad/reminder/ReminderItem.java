package com.hfad.reminder;

/**
 * Created by DEVANG on 7/13/2017.
 */

public class ReminderItem {
    private String listItemName;
    private int icon;

    public ReminderItem(String listItemName,int icon){
        this.listItemName = listItemName;
        this.icon = icon;
    }

    public String getListItemName(){
        return listItemName;
    }

    public int getIcon(){
        return icon;
    }
}
