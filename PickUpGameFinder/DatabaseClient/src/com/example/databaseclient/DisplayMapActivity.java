package com.example.databaseclient;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author Siddartha Tondapu
 * Purpose: Displays all Map with all the address. 
 */
public class DisplayMapActivity extends Activity {

	//Defining the global variables
	private Button back;
	private DisplayMapActivity mAct;
	private GoogleMap map; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_map);
		
		//Declaring the global variables
		this.back = (Button) findViewById(R.id.buttonBack_Load);
		this.mAct = this;
		//Retreving the map object and attaching it to GoogleMap object
		this.map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		this.map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		
		Geocoder coder = new Geocoder(this);
		List<Address> address;
		//loop through the arraylist and add them on the map as a marker
		for(int i = 0; i < LoadData.dataArray.size(); i++){
			try {
				List<Address> result = new Geocoder(this).getFromLocationName(LoadData.dataArray.get(i).getAddress(), 5);
				LatLng temp = new LatLng (result.get(0).getLatitude(), result.get(0).getLongitude());
				String text = LoadData.dataArray.get(i).getName() + " playing " + LoadData.dataArray.get(i).getActivity();
				String text1 = "Phone: " + LoadData.dataArray.get(i).getPhone();
 				map.addMarker(new MarkerOptions().position(temp).title(text).snippet(text1));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//Go back to the previous page when button is clicked
		this.back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				Intent intentToMap = new Intent(mAct, LoadData.class);
				mAct.startActivity(intentToMap); 
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_map, menu);
		return true;
	}


}
