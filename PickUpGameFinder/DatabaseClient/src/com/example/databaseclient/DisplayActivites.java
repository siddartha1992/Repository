package com.example.databaseclient;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
/**
 * 
 * @author Siddartha Tondapu
 * Purpose: Dummy class used in making linear layout. 
 *
 */
public class DisplayActivites extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_activites);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_activites, menu);
		return true;
	}

}
