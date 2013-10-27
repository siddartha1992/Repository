package com.example.databaseclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * 
 * @author Siddartha Tondapu
 * Purpose: AsyncTask which gets the data from the webserver and stores it in an arrayList of Data objects. 
 *
 */
public class LoadDataAsync extends AsyncTask<String, Void, String> {

	//Global variables
	private LoadData lAct;
	private StringBuilder sb;
	
	
	public LoadDataAsync(LoadData activityToCallBack){
		this.lAct = activityToCallBack;
		this.sb = new StringBuilder();
	}

	@Override
	protected String doInBackground(String... params) {
		try{
			 //create an object HttpClinet 
			 HttpClient client = new DefaultHttpClient();

			 //Create the url which adds the company name during run time. 
			 String url = "http://" + params[0] + "/data"; 
			 
			 //Creates an object of HTTPGet by passing in the url above. 
			 HttpGet request = new HttpGet(url);
			 
			 //Calls the execute function and data is stored in the response. 
			 HttpResponse response = client.execute(request);
			 
			 //loop through the response and convert that to a string using stringbuilder
			 BufferedReader responseLine = new BufferedReader(new InputStreamReader(response
                            .getEntity().getContent()));
			 String line = "";
			while (((line = responseLine.readLine()) != null)) { //loop till the last line of the response
				sb.append(line + "\r\n");
			}
		 }catch(Exception e){
			 sb.append("Error detected: "+ e);
		 }
		 String httpresponseval = sb.toString(); //convert the string builder value to a string 
		 
		 //returns the raw HTML data as a string
		 return httpresponseval; 
	}
	
	//Parses the elemnts using JSON and stores the items in an arraylist of Data objects
	protected void onPostExecute( String result )
    {
		String data = "{\"Activities\": " + result + "}";
		try
		{
			JSONObject obj  = new JSONObject(data);
			
			JSONArray dataArray = obj.getJSONArray("Activities");
			for(int i = 0; i < dataArray.length(); i++){
				JSONObject tempData = dataArray.getJSONObject(i);
				String name = tempData.getString("name");
				String age = tempData.getString("age");
				String phone = tempData.getString("phone");
				String address = tempData.getString("address");
				String gender = tempData.getString("gender");
				String activity = tempData.getString("activity");
				Data d = new Data(name, age, phone, address, gender, activity);
				lAct.dataArray.add(d);
			}
			
			
			
		} catch ( JSONException e1 )																			
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		lAct.serverUpdate();
    }
}
