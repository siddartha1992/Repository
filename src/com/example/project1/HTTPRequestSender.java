/**
 * @author Siddartha Tondapu
 * Purpose: Creates an asynchtask class which requests the HTTP data from the server. 
 * Internally calls DataParse class during on post execute so that data is parsed on 
 * a different thread. 
 */
package com.example.project1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


public class HTTPRequestSender extends AsyncTask<String, Void, String>{
	//define all the global variables
	private String line;
	private StringBuilder sb;
	Data newData;
	private MainActivity act; //variable of type act

	
	/**
	 * Constructor which initializes the MainActivity variable 
	 * @param activityToCallBack
	 */
	public HTTPRequestSender(MainActivity activityToCallBack){
		this.sb = new StringBuilder();
		this.act = activityToCallBack;
	}
	 
	/**
	 * Function responsible for executing the first HTTPGET. 
	 * @param companyName: The name of the comany whose stock we are looking for. 
	 * @return string of the raw HTML data returned.
	 */
	 public String getResource(String companyName){
		 try {
			 //create an object HttpClinet 
			 HttpClient client = new DefaultHttpClient();

			 //Create the url which adds the company name during run time. 
			 String url = "http://www.google.com/ig/api?stock=" + companyName; 
			 
			 //Creates an object of HTTPGet by passing in the url above. 
			 HttpGet request = new HttpGet(url);
			 
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
	 * Function called in a separate thread. Calls the getResource function which 
	 * 	is responsible for getting the raw html data. 
	 */
	protected String doInBackground(String... params) {
	    try
        {
	    	// call the function  getResoure and pass in the company name argument.
                return getResource(params[0]); 
        } catch ( Exception e )
        {
                e.printStackTrace();
                return null;
        }
	} 
	
	/**
	 * Calls this function after getting the HTTP request
	 */
	protected void onPostExecute( String result )
    {
		//create a new object of DataParse which parses the raw HTML string 
		DataParse dp = new DataParse(act); 
		
		//call the execute function
		dp.execute(result); 
    }
}
	 

	

