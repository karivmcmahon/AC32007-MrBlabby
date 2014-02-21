package com.tweet.servlets;

import java.io.IOException;
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
import com.tweet.model.*;
import com.tweet.stores.ProfileStore;
import com.tweet.stores.TweetStore;
import com.tweet.stores.UserStore;

/**
 * Servlet implementation class Profile
 */
@WebServlet(
		urlPatterns = { 
		"/Profile", 
		"/Profile/*"
}, 
initParams = { 
				@WebInitParam(name = "data-source", value = "jdbc/faultdb")
		})
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
   	 * @see Servlet#init(ServletConfig)
   	 */
   	public void init(ServletConfig config) throws ServletException {
   		// TODO Auto-generated method stub
   		//initialise connection
   		conn db = new conn();
   		db.createSchema();
   		_ds=db.assemble(config);
   	}

	/**
	 * Enables us to get profile information from model to display in view
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProfileModel profModel = new ProfileModel();
		TweetModel tweetModel = new TweetModel();
		UserStore u = new UserStore();
		//Get information of user current logged in
		u = (UserStore) request.getSession().getAttribute("currentSeshUser");
		
		if(u == null)
		{
			//Redirect to sign up page if current user sesh is null
			response.sendRedirect("/TweetTwoo/SignUp.jsp");
		}
		else
		{
			//check if users logged in
			if(u.getLoggedIn() == true)
			{
				if(request.getRequestURI().equals(request.getContextPath() + "/Profile"))
				{
					 //Set up data sources
					tweetModel.setDatasource(_ds);
					profModel.setDatasource(_ds);
					

					//Retrieve a specific profile and tweets
					LinkedList<TweetStore> tsl = tweetModel.getOwnTweets(u);
					LinkedList<ProfileStore> psl = profModel.getProfile(u);
					
					//Get profile and users own tweets info
					request.setAttribute("Tweets", tsl);
					request.setAttribute("Profiles", psl); 
					
					//Then forward request to Profile.jsp
					RequestDispatcher rd = request.getRequestDispatcher("/Profile.jsp");  
					rd.forward(request, response);
				}
				else
				{
				//Get end of url
				int lastSlash = request.getRequestURI().lastIndexOf('/');
				String endOfUrl = request.getRequestURI().substring(lastSlash + 1);
			    	//try to get id from end of url
					try
					{
						int id = Integer.valueOf(endOfUrl);
						 //Set up data sources
						tweetModel.setDatasource(_ds);
						profModel.setDatasource(_ds);
						
					
						//Retrieve a specific profile and tweets based on id
						LinkedList<TweetStore> tsl = tweetModel.getTweetsByID(id);
						LinkedList<ProfileStore> psl = profModel.getProfileByID(id,u);
						
						//Get profile and users own tweets info
						request.setAttribute("Tweets", tsl);
						request.setAttribute("Profiles", psl); 
						
						//Then forward request to Profile.jsp
						RequestDispatcher rd = request.getRequestDispatcher("/Profile.jsp");  
						rd.forward(request, response);
					}
					catch(Exception e)
					{
						//if not int then get username from end or url
						String username =endOfUrl.toString();
						 //Set up data sources
						tweetModel.setDatasource(_ds);
						profModel.setDatasource(_ds);
						

						
						
						//If json requested then get json of users own tweets
						if(username.equals("json"))
						{
							//Retrieve a specific profile and tweets
							LinkedList<TweetStore> tsl = tweetModel.getOwnTweets(u);
							LinkedList<ProfileStore> psl = profModel.getProfile(u);
							//Send data to Json
							request.setAttribute("data", tsl);
				            System.out.println("l");
				            request.getRequestDispatcher("/Json").forward(request, response);
				            return;
						}
						else
						{
							
							//Retrieve a specific profile and tweets ny username
							LinkedList<TweetStore> tsl2 = tweetModel.getOwnTweetsByUsername(username);
							LinkedList<ProfileStore> psl2 = profModel.getProfileByUsername(username,u);

							//Get profile and users own tweets info
							request.setAttribute("Tweets", tsl2);
							request.setAttribute("Profiles", psl2); 
							
							//Then forward request to Profile.jsp
							RequestDispatcher rd = request.getRequestDispatcher("/Profile.jsp");  
							rd.forward(request, response);
						}
					}
				}
				
			}
					
	else
	{
			//Redirect to sign up if not logged in
			response.sendRedirect("/TweetTwoo/SignUp.jsp");
	}
}

}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
