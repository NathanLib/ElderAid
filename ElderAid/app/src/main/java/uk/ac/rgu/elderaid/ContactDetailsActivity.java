package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ContactDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ContactDao contactDetailsDao;

    private Button btn_delete;
    private Button btn_call;
    private Button btn_return;

    private TextView tv_name;
    private TextView tv_number;

    private String contact_name;
    private String contact_number;

    private int contact_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        //Get the database instance
        ElderaidDatabase db = ElderaidDatabase.getDatabase(this);
        //Get the DAO from the database
        this.contactDetailsDao = db.cDao();

        btn_delete = (Button) findViewById(R.id.seeContact_btnDeleteContact);
        btn_delete.setOnClickListener(this);

        btn_call = (Button) findViewById(R.id.seeContact_btnReturnContact);
        btn_call.setOnClickListener(this);

        btn_return = (Button) findViewById(R.id.seeContact_btnCallContact);
        btn_return.setOnClickListener(this);

        tv_name = (TextView) findViewById(R.id.seeContact_tvContactName);
        tv_number = (TextView) findViewById(R.id.seeContact_tvContactPhoneNumber);

        contact_position = -1;

        Intent launcher = getIntent();
        if (launcher.hasExtra(ContactActivity.EXTRA_CONTACT_NAME)) {
            contact_name = launcher.getStringExtra(ContactActivity.EXTRA_CONTACT_NAME);

            tv_name.setText(contact_name);
        }
        if (launcher.hasExtra(ContactActivity.EXTRA_CONTACT_NUMBER)) {
            contact_number = launcher.getStringExtra(ContactActivity.EXTRA_CONTACT_NUMBER);

            tv_number.setText(contact_number);
        }
        if (launcher.hasExtra(ContactActivity.EXTRA_CONTACT_POSITION)) {
            contact_position = launcher.getIntExtra(ContactActivity.EXTRA_CONTACT_POSITION, -1);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.seeContact_btnDeleteContact) {
            new DeleteContact().execute();

            Toast.makeText(this, "Contact removed!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(
                    getApplicationContext(), ContactActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.seeContact_btnReturnContact) {
            Intent intent = new Intent(
                    getApplicationContext(), ContactActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.seeContact_btnCallContact) {
            launchPhone();
        }
    }

    private void launchPhone() {
        // create the Intent with the action to dial
        Intent intent = new Intent(Intent.ACTION_DIAL);
        // set the data to the phone number
        intent.setData(Uri.parse("tel:" + contact_number));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    class DeleteContact extends AsyncTask<List<Contact>, Void, Void> {
        @Override
        protected Void doInBackground(List<Contact>... contactsList) {
            //Get a list of all the contacts already on the Database
            List<Contact> allContacts = contactDetailsDao.getContacts();

            contactDetailsDao.delete(allContacts.get(contact_position));

            return null;
        }
    }
}