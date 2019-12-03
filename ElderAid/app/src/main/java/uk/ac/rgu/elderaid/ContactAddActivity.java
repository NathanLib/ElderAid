package uk.ac.rgu.elderaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String STATE_KEY_NAME = "Name of the contact";
    private static final String STATE_KEY_NUMBER = "Phone number";

    public static final int IMAGE_GALLERY_REQUEST = 20;

    private String name;
    private String number;

    private ImageView imgPicture;

    private Button btn_selectPhoto;

    private ContactDao contactAddDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(STATE_KEY_NAME)) {
                name = savedInstanceState.getString(STATE_KEY_NAME, "");
                if (!("".equals(name))) {
                    EditText et_name = findViewById(R.id.addContact_etName);
                    et_name.setText(name);
                }
            }

            if (savedInstanceState.containsKey(STATE_KEY_NUMBER)) {
                number = savedInstanceState.getString(STATE_KEY_NUMBER, "");
                if (!("".equals(number))) {
                    EditText et_number = findViewById(R.id.addContact_etPhoneNumber);
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

        imgPicture = (ImageView) findViewById(R.id.addContact_ic_profile);
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                // the address of the image on the SD Card.
                Uri imageUri = data.getData();

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
}
