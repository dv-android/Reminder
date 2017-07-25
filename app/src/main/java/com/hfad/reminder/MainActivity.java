package com.hfad.reminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import static com.hfad.reminder.R.mipmap.ic_launcher_round;

public class MainActivity extends Activity implements View.OnClickListener{

    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private String[] toDoItems;
    private String toDoItemEntered;
    private Button btnDatePicker, btnTimePicker , btnCancel ,btnOk , btnBdaySave;
    private EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Calendar calendar  , remindCalender;
    private long entered_dt_ts , reminder_date_time_inMillis;
    private SQLiteOpenHelper reminderDatabaseHelper;
    private SQLiteDatabase db;
    private String firstName , lastName ,rmndTitle;
    private EditText fName , lName , txtTitle;
    private AlertDialog alertDialog;
    private ArrayList<ReminderItem> itemArrayList = new ArrayList<>();
    private Fragment fragment;
    private ArrayList<String> toDOItems =  new ArrayList<String>();
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    Set<String> set;



    class DrawerItemClickListenr implements ListView.OnItemClickListener{
        public void onItemClick(AdapterView<?> parent,View view,int position,long id){
          selectItem(position);

          setActionBarTitle(position);
        }
    };

    private void selectItem(int position){

        switch(position){
            case 0:
                fragment = new DashBoardFragment();
                break;
            case 1:
                fragment = new BirthdayFragment();
                break;
            case 2:
                fragment = new ReminderFragment();
                break;
            case 3:
                fragment = new ToDoFragment();
                break;
            default:
                fragment = new DashBoardFragment();
        }

        FragmentTransaction     ft =  getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame,fragment);

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        drawerLayout.closeDrawer(drawerList);
    }


    private void setActionBarTitle(int position){
        String title;
        if(position==0)
            title = getResources().getString(R.string.app_name);
        else
            title = titles[position];

        getActionBar().setTitle(title);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titles = getResources().getStringArray(R.array.titles);
        toDoItems = getResources().getStringArray(R.array.addItems);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1,titles));

        drawerList.setOnItemClickListener(new DrawerItemClickListenr());
        Log.d("Main Activity","Activity created");


        itemArrayList.add(new ReminderItem("Reminder",R.mipmap.ic_launcher));
        itemArrayList.add(new ReminderItem("To-Do-List",R.mipmap.ic_launcher));
        itemArrayList.add(new ReminderItem("Birthday",R.mipmap.ic_launcher));


        Bundle extras = getIntent().getExtras();

        if(extras != null){
            Boolean openFrmRmndNtfn = extras.getBoolean("openFrmRmndNtfn");

            if(openFrmRmndNtfn==true){
                drawerList.setItemChecked(2,true);
                drawerList.setSelection(2);
                drawerList.performItemClick(
                        drawerList.getAdapter().getView(2, null, null),
                        2,
                        drawerList.getAdapter().getItemId(2));
            }

        }




    }


    public void onClick(View v)throws SQLiteException{
        Log.d("btnBdaySave","Value is"+(btnBdaySave==null));
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            calendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                            entered_dt_ts = calendar.getTimeInMillis();
                            remindCalender = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                            Log.d("MainActivity","Time in seconds "+entered_dt_ts);
                                                    }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                            remindCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            remindCalender.set(Calendar.MINUTE, minute);
                            reminder_date_time_inMillis = remindCalender.getTimeInMillis();

                            DateFormat formattter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(reminder_date_time_inMillis);
                            formattter.format(calendar.getTime());

                            Log.d("Remind calendr value ","dt time-"+reminder_date_time_inMillis);
                            Log.d("Remind calendr value","format"+formattter.format(calendar.getTime()));
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        else if(v == btnBdaySave) {
               Log.d("Main Activity","Bday save clicked");

               firstName =  fName.getText().toString();
               lastName = lName.getText().toString();
               reminderDatabaseHelper = new ReminderDatabaseHelper(this);
                db = reminderDatabaseHelper.getWritableDatabase();
               Log.d("Main Activity","Entered DOB"+entered_dt_ts);
             //  Log.d("Main Activity","First Name Last Name"+firstName+lastName);
               ReminderDatabaseHelper.insertBdayDetails(db,firstName,lastName,entered_dt_ts);


               setActionBarTitle(1);
               drawerList.setItemChecked(1,true);
               drawerList.setSelection(1);
               drawerList.performItemClick(
                    drawerList.getAdapter().getView(1, null, null),
                    1,
                    drawerList.getAdapter().getItemId(1));
               alertDialog.hide();


        }
        else if(v==btnOk){

            reminderDatabaseHelper = new ReminderDatabaseHelper(this);
            db = reminderDatabaseHelper.getWritableDatabase();
            Log.d("Main Activity","Entered DOB"+entered_dt_ts);
            //  Log.d("Main Activity","First Name Last Name"+firstName+lastName);

            rmndTitle= txtTitle.getText().toString();
            ReminderDatabaseHelper.insertRemindDetails(db,rmndTitle,reminder_date_time_inMillis);

            setActionBarTitle(2);

            drawerList.setItemChecked(2,true);
            drawerList.setSelection(2);
            drawerList.performItemClick(
                    drawerList.getAdapter().getView(2, null, null),
                    2,
                    drawerList.getAdapter().getItemId(2));


            Date date = new Date(reminder_date_time_inMillis);
            Calendar cal=GregorianCalendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.SECOND,-20);
            long notificationTime = cal.getTimeInMillis();

            long timeStamp;

            for (int id=0;id<2;id++){
                Intent intent = new Intent(this, AlarmReceiver.class);
                intent.putExtra("requesteCode",id);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        this.getApplicationContext(), id, intent, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


                timeStamp = (id==0)? notificationTime:reminder_date_time_inMillis;
                Log.d("Alarm Manager ","TIme is"+timeStamp);

                alarmManager.setExact(AlarmManager.RTC_WAKEUP,timeStamp , pendingIntent);   // start from API 19 always use setExact instead od set() mthd
            }

            alertDialog.hide();

        }
        else if(v==btnCancel){

        }
        Log.d("Main Activity","Button clicked");
    }

    public void setAlarm(long millisecs){

    }



    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){


        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.add:
                generateDialog();
                break;
        }

        return true;
    }

    public void generateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                this);

        ArrayAdapter<ReminderItem> adapter = new customItemArrayAdapter(this, 0, itemArrayList);
        builder.setAdapter(
                adapter
                ,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                         if(which==0) {
                             Log.d("Main Activity", "Reminder clicked");
                             showReminderDialog();
                         }
                             else if(which==1) {
                             Log.d("Main Activity", "Todo clicked");
                             showToDoDialog();
                         }
                        else if(which==2){
                             Log.d("Main Activity","Birthday clicked");
                             showBirthdayDialog();
                         }

                    }
                })
                .setTitle("What you would like to ADD ?");
        builder.show();


    }

    public void showBirthdayDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Birthday");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.birhday_layout, null);
        builder.setView(dialogView);

        alertDialog = builder.create();
        alertDialog.show();

        btnDatePicker=(Button)  dialogView.findViewById(R.id.btn_date);
        txtDate=(EditText)  dialogView.findViewById(R.id.in_date);
        btnBdaySave = (Button) dialogView.findViewById(R.id.save);
        fName = (EditText)  dialogView.findViewById(R.id.first_name);
        lName = (EditText)  dialogView.findViewById(R.id.last_name);


        btnDatePicker.setOnClickListener(this);
        btnBdaySave.setOnClickListener(this);

        dialogView.findViewById(R.id.cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Main Activity","Cancel clicked");
                        alertDialog.hide();
                    }
                }
        );
        Log.d("btnBdaySave","Value is"+(btnBdaySave==null));

    }

    public void showReminderDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remider Details");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.date_time_picker, null);
        builder.setView(dialogView);

        alertDialog = builder.create();
        alertDialog.show();


        btnDatePicker=(Button)  dialogView.findViewById(R.id.btn_date);
        btnTimePicker=(Button)  dialogView.findViewById(R.id.btn_time);
        btnCancel = (Button)  dialogView.findViewById(R.id.cancel);
        btnOk = (Button)  dialogView.findViewById(R.id.ok);
        txtTitle=(EditText)  dialogView.findViewById(R.id.reminder_title);
        txtDate=(EditText)  dialogView.findViewById(R.id.in_date);
        txtTime=(EditText)  dialogView.findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        dialogView.findViewById(R.id.cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Log.d("Main Activity","Cancel clicked");
                         alertDialog.hide();
                    }
                }
        );
    }

    public void showToDoDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create To Do List");


        final EditText input = new EditText(this);


        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                toDoItemEntered = input.getText().toString();
                Log.d("Main Activity", toDoItemEntered);

                toDOItems.add(input.getText().toString());

                prefs = getBaseContext().getSharedPreferences("todoPrefsKey",getApplicationContext().MODE_PRIVATE);
                edit = prefs.edit();
                for(int i=0;i<toDOItems.size();i++){
                    edit.putString("value"+i,toDOItems.get(i));
                }
                edit.putInt("size",toDOItems.size());
                edit.commit();

                drawerList.setItemChecked(3,true);
                drawerList.setSelection(3);
                drawerList.performItemClick(
                        drawerList.getAdapter().getView(3, null, null),
                        3,
                        drawerList.getAdapter().getItemId(3));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
