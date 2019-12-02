package uk.ac.rgu.elderaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String STATE_KEY_NAME = "Name of the contact";
    private static final String STATE_KEY_NUMBER = "Phone number";

    public static final int PICK_IMAGE = 1;

    private String name;
    private String number;

    private Button btn_selectPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(STATE_KEY_NAME)){
                name = savedInstanceState.getString(STATE_KEY_NAME, "");
                if (!("".equals(name))){
                    EditText et_name = findViewById(R.id.addContact_etName);
                    et_name.setText(name);
                }
            }

            if (savedInstanceState.containsKey(STATE_KEY_NUMBER)){
                number = savedInstanceState.getString(STATE_KEY_NUMBER, "");
                if (!("".equals(number))){
                    EditText et_number = findViewById(R.id.addContact_etPhoneNumber);
                    et_number.setText(number);
                }
            }
        }

        btn_selectPhoto = (Button) findViewById(R.id.addContact_btnSelectPhoto);
        btn_selectPhoto.setOnClickListener(this);
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
            // Code from https://stackoverflow.com/questions/5309190/android-pick-images-from-gallery
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

            startActivityForResult(chooserIntent, PICK_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            //TODO: action
        }
    }
}
