package com.tweet.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tweet.stores.UserStore;

/**
 * Servlet implementation class Logout
 */
@WebServlet(
		urlPatterns = { 
		"/Logout", 
		"/Logout/*"
}, 
initParams = { 
		@WebInitParam(name = "data-source", value = "jdbc/faultdb")
})
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Logs user out of session
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Invalidate session and set user logged in false
		UserStore u = new UserStore();
		u = (UserStore) request.getSession().getAttribute("currentSeshUser");
		//set the current user logged in as false
		u.setLoggedIn(false);
		//invalidate session
		request.getSession().invalidate();
		//Redirect user to sign up/log in page once they have logged out
		response.sendRedirect("/TweetTwoo/SignUp.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
