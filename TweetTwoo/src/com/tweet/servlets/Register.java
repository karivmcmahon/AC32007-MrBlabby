package com.tweet.servlets;

import java.io.IOException;

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
 * Servlet implementation class Register
 */
@WebServlet(
		urlPatterns = { 
		"/Register", 
		"/Register/*"
}, 
initParams = { 
		@WebInitParam(name = "data-source", value = "jdbc/faultdb")
})
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
		int  id = 0;
		
		UserStore u = new UserStore();
		UserModel um = new UserModel();
		
		//Get information from textboxs in register section of SignUp.jsp
		u.setUsername(request.getParameter("newUsername"));
		u.setPassword(request.getParameter("newPassword"));
		u.setEmail(request.getParameter("newEmail"));
		u.setName(request.getParameter("name"));
		u.setPermission(1);
		
		try
		{
			//Set up data source
			um.setDatasource(_ds);
			//Attempt to register user
			 id = um.registerUser(u);
			
		}
		catch(Exception e)
		{
			System.out.println("Could not register user");
			response.sendRedirect("/SignUp.jsp");
		}
		
		if(id != 0)
		{
			//Set users id
			u.setUserid(id);
			u.setLoggedIn(true);
			//Set session true
			HttpSession session = request.getSession(true);
			session.setAttribute("currentSeshUser", u);
			//Let user access there timeline
			response.sendRedirect("/TweetTwoo/Tweet");
		}
		else
		{
			//If user cant be registered redirect to sign up 
			response.sendRedirect("/SignUp.jsp");
		}
	}

}

