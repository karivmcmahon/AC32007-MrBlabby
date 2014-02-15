package com.tweet.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;

import com.tweet.stores.UserStore;

public class UserModel {
	
	private DataSource _ds = null;
	
	public UserModel()
	{
		
	}
	
	//Set up data source
	public void setDatasource(DataSource _ds)
	{
		this._ds=_ds;
		
	}
	
	/**
	 * Method attempts to log user in
	 * @param us
	 * @return
	 * @throws SQLException
	 */
	public UserStore login(UserStore us) throws SQLException
	{
		//Variable to set up connection
		Connection Conn;
		//Store results from query
		ResultSet rs;
		//Prepared statement
		PreparedStatement preps = null;
		//Information for query
		String username = us.getUsername();
		String password = us.getPassword();
		//Query
		String query = "SELECT * FROM users WHERE username = ? AND password = ?;";
		
		
		try 
		{
			//Get connection
			Conn = _ds.getConnection();
		} 
		catch (Exception et) 
		{
			//Display no connection available
			System.out.println("No Connection in User Model login()");
			return null;
		}
		
		try {
			//Prepare and set up query
			preps = Conn.prepareStatement(query);
			preps.setString(1, username);
			preps.setString(2, password);
		} 
		catch (Exception et) 
		{
			System.out.println("Can't create prepare statement login()");
			return null;
		}
		
		try 
		{
			//Execute query
			rs = preps.executeQuery();
			
			
		} 
		catch (Exception et) 
		{
		
			System.out.println("Can not execute query login() " + et);
			return null;
		}

		if (rs.wasNull()) 
		{
			//If result set null set user to invalid - so they cannot access any futher
			System.out.println("result set was null");
			us.setValid(false);
		} 
		else
		{
			
			while(rs.next())
			{
				//Set up user information and validate
				String name = rs.getString("name");
				int id = rs.getInt("userid");
				System.out.println("name  " + name);
				us.setName(name);
				us.setUserid(id);
				us.setValid(true);
			}
		}
		
		try 
		{
			//Attempt to close connection
			Conn.close();
		} 
		catch 
		(Exception ex) 
		{
			System.out.println("Could not close connection");
			return null;
		}
		
		//Return user store
		return us;
	}
	
	/**
	 * Method registers users by adding there information to the database
	 * @param u
	 * @return
	 * @throws SQLException
	 */
	public int registerUser(UserStore u) throws SQLException
	{
		Connection Conn = null;
		
		//Users information
		String un = u.getUsername();
		System.out.println("Username " + u.getUsername());
		String pw = u.getPassword();
		String em = u.getEmail();
		int userid = 0;
		String n = u.getName();
		
		//Result set
		ResultSet rs = null;
		try 
		{
			//Get connection
			Conn = _ds.getConnection();
		} catch (Exception et) 
		{

			System.out.println("No Connection in Tweets Model");
			
		}

		//Set up prepared statements
		PreparedStatement pmst = null;
		PreparedStatement pmst2 = null;
		PreparedStatement pmst3 = null;
		
		
		//Set up querys
		String sqlQuery = "INSERT users(username,password,email,name)  VALUES(?,?,?,?);";
		String selectQuery = "SELECT userid FROM users WHERE username = ? AND password = ?;";
		String sqlQuery2 = "INSERT profile(user_id) VALUES(?);";
		
		
	
				
				try 
				{
					//Prepare statement
					pmst = Conn.prepareStatement(sqlQuery);
				} 
				catch (SQLException e1) 
				{
					System.out.println("Could not prepare statement - register()");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try
				{
					//Prepare statement
					pmst.setString(1, un);
					pmst.setString(2,pw);
					pmst.setString(3,em);
					pmst.setString(4, n);
				}catch(SQLException e2)
				{
					System.out.println("Could not prepare statement 2 - register()");
					e2.printStackTrace();
				}
				
				try
				{
					//Execute query
					pmst.executeUpdate();
				}
				catch(SQLException e2)
				{
					System.out.println("Query 1 could not execute - register()");
					e2.printStackTrace();
				}
				
				try 
				{
					//Prepare statement
					pmst2 = Conn.prepareStatement(selectQuery);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("Query 2 could not prepare - register()");
					e1.printStackTrace();
				}
				try
				{
					//Prepare statement
					pmst2.setString(1, un);
					pmst2.setString(2,pw);
				
				}catch(SQLException e2)
				{
					System.out.println("Query 2 could not prepare - register()");
					e2.printStackTrace();
				}
				
				try
				{
					//Execute query
				   rs = pmst2.executeQuery();
				}
				catch(SQLException e2)
				{
					System.out.println("Query 2 could not execute - register()");
					e2.printStackTrace();
				}
				while(rs.next())
				{
					//Get user id
					userid = rs.getInt("userid");
					
				}
				try
				{
					//prepare statement
					pmst3 = Conn.prepareStatement(sqlQuery2);
				} 
				catch (SQLException e1) 
				{
					System.out.println("Query 3 could not prepare - register()");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try
				{
					
					pmst3.setInt(1, userid);
				
				
				}catch(SQLException e2)
				{
					System.out.println("Query 3 could not prepare - register()");
					e2.printStackTrace();
				}
				
				try
				{
				   pmst3.executeUpdate();
				}
				catch(SQLException e2)
				{
					System.out.println("Query 3 could not execute - register()");
					e2.printStackTrace();
				}
				
		

				try
				{
					Conn.close();
				}
				catch(Exception e)
				{
					System.out.print("Could not close connection");
					return 0;
				}
	//return userid
	return userid;
	
	
	}

}
