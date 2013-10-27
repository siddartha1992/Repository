package com.example.databaseclient;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * 
 * @author Siddartha Tondapu
 * Purpose: Loads the data from the webserver, parses, and displays them as a listview.
 *
 */
public class LoadData extends Activity {

	//Defining Global Variables
	private Button goBack, lookUp, loadMap;
	private LoadData lAct;
	private LoadDataAsync loadData;
	static public ArrayList<Data> dataArray;
	private ListView lView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_data);
		
		//Declaring all the global variables
		this.goBack = (Button) findViewById(R.id.button_back);
		this.lookUp = (Button) findViewById(R.id.button_Lookup);
		this.loadMap =(Button)findViewById(R.id.button_LoadMap);
		this.lAct = this;
		this.dataArray = new ArrayList<Data>();
		this.lView = (ListView) findViewById(R.id.listView);
		this.loadMap.setEnabled(false);
		
		//Call the Activity when DisplayMapActivity is called
		this.loadMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				Intent intentToMap = new Intent(lAct, DisplayMapActivity.class);
				lAct.startActivity(intentToMap); 
				
			}
		});
		
		//Call the Async task which retrieves data from the webserver and updates it onto the listview. 
		this.lookUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				loadData = new LoadDataAsync(lAct);
				loadData.execute(MainActivity.hostNumberAndIP);				
			}
		});
		
		//Go back to the previous page
		this.goBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				Intent intentToWelcome = new Intent(lAct, WelcomeActivity.class);
				lAct.startActivity(intentToWelcome);
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.load_data, menu);
		return true;
	}
	

	//when the asynch task finished getting data, the following function is invoked which updates the GUI
	public void serverUpdate()
	{
		
		HashMap<String, String> map;
		ArrayList<HashMap<String,String>> mapArray = new ArrayList<HashMap<String,String>>();
		for(int i = 0; i < dataArray.size();i++){
			map = new HashMap<String, String>();
			map.put("name", "Name: "+ dataArray.get(i).getName());
			map.put("age", "Age: " + dataArray.get(i).getAge());
			map.put("phone", "Phone Number: " + dataArray.get(i).getPhone());
			map.put("address", "Address: " + dataArray.get(i).getAddress());
			map.put("gender", "Gender: " + dataArray.get(i).getGender());
			map.put("activity", "Activity: "  + dataArray.get(i).getActivity());
			mapArray.add(map);
		}
		//Add all the elements to the listview
		final ListAdapter adp = new SimpleAdapter(this, mapArray, R.layout.activity_display_activites,
				new String[] { "name", "age", "phone", "address", "gender", "activity"}, new int[] 
						{R.id.textView_Name, R.id.textView_Age, R.id.textView_Phone, R.id.textView_Address,
						R.id.textView_Gender, R.id.textView_Activity});
		lView.setAdapter(adp);
		this.loadMap.setEnabled(true);
				
	}
	
}
