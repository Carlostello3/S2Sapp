package com.panthers.s2s;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.onesignal.OneSignal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;



public class HomeScreen extends AppCompatActivity implements OnMapReadyCallback {

    private long backPressedTime;
    private Toast backToast;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String welcomeUser, loggedUser, getUserEmail;
    FloatingActionButton fab;
    public static final String CHANNEL_1_ID = "MAIN";
    private NotificationManagerCompat notificationManagerCompat;
    MapView mapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNotificationChannels();


        //Setting User_Tags for notification
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        loggedUser = mUser.getEmail();
        OneSignal.sendTag("User_ID", loggedUser);

        //Get User's display name
        welcomeUser = mUser.getDisplayName();
        getUserEmail = mUser.getEmail();

        //View settings
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome!");
        setSupportActionBar(toolbar);
        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        //Map settings

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);



        //Floating action button
        fab = findViewById(R.id.fab);
        final TextView notificationText = findViewById(R.id.notificationText);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notTitle = "New Walk Chaperon Request";
                String notText = "A new request for a walk chaperon has been placed.";

                notificationText.setText(notText);

                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(notTitle)
                        .setContentText(notText)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSound(soundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();



                notificationManagerCompat.notify(1, notification);


            }
        });
}




    //Notifications
    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Walk Request",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            channel1.setDescription("A request for a chaperon has been placed.");


            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;

    }

    //Menu toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent intentHome = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intentHome);
                return true;

            case R.id.item2:
                Intent intentCalendar = new Intent(HomeScreen.this, CalendarView.class);
                startActivity(intentCalendar);
                return true;

            case R.id.item3:
                Intent intentAbout = new Intent(HomeScreen.this, AboutView.class);
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
            Intent intentHome = new Intent(Intent.ACTION_MAIN);
            intentHome.addCategory(Intent.CATEGORY_HOME);
            intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentHome);
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

    //Map settings
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng latLng = new LatLng(28.5383, -81.3792);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Orlando, FL");
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        map.addMarker(markerOptions);
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
