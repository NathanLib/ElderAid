package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MedicalInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnEditMedInfo;
    private Button btnShowInsurance;
    private ImageButton btnshowSideNav;
    private ImageButton btnSOS;
    private ImageButton btnHome;

    private List<Medication> medicationsList = new ArrayList<>();
    private RecyclerView recyclerView_medication;
    private MedicationAdapter mAdapter;

    private List<MedicalCondition> medicalConditionsList = new ArrayList<>();
    private RecyclerView recyclerView_medicalCondition;
    private MedicalConditionAdapter mcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_info);

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

        btnEditMedInfo = findViewById(R.id.toolbar_btnEdit);
        btnEditMedInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditDialog();
            }
        });

        btnShowInsurance = findViewById(R.id.btnInsurance);
        btnShowInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsuranceDialog();
            }
        });

        btnshowSideNav = (ImageButton) findViewById(R.id.btnMenu);
        btnshowSideNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavDialog();
            }
        });
        // The code below was adapted from a source on the internet from this point forward.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Medical Information");
        setSupportActionBar(toolbar);

        // Code from https://www.androidhive.info/2016/01/android-working-with-recycler-view/
        recyclerView_medication = (RecyclerView) findViewById(R.id.rvMedication);

        mAdapter = new MedicationAdapter(medicationsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_medication.setLayoutManager(mLayoutManager);
        recyclerView_medication.setItemAnimator(new DefaultItemAnimator());
        recyclerView_medication.setAdapter(mAdapter);

        prepareMedicationData();

        recyclerView_medicalCondition = (RecyclerView) findViewById(R.id.rvConditions);

        mcAdapter = new MedicalConditionAdapter(medicalConditionsList);
        RecyclerView.LayoutManager mcLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_medicalCondition.setLayoutManager(mcLayoutManager);
        recyclerView_medicalCondition.setItemAnimator(new DefaultItemAnimator());
        recyclerView_medicalCondition.setAdapter(mcAdapter);

        prepareMedicalConditionData();
    }

    private void prepareMedicationData() {
        Medication medication = new Medication("Atenolol");
        medicationsList.add(medication);

        medication = new Medication("Levothyroxine");
        medicationsList.add(medication);

        medication = new Medication("Rantidine");
        medicationsList.add(medication);
    }

    private void prepareMedicalConditionData() {
        MedicalCondition medicalCondition = new MedicalCondition("Medical Conditions");
        medicalConditionsList.add(medicalCondition);

        medicalCondition = new MedicalCondition("Gastroesophageal Reflux Disease");
        medicalConditionsList.add(medicalCondition);

        medicalCondition = new MedicalCondition("High Cholesterol");
        medicalConditionsList.add(medicalCondition);
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

    public void openInsuranceDialog(){
        final Dialog insuranceInfoDialog = new Dialog(this);
        insuranceInfoDialog.setContentView(R.layout.dialog_view_insurance);
        insuranceInfoDialog.setTitle("View Insurance Information");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(insuranceInfoDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        insuranceInfoDialog.show();
        insuranceInfoDialog.getWindow().setAttributes(lp);

        insuranceInfoDialog.show();
    }
    public void openEditDialog(){
        final Dialog editMedInfoDialog = new Dialog(this);
        editMedInfoDialog.setContentView(R.layout.dialog_edit_medinfo);
        editMedInfoDialog.setTitle("Edit Medical Info");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(editMedInfoDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        editMedInfoDialog.show();
        editMedInfoDialog.getWindow().setAttributes(lp);

        editMedInfoDialog.show();

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

}

