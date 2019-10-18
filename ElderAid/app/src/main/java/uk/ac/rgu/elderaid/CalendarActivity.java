package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnAddEvent;
    private ImageButton btnshowSideNav;
    private LinearLayout calendar_llEvent1;




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        btnAddEvent = (ImageButton) findViewById(R.id.calendar_btnAddEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });

        btnshowSideNav = (ImageButton) findViewById(R.id.btnMenu);
        btnshowSideNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavDialog();
            }
        });

        calendar_llEvent1 = (LinearLayout) findViewById(R.id.calendar_llEvent1);
        calendar_llEvent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSeeDialog();
            }
        });


        // The code below was adapted from a source on the internet from this point forward.

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My calendar");
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

    public void openAddDialog() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_add_event);
        dialog.setTitle("Add an event");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        dialog.show();
    }

    public void openSeeDialog() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_see_event);
        dialog.setTitle("Add an event");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        dialog.show();
    }

    @Override
    public void onClick(View view) {
    }
}
