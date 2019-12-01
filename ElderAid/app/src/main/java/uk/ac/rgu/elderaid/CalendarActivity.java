package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.api.services.calendar.*;

import org.json.JSONArray;
import org.json.JSONObject;


public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnAddEvent;
    private ImageButton btnshowSideNav;
    private LinearLayout calendar_llEvent1;
    private ImageButton btnSOS;
    private ImageButton btnHome;
    private ImageButton btnSyncEvents;
    private static final String TAG = "HELLO";

    // URL template to download data
    private static final String URL_TEMPLATE ="https://www.googleapis.com/calendar/v3/calendars/";






    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        btnSOS = (ImageButton) findViewById(R.id.btnSOS);
        btnSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSOSPhone();
            }
        });

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });;

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


        btnSyncEvents = (ImageButton) findViewById(R.id.calendar_btnSyncEvents);
        btnSyncEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadEvents();
            }
        });


        // The code below was adapted from a source on the internet from this point forward.

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My calendar");
        setSupportActionBar(toolbar);

    }

    public void downloadEvents(){

        String calendarID = "kqpt7ub3stsip5mrlf9nt6fnjg@group.calendar.google.com";
        String url = URL_TEMPLATE + calendarID +"/events?key=AIzaSyC-Gs8DUH7IH7XhShlgzMVO_VIORgl68Qs";
        Log.d("URL", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseObj = new JSONObject(response);
                            JSONArray itemsArray = responseObj.getJSONArray("items");
                            Log.d("Stuffs", itemsArray.toString());
                            for(int i =0; i < itemsArray.length();i++) {
                                JSONObject obj = itemsArray.getJSONObject(i);


                                String summary = obj.getString("summary");
                                String description = obj.getString("description");
                                String location = obj.getString("location");

                                JSONObject start = obj.getJSONObject("start");
                                String startDate = start.getString("date");

                                JSONObject end = obj.getJSONObject("end");
                                String endDate = end.getString("date");

                                String evrything =  summary + "\n"
                                        + description + "\n"
                                        + location + "\n"
                                        + startDate + "\n"
                                        + endDate + "\n";
                                Log.d("ItemSummary", evrything);

                            }


                        } catch (Exception e) {
                            Log.d(TAG, "Exception e");
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "VolleyError: " + error);
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);

    }

    public void openNavDialog(){
        final Dialog sideNavDialog = new Dialog(this);


        sideNavDialog.setContentView(R.layout.dialog_side_navigation);
        sideNavDialog.setTitle("Navigation View");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(sideNavDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        sideNavDialog.getWindow().setAttributes(lp);

        Button linkHome = (Button) sideNavDialog.findViewById(R.id.linkHome);
        linkHome.setOnClickListener(this);

        Button linkCalendar = (Button) sideNavDialog.findViewById(R.id.linkCalendar);
        linkCalendar.setOnClickListener(this);

        Button linkMaps = (Button) sideNavDialog.findViewById(R.id.linkMaps);
        linkMaps.setOnClickListener(this);

        Button linkTaskList = (Button) sideNavDialog.findViewById(R.id.linkTaskList);
        linkTaskList.setOnClickListener(this);

        Button linkContacts = (Button) sideNavDialog.findViewById(R.id.linkContacts);
        linkContacts.setOnClickListener(this);

        Button linkMedInfo = (Button) sideNavDialog.findViewById(R.id.linkMedInfo);
        linkMedInfo.setOnClickListener(this);

        Button linkPrescription = (Button) sideNavDialog.findViewById(R.id.linkPrescription);
        linkPrescription.setOnClickListener(this);

        Button btnClose = (Button) sideNavDialog.findViewById(R.id.closeBtn);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideNavDialog.cancel();
            }
        });

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

//    ***********************************

    private void launchSOSPhone() {
        // create the Intent with the action to dial
        Intent intent = new Intent(Intent.ACTION_DIAL);
        // set the data to the phone number
        intent.setData(Uri.parse("tel:+4401224272600"));

        // check the intent can be resolved by the device
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        // Side nav
        if (v.getId() == R.id.linkHome) {
            Intent intent = new Intent(
                    getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkCalendar) {
            Intent intent = new Intent(
                    getApplicationContext(), CalendarActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkMaps) {
            Intent intent = new Intent(
                    getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkTaskList) {
            Intent intent = new Intent(
                    getApplicationContext(), TaskCheckListActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkContacts) {
            Intent intent = new Intent(
                    getApplicationContext(), ContactActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkMedInfo) {
            Intent intent = new Intent(
                    getApplicationContext(), MedicalInfoActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkPrescription) {
            Intent intent = new Intent(
                    getApplicationContext(), PrescriptionLevelActivity.class);
            startActivity(intent);
        }
    }
}
