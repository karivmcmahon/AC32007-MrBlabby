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
           _ds=db.assemble(config);
   	}
   	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
				UserStore u = new UserStore();
				//Get session for user currently logged in
				u = (UserStore) request.getSession().getAttribute("currentSeshUser");
				if( u == null)
				{
					response.sendRedirect("/TweetTwoo/SignUp.jsp");
				}
				else
				{
				if(u.getLoggedIn() == true)
				{
					Iterator<ProfileStore> iterator;
					FollowingModel fm = new FollowingModel();
					
					fm.setDatasource(_ds);
					//Get users followers by passing current logged in users info
					LinkedList<ProfileStore> lps = fm.getFollowers(u);
					request.setAttribute("Followers", lps); 
					//forward followers to follower.jsp
					RequestDispatcher rd = request.getRequestDispatcher("/Follower.jsp"); 

					rd.forward(request, response);
				}
				else
				{
					response.sendRedirect("/TweetTwoo/SignUp.jsp");
				}
			}
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserStore u = new UserStore();
		//Get session for user currently logged in
		u = (UserStore) request.getSession().getAttribute("currentSeshUser");
		if(u == null)
		{
			response.sendRedirect("/TweetTwoo/SignUp.jsp");
		}
		if(u.getLoggedIn() == true)
		{
		//Gets user id  from the the person user selected to follow
		int id=Integer.parseInt(request.getParameter("userid"));
		FollowingModel fm = new FollowingModel();
		fm.setDatasource(_ds);
		//follow user
		fm.followUser(id,u);
		//redirect back to suggestions
		response.sendRedirect("/TweetTwoo/Suggestions");
		}
		else
		{
			response.sendRedirect("/TweetTwoo/SignUp.jsp");
		}
		
		
		
	}

}
