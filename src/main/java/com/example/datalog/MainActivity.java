package com.example.datalog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "trial.txt";
    private Button info;

    //timestamp
    public static String getCurrentTimeStamp() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd , HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date
            return currentDateTime;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<String> al = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getEndpointList() ;
        //info = (Button) findViewById(R.id.button);
        final ListView lv = findViewById(R.id.list);


        ArrayAdapter arrayadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,al);
        lv.setAdapter(arrayadapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                logaid(i);
            }
        });



        getEndpointList();
    }


    //parsing json
    private void getEndpointList() {
        try {
            ArrayList<HashMap<String, String>> device = new ArrayList<>();
            ListView lv = findViewById(R.id.list);
            ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
            lv.setAdapter(aa);
            //getting json from assets
            String jsonLoc = readJSONFromAsset();
            JSONObject jsonObj = new JSONObject(jsonLoc);
            JSONObject endpoints = jsonObj.getJSONObject("endpoints");
            Iterator<?> keys = endpoints.keys();
            al.clear();
            while (keys.hasNext()) {
                String keys1 = (String) keys.next();

                // if jsonobject keys has another object in endpoint make another object
                if (endpoints.get(keys1) instanceof JSONObject) {
                    // then create another object with its own keys
                    JSONObject jsonobj2 = endpoints.getJSONObject(keys1);

                    String id = jsonobj2.getString("id");
                    String name = jsonobj2.getString("name");
                    int floor = jsonobj2.getInt("floor");
                    String roomname = jsonobj2.getString("roomname");
                    String manufacturer = jsonobj2.getString("manufacturer");
                    String model = jsonobj2.getString("model");
                    String type = jsonobj2.getString("type");
                    String owner = jsonobj2.getString("owner");
                    int permission = jsonobj2.getInt("permissions");
                    String master = jsonobj2.getString("master");


                    Log.i("message" , name);
                    al.add(id);
                    int position = aa.getPosition(id);
                    aa.notifyDataSetChanged();

                } }


        }
        catch (JSONException e) {
            e.printStackTrace();
            Log.e("JsonParser" , "Exception" , e);
        }


    }
    //reading json
    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("jsonUI.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void logaid(int pos) {

        try {

            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("trial.txt", MODE_APPEND));
            out.write( al.get(pos)+ "," + getCurrentTimeStamp() +  "\n");
            out.close();
            //popup that shows file path
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
            readText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readText() {
        try {
            File file = new File(getFilesDir() + "/trial.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            fr.close();
            Log.d("Read Text", sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}











