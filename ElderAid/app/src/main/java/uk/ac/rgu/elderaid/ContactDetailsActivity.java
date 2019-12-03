package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ContactDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ContactDao cDao;

    private Button btn_delete;
    private Button btn_call;
    private Button btn_return;

    private TextView tv_name;
    private TextView tv_number;

    private ImageView iv_photo;

    private String contact_name;
    private String contact_number;
    private String contact_photo;

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

        tv_name = (TextView) findViewById(R.id.seeContact_tvContactName);
        tv_number = (TextView) findViewById(R.id.seeContact_tvContactPhoneNumber);

        iv_photo = (ImageView) findViewById(R.id.seeContact_ivContactPhoto);

        Intent launcher = getIntent();
        if (launcher.hasExtra(ContactActivity.EXTRA_CONTACT_NAME)) {
            contact_name = launcher.getStringExtra(ContactActivity.EXTRA_CONTACT_NAME);

            tv_name.setText(contact_name);
        }
        if (launcher.hasExtra(ContactActivity.EXTRA_CONTACT_NUMBER)) {
            contact_number = launcher.getStringExtra(ContactActivity.EXTRA_CONTACT_NUMBER);

            tv_number.setText(contact_number);
        }
//        if (launcher.hasExtra(ContactActivity.EXTRA_CONTACT_PHOTO)) {
//            contact_photo = launcher.getStringExtra(ContactActivity.EXTRA_CONTACT_PHOTO);
//
//            Uri imageUri = Uri.parse(contact_photo);
//
//            InputStream inputStream;
//
//            try {
//                inputStream = getContentResolver().openInputStream(imageUri);
//                Bitmap image = BitmapFactory.decodeStream(inputStream);
//
//
//                iv_photo.setImageBitmap(image);
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
//            }
//        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.seeContact_btnDeleteContact) {

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
}
