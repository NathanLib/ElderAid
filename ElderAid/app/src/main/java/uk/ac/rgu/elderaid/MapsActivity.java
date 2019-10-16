package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MapsActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnEditFavourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        btnEditFavourites = findViewById(R.id.btnEditFav);
        btnEditFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

    }

    public void openDialog(){
        final Dialog favDialog = new Dialog(this);
        favDialog.setContentView(R.layout.dialog_add_favourites);
        favDialog.setTitle("Edit Favourites");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(favDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        favDialog.show();
        favDialog.getWindow().setAttributes(lp);

        favDialog.show();

    }

    @Override
    public void onClick(View view) {

    }

}
