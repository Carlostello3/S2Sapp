package com.panthers.s2s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.EventLog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;

public class CalendarView extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Calendar");
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        final android.widget.CalendarView calendarView = (android.widget.CalendarView) findViewById(R.id.calendarView);


        /**
         * CALENDAR EVENTS
         */

        final String cal1 = "10/1/2019";
        final String cal1Text = "Work Session";

        final String cal2 = "10/2/2019";
        final String cal2Text = "Hispanic Heritage Month Event";

        final String cal3 = "10/8/2019";
        final String cal3Text = "School Board Meeting";

        final String cal4 = "10/10/2019";
        final String cal4Text = "Parent Academy Virtual";

        final String cal5 = "10/11/2019";
        final String cal5Text = "Foundation for OCPS";

        final String cal6 = "10/16/2019";
        final String cal6Text = "End of First Marking Period";

        final String cal7 = "10/17/2019";
        final String cal7Text = "Hurricane Dorian Make-Up Day";

        final String cal8 = "10/18/2019";
        final String cal8Text = "Teacher Work / Student Holiday";

        final String cal9 = "10/21/2019";
        final String cal9Text = "Begin Second Marking Period";

        final String cal10 = "10/22/2019";
        final String cal10Text = "School Board Meeting";







        calendarView.setOnDateChangeListener(new android.widget.CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull android.widget.CalendarView view, int year, int month, int dayOfMonth) {

                String date = (month + 1) + "/" + dayOfMonth + "/" + year;

                if (date.equals(cal1)){
                    final Toast cal1Toast = Toast.makeText(getApplicationContext(), cal1Text, Toast.LENGTH_SHORT);
                    cal1Toast.show();
                } if (date.equals(cal2)){
                    final Toast cal1Toast = Toast.makeText(getApplicationContext(), cal2Text, Toast.LENGTH_SHORT);
                    cal1Toast.show();
                } if (date.equals(cal3)){
                    final Toast cal1Toast = Toast.makeText(getApplicationContext(), cal3Text, Toast.LENGTH_SHORT);
                    cal1Toast.show();
                } if (date.equals(cal4)){
                    final Toast cal1Toast = Toast.makeText(getApplicationContext(), cal4Text, Toast.LENGTH_SHORT);
                    cal1Toast.show();
                } if (date.equals(cal5)){
                    final Toast cal1Toast = Toast.makeText(getApplicationContext(), cal5Text, Toast.LENGTH_SHORT);
                    cal1Toast.show();
                } if (date.equals(cal6)){
                    final Toast cal1Toast = Toast.makeText(getApplicationContext(), cal6Text, Toast.LENGTH_SHORT);
                    cal1Toast.show();
                } if (date.equals(cal7)){
                    final Toast cal1Toast = Toast.makeText(getApplicationContext(), cal7Text, Toast.LENGTH_SHORT);
                    cal1Toast.show();
                } if (date.equals(cal8)){
                    final Toast cal1Toast = Toast.makeText(getApplicationContext(), cal8Text, Toast.LENGTH_SHORT);
                    cal1Toast.show();
                } if (date.equals(cal9)){
                    final Toast cal1Toast = Toast.makeText(getApplicationContext(), cal9Text, Toast.LENGTH_SHORT);
                    cal1Toast.show();
                } if (date.equals(cal10)){
                    final Toast cal1Toast = Toast.makeText(getApplicationContext(), cal10Text, Toast.LENGTH_SHORT);
                    cal1Toast.show();
                } if (date.equals(null)) {
                    final Toast noEventToast = Toast.makeText(getApplicationContext(), "No Events", Toast.LENGTH_SHORT);
                    noEventToast.show();
                }








            }
        });











    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent intentHome = new Intent(CalendarView.this, HomeScreen.class);
                startActivity(intentHome);
                return true;

            case R.id.item2:
                Intent intentCalendar = new Intent(CalendarView.this, CalendarView.class);
                startActivity(intentCalendar);
                return true;

            case R.id.item3:
                Intent intentAbout = new Intent(CalendarView.this, AboutView.class);
                startActivity(intentAbout);
                return true;

            case R.id.item4:
                openDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();

    }

    public void openDialog(){
        SignOutDialog signOutDialog = new SignOutDialog();
        signOutDialog.show(getSupportFragmentManager(), "Sign Out");

    }
}
