/**
 * @author Siddartha Tondapu
 * @Description: Android application for getting financial stock quotes. 
 */
package com.example.project1;


import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends Activity {
	//GUI elements defined
	private Button submitRequest;
	public TextView output;
	private EditText companyName;
	private Button exit;
	private String company;
    ImageView chart;
	MainActivity helper;
	ListView lv;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//declare the GUI objects
		this.chart = (ImageView) findViewById(R.id.imageView_Chart);
		this.submitRequest = (Button) findViewById(R.id.button_Submit);
		this.output = (TextView) findViewById(R.id.textView_Output);
		this.output.setMovementMethod(ScrollingMovementMethod.getInstance());
		this.output.setTextSize(20.0f);
		this.output.setTextColor(Color.RED);
		this.companyName = (EditText) findViewById(R.id.editText_Company);
		this.exit = (Button) findViewById(R.id.button_Exit);
		this.lv = (ListView) findViewById(R.id.listView);
		this.helper = this;
		this.output.setText("Please Enter Compnay Name and press Submit.");
		this.companyName.setText("");
		
		//when the exit button is clicked, clear the screen. 
		this.exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				output.setText("Exiting the application");
				lv.setAdapter(null);
				companyName.setText("");
				chart.setImageBitmap(null);
			}
		});
		
		//when the button is clicked
		OnClickListener listener = new OnClickListener() {
			public void onClick(View v) {
				company = companyName.getText().toString();
				if(company.trim().equals("")){
					output.setText("Please Enter a valid Name");
				}else{
					//create an instance of HTTPRequestSender Object and Pass in helper as a default argument. 
					final HTTPRequestSender req = new HTTPRequestSender(helper);
					
					exit.setOnClickListener(new OnClickListener() {
						//if exit is clicked, stop the process and clear the screen
						public void onClick(View v) {
							req.cancel(true);
							output.setText("Exiting the application");
							lv.setAdapter(null);
							companyName.setText("");
							chart.setImageBitmap(null);
							
						}
					});
					req.execute(company.trim()); //starting fetching the data from the server
				}
			}
		};
		submitRequest.setOnClickListener(listener);
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Updates the main GUI. 
	 * @param result - string returned after parsing the HTML data. 
	 */
	public void dataReceivedFromNetwork(String result) {
		output.setText(result);
	}


	/**
	 * Getter for company variale declared as private
	 * @return
	 */
	public String getCompany() {
		// TODO Auto-generated method stub
		return company;
	}

}

