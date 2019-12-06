package uk.ac.rgu.elderaid;

import android.util.Log;
import android.widget.Toast;

import com.google.api.client.json.Json;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)

public class EventsJsonUnpackerTest {
    private String jsonFile = "{" +
            "\"kind\": \"calendar#events\"," +
            "\"etag\": \"p33gfnruilufuc0g\"," +
            "\"summary\": \"Other Elder Cal\"," +
            "\"description\": \"TestingCalendar\"," +
            "\"updated\": \"2019-12-05T20:43:13.132Z\"," +
            "\"timeZone\": \"Europe/London\"," +
            "\"accessRole\": \"reader\"," +
            "\"defaultReminders\": []," +
            "\"nextSyncToken\": \"COD779Kvn-YCEAAYAQ==\"," +
            "\"items\": [" +
            "{" +
            "\"kind\": \"calendar#event\"," +
            "\"etag\": \"3151157186264000\"," +
            "\"id\": \"4q4quqcfsri2sgiv96bml3ilm8\"," +
            "\"status\": \"confirmed\"," +
            "\"htmlLink\": \"https://www.google.com/calendar/event?eid=NHE0cXVxY2Zzcmkyc2dpdjk2Ym1sM2lsbTggcDNjbzJqMnRsa2dnZWtoM2ZsaWd2amEwOW9AZw\"," +
            "\"created\": \"2019-12-05T20:43:13.000Z\"," +
            "\"updated\": \"2019-12-05T20:43:13.132Z\"," +
            "\"summary\": \"Mail order arrives\"," +
            "\"description\": \"3 Parcels\"," +
            "\"location\": \"Post Office\"," +
            "\"creator\": {" +
            "\"email\": \"clorixian@gmail.com\"" +
            "}," +
            "\"organizer\": {" +
            "\"email\": \"p3co2j2tlkggekh3fligvja09o@group.calendar.google.com\"," +
            "\"displayName\": \"Other Elder Cal\"," +
            "\"self\": true" +
            "}," +
            "\"start\": {" +
            "\"date\": \"2019-12-09\"" +
            "}," +
            "\"end\": {" +
            "\"date\": \"2019-12-10\"" +
            "}," +
            "\"transparency\": \"transparent\"," +
            "\"iCalUID\": \"4q4quqcfsri2sgiv96bml3ilm8@google.com\"," +
            "\"sequence\": 0" +
            "}" +
            "]" +
            "}";
    private JSONObject responseObj;
    private JSONArray itemsArray;
    private List<Event> downloadedEventsList;

    @Before
    public void setUp(){
        try {
            responseObj = new JSONObject(jsonFile);
            itemsArray = responseObj.getJSONArray("items");
            downloadedEventsList = new ArrayList<>();
        } catch (Exception e) {
        }
        try{
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
        }
        catch(Exception e){

        }





    }

    @Test
    public void jsonUnpacksCorrectly(){
        Event f = downloadedEventsList.get(0);
        assertThat(f.getTitle(), is(equalTo("Mail order arrives")));
        assertThat(f.getDesc(), is(equalTo("3 Parcels")));
        assertThat(f.getLocation(), is(equalTo("Post Office")));
        assertThat(f.getStartDate(), is(equalTo("2019-12-09")));
        assertThat(f.getEndDate(), is(equalTo("2019-12-10")));

    }

    @After
    public void tearDown(){
        //Do nothing
    }


}
