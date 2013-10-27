package edu.vt.ece4564.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Siddartha Tondapu
 *	Stores the Data Objects as JSON. 
 */

public class DataServlet extends HttpServlet {

	JSONArray data = new JSONArray();
	
	//When get is called
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException{
		resp.setContentType("text/plain");
		resp.getWriter().write(data.toString());
	}
	
	//When post is called, parse the data. 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws IOException{
		 StringBuffer jb = new StringBuffer();
		 String line = null;
		 try {
		      jb.append(line);
			  JSONObject obj = new JSONObject();
			  obj.put("name", req.getParameter("name"));
			  obj.put("age", req.getParameter("age"));
			  obj.put("phone", req.getParameter("phone"));
			  obj.put("address", req.getParameter("address"));
			  obj.put("gender", req.getParameter("gender"));
			  obj.put("activity", req.getParameter("activity"));
			  data.put(obj);
//			  System.out.println(data.toString());
			  resp.setContentType("text/plain");
			  resp.getWriter().write(req.getParameter("name") + " activity has been posted correctly");
		 } catch (Exception e) { 
			 resp.setContentType("text/plain");
			 resp.getWriter().write("Error detected: " + e);
		 }
		
	}


}
