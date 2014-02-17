package com.tweet.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.sql.DataSource;

import com.tweet.stores.ProfileStore;
import com.tweet.stores.TweetStore;
import com.tweet.stores.UserStore;

public class FollowingModel {
	//Store data source
	private DataSource _ds = null;
	
	public FollowingModel()
	{
		
	}
	
	//Set up data source
	public void setDatasource(DataSource _ds)
	{
			this._ds=_ds;
			System.out.println("Set Data Source in Model"+_ds.toString());
	}
	
	/**
	 * Returns a linked list of people the user is following
	 * @param u
	 * @return
	 */
	public LinkedList<ProfileStore> getFollowing(UserStore u)
	{
		//Linked list to store those they are following profile info
		LinkedList<ProfileStore> psl = new LinkedList<ProfileStore>();
		//Connection to store connection
		Connection Conns;
		//Set up profile store to store profile info
		ProfileStore ps = null;
		//Result set to store results from querys
		ResultSet rs = null;
		ResultSet rs2 = null;
		//Store user id
		int id = u.getUserid();
		try 
		{
			//Get connection
			Conns = _ds.getConnection();
		} 
		catch (Exception et) 
		{
			//Display no connection available
			System.out.println("No Connection in Following Model getFollowing()");
			return null;
		}
		
		//Set up prepared statements
		PreparedStatement pmst = null;
		PreparedStatement pmst2 = null;
		
		//Set up querys to retrieve following information
		String sqlQuery = "SELECT following_id FROM followrelationships WHERE user_id = ? ;";
		String sqlQuery2 = "SELECT name,username,bio,location,country,userid FROM users JOIN profile WHERE users.userid = ? AND profile.user_id =?;";
		
		
		try 
		{
			try
			{
				//Prepare statement with query
				pmst = Conns.prepareStatement(sqlQuery);
				pmst.setInt(1, id);
			} 
			catch (Exception et)
			{
				System.out.println("Can't create prepare statement getFollowing()");
				return null;
			}
			
			try 
			{
				//execute query
				rs = pmst.executeQuery();
				
				
			} 
			catch (Exception et) 
			{
				//Displays can not execute query
				System.out.println("Can not execute query getFollowing() " + et);
				return null;
			}
			
			if (rs.wasNull()) 
			{
				//Display result set is null
				System.out.println("Result set was null getFollowing()");
				return null;
			} 
			while (rs.next()) 
			{
				//Get and set info from result set
				ps = new ProfileStore();
				int ids = rs.getInt("following_id");
				
			    pmst2 = Conns.prepareStatement(sqlQuery2);
				pmst2.setInt(1,ids);
				pmst2.setInt(2, ids);
			    rs2 = pmst2.executeQuery();
			   
			    while(rs2.next())
			    {
			    	ps.setName(rs2.getString("name"));
			    	ps.setUsername(rs2.getString("username"));
			    	ps.setBio(rs2.getString("bio"));
			    	ps.setLocation(rs2.getString("location"));
			    	ps.setCountry(rs2.getString("country"));
			    	ps.setUserid(rs2.getInt("userid"));
			    }
				
			
			    //Add info into linked list
				psl.add(ps);
			}
		} 
		catch (Exception ex)
		{
			System.out.println("Opps, error in query getFollowing() " + ex);
			return null;
		}


		try 
		{
			//Close connection
			Conns.close();
		} 
		catch (Exception ex) 
		{
			System.out.println("Connection could not be closed");
			return null;
		}
		//Return linked list
		return psl;

	}
	
	/**
	 * Gets users follower information and returns as a list
	 * @param u
	 * @return
	 */
	public LinkedList<ProfileStore> getFollowers(UserStore u)
	{
		//Linked list to store follower info
		LinkedList<ProfileStore> psl = new LinkedList<ProfileStore>();
		//Connection to store connection
		Connection Conns;
		//Set up profile store to store profile info
		ProfileStore ps = null;
		//Result set to store results from querys
		ResultSet rs = null;
		ResultSet rs2 = null;
		//Store user id
		int id = u.getUserid();
		try 
		{
			//Get connection
			Conns = _ds.getConnection();
		} 
		catch (Exception et) 
		{
			//Display no connection available
			System.out.println("No Connection in Following Model getFollowers()");
			return null;
		}
		
		//Set up prepared statements
		PreparedStatement pmst = null;
		PreparedStatement pmst2 = null;
		
		//Set up querys to retrieve follower information
		String sqlQuery = "SELECT user_id FROM followrelationships WHERE following_id = ? ;";
		String sqlQuery2 = "SELECT name,username,bio,location,country FROM users JOIN profile WHERE users.userid = ? AND profile.user_id =?;";
		
		
		try 
		{
			try
			{
				//Prepare statement with query
				pmst = Conns.prepareStatement(sqlQuery);
				pmst.setInt(1, id);
			} 
			catch (Exception et)
			{
				//Display error
				System.out.println("Can't create prepare statement getFollowers()");
				return null;
			}
			
			try 
			{
				//execute query
				rs = pmst.executeQuery();
				
				
			} 
			catch (Exception et) 
			{
				//Displays can not execute query
				System.out.println("Can not execute query getFollowers() " + et);
				return null;
			}
			
			if (rs.wasNull()) 
			{
				//Display result set is null
				System.out.println("Result set was null getFollowers()");
			} 
			while (rs.next()) 
			{
				//Get and set info from result set
				ps = new ProfileStore();
				int ids = rs.getInt("user_id");
				System.out.println("ID s" + ids);
			    
				pmst2 = Conns.prepareStatement(sqlQuery2);
				pmst2.setInt(1,ids);
				pmst2.setInt(2, ids);
			    rs2 = pmst2.executeQuery();
			    while(rs2.next())
			    {
			    	ps.setName(rs2.getString("name"));
			    	ps.setUsername(rs2.getString("username"));
			    	ps.setBio(rs2.getString("bio"));
			    	ps.setLocation(rs2.getString("location"));
			    	ps.setCountry("country");
			    }
				
			
			    //Add info into linked list
				psl.add(ps);
			}
		} 
		catch (Exception ex)
		{
			System.out.println("Opps, error in query getFollowers() " + ex);
			return null;
		}


		try 
		{
			//Close connection
			Conns.close();
		} 
		catch (Exception ex) 
		{
			System.out.println("Connection could not be closed");
			return null;
		}
		//Return linked list
		return psl;

	}
	
	/**
	 * Gets suggestions for user based on who they follow
	 * @param u
	 * @return
	 */
	public LinkedList<ProfileStore> getSuggestions(UserStore u)
	{
		//Linked list to store suggestions info
		LinkedList<ProfileStore> psl = new LinkedList<ProfileStore>();
		//Connection to store connection
		Connection Conns;
		//Set up profile store to store profile info
		ProfileStore ps = null;
		//Result set to store results from querys
		ResultSet rs = null;
		ResultSet rs2 = null;
		//Store user id
		int id = u.getUserid();
		try 
		{
			//Get connection
			Conns = _ds.getConnection();
		} 
		catch (Exception et) 
		{
			//Display no connection available
			System.out.println("No Connection in Following Model getSuggestions()");
			return null;
		}
		
		//Set up prepared statements
		PreparedStatement pmst = null;
		PreparedStatement pmst2 = null;
		
		//Set up querys to retrieve suggestion information
		String sqlQuery = "SELECT following_id FROM followrelationships WHERE user_id IN (SELECT following_id FROM followrelationships WHERE user_id = ? ) AND following_id NOT IN ( SELECT following_id FROM followrelationships WHERE user_id = ?) AND NOT following_id = ?;";
		String sqlQuery2 = "SELECT name,username,bio,location,country,userid FROM users JOIN profile WHERE users.userid = ? AND profile.user_id =?;";
		
		
		try 
		{
			try
			{
				//Prepare statement with query
				pmst = Conns.prepareStatement(sqlQuery);
				pmst.setInt(1, id);
				pmst.setInt(2, id);
				pmst.setInt(3, id);
		
			} 
			catch (Exception et)
			{
				System.out.println("Can't create prepare statement getSuggestions()");
				return null;
			}
			
			try 
			{
				//execute query
				rs = pmst.executeQuery();
				
			} 
			catch (Exception et) 
			{
				//Displays can not execute query
				System.out.println("Can not execute query getSuggestions() " + et);
				return null;
			}
			
			if (rs.wasNull()) 
			{
				//Display result set is null
				System.out.println("Result set was null getSuggestions()");
			} 
			while (rs.next()) 
			{
				//Get and set info from result set
				ps = new ProfileStore();
				int ids = rs.getInt("following_id");
			    pmst2 = Conns.prepareStatement(sqlQuery2);
				pmst2.setInt(1,ids);
				pmst2.setInt(2, ids);
			    rs2 = pmst2.executeQuery();
			    
			    while(rs2.next())
			    {
			    	
			    	ps.setName(rs2.getString("name"));
			    	ps.setUsername(rs2.getString("username"));
			    	ps.setBio(rs2.getString("bio"));
			    	ps.setLocation(rs2.getString("location"));
			    	ps.setCountry(rs2.getString("country"));
			    	ps.setUserid(rs2.getInt("userid"));
			    }
				
			     
			    //Add info into linked list
				psl.add(ps);
			}
		} 
		catch (Exception ex)
		{
			System.out.println("Opps, error in query getFollowing() " + ex);
			return null;
		}


		try 
		{
			//Close connection
			Conns.close();
		} 
		catch (Exception ex) 
		{
			System.out.println("Connection could not be closed");
			return null;
		}
		//Return linked list
		return psl;

	}
	
	/**
	 * Enables user to follow someone
	 * @param id
	 * @param u
	 */
	public void followUser(int id,UserStore u)
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
			System.out.println("No Connection in Following Model followUser()");
			
		}
		
		//Set up prepared statements
		PreparedStatement pmst = null;
		int uid = u.getUserid();
		String query = "INSERT followrelationships(user_id,following_id) VALUES(?,?);";
		
		try
		{
			//Prepare statement with query
			pmst = Conns.prepareStatement(query);
			pmst.setInt(1, uid);
			pmst.setInt(2, id);
		} 
		catch (Exception et)
		{
			System.out.println("Can't create prepare statement followUser()");
			
		}
		
		try 
		{
			//Execute insert
			pmst.executeUpdate();
		} 
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			System.out.println("The statment couldn't execute followUser()");
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
	
	/**
	 * Enables user to unfollow someone
	 * @param id
	 * @param u
	 */
	public void unfollowUser(int id,UserStore u)
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
			System.out.println("No Connection in Following Model unfollowUser()");
			
		}
		
		//Set up prepared statements
		PreparedStatement pmst = null;
		int uid = u.getUserid();
		String query = "DELETE FROM followrelationships WHERE user_id = ? And following_id = ?";
		try
		{
			//Prepare statement with query
			pmst = Conns.prepareStatement(query);
			pmst.setInt(1, uid);
			pmst.setInt(2, id);
		} 
		catch (Exception et)
		{
			System.out.println("Can't create prepare statement unfollowUser()");
			
		}
		
		try 
		{
			//Execute delete
			pmst.executeUpdate();
		} 
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			System.out.println("Can't execute query unfollowUser()");
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
