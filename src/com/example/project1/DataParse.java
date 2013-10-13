/**
 * @author Siddartha Tondapu
 * Responsbile for parsing the raw HTML data and displaying the result onto the updaed gui.
 */
package com.example.project1;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;


public class DataParse extends AsyncTask<String, Void, Data>{
	private MainActivity act;
	
	/**
	 * Constructor which assigns the value of act to mainactivity. 
	 * @param activityToCallBack
	 */
	public DataParse(MainActivity activityToCallBack){
		this.act = activityToCallBack;
	}
	
	
	/**
	 * Function responsible for parsing data and is also executed on a separate thread. 
	 */
	protected Data doInBackground(String... params) {
		String dataLine = params[0];
		
		String[] resultList = dataLine.split("<");
		String outputResult="";
		Bitmap image = null;
		for(int i =0; i < resultList.length; i++){
			if(resultList[i].endsWith("/>")){
				String key = resultList[i].substring(0, (resultList[i].indexOf("=")-4));
				String value = resultList[i].substring((resultList[i].indexOf("=")+2), (resultList[i].indexOf("/>")-1));
				
				//parses the data and adds them to a new string (String concatenation)
				if(key.trim().equals("symbol")){
					outputResult = outputResult + "Symbol: " + value + "\n";
				}
				
				if(key.trim().equals("company")){
					outputResult = outputResult + "Company: "+ value + "\n";
				}
				
				if(key.trim().equals("exchange")){
					outputResult = outputResult + "Exchange: " + value + "\n";
				}
				
				if(key.trim().equals("last")){
					outputResult = outputResult + "Last Reading: " + value + "\n";
				}
				
				if(key.trim().equals("high")){
					outputResult = outputResult + "Highest Reading: " + value + "\n";
				}
				
				if(key.trim().equals("low")){
					outputResult = outputResult + "Lowest Reading: " + value + "\n";
				}
				
				if(key.trim().equals("volume")){
					outputResult = outputResult + "Volume: " + value + "\n";
				}
				
				if(key.trim().equals("avg_volume")){
					outputResult = outputResult + "Average Volume: " + value + "\n";
				}
				
				if(key.trim().equals("change")){
					outputResult = outputResult + "Change: " + value + "\n";
				}
				
				if(key.trim().equals("perc_change")){
					outputResult = outputResult + "Percent Change: " + value + "\n";
				}
				
				//Gets the content of a picture from a different server.
				if(key.trim().equals("chart_url")){
					try{
						//Determines the URL for the picture 
						String googleChart = "http://www.google.com"+(value.trim());
						
						//Stores the image in a Bitmap variable image
						InputStream in = new URL(googleChart).openStream();
						image = BitmapFactory.decodeStream(in);
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		//Creates a new object of variable Data which accepts a string and a bitmap 
		Data newData = new Data(outputResult, image);
		
		//returns the data which is received on the onpostexecute function call
		return newData;
	}
	
	/**
	 * Responsbile for updating the GUI objects in the main activity. 
	 */
	protected void onPostExecute( Data result )
    {	
		try{
			//Displays the image on the image view. 
			String[] outputValues = result.getWebData().split("\n");
			Bitmap image = result.getImage();
			
			//Displays the parsed data on a listView. 
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(act, 
					android.R.layout.simple_list_item_1,android.R.id.text1, outputValues);
			act.lv.setAdapter(adapter);
			
			act.chart.setImageBitmap(image);
			String companyFullName = outputValues[1].substring((outputValues[1].indexOf(":")+2), outputValues[1].length());
			act.dataReceivedFromNetwork("Search Results for " + companyFullName);
		} catch ( Exception e )
		{
			e.printStackTrace();
		}
    }
	
}