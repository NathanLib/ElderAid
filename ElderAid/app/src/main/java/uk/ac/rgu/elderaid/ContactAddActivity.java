package uk.ac.rgu.elderaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String STATE_KEY_NAME = "Name of the contact";
    private static final String STATE_KEY_NUMBER = "Phone number";

    public static final int IMAGE_GALLERY_REQUEST = 20;

    private String name;
    private String number;
    private String path;

    private EditText et_name;
    private EditText et_number;

    private ImageView imgPicture;

    private Button btn_selectPhoto;
    private Button btn_save;

    private CheckBox cb_favourite;

    private Boolean isFavourite;
    private Boolean favourites_full;

    private ContactDao contactAddDao;

    private Uri imageUri;

    private List<Contact> newContact = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        this.name = "";
        this.number = "";
        this.path = "";
        this.isFavourite = false;

        this.imageUri = null;

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(STATE_KEY_NAME)) {
                name = savedInstanceState.getString(STATE_KEY_NAME, "");
                if (!("".equals(name))) {
                    et_name = findViewById(R.id.addContact_etName);
                    et_name.setText(name);
                }
            }

            if (savedInstanceState.containsKey(STATE_KEY_NUMBER)) {
                number = savedInstanceState.getString(STATE_KEY_NUMBER, "");
                if (!("".equals(number))) {
                    et_number = findViewById(R.id.addContact_etPhoneNumber);
                    et_number.setText(number);
                }
            }
        }

        //Get the database instance
        ElderaidDatabase db = ElderaidDatabase.getDatabase(this);
        //Get the DAO from the database
        this.contactAddDao = db.cDao();

        btn_selectPhoto = (Button) findViewById(R.id.addContact_btnSelectPhoto);
        btn_selectPhoto.setOnClickListener(this);

        btn_save = (Button) findViewById(R.id.addContact_btnSaveContact);
        btn_save.setOnClickListener(this);

        imgPicture = (ImageView) findViewById(R.id.addContact_ic_profile);

        cb_favourite = (CheckBox) findViewById(R.id.addContact_cbFavorite);

        Intent launcher = getIntent();
        if (launcher.hasExtra(ContactActivity.EXTRA_CONTACT_FAV_FULL)) {
            favourites_full = launcher.getBooleanExtra(ContactActivity.EXTRA_CONTACT_FAV_FULL, false);

            if (favourites_full == true) {
                cb_favourite.setEnabled(false);
                cb_favourite.setText("You already have 3 favorite contacts!");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_KEY_NAME, name);

        outState.putString(STATE_KEY_NUMBER, number);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addContact_btnSelectPhoto) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

            File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String pictureDirectoryPath = pictureDirectory.getPath();
            Uri data = Uri.parse(pictureDirectoryPath);
            photoPickerIntent.setDataAndType(data, "image/*");
            startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
        } else if (v.getId() == R.id.addContact_btnSaveContact) {

            et_name = findViewById(R.id.addContact_etName);
            name = et_name.getText().toString();

            et_number = findViewById(R.id.addContact_etPhoneNumber);
            number = et_number.getText().toString();

            if (imageUri != null) {
                path = imageUri.toString();
            }

            if (cb_favourite.isChecked()) {
                isFavourite = true;
            }

            Contact c = new Contact(name, number, path, isFavourite);
            newContact.add(c);

            new UpdateOrInsertContact().execute(newContact);

            Intent intent = new Intent(
                    getApplicationContext(), ContactActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                // the address of the image on the SD Card.
                imageUri = data.getData();

                // declare a stream to read the image data from the SD Card.
                InputStream inputStream;

                // we are getting an input stream, based on the URI of the image.
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    // get a bitmap from the stream.
                    Bitmap image = BitmapFactory.decodeStream(inputStream);


                    // show the image to the user
                    imgPicture.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    class UpdateOrInsertContact extends AsyncTask<List<Contact>, Void, Void> {

        @Override
        protected Void doInBackground(List<Contact>... contactsList) {
            if (contactsList.length == 0) {
                return null;
            }

            List<Contact> contacts = contactsList[0];

            //Get a list of all the contacts already on the Database
            List<Contact> savedContacts = contactAddDao.getContacts();

            for (Contact c : savedContacts) {

                if (c.getPhoneNum().equals(contacts.get(0).getPhoneNum())) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "This phone number is already used in your contacts!", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            }


            contactAddDao.insertContacts((Contact[]) contacts.toArray(new Contact[contacts.size()]));

            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "Contact added!", Toast.LENGTH_LONG).show();
                }
            });

            return null;
        }
    }
}
