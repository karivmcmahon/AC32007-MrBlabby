package com.tweet.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import com.google.gson.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Json
 */
@WebServlet(
		urlPatterns = { 
		"/Json", 
		"/Json/*"
})
public class Json extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Json() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Enables us to get a json object
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 	
			//****Must have google GSON jar in tomcat lib for this to work ******
		
			//Creates new gson
			Gson gson = new Gson();

			//Requests data from attribute
	        Object data = request.getAttribute("data");
	        //Converts gson to json which is stored in a string
	        String json = gson.toJson(data);
	        
	        try 
	        {
	        	//Json is then printed
	            PrintWriter out = response.getWriter();
	            out.print(json);
	        } 
	        catch (IOException ex) 
	        {
	            System.out.println("Json could not be printed");
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
