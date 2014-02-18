package com.tweet.stores;

public class UserStore {
	
	private int userid;
	private String name;
	private String username;
	private String password;
	private String email;
	private boolean valid;
	public boolean loggedIn = false;
	public int permission;
	public String error = "";
	
	public void setPermission(int p)
	{
		permission = p;
	}
	
	public int getPermission()
	{
		return permission;
	}
	
	public void setError(String e)
	{
		error = e;
	}
	
	public String getError()
	{
		return error;
	}
	
	public void setUserid(int id)
	{
		userid = id;
	}
	
	public void setName(String fullname)
	{
		name = fullname;
	}
	
	public void setUsername(String un)
	{
		username = un;
	}
	
	public void setPassword(String pass)
	{
		password = pass;
	}
	
	public void setEmail(String emails)
	{
		email = emails;
	}
	
	public void setValid(boolean valids)
	{
		valid = valids;
	}
	
	public boolean getValid()
	{
		return valid;
	}
	
	public int getUserid()
	{
		return userid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setLoggedIn(boolean logged)
	{
		loggedIn = logged;
	}
	
	public boolean getLoggedIn()
	{
		System.out.println("logged in " + loggedIn);
		return loggedIn;
	}
	

}
