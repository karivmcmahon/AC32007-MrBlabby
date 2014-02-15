package com.tweet.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.tweet.stores.*;

public class TweetModel {
	//Store data source
	private DataSource _ds = null;
	
	//Store int tweetuserid
	int tweetuserid;
	
	public TweetModel()
	{
		
	}
	
	//Set up data source
	public void setDatasource(DataSource _ds)
	{
		this._ds=_ds;
		System.out.println("Set Data Source in Model"+_ds.toString());
	}
	
	/**
	 * Gets tweets for home.jsp timeline
	 * @param u
	 * @return
	 */
	public LinkedList<TweetStore> getTweets(UserStore u)
	{
		//Linked list to store tweets
		LinkedList<TweetStore> psl = new LinkedList<TweetStore>();
		//Connection to store connection
		Connection Conns;
		//Set up tweetstore to store tweets
		TweetStore ps = null;
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
			System.out.println("No Connection in Tweets Model getTweets()");
			return null;
		}
		
		//Set up prepared statements
		PreparedStatement pmst = null;
		PreparedStatement pmst2 = null;
		
		//Set up querys to retrieve tweet information
		String sqlQuery2 = "SELECT name,username,userid FROM users WHERE userid = ? ;";
		String sqlQuery = "SELECT DISTINCT tweets.tweet, tweets.time, tweets.tweetid, tweets.user_tweet_id FROM tweets JOIN users JOIN followrelationships on (followrelationships.user_id = users.userid) WHERE users.userid = ? AND (tweets.user_tweet_id = users.userid OR followrelationships.following_id = tweets.user_tweet_id) GROUP BY tweetid ORDER BY time DESC;";
		
		
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
				System.out.println("Can't create prepare statement getTweets()");
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
				System.out.println("Can not execute query getTweets() " + et);
				return null;
			}
			
			if (rs.wasNull()) 
			{
				//Display result set is null
				System.out.println("Result set was null getTweets()");
				return null;
			} 
			while (rs.next()) 
			{
				//Get and set info from result set
				ps = new TweetStore();
				ps.setTweetid(rs.getInt("tweetid"));
				ps.setTweet(rs.getString("tweet"));
				ps.setTime(rs.getTimestamp("time"));
			    tweetuserid = rs.getInt("user_tweet_id");
			    System.out.println("UID " +  tweetuserid);
			    pmst = Conns.prepareStatement(sqlQuery2);
				pmst.setInt(1,tweetuserid);
			    rs2 = pmst.executeQuery();
			    while(rs2.next())
			    {
			    	ps.setName(rs2.getString("name"));
			    	ps.setUsername(rs2.getString("username"));
			    	ps.setUserid(rs2.getInt("userid"));
			    }
				
			
			    //Add info into linked list
				psl.add(ps);
			}
		} 
		catch (Exception ex)
		{
			System.out.println("Opps, error in query getTweets() " + ex);
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
	 * Get tweets from one certain user
	 * @param u
	 * @return
	 */
	public LinkedList<TweetStore> getOwnTweets(UserStore u)
	{
		//Linked list to store tweets
		LinkedList<TweetStore> psl = new LinkedList<TweetStore>();
		//Connection var to store connection
		Connection Conn;
		TweetStore ps = null;
		//Resultset to store results from querys
		ResultSet rs = null;
		try 
		{
			//Set up connection
			Conn = _ds.getConnection();
		} 
		catch (Exception et)
		{
			//Display connection could not be set up
			System.out.println("No Connection in Tweets Model getOwnTweets()");
			return null;
		}
		//Set up prepared statement
		PreparedStatement pmst = null;
		//Get user id
		int id = u.getUserid();
		
		//Set up query to get specific users tweets
		String sqlQuery = "SELECT tweet,time,name,username FROM tweets JOIN users WHERE userid = ? AND tweets.user_tweet_id = ? ORDER BY time DESC;";
		
		
		try {
			try
			{
				//Prepare statement with user id
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.setInt(1, id);
				pmst.setInt(2, id);
				
			} 
			catch (Exception et)
			{
				System.out.println("Can't create prepare statement getOwnTweets");
				return null;
			}
			
			try 
			{
				//Execute query
				rs = pmst.executeQuery();
				
				
			} 
			catch (Exception et) 
			{
				//Display error if query cant be execueted
				System.out.println("Can not execute query getOwnTweets " + et);
				return null;
			}
			
			if (rs.wasNull()) 
			{
				System.out.println("Result set was null getOwnTweets");
			} 
			while (rs.next()) 
			{
				
				ps = new TweetStore();
				ps.setTweet(rs.getString("tweet"));
				ps.setTime(rs.getTimestamp("time"));
			    ps.setName(rs.getString("name"));
			    ps.setUsername(rs.getString("username"));
				psl.add(ps);
			}
		} 
		catch (Exception ex) 
		{
			System.out.println("Opps, error in query getOwnTweets " + ex);
			return null;
		}

		try {

			Conn.close();
		} 
		catch (Exception ex) 
		{
			System.out.println("Connection could not close");
			return null;
		}
		//Return linked list
		return psl;
	
	
	}
	
	/**
	 * Get tweets based on id
	 * @param u
	 * @return
	 */
	public LinkedList<TweetStore> getTweetsByID(TweetStore t)
	{
		//Linked list to store tweets
		LinkedList<TweetStore> psl = new LinkedList<TweetStore>();
		//Connection var to store connection
		Connection Conn;
		TweetStore ps = null;
		//Resultset to store results from querys
		ResultSet rs = null;
		try 
		{
			//Set up connection
			Conn = _ds.getConnection();
		} 
		catch (Exception et)
		{
			//Display connection could not be set up
			System.out.println("No Connection in Tweets Model getTweetsByID()");
			return null;
		}
		//Set up prepared statement
		PreparedStatement pmst = null;
		//Get user id
		int id = t.getTweetid();
		
		//Set up query to get specific users tweets
		String sqlQuery = "SELECT tweet,time,name,username,user_tweet_id FROM tweets JOIN users WHERE tweetid = ? AND tweets.user_tweet_id = users.userid ORDER BY time DESC;";
		
		
		try {
			try
			{
				//Prepare statement with user id
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.setInt(1, id);

				
			} 
			catch (Exception et)
			{
				System.out.println("Can't create prepare statement getTweetsByID()");
				return null;
			}
			
			try 
			{
				//Execute query
				rs = pmst.executeQuery();
				
				
			} 
			catch (Exception et) 
			{
				//Display error if query cant be execueted
				System.out.println("Can not execute query getTweetsByID() " + et);
				return null;
			}
			
			if (rs.wasNull()) 
			{
				System.out.println("Result set was null getTweetsByID()");
			} 
			while (rs.next()) 
			{
				
				ps = new TweetStore();
				ps.setTweet(rs.getString("tweet"));
				ps.setTime(rs.getTimestamp("time"));
			    ps.setName(rs.getString("name"));
			    ps.setUsername(rs.getString("username"));
				psl.add(ps);
			}
		} 
		catch (Exception ex) 
		{
			System.out.println("Opps, error in query getTweetsByID() " + ex);
			return null;
		}

		try {

			Conn.close();
		} 
		catch (Exception ex) 
		{
			System.out.println("Connection could not close");
			return null;
		}
		//Return linked list
		return psl;
	
	
	}
	
	/**
	 * Get tweets based on id
	 * @param u
	 * @return
	 */
	public LinkedList<TweetStore> getTweetsByUsername(UserStore u)
	{
		//Linked list to store tweets
		LinkedList<TweetStore> psl = new LinkedList<TweetStore>();
		//Connection var to store connection
		Connection Conn;
		TweetStore ps = null;
		//Resultset to store results from querys
		ResultSet rs = null;
		try 
		{
			//Set up connection
			Conn = _ds.getConnection();
		} 
		catch (Exception et)
		{
			//Display connection could not be set up
			System.out.println("No Connection in Tweets Model getTweetsByID()");
			return null;
		}
		//Set up prepared statement
		PreparedStatement pmst = null;
		//Get user id
		String id =  u.getUsername();
		
		
		//Set up query to get specific users tweets
		String sqlQuery = "SELECT tweet,time,name,username,user_tweet_id FROM tweets JOIN users WHERE users.username = ? AND tweets.user_tweet_id = users.userid ORDER BY time DESC;";
		
		
		try {
			try
			{
				//Prepare statement with user id
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.setString(1, id);

				
			} 
			catch (Exception et)
			{
				System.out.println("Can't create prepare statement getTweetsByUsername()");
				return null;
			}
			
			try 
			{
				//Execute query
				rs = pmst.executeQuery();
				
				
			} 
			catch (Exception et) 
			{
				//Display error if query cant be execueted
				System.out.println("Can not execute query getTweetsByUsername() " + et);
				return null;
			}
			
			if (rs.wasNull()) 
			{
				System.out.println("Result set was null getTweetsByUsername()");
			} 
			while (rs.next()) 
			{
				
				ps = new TweetStore();
				ps.setTweet(rs.getString("tweet"));
				ps.setTime(rs.getTimestamp("time"));
			    ps.setName(rs.getString("name"));
			    ps.setUsername(rs.getString("username"));
				psl.add(ps);
			}
		} 
		catch (Exception ex) 
		{
			System.out.println("Opps, error in query getTweetsByUsername() " + ex);
			return null;
		}

		try {

			Conn.close();
		} 
		catch (Exception ex) 
		{
			System.out.println("Connection could not close");
			return null;
		}
		//Return linked list
		return psl;
	
	
	}
	
	/**
	 * Inserts tweets into database when created
	 * @param u
	 * @param t
	 * @throws SQLException
	 */
	public void createTweet(UserStore u,TweetStore t) throws SQLException
	{
		//Set up connection variable
		Connection Conn = null;
		
		//Store tweet information to be inserted
		String tweetss = t.getTweet();
		Timestamp times = t.getTime();
		//Set up result set
		ResultSet rs = null;
		try 
		{
			//Set up connection
			Conn = _ds.getConnection();
		}
		catch (Exception et)
		{
			//Display no connection available in tweets model
			System.out.println("No Connection in Tweets Model createTweet()");
			
		}

		//Set up prepared statement
		PreparedStatement pmst = null;
		//get user id
		int id = u.getUserid();
		
		//Query to insert tweets
		String sqlQuery = "INSERT tweets(tweet,time,user_tweet_id)  VALUES(?,?,?);";
		
		
	
				//Prepare statement then execute it
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.setString(1, tweetss);
				pmst.setTimestamp(2,times);
				pmst.setInt(3,id);
				
				try
				{
					pmst.executeUpdate();
				}
				catch(Exception e)
				{
					System.out.println("Can't execute query createTweet()");
				}
		

	try
	{
		//Close connection
		Conn.close();
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
	public void deleteTweet(int id)
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
			System.out.println("No Connection in Following Model deleteTweet()");
			
		}
		
		//Set up prepared statements
		PreparedStatement pmst = null;
		
		String query = "DELETE FROM tweets WHERE tweetid = ?";
		try
		{
			//Prepare statement with query
			pmst = Conns.prepareStatement(query);
			pmst.setInt(1,id);
		} 
		catch (Exception et)
		{
			System.out.println("Can't create prepare statement deleteTweet()");
			
		}
		
		try 
		{
			pmst.executeUpdate();
		} catch (SQLException e1) 
		{
			System.out.println("Can't execute query deleteTweet()");
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
	
	



