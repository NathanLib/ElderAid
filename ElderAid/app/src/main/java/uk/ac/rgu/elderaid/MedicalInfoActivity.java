package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MedicalInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnEditMedInfo;
    private Button btnShowInsurance;
    private ImageButton btnshowSideNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_info);



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
    }

    public void openNavDialog(){
        final Dialog sideNavDialog = new Dialog(this);


        sideNavDialog.setContentView(R.layout.dialog_side_navigation);
        sideNavDialog.setTitle("Navigation View");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(sideNavDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        sideNavDialog.show();
        sideNavDialog.getWindow().setAttributes(lp);

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

    @Override
    public void onClick(View view) {

    }

}

