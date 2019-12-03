package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.LinearLayout;

import android.widget.TextView;

import java.util.List;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener  {

    private ImageButton btnshowSideNav;
    private Button toolbar_addContact;
    private TextView contact_tvContactA1;
    private TextView btnMyCard;
    private LinearLayout favouriteContact;
    private ImageButton btnSOS;
    private ImageButton btnHome;


    private ContactDao contactDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

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
                Intent intent = new Intent(
                        getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });;



        favouriteContact = (LinearLayout) findViewById(R.id.favouriteLinear1);
        favouriteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSeeContactDialog();
            }
        });
        btnshowSideNav = (ImageButton) findViewById(R.id.btnMenu);
        btnshowSideNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavDialog();
            }
        });


        toolbar_addContact = (Button) findViewById(R.id.toolbar_btnAddContact);
        toolbar_addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });

        contact_tvContactA1 = (TextView) findViewById(R.id.contact_tvContactA1);
        contact_tvContactA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSeeContactDialog();
            }
        });

        btnMyCard = (TextView) findViewById(R.id.contact_tvMyCard);
        btnMyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMyCardDialog();
            }
        });

        //Get the database instance
        ElderaidDatabase db = ElderaidDatabase.getDatabase(this);
        //Get the DAO from the database
        this.contactDao = db.cDao();



        // The code below was adapted from a source on the internet from this point forward.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My contacts");
        setSupportActionBar(toolbar);

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


    public void openAddDialog(){
        final Dialog addDialog = new Dialog(this);


        addDialog.setContentView(R.layout.dialog_add_contact);
        addDialog.setTitle("Add a contact");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(addDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        addDialog.show();
        addDialog.getWindow().setAttributes(lp);

        addDialog.show();
    }

    public void openSeeContactDialog() {
        final Dialog seeDialog = new Dialog(this);


        seeDialog.setContentView(R.layout.dialog_see_contact);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(seeDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        seeDialog.show();
        seeDialog.getWindow().setAttributes(lp);

        seeDialog.show();
    }

    public void openMyCardDialog(){
        final Dialog myCardDialog = new Dialog(this);


        myCardDialog.setContentView(R.layout.dialog_view_card);
        myCardDialog.setTitle("Navigation View");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(myCardDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        myCardDialog.show();
        myCardDialog.getWindow().setAttributes(lp);

        myCardDialog.show();

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
        } else if (v.getId() == R.id.linkPreferences) {
            Intent intent = new Intent(
                    getApplicationContext(), PreferencesActivity.class);
            startActivity(intent);
        }
    }


    //This is an Async task

    class GetAllContactsTask extends AsyncTask<Void, Void, List<Contact>>{
        @Override
        protected List<Contact> doInBackground(Void... voids) {
            return contactDao.getContacts();
        }

        @Override
        protected void onPostExecute(List<Contact> forecasts) {
            super.onPostExecute(forecasts);
            //Do stuff
        }
    }

    class UpdateInsertContactTask extends AsyncTask<List<Contact>, Void, Void>{
        @Override
        protected Void doInBackground(List<Contact>... contacts){
            if (contacts.length == 0){
                return null;
            }

            return null;
        }

    }

}


