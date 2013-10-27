package com.example.databaseclient;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Siddartha Tondapu
 * Purpose: Responsible for getting the username and IP + port number which are used for logging in
 * to the webserver
 *
 */
public class MainActivity extends Activity {
	
	//Defining GUI Elements
	private Button login;
	public EditText username, address;
	private ConnectServerAsync connect;
	private MainActivity act;
	public static String hostNumberAndIP, outputStream;
	public static Bitmap image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.image = null;
		
		this.login = (Button) findViewById(R.id.button_Login);
		
		this.username = (EditText) findViewById(R.id.editText_Username);
		this.address = (EditText) findViewById(R.id.editText_Address);
		
		this.act = this;
		
		this.login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				connect = new ConnectServerAsync(act);
				//invalid arguments provided
				if(username.getText().toString().trim().matches("") 
						|| address.getText().toString().trim().matches("")){
					Toast.makeText(act, "Missing Information. Please Check Again!!!", Toast.LENGTH_SHORT).show();
					return;
				}else{
				//code to login in to the server
					hostNumberAndIP = address.getText().toString().trim();
					connect.execute(username.getText().toString().trim(),
						 address.getText().toString().trim());
				}
				
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
