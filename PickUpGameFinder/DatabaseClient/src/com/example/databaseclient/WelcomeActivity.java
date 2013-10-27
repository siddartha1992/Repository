package com.example.databaseclient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Siddartha Tondapu
 * Purpose: Displays the welcome screem with instructions and other actions to perform. 
 *
 */
public class WelcomeActivity extends Activity {

	//Defining GUI elements
	private  TextView output;
	private Button addUser, lookUp, logout,capture;
	private ImageView captureImage;
	private WelcomeActivity wAct;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		this.output = (TextView) findViewById(R.id.textView_Welcome);
		this.addUser = (Button) findViewById(R.id.button_AddUser);
		this.lookUp = (Button) findViewById(R.id.button_LookUpData);
		this.logout = (Button) findViewById(R.id.button_Logout);
		this.capture = (Button) findViewById(R.id.button_Capture);
		this.captureImage = (ImageView) findViewById(R.id.image_Capture);
		this.wAct = this;
		
		this.output.setText(MainActivity.outputStream);
//		this.ipAddress  = intent.getStringExtra("ipAddress");
		
		captureImage.setImageBitmap(MainActivity.image);
		
		this.capture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				//Get an image from the camera
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 0);
			}
		});
		
		//return to the mainactivity screen
		this.logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {			
				Intent intentToMain = new Intent(wAct, MainActivity.class);
				wAct.startActivity(intentToMain);
			}
		});
		
		//Go to addactivity screen
		this.addUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {			
				Intent intentToAddData = new Intent(wAct, AddActivity.class);
				intentToAddData.putExtra("ipAddress", MainActivity.hostNumberAndIP);
				wAct.startActivity(intentToAddData);
			}
		});
		
		//go to lookup screen
		this.lookUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {			
				Intent intentToAddData = new Intent(wAct, LoadData.class);
				intentToAddData.putExtra("ipAddress", MainActivity.hostNumberAndIP);
				wAct.startActivity(intentToAddData);
			}
		});
	}
	
	//Updates the image to the imageview.
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		try{
			if(requestCode == 0){
				MainActivity.image = (Bitmap) data.getExtras().get("data");
				captureImage.setImageBitmap(MainActivity.image);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

}
