package com.example.databaseclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * 
 * @author Siddartha Tondapu
 * Purpose: Async task to post data to the webserver
 *
 */
public class PostDataAsynch extends AsyncTask<String, Void, String> {
	
	private AddActivity addAct;
	private StringBuilder sb;
	private String ipAddress ="";
	private String name = "";
	private String age = "";
	private String phone = "";
	private String address = "";
	private String gender = "";
	private String activity = "";
	private String line ="";
	
	public PostDataAsynch(AddActivity activityToCallBack){
		this.addAct = activityToCallBack;
		this.sb = new StringBuilder();
	}
	
	protected String doInBackground(String... params) {
		try{
			 ipAddress = params[0];
			 name = params[1];
			 age = params[2];
			 phone = params[3];
			 address = params[4];
			 gender = params[5];
			 activity = params[6];
			
			 HttpClient client = new DefaultHttpClient();
	
			 //Create the url which adds the company name during run time. 
			 String url = "http://" + ipAddress + "/data"; 
			 
			 //Creates an object of HTTPGet by passing in the url above. 
			 HttpPost request = new HttpPost(url);
			 
			 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			 nameValuePairs.add(new BasicNameValuePair("name",name));
			 nameValuePairs.add(new BasicNameValuePair("age",age));
			 nameValuePairs.add(new BasicNameValuePair("phone",phone));
			 nameValuePairs.add(new BasicNameValuePair("address",address));
			 nameValuePairs.add(new BasicNameValuePair("gender",gender));
			 nameValuePairs.add(new BasicNameValuePair("activity",activity));
			 
	
			 UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
			 request.setEntity(entity);
			 
			 //Calls the execute function and data is stored in the response. 
			 HttpResponse response = client.execute(request);
			 
			 //loop through the response and convert that to a string using stringbuilder
			 BufferedReader responseLine = new BufferedReader(new InputStreamReader(response
	                         .getEntity().getContent()));
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
	
	/**
	 * Calls this function after getting the HTTP request
	 */
	protected void onPostExecute( String result )
    {	
		//Display a message indicating that data has been posted. 
		Toast.makeText(addAct,
    			result, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(addAct, WelcomeActivity.class);
		addAct.startActivity(intent);
    }

}
