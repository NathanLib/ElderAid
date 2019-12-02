package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ContactDao cDao;

    private Button btn_delete;
    private Button btn_call;
    private Button btn_return;

    private TextView tv_phoneNumber;

    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        //Set the Dao
        this.cDao = ElderaidDatabase.getDatabase(this).cDao();

        btn_delete = (Button) findViewById(R.id.seeContact_btnDeleteContact);
        btn_delete.setOnClickListener(this);

        btn_call = (Button) findViewById(R.id.seeContact_btnReturnContact);
        btn_call.setOnClickListener(this);

        btn_return = (Button) findViewById(R.id.seeContact_btnCallContact);
        btn_return.setOnClickListener(this);

        tv_phoneNumber = (TextView) findViewById(R.id.seeContact_tvContactPhoneNumber);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.seeContact_btnDeleteContact) {
//            Intent intent = new Intent(
//                    getApplicationContext(), ContactActivity.class);
//            startActivity(intent);
        } else if (v.getId() == R.id.seeContact_btnReturnContact) {
            Intent intent = new Intent(
                    getApplicationContext(), ContactActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.seeContact_btnCallContact) {
            launchPhone();
        }
    }

    private void launchPhone() {
        this.phoneNumber = tv_phoneNumber.getText().toString();

        // create the Intent with the action to dial
        Intent intent = new Intent(Intent.ACTION_DIAL);
        // set the data to the phone number
        intent.setData(Uri.parse("tel:" + this.phoneNumber));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
