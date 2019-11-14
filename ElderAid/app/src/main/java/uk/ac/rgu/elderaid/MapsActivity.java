package uk.ac.rgu.elderaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MapsActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnEditFavourites;
    private ImageButton btnshowSideNav;
    private ImageButton btnSOS;
    private ImageButton btnHome;
    private Button btnSubmit;
    private ImageButton btnFav1, btnFav2, btnFav3, btnFav4;
    private static final String preferencesFile = "uk.ac.rgu.elderaid";
    private SharedPreferences sharedPrefs;



    private String destination;
    private static final String STATE_KEY_DESTINATION = "destination";
    private static final String TAG = MapsActivity.class.getCanonicalName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        btnSOS = (ImageButton) findViewById(R.id.btnSOS);
        btnSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSOSPhone();
            }
        });

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        sharedPrefs = getSharedPreferences(preferencesFile, MODE_PRIVATE);

        btnEditFavourites = findViewById(R.id.btnEditFav);
        btnEditFavourites.setOnClickListener(this);


        btnshowSideNav = (ImageButton) findViewById(R.id.btnMenu);
        btnshowSideNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavDialog();
            }
        });

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);


        // The code below was adapted from a source on the internet from this point.

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My maps");
        setSupportActionBar(toolbar);
        // Until this point. (The code from this point onward is our own.)

        btnFav1 = (ImageButton) findViewById(R.id.btnHospital);
        btnFav2 = (ImageButton) findViewById(R.id.btnHomeFav);
        btnFav3 = (ImageButton) findViewById(R.id.btnVet);
        btnFav4 = (ImageButton) findViewById(R.id.btnShop);
        btnFav1.setOnClickListener(this);
        btnFav2.setOnClickListener(this);
        btnFav3.setOnClickListener(this);
        btnFav4.setOnClickListener(this);


        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(STATE_KEY_DESTINATION)){
                destination = savedInstanceState.getString(STATE_KEY_DESTINATION, "");
                if (!("".equals(destination))){
                    EditText addressLocation = findViewById(R.id.etSearch);
                    addressLocation.setText(destination);
                }
            }
        }




    }

    public void openNavDialog(){
        final Dialog sideNavDialog = new Dialog(this);


        sideNavDialog.setContentView(R.layout.dialog_side_navigation);
        sideNavDialog.setTitle("Navigation View");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(sideNavDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        sideNavDialog.getWindow().setAttributes(lp);

        Button linkHome = (Button) sideNavDialog.findViewById(R.id.linkHome);
        linkHome.setOnClickListener(this);

        Button linkCalendar = (Button) sideNavDialog.findViewById(R.id.linkCalendar);
        linkCalendar.setOnClickListener(this);

        Button linkMaps = (Button) sideNavDialog.findViewById(R.id.linkMaps);
        linkMaps.setOnClickListener(this);

        Button linkTaskList = (Button) sideNavDialog.findViewById(R.id.linkTaskList);
        linkTaskList.setOnClickListener(this);

        Button linkContacts = (Button) sideNavDialog.findViewById(R.id.linkContacts);
        linkContacts.setOnClickListener(this);

        Button linkMedInfo = (Button) sideNavDialog.findViewById(R.id.linkMedInfo);
        linkMedInfo.setOnClickListener(this);

        Button linkPrescription = (Button) sideNavDialog.findViewById(R.id.linkPrescription);
        linkPrescription.setOnClickListener(this);

        Button btnClose = (Button) sideNavDialog.findViewById(R.id.closeBtn);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideNavDialog.cancel();
            }
        });



        sideNavDialog.show();
    }

    //Maps only Java
//    public void openDialog(){
//        final Dialog favDialog = new Dialog(this);
//        favDialog.setContentView(R.layout.dialog_add_favourites);
//        favDialog.setTitle("Edit Favourites");
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(favDialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        favDialog.show();
//        favDialog.getWindow().setAttributes(lp);
//
//        favDialog.show();
//
//    }


    private void launchMap(){
        EditText addressLocation = findViewById(R.id.etSearch);
        String address = addressLocation.getText().toString();
        address.replace(" ","+");

        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + address);
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }
    }

    private void launchMap(String location){
        location.replace(" ","+");
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location);
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }
    }


    //    ***********************************

    private void launchSOSPhone() {
        // create the Intent with the action to dial
        Intent intent = new Intent(Intent.ACTION_DIAL);
        // set the data to the phone number
        intent.setData(Uri.parse("tel:+4401224272600"));

        // check the intent can be resolved by the device
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @Override
    public void onClick(View v) {
        // Side nav
        if (v.getId() == R.id.linkHome) {
            Intent intent = new Intent(
                    getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkCalendar) {
            Intent intent = new Intent(
                    getApplicationContext(), CalendarActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkMaps) {
            Intent intent = new Intent(
                    getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkTaskList) {
            Intent intent = new Intent(
                    getApplicationContext(), TaskCheckListActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkContacts) {
            Intent intent = new Intent(
                    getApplicationContext(), ContactActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkMedInfo) {
            Intent intent = new Intent(
                    getApplicationContext(), MedicalInfoActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkPrescription) {
            Intent intent = new Intent(
                    getApplicationContext(), PrescriptionLevelActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btnSubmit) {
            launchMap();
        } else if (v.getId() == R.id.btnEditFav){
            Intent intent = new Intent(getApplicationContext(), MapsEditFavouritesActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.btnHospital){
            String prefFav1Key = getString(R.string.prefLoc1Key);
            String defaultFav1 = getString(R.string.Loc1Default);
            String fav1Location = sharedPrefs.getString(prefFav1Key, defaultFav1);
            if (!fav1Location.equals(defaultFav1)){
                launchMap(fav1Location);
            }
            else{
                Toast.makeText(getApplicationContext(),"You have not setup this favourite",Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btnHomeFav){
            String prefFav2Key = getString(R.string.prefLoc2Key);
            String defaultFav2 = getString(R.string.Loc2Default);
            String fav2Location = sharedPrefs.getString(prefFav2Key, defaultFav2);
            if (!fav2Location.equals(defaultFav2)){
                launchMap(fav2Location);
            } else{
                Toast.makeText(getApplicationContext(),"You have not setup this favourite",Toast.LENGTH_SHORT).show();

            }
        } else if (v.getId() == R.id.btnVet){
            String prefFav3Key = getString(R.string.prefLoc3Key);
            String defaultFav3 = getString(R.string.Loc3Default);
            String fav3Location = sharedPrefs.getString(prefFav3Key, defaultFav3);
            if (!fav3Location.equals(defaultFav3)){
                launchMap(fav3Location);
            } else{
                Toast.makeText(getApplicationContext(),"You have not setup this favourite",Toast.LENGTH_SHORT).show();

            }
        } else if (v.getId() == R.id.btnShop){
            String prefFav4Key = getString(R.string.prefLoc3Key);
            String defaultFav4 = getString(R.string.Loc4Default);
            String fav4Location = sharedPrefs.getString(prefFav4Key, defaultFav4);
            if (!fav4Location.equals(defaultFav4)){
                launchMap(fav4Location);
            } else{
                Toast.makeText(getApplicationContext(),"You have not setup this favourite",Toast.LENGTH_SHORT).show();

            }

        }



    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_KEY_DESTINATION, destination);

        //outState.putString(STATE_KEY_FORECAST, forecast);

    }



}
