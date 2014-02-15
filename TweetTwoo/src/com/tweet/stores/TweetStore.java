package com.tweet.stores;
import java.sql.Timestamp;

public class TweetStore {
	
	private int tweetid;
	private String tweet;
	private String username;
	private String name;
	private Timestamp time;
	private int userid;
	
	public TweetStore(){

	}

	public void setTweetid(int id){
		tweetid=id;
	}
	
	public void setUserid(int id)
	{
		userid=id;
	}
	
	public void setTweet(String tweetpost){
		tweet=tweetpost;
	}
	
	public void setName(String fullname)
	{
		name=fullname;
	}
	
	public void setUsername(String uname)
	{
		username=uname;
	}
	
	public void setTime(Timestamp ts)
	{
		time=ts;
	}

	public int getTweetid(){
		return tweetid;
	}
	
	public int getUserid()
	{
		return userid;
	}

	public String getTweet(){
		return tweet;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Timestamp getTime()
	{
		return time;
	}
	
	

}
