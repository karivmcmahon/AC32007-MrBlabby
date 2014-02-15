package com.tweet.libs;

import java.sql.Connection;
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
			PreparedStatement pmst = null;
			Connection Conn;
			try {
				Conn = _ds.getConnection();
			} catch (Exception et) {
				return;
			}
			System.out.println("Table create");
			String sqlQuery = "CREATE TABLE IF NOT EXISTS users(" +
					"userid INT NOT NULL AUTO_INCREMENT," +
					"name VARCHAR(45) NOT NULL," +
					"email VARCHAR(25) NOT NULL," +
					"username VARCHAR(15) NOT NULL," +
					"password VARCHAR(15) NOT NULL," +
					 "PRIMARY KEY(userid)" +
				") ENGINE=INNODB;";

			try {
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.executeUpdate();
			} catch (Exception ex) {
				System.out.println("Can not create table "+ex);
				return;
			}
			sqlQuery = "CREATE TABLE IF NOT EXISTS profile(" +
							"profileid INT NOT NULL AUTO_INCREMENT," +
							"bio VARCHAR(140) NULL," +
							"location VARCHAR(60) NULL," +
							"country VARCHAR(60) NULL," +
							"photo BLOB NULL," +
							"PRIMARY KEY(profileid)" +
						") ENGINE=INNODB;";
			
			try {
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.executeUpdate();
			} catch (Exception ex) {
				System.out.println("Can not create table "+ex);
				return;
			}
			sqlQuery = "CREATE TABLE IF NOT EXISTS tweets(" +
	"tweetid INT NOT NULL AUTO_INCREMENT," + "tweet VARCHAR(140) NULL," +
	"user_tweet_id INT NOT NULL," + "time TIMESTAMP NOT NULL," + "PRIMARY KEY(tweetid),"
     +  "FOREIGN KEY(user_tweet_id)" + "REFERENCES users(userid)"+ 	"ON DELETE CASCADE ON UPDATE CASCADE" +
     ") ENGINE=INNODB;";
			
			try {
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.executeUpdate();
			} catch (Exception ex) {
				System.out.println("Can not create table "+ex);
				return;
			}
			
			
			sqlQuery = "CREATE TABLE IF NOT EXISTS followRelationships(" +
							"user_follower_id INT NOT NULL AUTO_INCREMENT," +
							"user_id INT NOT NULL," +
							"follower_id INT NOT NULL," +
							"PRIMARY KEY(user_follower_id)," +
							"FOREIGN KEY(user_id)" +
								"REFERENCES users(userid)" +
								"ON DELETE CASCADE ON UPDATE CASCADE" +
						") ENGINE=INNODB;";
			try {
				pmst = Conn.prepareStatement(sqlQuery);
				pmst.executeUpdate();
			} catch (Exception ex) {
				System.out.println("Can not create table "+ex);
				return;
			}
		/**	ResultSet rs=null;
			sqlQuery="Select count(name) from author as rowcount";
			try {
				pmst = Conn.prepareStatement(sqlQuery);
				rs=pmst.executeQuery();
				if(rs.next()) {
				    int rows = rs.getInt(1);
				    System.out.println("Number of Rows " + rows);
				    if (rows==0){
				    	sqlQuery="INSERT INTO `author` (`name`) VALUES ('Andy'),('Tracey'),('Tom'),('Bill');";
						try {
							pmst = Conn.prepareStatement(sqlQuery);
							pmst.executeUpdate();
						} catch (Exception ex) {
							System.out.println("Can not insert names in authors "+ex);
							return;
						}
						sqlQuery="INSERT INTO `section` (`name`) VALUES ('Cassandra'),('Hadoop'),('Debian');";
						try {
							pmst = Conn.prepareStatement(sqlQuery);
							pmst.executeUpdate();
						} catch (Exception ex) {
							System.out.println("Can not insert names in sections "+ex);
							return;	
						}
						sqlQuery="INSERT INTO `fault` (`summary`,`details`,`author_idauthor`,`section_idsection`) VALUES ('Startup fails on a pi','Because the number of processors returned is zero startup fails','1','1');";
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
			} **/


	 

		}

}
