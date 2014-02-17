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
 * Servlet implementation class Following
 */
@WebServlet(
		urlPatterns = { 
		"/Suggestions", 
		"/Suggestions/*"
}, 
initParams = { 
		@WebInitParam(name = "data-source", value = "jdbc/faultdb")
})
public class Suggestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Suggestions() {
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
		int lastSlash = request.getRequestURI().lastIndexOf('/');
		String endOfUrl = request.getRequestURI().substring(lastSlash + 1);
	    String urlEnd = endOfUrl.toString();
		fm.setDatasource(_ds);
		//Get a list of suggestion
		LinkedList<ProfileStore> lps = fm.getSuggestions(u);
		if(urlEnd.equals("json"))
		{
			request.setAttribute("data", lps);
            System.out.println("l");
            request.getRequestDispatcher("/Json").forward(request, response);
            return;
		}
		else
		{
		request.setAttribute("Suggestions", lps); 
		//forward tweets to Home.jsp
		RequestDispatcher rd = request.getRequestDispatcher("/Suggestions.jsp"); 

		rd.forward(request, response);
		}
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
	}

}
