package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener  {

    private ImageButton btnshowSideNav;
    private Button toolbar_addContact;
    private TextView contact_tvContactA1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My contacts");
        setSupportActionBar(toolbar);

        btnshowSideNav = (ImageButton) findViewById(R.id.btnMenu);
        btnshowSideNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavDialog();
            }
        });

        toolbar_addContact = (Button) findViewById(R.id.toolbar_btnAddContact);
        toolbar_addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });

        contact_tvContactA1 = (TextView) findViewById(R.id.contact_tvContactA1);
        contact_tvContactA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSeeContactDialog();
            }
        });
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

    public void openAddDialog(){
        final Dialog addDialog = new Dialog(this);


        addDialog.setContentView(R.layout.dialog_add_contact);
        addDialog.setTitle("Add a contact");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(addDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        addDialog.show();
        addDialog.getWindow().setAttributes(lp);

        addDialog.show();
    }

    public void openSeeContactDialog(){
        final Dialog seeDialog = new Dialog(this);


        seeDialog.setContentView(R.layout.dialog_see_contact);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(seeDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        seeDialog.show();
        seeDialog.getWindow().setAttributes(lp);

        seeDialog.show();
    }

    @Override
    public void onClick(View v) {

    }
}
