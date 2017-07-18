package com.hfad.reminder;

/**
 * Created by DEVANG on 7/18/2017.
 */

public class BdayListItem {
    private String fName ,lName;
    private int icon;
    private long bDate;
    public BdayListItem(int icon,String fName,String lName,long bDate){

        this.icon = icon;
        this.fName = fName;
        this.lName = lName;
        this.bDate = bDate;
    }

    public String getFirstName(){
        return fName;
    }

    public String getLastName(){
        return lName;
    }
    public int getIcon(){
        return icon;
    }
    public long getBdayDate(){ return bDate;}
}
