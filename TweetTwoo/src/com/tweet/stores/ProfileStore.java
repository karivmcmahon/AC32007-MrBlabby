package com.tweet.stores;

import java.sql.Blob;

public class ProfileStore {
	private int profileId;
	private String name;
	private String username;
	private String bio;
	private String location;
	private String country;
	private int followingCount;
	private int followerCount;
	private int tweetCount;
	private String password;
	private String email;
	private byte[] photo;
	private int userid;
	private boolean following = false;
	private int permission;
	public ProfileStore()
	{
		
	}
	public void setPermission(int p)
	{
		permission = p;
	}
	
	public int getPermission()
	{
		return permission;
	}
	
	public void setProfileid(int id)
	{
		int profileId = id;
	}
	
	public void setFollowing(boolean f)
	{
		following = f;
	}
	
	public boolean getFollowing()
	{
		return following;
	}
	public void setUserid(int id)
	{
		 userid = id;
	}

	public void setName(String fullname)
	{
		name=fullname;
	}
	
	public void setUsername(String un)
	{
		username=un;
	}
	
	public void setBio(String biog)
	{
		bio = biog;
	}

	public void setLocation(String loc)
	{
		location = loc;
	}
	
	public void setCountry(String count)
	{
		country = count;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getProfileid()
	{
		return profileId;
	}
	
	public int getUserid()
	{
		System.out.println("Return int " + userid);
		return userid;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getBio()
	{
		return bio;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public String getCountry()
	{
		return country;
	}
	
	public void setFollowingCount(int fc)
	{
		followingCount = fc;
	}
	
	public int getFollowingCount()
	{
		return followingCount;
	}
	
	public void setFollowerCount(int frc)
	{
		followerCount = frc;
	}
	
	public int getFollowerCount()
	{
		return followerCount;
	}
	
	public void setTweetCount(int tc)
	{
		tweetCount = tc;
	}
	
	public int getTweetCount()
	{
		return tweetCount;
	}
	
	public void setPassword(String pw)
	{
		password = pw;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setEmail(String e)
	{
		email = e;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setPhoto(byte[] p)
	{
		photo = p;
	}
	
	public byte[] getPhoto()
	{
		return photo;
	}
	}
	
	
	


