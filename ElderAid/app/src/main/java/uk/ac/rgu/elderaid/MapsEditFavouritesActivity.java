package uk.ac.rgu.elderaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.lang.reflect.Array;

public class MapsEditFavouritesActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btnshowSideNav;
    private ImageButton btnSOS;
    private ImageButton btnHome;
    private Button btnCancel;
    private Button btnSubmit;

    private static final String[] STATE_KEY_FAVOURITE= {"favourite1","favourite2","favourite3","favourite4"};
    private String favourite1;
    private String favourite2;
    private String favourite3;
    private String favourite4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_favourites);

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

        btnshowSideNav = (ImageButton) findViewById(R.id.btnMenu);
        btnshowSideNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavDialog();
            }
        });

        btnSubmit = (Button) findViewById(R.id.btnFavSubmit);
        btnSubmit.setOnClickListener(this);
        btnCancel = (Button) findViewById(R.id.btnCancelFav);
        btnCancel.setOnClickListener(this);
        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(STATE_KEY_FAVOURITE[0])){
                favourite1 = savedInstanceState.getString(STATE_KEY_FAVOURITE[0], "");
                if (!("".equals(favourite1))){
                    EditText etFavourite1 = findViewById(R.id.etFav1);
                    etFavourite1.setText(favourite1);
                }
            }
            if (savedInstanceState.containsKey(STATE_KEY_FAVOURITE[1])){
                favourite2 = savedInstanceState.getString(STATE_KEY_FAVOURITE[1], "");
                if (!("".equals(favourite2))){
                    EditText etFavourite2 = findViewById(R.id.etFav2);
                    etFavourite2.setText(favourite2);
                }
            }
            if (savedInstanceState.containsKey(STATE_KEY_FAVOURITE[2])){
                favourite3 = savedInstanceState.getString(STATE_KEY_FAVOURITE[2], "");
                if (!("".equals(favourite3))){
                    EditText etFavourite3 = findViewById(R.id.etFav3);
                    etFavourite3.setText(favourite3);
                }
            }
            if (savedInstanceState.containsKey(STATE_KEY_FAVOURITE[3])){
                favourite4 = savedInstanceState.getString(STATE_KEY_FAVOURITE[3], "");
                if (!("".equals(favourite4))){
                    EditText etFavourite4 = findViewById(R.id.etFav4);
                    etFavourite4.setText(favourite4);
                }
            }
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
        } else if (v.getId() == R.id.linkMaps || v.getId() == R.id.btnCancelFav) {
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
        } else if (v.getId() == R.id.btnFavSubmit){
            // Add to shared Preferences
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
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
}
