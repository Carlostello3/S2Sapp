package com.panthers.s2s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AboutView extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("About Us");
        setSupportActionBar(toolbar);

        final Button websiteButton = findViewById(R.id.buttonWebsite);

        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent();
                webIntent.setAction(Intent.ACTION_VIEW);
                webIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                webIntent.setData(Uri.parse("https://www.soldierstoscholars.org"));
                startActivity(webIntent);
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
                Intent intentHome = new Intent(AboutView.this, HomeScreen.class);
                startActivity(intentHome);
                return true;

            case R.id.item2:
                Intent intentCalendar = new Intent(AboutView.this, CalendarView.class);
                startActivity(intentCalendar);
                return true;

            case R.id.item3:
                Intent intentAbout = new Intent(AboutView.this, AboutView.class);
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
