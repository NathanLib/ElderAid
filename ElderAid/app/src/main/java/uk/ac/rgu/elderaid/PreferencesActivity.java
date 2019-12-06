package uk.ac.rgu.elderaid;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class PreferencesActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btnshowSideNav;
    private ImageButton btnSOS;
    private ImageButton btnHome;
    private Button btnCancel;
    private Button btnSubmit;
    private SharedPreferences sharedPrefs;
    private static final String STATE_KEY_CALID="calID";
    private String calID;
    private EventDao eventDao;
    private static final String preferencesFile = "uk.ac.rgu.elderaid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

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
        restorePrefs();

        btnshowSideNav = (ImageButton) findViewById(R.id.btnMenu);
        btnshowSideNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavDialog();
            }
        });

        btnSubmit = (Button) findViewById(R.id.btnPrefSubmit);
        btnSubmit.setOnClickListener(this);
        btnCancel = (Button) findViewById(R.id.btnCancelPref);
        btnCancel.setOnClickListener(this);
        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(STATE_KEY_CALID)){
                calID = savedInstanceState.getString(STATE_KEY_CALID, "");
                if (!("".equals(calID))){
                    EditText etCalID = findViewById(R.id.etCalID);
                    etCalID.setText(calID);
                }
            }
        }
        // *Database setup*
        //Get the database instance
        ElderaidDatabase db = ElderaidDatabase.getDatabase(this);
        //Get the DAO from the database
        this.eventDao = db.eDao();



    }





    @Override
    public void onClick(View v) {
        // Side nav
        if (v.getId() == R.id.linkHome || v.getId() == R.id.btnCancelPref) {
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
        } else if (v.getId() == R.id.btnPrefSubmit){


            // Add to shared Preferences
            saveSharedPrefs();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkPreferences) {
            Intent intent = new Intent(
                    getApplicationContext(), PreferencesActivity.class);
            startActivity(intent);
        }
    }

    public void saveSharedPrefs(){
        SharedPreferences.Editor preferenceEditor = sharedPrefs.edit();
        String prefCalIDKey = getString(R.string.prefCalIDKey);
        String defaultCalID = getString(R.string.CalIdDefault);
        EditText calID = findViewById(R.id.etCalID);

        String CalSet = sharedPrefs.getString(prefCalIDKey, defaultCalID);

        String etcalIDVal = String.valueOf(calID.getText());

        if(!(etcalIDVal.equals(CalSet))){
            new EmptyEventsTableTask().execute();
            Log.d("Changed","It was changed!");
        }
        preferenceEditor.putString(prefCalIDKey, etcalIDVal);

        preferenceEditor.apply();

    }

    public void restorePrefs(){
        String prefCalIDKey = getString(R.string.prefCalIDKey);

        String defaultCalID = getString(R.string.CalIdDefault);


        EditText calID = findViewById(R.id.etCalID);


        String CalSet = sharedPrefs.getString(prefCalIDKey, defaultCalID);


        calID.setText(CalSet);

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

        Button linkPreferences = (Button) sideNavDialog.findViewById(R.id.linkPreferences);
        linkPreferences.setOnClickListener(this);

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

    class EmptyEventsTableTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            eventDao.deleteAllEvents();
            return null;
        }
    }
}
