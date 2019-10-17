package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MedicalInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnEditMedInfo;
    private Button btnShowInsurance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My medical informations");
        setSupportActionBar(toolbar);

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

