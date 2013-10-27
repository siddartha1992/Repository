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


import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * 
 * @author Siddartha Tondapu
 * Purpose: AsynchTask which posts data to the webserver and returns following data. 
 * If invalid input is provided, the program would not enter the next activity. 
 *
 */
public class ConnectServerAsync extends AsyncTask<String, Void, String> {
	//Declaring global variables
	private String line;
	private MainActivity act; 
	private StringBuilder sb;
	private String ipAddress;

	//Constructor 
	public ConnectServerAsync(MainActivity activityToCallBack) {
		this.act = activityToCallBack;
		this.sb = new StringBuilder();
	}

	protected String doInBackground(String... params) {
		try{
			 //create an object HttpClinet 
			 HttpClient client = new DefaultHttpClient();

			 //Create the url which adds the company name during run time. 
			 String url = "http://" + params[1]; 
			 
			 this.ipAddress = params[1];
			 
			 //Creates an object of HTTPGet by passing in the url above. 
			 HttpPost request = new HttpPost(url);
			 
			 //Set the corresponding key values that needs to be posted on the webserver. 
			 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			 nameValuePairs.add(new BasicNameValuePair("username",params[0]));
			 

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
	 * Calls this function after getting the Posting task
	 */
	protected void onPostExecute( final String result )
    {
		//If correct input returned, go to the next WelcomeAcitivty or else
		//display a toast with the error message.
		if(result.startsWith("Welcome")){
			Intent intent = new Intent(act, WelcomeActivity.class);
			intent.putExtra("output", result);
			intent.putExtra("ipAddress", ipAddress);
			MainActivity.outputStream = result;
			act.startActivity(intent);
		}else{
			act.runOnUiThread( new Runnable()
			{
				@Override
				public void run()
				{
					//Error detected when posting to the server. Could be caused by invalid ip and port. 
					Toast.makeText( act,
							"Error Detected: " + result,
							Toast.LENGTH_SHORT ).show();
					
				}
			} );
		}
    }
}
