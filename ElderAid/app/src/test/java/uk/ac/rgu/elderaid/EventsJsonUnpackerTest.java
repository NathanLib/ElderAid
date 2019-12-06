package uk.ac.rgu.elderaid;

import android.util.JsonToken;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.ObjectCodec;
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

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)

public class EventsJsonUnpackerTest {

    private JSONArray itemsArray;
    private List<Event> downloadedEventsList;
    private JSONObject responseObj;
    private Object obj;
    private JsonParser parser;
    @Before
    public void setUp(){







    }

    @Test
    public void jsonUnpacksCorrectly(){
        parser = new JsonParser();
        try {
            obj = parser.parse(new FileReader("/Users/Jack/AndroidStudioProjects/coursework-submission-elderaid/ElderAid/app/src/test/java/uk/ac/rgu/elderaid/jsonFile.txt"));
            String resp = obj.toString();
            responseObj = new JSONObject(resp);
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
