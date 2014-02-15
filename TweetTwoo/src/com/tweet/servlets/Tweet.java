package com.tweet.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.tweet.libs.StringSplitter;
import com.tweet.libs.conn;
import com.tweet.stores.*;
import com.tweet.model.*;


/**
 * Servlet implementation class Tweet
 */
@WebServlet(
		urlPatterns = { 
		"/Tweet", 
		"/Tweet/*"
}, 
initParams = { 
		@WebInitParam(name = "data-source", value = "jdbc/faultdb")
})
public class Tweet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
	private HashMap CommandsMap = new HashMap();

    /**
     * Default constructor. 
     */
    public Tweet() {
        super();
        // TODO Auto-generated constructor stub
        CommandsMap.put("id",1);
        CommandsMap.put("name",2);
     
    	
    	
    	
    }
    
    /**
   	 * @see Servlet#init(ServletConfig)
   	 */
   	public void init(ServletConfig config) throws ServletException {
   		// TODO Auto-generated method stub
   		//Initialise connection
   		conn db = new conn();
           _ds=db.assemble(config);
   	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserStore u = new UserStore();
		Iterator<TweetStore> iterator;
		TweetModel Tweet = new TweetModel();//Create a new instance of the model
		TweetStore t = new TweetStore();
		LinkedList<TweetStore> psl;
		RequestDispatcher rd;
		String username = "";
		int x = 0;
		//Get session for user currently logged in
		u = (UserStore) request.getSession().getAttribute("currentSeshUser");
		if(u == null)
		{
			response.sendRedirect("/TweetTwoo/SignUp.jsp");
		}
		else
		{
		
		if(u.getLoggedIn() == true)
		{
		if(request.getRequestURI().equals(request.getContextPath() + "/Tweet"))
		{
			//Set up data source
			Tweet.setDatasource(_ds);
			//Get tweets info
		    psl = Tweet.getTweets(u); 
			request.setAttribute("Tweets", psl); 
			//forward tweets to Home.jsp
			 rd = request.getRequestDispatcher("/Home.jsp"); 

			rd.forward(request, response);
		}
		else
		{
			int lastSlash = request.getRequestURI().lastIndexOf('/');
			String endOfUrl = request.getRequestURI().substring(lastSlash + 1);
			try
			{
				int id = Integer.valueOf(endOfUrl);
				Tweet.setDatasource(_ds);
				t.setTweetid(id);
				//Get tweets info
				psl = Tweet.getTweetsByID(t); 
				request.setAttribute("Tweets", psl); 
				//forward tweets to Home.jsp
				rd = request.getRequestDispatcher("/Home.jsp"); 
				rd.forward(request, response);
			}
			catch(Exception e)
			{
				String usernames = endOfUrl.toString();
				System.out.println("u " + usernames);
				 Tweet.setDatasource(_ds);
				  u.setUsername(usernames);
				  //Get tweets info
				  psl = Tweet.getTweetsByUsername(u); 
				  request.setAttribute("Tweets", psl); 
				  //forward tweets to Home.jsp
			      rd = request.getRequestDispatcher("/Home.jsp"); 
			      rd.forward(request, response); 
			}
			}
		}
		else
		{
		  response.sendRedirect("/TweetTwoo/Signup.jsp");
		}
	}
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserStore u = new UserStore();
		TweetStore t = new TweetStore();
		TweetModel tm = new TweetModel();
		//Get current user logged info
		u = (UserStore) request.getSession().getAttribute("currentSeshUser");
		if(u == null)
		{
			response.sendRedirect("/TweetTwoo/SignUp.jsp");
		}
		else
		{
			if(u.getLoggedIn() == true)
			{
		//Get information in textbox from Home.jsp
		t.setTweet(request.getParameter("postTweet"));
		//Get timestamp  when it was posted
		java.util.Date date= new java.util.Date();
		t.setTime(new Timestamp(date.getTime()));
		//Set up data source
		tm.setDatasource(_ds);
		try 
		{
			//Attempts to create tweet
			tm.createTweet(u,t);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Creating tweet could not be execueted ");
			e.printStackTrace();
		}
		
		//Redirect to home timeline once tweet created 
		response.sendRedirect("/TweetTwoo/Tweet");
		}
		else
		{
			response.sendRedirect("/TweetTwoo/SignUp.jsp");
		}
	}
		
		
		
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		TweetModel Tweet = new TweetModel();
		System.out.println(request.getRequestURI());
		StringSplitter SS = new StringSplitter();
		String args[]  = SS.SplitRequestPath(request);
		int id = Integer.parseInt(args[2]);
		Tweet.setDatasource(_ds);
		Tweet.deleteTweet(id);
		
	}

	
	
	

}
