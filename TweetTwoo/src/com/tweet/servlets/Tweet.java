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
		StringSplitter SS = new StringSplitter();
		String args[]  = SS.SplitRequestPath(request);
		if (args.length < 3 && args[1].toString().equals("Tweet") ){
	
				//Set up data source
				Tweet.setDatasource(_ds);
				//Get tweets info
			    psl = Tweet.getTweets(u); 
				request.setAttribute("Tweets", psl); 
				//forward tweets to Home.jsp
				 rd = request.getRequestDispatcher("/Home.jsp"); 

				rd.forward(request, response);

		}
		
		int command;
		try{
			
		
				command =(Integer)CommandsMap.get(args[2]);
			 
		  
		   
		}catch(Exception et){
			//error("Bad Operator",out);
			return;
		}
		System.out.println("Command"+command);
		
		String name;
		try
		{
			
					if(args[2].toString().equals("id"))
					{
						x = Integer.parseInt(args[3]);
					}
					if(args[2].toString().equals("name"))
					{
						 username = args[3].toString();
				
					}
				
				
			   
			   
		}
		catch(Exception et)
		{
			//	error("Bad numbers in calc",out);
				return;			
		}
		switch (command){
		  case 1:
			Tweet.setDatasource(_ds);
			t.setTweetid(x);
			//Get tweets info
			psl = Tweet.getTweetsByID(t); 
			request.setAttribute("Tweets", psl); 
			//forward tweets to Home.jsp
			 rd = request.getRequestDispatcher("/Home.jsp"); 
			 rd.forward(request, response);
			 break;
		  case 2:
			  Tweet.setDatasource(_ds);
			  u.setUsername(username);
			  //Get tweets info
			  psl = Tweet.getTweetsByUsername(u); 
			  request.setAttribute("Tweets", psl); 
			  //forward tweets to Home.jsp
		      rd = request.getRequestDispatcher("/Home.jsp"); 
		      rd.forward(request, response); 
		default:
			break;
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
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("delete");
	}

	
	
	

}
