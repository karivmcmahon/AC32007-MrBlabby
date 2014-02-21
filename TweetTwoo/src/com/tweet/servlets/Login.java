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

import com.tweet.libs.conn;
import com.tweet.model.UserModel;
import com.tweet.stores.UserStore;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		urlPatterns = { 
		"/Login", 
		"/Login/*"
}, 
initParams = { 
		@WebInitParam(name = "data-source", value = "jdbc/faultdb")
})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * Enables us to get information from view to send to model to confirm login
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST");
		// TODO Auto-generated method stub
		UserStore user = new UserStore();
		UserModel uModel = new UserModel();
		//Set up data source
		uModel.setDatasource(_ds);
		//Get user info from text boxes in SignUp.jsp
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		try
		{
			//Attempt to log user in
			user = uModel.login(user);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("failed to log user in");
			e.printStackTrace();
		}
		
		//If user has logged into a valid account
		if(user.getValid())
		{
			//Set user as logged in
			user.setLoggedIn(true);
			//Set error to empty
			user.setError2("");
			//Set session to true and set it to an attribute
			HttpSession session = request.getSession(true);
			session.setAttribute("currentSeshUser", user);
			//Direct to home.jsp once session true
			response.sendRedirect("/TweetTwoo/Tweet");
			
		}
		else
		{
			//If it fails set user logged in as false
			user.setLoggedIn(false);
			user.setError2("Could not log user in");
			request.setAttribute("Users", user);
			 //forward tweets to Home.jsp
		     RequestDispatcher rd = request.getRequestDispatcher("/SignUp.jsp"); 
		      rd.forward(request, response); 
			
		}
	}

}
