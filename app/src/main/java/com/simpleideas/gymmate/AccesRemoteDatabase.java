package com.simpleideas.gymmate;

import android.os.AsyncTask;
import android.net.Uri;
import android.renderscript.ScriptGroup;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;

/**
 * Created by programmerByAccident on 9/17/2016.
 */

public class AccesRemoteDatabase extends AsyncTask<String, Void, String> {

    private HttpURLConnection httpURLConnection;
    private BufferedReader bufferedReader;
    private StringBuffer buffer;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    @Override
    protected String doInBackground(String[] parameters) {


        String result_returned = "";

        try {

            System.out.print("here");

            URL url = new URL(parameters[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            
            int code = httpURLConnection.getResponseCode();

            Log.d("error code", Integer.toString(code));
            InputStream stream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(stream));

            buffer = new StringBuffer();
            Log.d("RemoteDatabase","doinbackground before while");
            String line;
            while((line  = bufferedReader.readLine()) != null){
            buffer.append(line);
            }

//            jsonArray = new JSONArray(buffer.toString());
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//            }

            //jsonObject = new JSONObject(buffer.toString());
            //JSONArray parentArray  = jsonObject.getJSONArray("one");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            try
            {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Log.d("RemoteDatabase","doinbackground after while");
        Log.d("BUFFER VALUE", buffer.toString());
        return buffer.toString();

    }


    @Override
    protected void onPostExecute(String  result_from_doInBackground) {
        super.onPostExecute(result_from_doInBackground);
        Log.d("RemoteDatabase","doInBackgroundb is called");
        Log.d("RemoteDatabase",result_from_doInBackground + "output");

    }


}
