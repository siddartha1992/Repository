package com.example.databaseclient;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 
 * @author Siddartha Tondapu
 * Purpose: Adds a new Activity to the Web server. Calls PostDataAsync 
 * for posting the data. 
 *
 */
public class AddActivity extends Activity {

	//Declaring global variables
	private Button submit, goBack;
	private EditText name, age, phone, address;
	private AddActivity addAct;
	private Spinner activitySpinner;
	private RadioGroup radioGenderGroup;
	private RadioButton radioGenderButton;
	private PostDataAsynch postData;
//	private String ipAddress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		//Initialize the global variables to the corresponding GUI elements
		this.submit = (Button) findViewById(R.id.button_Submit);
		this.goBack = (Button) findViewById(R.id.button_Back);
		
		this.name = (EditText) findViewById(R.id.editText_Name);
		this.age = (EditText) findViewById(R.id.editText_Age);
		this.phone = (EditText) findViewById(R.id.editText_Phone);
		this.address = (EditText) findViewById(R.id.editText_Address);
		this.addAct = this;
		this.activitySpinner = (Spinner) findViewById(R.id.spinner_Activity);
		this.radioGenderGroup = (RadioGroup) findViewById(R.id.radioGender);
		
		//Creating button handler events
		this.goBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Create an intent to go back to the previous page (Welcome Activity)
				Intent intentToWelcome = new Intent(addAct, WelcomeActivity.class);
				addAct.startActivity(intentToWelcome);
			}
		});
		
		this.submit.setOnClickListener(new OnClickListener() {
			//Posts the data to the webserver by calling the corresponding postdataasync class
			public void onClick(View v) {
				postData = new PostDataAsynch(addAct); 
				//if information is missing, display an error message
				if(name.getText().toString().trim().matches("") || age.getText().toString().trim().matches("") || 
						phone.getText().toString().trim().matches("") || 
						address.getText().toString().trim().matches("")){
					Toast.makeText(addAct, "Missing Information. Please Check Again!!!", Toast.LENGTH_SHORT).show();
				}else{
					//Getting the radio button information
					int selectedId = radioGenderGroup.getCheckedRadioButtonId();
					radioGenderButton = (RadioButton) findViewById(selectedId);
					 

					//Getting information from spinner					
					String selectedActivity = activitySpinner.getSelectedItem().toString();
			         if ( !selectedActivity.equals( "Select an Activity" ) )
                     {
			        	//When all the information has been passed, call the asynctask execute with 
			        	// the following parameters
			 			Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                     	postData.execute(MainActivity.hostNumberAndIP, name.getText().toString().trim(),
                     			age.getText().toString().trim(), 
                     			phone.getText().toString().trim(), address.getText().toString().trim(), 
                     			radioGenderButton.getText().toString().trim(), selectedActivity.trim());
                     	vib.vibrate(300);
                     	//vibrates when the execution has completed
                     }else{
                     	return;
                     }
					
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

}
