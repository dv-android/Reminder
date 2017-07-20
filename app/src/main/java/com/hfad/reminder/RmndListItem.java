package com.hfad.reminder;

/**
 * Created by DEVANG on 7/21/2017.
 */

public class RmndListItem {
    private String title ;
    private int icon;
    private long dateTime;
    public RmndListItem(int icon,String title,long dateTime){

        this.icon = icon;
        this.title = title;
        this.dateTime = dateTime;

    }

    public String getTitle(){
        return title;
    }

    public int getIcon(){
        return icon;
    }

    public long getDateTime(){ return dateTime;}
}
