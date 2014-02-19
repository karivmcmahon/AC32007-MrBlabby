package com.tweet.servlets;

import java.io.IOException;
import java.sql.SQLException;

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

import com.tweet.libs.StringSplitter;
import com.tweet.libs.conn;
import com.tweet.model.ProfileModel;
import com.tweet.model.TweetModel;
import com.tweet.stores.ProfileStore;
import com.tweet.stores.UserStore;

/**
 * Servlet implementation class EditProfile
 */
@WebServlet(
		urlPatterns = { 
		"/EditProfile", 
		"/EditProfile/*"
}, 
initParams = { 
		@WebInitParam(name = "data-source", value = "jdbc/faultdb")
})
public class EditProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfile() {
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
	 * Enables getting profile to place in textboxs in edit profile page
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserStore user = new UserStore();
		ProfileModel prof = new ProfileModel();
		ProfileStore ps = new ProfileStore();
		//Get information of user who is currently logged in
		user = (UserStore)  request.getSession().getAttribute("currentSeshUser");
		//If no current information
		if(user == null)
		{
			//Redirect to sign up page
			response.sendRedirect("/TweetTwoo/SignUp.jsp");
		}
		else
		{
			//Checks if user is logged in
			if(user.getLoggedIn() == true)
			{
				//Set up data source and get information for editing profile
				prof.setDatasource(_ds);
				ps = prof.getProfileEdit(user);
				//Then set profile store to request and forward it to EditProfile.jsp
				request.setAttribute("Profile", ps); 
				RequestDispatcher rd = request.getRequestDispatcher("/EditProfile.jsp"); 
		
				rd.forward(request, response);
			}
			else
			{
				//If not logged in redirect to sign up page
				response.sendRedirect("/TweetTwoo/SignUp.jsp");
			}
		}
		
	}

	/**
	 * Enables updating profile
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserStore user = new UserStore();
		ProfileModel prof = new ProfileModel();
		ProfileStore ps = new ProfileStore();
		//Get user info who is currently logged
		user = (UserStore)  request.getSession().getAttribute("currentSeshUser");
		
		//Set up data source
		prof.setDatasource(_ds);
		//Set error to empty
		user.setError("");
		//Get information from textboxes in EditProfile.jsp
		ps.setName(request.getParameter("name"));
		ps.setUsername(request.getParameter("username"));
		ps.setPassword(request.getParameter("password"));
		ps.setEmail(request.getParameter("email"));
		ps.setBio(request.getParameter("bio"));
		ps.setCountry(request.getParameter("country"));
		ps.setLocation(request.getParameter("location"));
		
		try
		{
			//Attempt to update users profile
			boolean v = prof.updateProfile(user,ps);
			if(v == false)
			{
				//Set error to show you could not update profile and redirect to edit profile
				user.setError("Could not update profile");
				response.sendRedirect("/TweetTwoo/EditProfile");
			}
			else
			{
				//Redirect to profile if update sucessful
				response.sendRedirect("/TweetTwoo/Profile");
			}
		} 
		catch (SQLException e) 
		{
			//Set error to display in html
			user.setError("Could not update profile");
			System.out.println("Error updating user profile : ");
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Redirect to edit profile
			response.sendRedirect("/TweetTwoo/EditProfile");
		}
		
		
	}
	
	/**
	 * Enables us to delete a users account
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		ProfileModel prof = new ProfileModel();
		UserStore u = new UserStore();
		//Gets current user sesh
		u = (UserStore) request.getSession().getAttribute("currentSeshUser");
		
		//Gets id sent in url
		StringSplitter SS = new StringSplitter();
		String args[]  = SS.SplitRequestPath(request);
		int id = Integer.parseInt(args[2]);
		//Set up data source
		prof.setDatasource(_ds);
		try
		{
			//Attempt to delete account then invalidate account
			prof.deleteAccount(id);
			//Set as logged in false
			u.setLoggedIn(false);
			//Invalidate session
			request.getSession().invalidate();
			//Redirect user to sign up/log in page once they have logged out
			response.sendRedirect("/TweetTwoo/SignUp.jsp");
		}
		catch(Exception e)
		{
			System.out.println("Couldn't delete current user's account");
		}
		
		
		
	}

}
