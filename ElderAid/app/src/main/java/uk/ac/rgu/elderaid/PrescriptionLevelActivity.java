package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class PrescriptionLevelActivity extends AppCompatActivity implements View.OnClickListener, PrescriptionAdapter.OnPrescriptionListener {

    private ImageButton btnshowSideNav;
    private LinearLayout prescription_llAddMedications;
    private LinearLayout prescription_llMyMedication1;
    private ImageButton btnSOS;
    private ImageButton btnHome;

    private ArrayList<Prescription> prescriptionList = new ArrayList<>();
    private RecyclerView recyclerView_presciption;
    private PrescriptionAdapter pAdapter;

    private PrescriptionDao prescriptionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_level);

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

        btnshowSideNav = (ImageButton) findViewById(R.id.btnMenu);
        btnshowSideNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavDialog();
            }
        });

        prescription_llAddMedications = (LinearLayout) findViewById(R.id.prescription_llAddMedications);
        prescription_llAddMedications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });

        //Get the database instance
        ElderaidDatabase db = ElderaidDatabase.getDatabase(this);
        //Get the DAO from the database
        this.prescriptionDao = db.pDao();


        // The code below was adapted from a source on the internet from this point forward./
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My prescription levels");
        setSupportActionBar(toolbar);

        recyclerView_presciption = (RecyclerView) findViewById(R.id.rvPrescription);

        pAdapter = new PrescriptionAdapter(prescriptionList, this);
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_presciption.setLayoutManager(pLayoutManager);
        recyclerView_presciption.setItemAnimator(new DefaultItemAnimator());
        recyclerView_presciption.setAdapter(pAdapter);

        preparePrescriptionData();
    }

    private void preparePrescriptionData() {
        Prescription prescription = new Prescription("Xanax", "10/2/2021","50");
        prescriptionList.add(prescription);

        prescription = new Prescription("Vicodin", "15/6/2020","23");
        prescriptionList.add(prescription);

        prescription = new Prescription("Simvastatin", "21/9/2020","34");
        prescriptionList.add(prescription);

        prescription = new Prescription("Lisinopril", "29/12/2019","14");
        prescriptionList.add(prescription);

        prescription = new Prescription("Levothyroxine", "11/10/2022","103");
        prescriptionList.add(prescription);

        prescription = new Prescription("Paracetamol", "18/5/2022","64");
        prescriptionList.add(prescription);


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

    public void openAddDialog() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_add_medication);
        dialog.setTitle("Add a medication");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        dialog.show();
    }

    public void openEditDialog() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_edit_prescription);
        dialog.setTitle("Add a medication");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        dialog.show();
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

    @Override
    public void onPrescriptionClick(int position){
        prescriptionList.get(position);
        openEditDialog();
    }
}
