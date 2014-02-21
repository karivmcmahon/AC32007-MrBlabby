package com.tweet.libs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.sql.DataSource;

public class conn {
	
	private static final void listContext(Context ctx, String indent) {
		try {
			NamingEnumeration list = ctx.listBindings("");
			while (list.hasMore()) {
				Binding item = (Binding) list.next();
				String className = item.getClassName();
				String name = item.getName();
				System.out.println("" + className + " " + name);
				Object o = item.getObject();
				if (o instanceof javax.naming.Context) {
					listContext((Context) o, indent + " ");
				}
			}
		} catch (NamingException ex) {
			System.out.println("JNDI failure: " + ex);
		}
	}

	/**
	 * Assembles  a DataSource from JNDI.
	 */
	 public DataSource assemble(ServletConfig config) throws ServletException {
		DataSource _ds = null;
		String dataSourceName = config.getInitParameter("data-source");
		System.out.println("Data Source Parameter" + dataSourceName);
		if (dataSourceName == null)
			throw new ServletException("data-source must be specified");
		Context envContext = null;
		try {
			Context ic = new InitialContext();
			System.out.println("initial context " + ic.getNameInNamespace());
			envContext = (Context) ic.lookup("java:/comp/env");
			System.out.println("envcontext  " + envContext);
			listContext(envContext, "");
		} catch (Exception et) {
			throw new ServletException("Can't get contexts " + et);
		}
		// _ds = (DataSource) ic.lookup("java:"+dataSourceName);
		// _ds = (DataSource) ic.lookup("java:comp/env/" );
		try {
			_ds = (DataSource) envContext.lookup(dataSourceName);

			if (_ds == null)
				throw new ServletException(dataSourceName
						+ " is an unknown data-source.");
		} catch (NamingException e) {
			throw new ServletException("Cant find datasource name " +dataSourceName+" Error "+ e);
		}
		 CreateSchema(_ds);
		return _ds;

	}
	 
	// create the schema if it doesn't exist
		private void CreateSchema(DataSource _ds) {
			System.out.println("yello");
			PreparedStatement pmst = null;
			Connection Conn;
			try {
				Conn = _ds.getConnection();
			} catch (Exception et) {
				return;
			}
			System.out.println("Table create");
			String sqlQuery = "CREATE TABLE IF NOT EXISTS users (" +
								"userid int(11) NOT NULL AUTO_INCREMENT," +
								"name varchar(45) NOT NULL," +
								"email varchar(25) NOT NULL," +
								"username varchar(15) NOT NULL," +
								"password varchar(15) NOT NULL," +
								"permission int(11) NOT NULL," +
								"PRIMARY KEY (userid)" +
								") ENGINE=InnoDB;";

			try {
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.executeUpdate();
			} catch (Exception ex) {
				System.out.println("Can not create table "+ex);
				return;
			}
			sqlQuery = "CREATE TABLE IF NOT EXISTS profile (" +
						"profileid int(11) NOT NULL AUTO_INCREMENT," +
						"bio varchar(140) DEFAULT NULL," +
						"location varchar(60) DEFAULT NULL," +
						"country varchar(60) DEFAULT NULL," +
						"user_id int(11) NOT NULL," +
						"PRIMARY KEY (profileid)," +
						"KEY uid_idx (user_id)," +
						"CONSTRAINT uid FOREIGN KEY (user_id) REFERENCES users (userid) ON DELETE CASCADE ON UPDATE CASCADE" +
						") ENGINE=InnoDB;";
			
			try {
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.executeUpdate();
			} catch (Exception ex) {
				System.out.println("Can not create table "+ex);
				return;
			}
			sqlQuery = "CREATE TABLE IF NOT EXISTS tweets (" +
						"tweetid int(11) NOT NULL AUTO_INCREMENT," +
						"tweet varchar(140) DEFAULT NULL," +
						"user_tweet_id int(11) NOT NULL," +
						"time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
						"PRIMARY KEY (tweetid)," +
						"KEY user_tweet_id (user_tweet_id)," +
						"CONSTRAINT tweets_ibfk_1 FOREIGN KEY (user_tweet_id) REFERENCES users (userid) ON DELETE CASCADE ON UPDATE CASCADE" +
						") ENGINE=InnoDB;";
			
			try {
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.executeUpdate();
			} catch (Exception ex) {
				System.out.println("Can not create table "+ex);
				return;
			}
			
			
			sqlQuery = "CREATE TABLE IF NOT EXISTS `followrelationships` (" +
					 "`user_follower_id` int(11) NOT NULL AUTO_INCREMENT," +
					 "`user_id` int(11) NOT NULL," +
					 "`following_id` int(11) NOT NULL," +
					  "PRIMARY KEY (`user_follower_id`)," +
					  "KEY `user_id` (`user_id`)," +
					  "CONSTRAINT `followrelationships_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE," +
					"KEY `following_id`(`following_id`)," +
					"CONSTRAINT  `fid` FOREIGN KEY (`following_id`) REFERENCES `users` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE" +
					") ENGINE=InnoDB;";
			try {
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.executeUpdate();
			} catch (Exception ex) {
				System.out.println("Can not create table "+ex);
				return;
			}
			ResultSet rs=null;
			sqlQuery="Select count(userid) from users as rowcount";
			try {
				pmst = Conn.prepareStatement(sqlQuery);
				rs=pmst.executeQuery();
				if(rs.next()) 
				{
				    int rows = rs.getInt(1);
				    System.out.println("Number of Rows " + rows);
				    if (rows==0)
				    {
				    	sqlQuery="INSERT INTO users (name,email,username,password,permission)"
				    			+ " VALUES ('Andy','Andy@aol.co.uk','andy_uk','p456nh',1),"
				    			+ "('Beyonce','Queenb@hotmail.com','beyonce','blueivy22',1),"
				    			+ "('Tomcat','Tomcat@nestcape.co.uk','tomcat','mn2er3',2),"
				    			+ "('Harry Styles','harrys@hotmail.co.uk','harrys_manutd','1dhs',1),"
				    			+ "('Kim Kardashian','kimk@aol.co.uk','kimk','lmaolol',3),"
				    			+ "('Bill','billandben@yahoo.com','billandben','flowerpot',2);";
						try {
							pmst = Conn.prepareStatement(sqlQuery);
							pmst.executeUpdate();
						} catch (Exception ex) {
							System.out.println("Can not insert names in authors "+ex);
							return;
						}
						sqlQuery="INSERT INTO profile (bio,location,country,user_id)"
								+ "VALUES ('Journalist in the center of London','London','UK',1),"
								+ "('Pop sensation','LA','USA',2),"
								+ "('Contact us with any Tomcat server issues','San Francisco','USA',3),"
								+ "('Member of one direction','London','UK',4),"
								+ "('Owns a make up line','LA','USA',5),"
								+ "('The original flowerpot man','Bristol','UK',6);";
						try {
							pmst = Conn.prepareStatement(sqlQuery);
							pmst.executeUpdate();
						} catch (Exception ex) {
							System.out.println("Can not insert names in sections "+ex);
							return;	
						}
						sqlQuery="INSERT INTO tweets (tweet,user_tweet_id,time)"
								+ "VALUES ('Breaking news : Government cut penisions by 5%',1,'2012-05-01 14:20:00'),"
								+ "('Come see my show in Atlanta',2,'2013-08-01 19:00:00'),"
								+ "('New eyeshadow in stores Monday',5,'2012-08-22 10:00:00'),"
								+ "('Starbucks christmas cups are in',6,'2013-01-01 11:00:00'),"
								+ "('Tomcat tweet 1',3,'2013-02-24 15:30:00'),"
								+ "('London show was amazing',4,'2013-03-01 15:30:00'),"
								+ "('My names Bill',6,'2013-03-12 18:30:00'),"
								+ "('New album out now',2,'2014-01-02 20:30:00'),"
								+ "('Give your opinion on tax increase :',1,'2014-01-22 20:30:00');";
						try {
							pmst = Conn.prepareStatement(sqlQuery);
							pmst.executeUpdate();
						} catch (Exception ex) {
							System.out.println("Can not insert default fault "+ex);
							return;	
						}
						
						sqlQuery="INSERT INTO followrelationships(user_id,following_id)"
								+ "VALUES(1,2),"
								+ "(1,3),"
								+ "(1,6),"
								+ "(2,4),"
								+ "(2,5),"
								+ "(3,1),"
								+ "(3,6),"
								+ "(3,5),"
								+ "(4,2),"
								+ "(5,4),"
								+ "(6,1);";
						
						try {
							pmst = Conn.prepareStatement(sqlQuery);
							pmst.executeUpdate();
						} catch (Exception ex) {
							System.out.println("Can not insert default fault "+ex);
							return;	
						}

				    }
				}

			} catch (Exception ex) {
				System.out.println("Can not select count "+ex);
				return;
			} 


	 

		}
		
		public void createSchema(){
			System.out.println("hello");
			String url = "jdbc:mysql://localhost/";
			Connection conn=null;
			try {
			   Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			   conn = DriverManager.getConnection (url, "root", "Cl1m8t3;");

			}catch (Exception et){
				System.out.println("Can't get conenction to create schema "+et);
				return;
			}
			String sqlcreateSchema="Create database if not exists tweettwookari;";
			try{
				java.sql.Statement statement=conn.createStatement();
				statement.execute(sqlcreateSchema);
				conn.close();
			}catch (Exception et){
				System.out.println("Can not create schema ");
				return;
			}

		}
		
		

}
