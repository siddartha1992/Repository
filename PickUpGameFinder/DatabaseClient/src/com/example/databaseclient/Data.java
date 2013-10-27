package com.example.databaseclient;

/**
 * 
 * @author Siddartha Tondapu
 * Class created to store and retrieve data easily.
 *
 */
public class Data {
	//Variables of the class
	private String name, age, phone, address, gender, activity; 
	
	public Data(String name, String age, String phone, String address, String gender, String activity){
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.activity = activity; 
	}
	
	//Getters of the above variable. 
	public String getName(){
		return this.name;
	}
	
	public String getAge(){
		return this.age;
	}
	
	public String getPhone(){
		return this.phone;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public String getGender(){
		return this.gender;
	}
	
	public String getActivity(){
		return this.activity;
	}
}
