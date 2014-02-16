package com.tweet.model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.sql.DataSource;

import com.tweet.stores.ProfileStore;
import com.tweet.stores.TweetStore;
import com.tweet.stores.UserStore;

public class ProfileModel {
	//Set Datasource to null
	private DataSource _ds = null;
	public ProfileModel()
	{
	
	}
	
	public void setDatasource(DataSource _ds)
	{
		//Set up data store
		this._ds=_ds;
		
	}
	
	/**
	 * Gets profile for user
	 * @param u
	 * @return LinkedList ProfileStore
	 */
	public LinkedList<ProfileStore> getProfile(UserStore u)
	{
		//Stores profile
		LinkedList<ProfileStore> psl = new LinkedList<ProfileStore>();
		//Store conn
		Connection Conn;
		//Set up profile store
		ProfileStore ps = null;
		//Set up result sets for storing sql query
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		//Store image data for profile
		byte[] imgData = null ;
		//Store the user thats current logged in - users id
		int id = u.getUserid();
		
		try 
		{
			//Gets connection
			Conn = _ds.getConnection();
		} 
		catch (Exception et)
		{
			//Display error if we cant get connection
			System.out.println("No Connection in the Profile Model getProfile()");
			return null;
		}

		//Set up prepared statements
		PreparedStatement pmst = null;
		PreparedStatement pmst2 = null;
		PreparedStatement pmst3 = null;
		PreparedStatement pmst4 = null;
		
		//Query to retrieve info for profile
		String sqlQuery = "SELECT name,username,bio,location,country,photo FROM users JOIN profile ON (profile.user_id = users.userid) WHERE users.userid = ? ;";
		//Query to retrieve number of people the user is following
		String sqlQueryFollowing = "SELECT COUNT(following_id) FROM followrelationships WHERE user_id = ? ;";
		//Query to retrieve number of followers the user has
		String sqlQueryFollower = "SELECT COUNT(user_id) FROM followrelationships WHERE following_id = ? ;";
		//Query amount of tweets
		String sqlQueryTweet = "SELECT COUNT(tweetid) FROM tweets WHERE user_tweet_id = ?;";
		
		try {
			try {
				//Set up prepared statements with the user id
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.setInt(1, id);
				pmst2 = Conn.prepareStatement(sqlQueryFollowing);
				pmst2.setInt(1, id);
				pmst3 = Conn.prepareStatement(sqlQueryFollower);
				pmst3.setInt(1,id);
				pmst4 = Conn.prepareStatement(sqlQueryTweet);
				pmst4.setInt(1,id);
				
			} 
			catch (Exception et)
			{
				//Prints that prepared statement can't be created 
				System.out.println("Can't create prepared statement");
				return null;
			}
			
			try 
			{
				//Execute query for profile
				rs = pmst.executeQuery();
				
			} 
			catch (Exception et) 
			{
				//Displays query 1 cant be executed
				System.out.println("Can not execute profile query 1 " + et);
				return null;
			}
			
			if (rs.wasNull())
			{
				//Display result set was null
				System.out.println("Result set 1 was null");
			} 
			while (rs.next()) 
			{
				//Gets info from query
				ps = new ProfileStore();
				ps.setName(rs.getString("name"));
				ps.setUsername(rs.getString("username"));
				ps.setBio(rs.getString("bio"));
				ps.setLocation(rs.getString("location"));
				ps.setCountry(rs.getString("country"));

			}
			
			try 
			{
				//Execute query for follower count
				rs2 = pmst2.executeQuery();
				
				
			} 
			catch (Exception et) 
			{
				//Displays that query 3 could not be executed
				System.out.println("Can not execute query 2 " + et);
				return null;
			}
			if (rs2.wasNull()) 
			{
				//Displays that result set 3 was null
				System.out.println("Result set 2 was null");
			} 
			while(rs2.next())
			{
				//Sets up follower count
				ps.setFollowingCount(rs2.getInt("COUNT(following_id)"));
				
			}

			
			try 
			{
				//Execute query for follower count
				rs3 = pmst3.executeQuery();
				
				
			} 
			catch (Exception et) 
			{
				//Displays that query 3 could not be executed
				System.out.println("Can not execute query 3 " + et);
				return null;
			}
			if (rs3.wasNull()) 
			{
				//Displays that result set 3 was null
				System.out.println("Result set 3 was null");
			} 
			while(rs3.next())
			{
				//Sets up follower count
				ps.setFollowerCount(rs3.getInt("COUNT(user_id)"));
				
			}
			
		
		try 
		{
			//Execute query for follower count
			rs4 = pmst4.executeQuery();
			
			
		} 
		catch (Exception et) 
		{
			//Displays that query 4 could not be executed
			System.out.println("Can not execute query 4 " + et);
			return null;
		}
		if (rs4.wasNull()) 
		{
			//Displays that result set 5 was null
			System.out.println("Result set 4 was null");
		} 
		while(rs4.next())
		{
			//Get tweet count
			ps.setTweetCount(rs4.getInt("COUNT(tweetid)"));
			//The profile store is then added to a linked list
			psl.add(ps);
		}
		
	}
	catch(Exception e)
	{
		//Displays an error has occurred in this method
		System.out.println("Error in getProfile() in ProfileModel");
	}
				
		 
		
		
		try 
		{
			//Close connection
			Conn.close();
		} 
		catch (Exception ex) 
		{
			//return null
			return null;
		}
		//Return profile list
		return psl;
	} 
	
	/**
	 * Gets profile information to display in textboxes in the edit profile jsp page
	 * @param u
	 * @return ProfileStore
	 */
	public ProfileStore getProfileEdit(UserStore u)
	{
		//Set up profile store
		ProfileStore ps = null;
		//Set up connection
		Connection conn;
		//Set up result set
		ResultSet rs;
		//Set up prepared statement
		PreparedStatement pmst;
		//Retrieve current logged in user id
		int id = u.getUserid();
		//Query to retrieve info for profile
		String sqlQuery = "SELECT name,username,bio,location,country,email,password,userid FROM users JOIN profile ON (profile.user_id = users.userid) WHERE users.userid = ? ;";
		
		try 
		{
			//Gets connection
			conn = _ds.getConnection();
		} 
		catch (Exception et)
		{
			//Displays no connection in profile model
			System.out.println("No Connection in the Profile Model getProfileEdit()");
			return null;
		}
		
		try {
			try {
				//Set up statements with the user id
				pmst = conn.prepareStatement(sqlQuery);
				pmst.setInt(1, id);
			} 
			catch (Exception et)
			{
				//Prints that prepared statement can't be created 
				System.out.println("Can't create prepare statement getProfileEdit()");
				return null;
			}
			
			try 
			{
				//Execute query for profile
				rs = pmst.executeQuery();
				
			} 
			catch (Exception et) 
			{
				//Displays querys cant be executed
				System.out.println("Can not execute profile query getProfileEdit() " + et);
				return null;
			}
			
			if (rs.wasNull())
			{
				//Display result set was null
				System.out.println("Result set was null getProfileEdit()");
				return null;
			} 
			while (rs.next()) 
			{
				//Gets info from query
				ps = new ProfileStore();
				ps.setName(rs.getString("name"));
				ps.setUsername(rs.getString("username"));
				ps.setBio(rs.getString("bio"));
				ps.setLocation(rs.getString("location"));
				ps.setCountry(rs.getString("country"));
				ps.setPassword(rs.getString("password"));
				ps.setEmail(rs.getString("email"));
				ps.setUserid(rs.getInt("userid"));

			}
		
		}
		catch(Exception e)
		{
			System.out.println("Error in getProfileEdit()");
			return null;
		}
		
		try 
		{
			//Close connection
			conn.close();
		} 
		catch (Exception ex) 
		{
			//return null
			return null;
		}
		//Return profile store
		return ps;
	
}

/**
 * Update users profile when they have edited it from jsp page EditProfile
 * @param u
 * @param ps
 * @throws SQLException
 */
public void updateProfile(UserStore u, ProfileStore ps) throws SQLException
{
	//Set up connection
	Connection conn = null;
	
	//Set up prepared statements
	PreparedStatement pmst;
	PreparedStatement pmst2;
	
	//Retrieve values to be updated
	int id = u.getUserid();
	String names = ps.getName();
	String usernames = ps.getUsername();
	String passwords = ps.getPassword();
	String emails = ps.getEmail();
	String bios = ps.getBio();
	String locations = ps.getLocation();
	String countrys = ps.getCountry();
	
	//Querys to update profile and account
	String query1 = "UPDATE users SET name=?,username=?,password=?,email=? WHERE userid = ?;";
	String query2 = "UPDATE profile SET bio=?,location=?,country=? WHERE user_id=?;";
	
	try
	{
		//Set up connection
		conn = _ds.getConnection();
	} 
	catch (Exception et) 
	{
		//Display no connection available profile model
		System.out.println("No Connection in Profile Model updateProfile()");
		
	}
	
	//Prepare statement with query
	pmst = conn.prepareStatement(query1);
	pmst.setString(1, names);
	pmst.setString(2,usernames);
	pmst.setString(3,passwords);
	pmst.setString(4, emails);
	pmst.setInt(5, id);
	
    //Execute update	
	pmst.executeUpdate();
	
	//Prepare statement 
	pmst2 = conn.prepareStatement(query2);
	pmst2.setString(1,bios);
	pmst2.setString(2, locations);
	pmst2.setString(3,countrys);
	pmst2.setInt(4, id);
	
	try
	{
	//Execute update
	pmst2.executeUpdate();
	}
	catch(Exception e)
	{
		System.out.println("Couldn't execute updateProfile()");
	}


	try
	{
		//close connection
		conn.close();
	}
	catch(Exception e)
	{
		System.out.println("Connection could not close");
	}

	
	
}

/**
 * Deletes tweet from database by id
 * @param id
 */
public void deleteAccount(int id)
{
	Connection Conns = null;
	try 
	{
		//Get connection
		Conns = _ds.getConnection();
	} 
	catch (Exception et) 
	{
		//Display no connection available
		System.out.println("No Connection in Profile Model deleteAccount()");
		
	}
	
	//Set up prepared statements
	PreparedStatement pmst = null;
	
	String query = "DELETE FROM users WHERE userid = ?";
	try
	{
		//Prepare statement with query
		pmst = Conns.prepareStatement(query);
		pmst.setInt(1,id);
	} 
	catch (Exception et)
	{
		System.out.println("Can't create prepare statement deleteAccount()");
		
	}
	
	try 
	{
		pmst.executeUpdate();
	} catch (SQLException e1) 
	{
		System.out.println("Can't execute query deleteAccount()");
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	

	try
	{
		//Close connection
		Conns.close();
	}
	catch(Exception e)
	{
		System.out.println("Connection could not close");
	}
}
}

