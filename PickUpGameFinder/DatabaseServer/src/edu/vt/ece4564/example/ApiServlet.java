package edu.vt.ece4564.example;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiServlet extends HttpServlet {


	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException{
		resp.setContentType("text/plain");
		resp.getWriter().write("Api Servelt Opened!");
	}

}
