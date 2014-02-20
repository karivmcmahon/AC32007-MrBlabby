package com.tweet.servlets;

import java.io.IOException;
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
import javax.sql.DataSource;

import com.tweet.libs.conn;
import com.tweet.model.FollowingModel;
import com.tweet.stores.ProfileStore;
import com.tweet.stores.UserStore;

/**
 * Servlet implementation class Follower
 */
@WebServlet(
		urlPatterns = { 
		"/Follower", 
		"/Follower/*"
}, 
initParams = { 
				@WebInitParam(name = "data-source", value = "jdbc/faultdb")
		})
public class Follower extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Follower() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
   	 * @see Servlet#init(ServletConfig)
   	 */
   	public void init(ServletConfig config) throws ServletException {
   		// TODO Auto-generated method stub
   		//Initialise connection
   		conn db = new conn();
   		db.createSchema();
   		_ds=db.assemble(config);
   	}
   	

	/**
	 * Enables us to get who the user followers are from model and send to view
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
				UserStore u = new UserStore();
				//Get session for user currently logged in
				u = (UserStore) request.getSession().getAttribute("currentSeshUser");
				if( u == null)
				{
					//If no current user send to sign up page
					response.sendRedirect("/TweetTwoo/SignUp.jsp");
				}
				else
				{
					//check logged in
					if(u.getLoggedIn() == true)
					{
						
						FollowingModel fm = new FollowingModel();
						//Gets end of url
						int lastSlash = request.getRequestURI().lastIndexOf('/');
						String endOfUrl = request.getRequestURI().substring(lastSlash + 1);
					    String urlEnd = endOfUrl.toString();
					    
					    //Sets up data source
						fm.setDatasource(_ds);
						
						
						//Get users followers by passing current logged in users info
						LinkedList<ProfileStore> lps = fm.getFollowers(u);
					
						//If url ends in json then create a json object
						if(urlEnd.equals("json"))
						{
							//Sends users own tweets to JSON to get as a JSON object
							request.setAttribute("data", lps);
				            RequestDispatcher rd = request.getRequestDispatcher("/Json");
				            rd.forward(request, response);
				            return;
						}
						else
						{
							request.setAttribute("Followers", lps); 
							//forward followers to follower.jsp
							RequestDispatcher rd = request.getRequestDispatcher("/Follower.jsp"); 
							rd.forward(request, response);
						}
					}
					else
					{
						//If  not logged in then redirect to sign up
						response.sendRedirect("/TweetTwoo/SignUp.jsp");
					}
			}
				
	}

	/**
	 * Enables us to get who user wants to follow from view and send to model
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserStore u = new UserStore();
		//Get session for user currently logged in
		u = (UserStore) request.getSession().getAttribute("currentSeshUser");
		if(u == null)
		{
			//If no current user redirect to sign up page
			response.sendRedirect("/TweetTwoo/SignUp.jsp");
		}
		else
		{
			//check user logged in
			if(u.getLoggedIn() == true)
			{
				//Gets user id  from the the person user selected to follow
				int id=Integer.parseInt(request.getParameter("userid"));
				FollowingModel fm = new FollowingModel();
				fm.setDatasource(_ds);
				//follow user
				fm.followUser(id,u);
				//redirect back to suggestions
				response.sendRedirect("/TweetTwoo/Following");
			}
			else
			{
				response.sendRedirect("/TweetTwoo/SignUp.jsp");
			}
		}
		
		
		
	}

}
