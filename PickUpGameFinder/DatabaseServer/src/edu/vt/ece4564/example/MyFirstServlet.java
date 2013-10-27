package edu.vt.ece4564.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyFirstServlet extends HttpServlet {

	/**
	 * @author Siddartha Tondapu
	 * Purpose: Used for storing usernames and displays the corresponding messages
	 */
	private static List<String> usernames;
	JSONArray data;
	public static void main(String[] args) throws Exception {
		//starting up the server
		Server server = new Server(8081);
		usernames = new ArrayList<String>();
		
		WebAppContext context = new WebAppContext();
		context.setWar("war");
		context.setContextPath("/");
		server.setHandler(context);
		
		server.start();
		server.join();
		
		// TODO Auto-generated method stub

	}

	//Add the usernames to the webserver and respond with corresponding messages based on 
	//if the user already exists or not. 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String name = req.getParameter("username");
		resp.setContentType("text/plain");
		String temp = "\n1)Click on Add user to add a new activity\n2)Click Lookup data to " +
				"view the current data\n3)Click Logout to return to the previous page.";
		if(!usernames.contains(name)){
			usernames.add(name);
			resp.getWriter().write("Welcome New User: " + name + temp);
		}else{
			resp.getWriter().write("Welcome Existing User: " +  name + temp);
		}
		
	}
}

