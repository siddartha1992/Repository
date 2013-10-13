/**
 * Authod: Siddartha Tondapu
 * Class defined to hold a bitmap and a string value
 */
package com.example.project1;

import android.graphics.Bitmap;

public class Data {
	private String webData;
	private Bitmap image;
	
	public Data(String webData, Bitmap image){
		this.webData = webData;
		this.image = image;
	}
	
	public String getWebData(){
		return webData;
	}
	
	public Bitmap getImage(){
		return image;
	}

}
