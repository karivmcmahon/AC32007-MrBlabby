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
import com.tweet.model.UserModel;
import com.tweet.stores.ProfileStore;
import com.tweet.stores.UserStore;

/**
 * Servlet implementation class Search
 */
@WebServlet(
		urlPatterns = { 
		"/Search", 
		"/Search/*"
}, 
initParams = { 
				@WebInitParam(name = "data-source", value = "jdbc/faultdb")
		})
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
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
	 * Directs to search.jsp
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("/TweetTwoo/Search.jsp");
	}

	/**
	 * Gets who the user searches for and returns the results
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserModel model = new UserModel();
		LinkedList<ProfileStore> psl;
		UserStore u = new UserStore();
		u = (UserStore) request.getSession().getAttribute("currentSeshUser");
		if(u == null)
		{
			//Redirect to sign up
			response.sendRedirect("/SignUp.jsp");
		}
		else
		{
			//Set up data source
			model.setDatasource(_ds);
			//Get name from textbox
			String name = request.getParameter("searchbox");
			//Store all found users in a linked list
			try
			{
			 psl = model.findUser(name,u);
			}catch(Exception e)
			{
				//else make linked list null
			  psl = null;
			}
			//Then send linked list to Search.jsp
			request.setAttribute("Profiles", psl);
			RequestDispatcher rd = request.getRequestDispatcher("/Search.jsp");  
			rd.forward(request, response);
		}
		
	}

}
