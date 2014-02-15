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
import com.tweet.model.TweetModel;
import com.tweet.stores.ProfileStore;
import com.tweet.stores.TweetStore;
import com.tweet.stores.UserStore;

/**
 * Servlet implementation class Following
 */
@WebServlet(
		urlPatterns = { 
		"/Following", 
		"/Following/*"
}, 
initParams = { 
		@WebInitParam(name = "data-source", value = "jdbc/faultdb")
})
public class Following extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Following() {
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
		if(u == null)
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
		//Get a list of who the user is following
		LinkedList<ProfileStore> lps = fm.getFollowing(u);
		request.setAttribute("Following", lps); 
		//forward this to following.jsp
		RequestDispatcher rd = request.getRequestDispatcher("/Following.jsp"); 

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
		
		int id=Integer.parseInt(request.getParameter("userid"));

		System.out.println("User " + id);
		FollowingModel fm = new FollowingModel();
		fm.setDatasource(_ds);
		//Enable user to unfollow user
		fm.unfollowUser(id,u);
		//redirect to following page to show changes
		response.sendRedirect("/TweetTwoo/Following");
		
	}

}
