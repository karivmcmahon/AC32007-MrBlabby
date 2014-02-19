package com.tweet.libs;

import java.net.URLDecoder;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class StringSplitter 
{
	
	public StringSplitter()
	{
		
	}
	
	/**
	 * Enables us to split the url
	 * @param request
	 * @return
	 */
	public static String[] SplitRequestPath(HttpServletRequest request){
		String args[] = null;


		StringTokenizer st = SplitString(request.getRequestURI());
		args = new String[st.countTokens()];
		//Lets assume the number is the last argument

		int argv=0;
		while (st.hasMoreTokens ()) {;
			args[argv]=new String();

			args[argv]=st.nextToken();
			try{
				//System.out.println("String was "+URLDecoder.decode(args[argv],"UTF-8"));
				args[argv]=URLDecoder.decode(args[argv],"UTF-8");

			}catch(Exception et){
				System.out.println("Bad URL Encoding"+args[argv]);
			}
			argv++;
			} 

	//so now they'll be in the args array.  
	// argv[0] should be the user directory

		return args;
		}

	  private static StringTokenizer SplitString(String str){
	  		return new StringTokenizer (str,"/");

	  }


}
