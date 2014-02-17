package com.tweet.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.tweet.libs.StringSplitter;
import com.tweet.libs.conn;
import com.tweet.model.ProfileModel;
import com.tweet.stores.UserStore;

/**
 * Servlet implementation class EditProfile
 */
@WebServlet(
		urlPatterns = { 
		"/User", 
		"/User/*"
}, 
initParams = { 
		@WebInitParam(name = "data-source", value = "jdbc/faultdb")
})
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ProfileModel prof = new ProfileModel();
		UserStore u = new UserStore();
		u = (UserStore) request.getSession().getAttribute("currentSeshUser");
		
		//Get id to delete user from url 
		System.out.println(request.getRequestURI());
		StringSplitter SS = new StringSplitter();
		String args[]  = SS.SplitRequestPath(request);
		int id = Integer.parseInt(args[2]);
		
		//Attempt to delete user
		prof.setDatasource(_ds);
		prof.deleteAccount(id);
		//Redirect user to sign up/log in page once they have logged out
	//	response.sendRedirect("/TweetTwoo/Search.jsp");
		
	}

}
