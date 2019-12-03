package uk.ac.rgu.elderaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.api.services.calendar.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnAddEvent;
    private ImageButton btnshowSideNav;
    private LinearLayout calendar_llEvent1;
    private ImageButton btnSOS;
    private ImageButton btnHome;
    private ImageButton btnSyncEvents;
    private static final String TAG = "HELLO";
    private static final String preferencesFile = "uk.ac.rgu.elderaid";
    private SharedPreferences sharedPrefs;


    // URL template to download data
    private static final String URL_TEMPLATE ="https://www.googleapis.com/calendar/v3/calendars/";

    private EventDao eventDao;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        sharedPrefs = getSharedPreferences(preferencesFile, MODE_PRIVATE);

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
                String prefCalKey = getString(R.string.prefCalIDKey);
                String defaultCal = getString(R.string.CalIdDefault);
                String calID = sharedPrefs.getString(prefCalKey, defaultCal);
                if (!calID.equals(defaultCal)){
                    downloadEvents(calID);
                    new GetAllEventsTask().execute();
                }
                else{
                    Toast.makeText(getApplicationContext(),"You have not setup the Calendar ID",Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Get the database instance
        ElderaidDatabase db = ElderaidDatabase.getDatabase(this);
        //Get the DAO from the database
        this.eventDao = db.eDao();




        // The code below was adapted from a source on the internet from this point.

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My calendar");
        setSupportActionBar(toolbar);
        // To this point.
    }

    public void downloadEvents(String CalID){

        String calendarID = CalID;
        String url = URL_TEMPLATE + calendarID +"/events?key=AIzaSyC-Gs8DUH7IH7XhShlgzMVO_VIORgl68Qs";
        Log.d("URL", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseObj = new JSONObject(response);
                            JSONArray itemsArray = responseObj.getJSONArray("items");
                            List<Event> downloadedEventsList = new ArrayList<>();
                            for(int i =0; i < itemsArray.length();i++) {
                                JSONObject obj = itemsArray.getJSONObject(i);


                                String description;
                                String location;

                                if (!obj.has("description")){
                                    description = "No description provided";
                                }
                                else{
                                     description= obj.getString("description");
                                }

                                if (!obj.has("location")){
                                    location = "No location provided";
                                }
                                else{
                                    location = obj.getString("location");
                                }

                                String summary = obj.getString("summary");
                                String creation = obj.getString("created");

                                JSONObject start = obj.getJSONObject("start");
                                String startDate = start.getString("date");

                                JSONObject end = obj.getJSONObject("end");
                                String endDate = end.getString("date");


                                Event e = new Event(summary,description,startDate,endDate,location, creation);
                                downloadedEventsList.add(e);

                            }
                            new UpdateOrInsertEvent().execute(downloadedEventsList);


                        } catch (Exception e) {
                            Log.d(TAG, e.toString());
                            Toast.makeText(getApplicationContext(),"Please check your Calendar ID, and make sure the calendar is public",Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "VolleyError: " + error);
                        Toast.makeText(getApplicationContext(),"Please check your Calendar ID, and make sure the calendar is public",Toast.LENGTH_SHORT).show();

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

        Button linkPreferences = (Button) sideNavDialog.findViewById(R.id.linkPreferences);
        linkPreferences.setOnClickListener(this);

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
        } else if (v.getId() == R.id.linkPreferences) {
            Intent intent = new Intent(
                    getApplicationContext(), PreferencesActivity.class);
            startActivity(intent);
        }
    }



    //TODO Heavily adapted from Lab solution 3
    class UpdateOrInsertEvent extends AsyncTask<List<Event>, Void, Void> {


        @Override
        protected Void doInBackground(List<Event>... eventList) {
            if (eventList.length == 0){
                return null;
            }

            List<Event> events = eventList[0];


            //Get a list of all the events already on the Database
            List<Event> savedEvents = eventDao.getEvents();


            for (Event e : savedEvents){
                String eCreation = e.getCreation();

                //Iterates over the newEvents with an event already in the db as e
                for (int i=0; i < events.size(); i++){
                    Event eImport = events.get(i);
                    //If the creation dates of the events match
                    if(eImport.getCreation().equals(eCreation)){
                        //Then set all of the other attributes to the new ones
                        e.setTitle(eImport.getTitle());
                        e.setDesc(eImport.getDesc());
                        e.setLocation(eImport.getLocation());
                        e.setStartDate(eImport.getStartDate());
                        e.setEndDate(eImport.getEndDate());
                        //Remove the old event from the new imported events to prevent duplication.
                        events.remove(i);
                        //Since the size is now smaller by one,
                        //we need to reset the counter back by one.
                        i--;
                        break;
                    }
                }
            }


                eventDao.updateEvents((Event[])savedEvents.toArray(new Event[savedEvents.size()]));

            if (events.size() != 0) {
                eventDao.insertEvents((Event[]) events.toArray(new Event[events.size()]));
            }
            return null;
        }


    }

    private String unpackageEvents(List<Event> events) {
        StringBuilder eventString = new StringBuilder();
        for (Event event : events){
            eventString.append(event.getEventID())
                    .append("\n")
                    .append(event.getTitle())
                    .append(event.getDesc())
                    .append(event.getLocation())
                    .append(event.getStartDate())
                    .append(event.getEndDate())
                    .append("\n")
                    .append("\n");
        }
        return eventString.toString();
    }

    class GetAllEventsTask extends AsyncTask<Void, Void, List<Event>>{
        @Override
        protected List<Event> doInBackground(Void... voids) {
            return eventDao.getEvents();
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            super.onPostExecute(events);
            String eventString = unpackageEvents(events);
            Log.d("EVENTSOUTPUT", eventString);
        }
    }

    class EmptyEventsTableTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            eventDao.deleteAllEvents();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new GetAllEventsTask().execute();
        }
    }

}
