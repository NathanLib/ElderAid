package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ContactActivity extends AppCompatActivity implements View.OnClickListener, ContactAdapter.OnContactListener {

    public final static String EXTRA_CONTACT_NAME = "uk.ac.rgu.elderaid.CONTACT_NAME";
    public final static String EXTRA_CONTACT_NUMBER = "uk.ac.rgu.elderaid.CONTACT_NUMBER";
    public final static String EXTRA_CONTACT_PHOTO = "uk.ac.rgu.elderaid.CONTACT_PHOTO";

    private ImageButton btnshowSideNav;
    private Button toolbar_addContact;
    private TextView btnMyCard;
    private LinearLayout favouriteContact1;
    private LinearLayout favouriteContact2;
    private LinearLayout favouriteContact3;
    private ImageButton btnSOS;
    private ImageButton btnHome;

    private List<Contact> contactList = new ArrayList<>();
    private RecyclerView recyclerView_contact;
    private ContactAdapter cAdapter;

    private ContactDao contactDao;
    private Contact contact;


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
        });
        ;


        favouriteContact1 = (LinearLayout) findViewById(R.id.favouriteLinear1);
        favouriteContact1.setOnClickListener(this);
        favouriteContact2 = (LinearLayout) findViewById(R.id.favouriteLinear2);
        favouriteContact2.setOnClickListener(this);
        favouriteContact3 = (LinearLayout) findViewById(R.id.favouriteLinear3);
        favouriteContact3.setOnClickListener(this);

        btnshowSideNav = (ImageButton) findViewById(R.id.btnMenu);
        btnshowSideNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavDialog();
            }
        });


        toolbar_addContact = (Button) findViewById(R.id.toolbar_btnAddContact);
        toolbar_addContact.setOnClickListener(this);

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

        // Code from https://www.androidhive.info/2016/01/android-working-with-recycler-view/
        recyclerView_contact = (RecyclerView) findViewById(R.id.rvContact);

        cAdapter = new ContactAdapter(contactList, this);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_contact.setLayoutManager(cLayoutManager);
        recyclerView_contact.setItemAnimator(new DefaultItemAnimator());
        recyclerView_contact.setAdapter(cAdapter);

//        new GetAllContacts().execute();
        prepareContactData();
    }

    private void prepareContactData() {
        contact = new Contact("Harry Potter", "+44 1234 5678", "content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F25/ORIGINAL/NONE/984870976", false);
        contactList.add(contact);

        contact = new Contact("Peter Parker", "+44 9876 4321", "content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F25/ORIGINAL/NONE/984870976", false);
        contactList.add(contact);
    }

    public void openNavDialog() {
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


    public void openMyCardDialog() {
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
        } else if (v.getId() == R.id.favouriteLinear1) {
            Intent intent = new Intent(
                    getApplicationContext(), ContactDetailsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.favouriteLinear2) {
            Intent intent = new Intent(
                    getApplicationContext(), ContactDetailsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.favouriteLinear3) {
            Intent intent = new Intent(
                    getApplicationContext(), ContactDetailsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.toolbar_btnAddContact) {
            Intent intent = new Intent(
                    getApplicationContext(), ContactAddActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onContactClick(int position) {
        Intent intent = new Intent(this, ContactDetailsActivity.class);

        intent.putExtra(EXTRA_CONTACT_NAME, contactList.get(position).getName());
        intent.putExtra(EXTRA_CONTACT_NUMBER, contactList.get(position).getPhoneNum());
        intent.putExtra(EXTRA_CONTACT_PHOTO, contactList.get(position).getImagePath());

        startActivity(intent);
    }


    //This is an Async task

    class GetAllContacts extends AsyncTask<Void, Void, List<Contact>> {
        @Override
        protected List<Contact> doInBackground(Void... voids) {
            return contactDao.getContacts();
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {
            super.onPostExecute(contacts);

//            for (Contact c : contacts) {
//                Log.d("Contacts", c.toString());
//
//                contactList.add(c);
//            }
        }
    }

}


