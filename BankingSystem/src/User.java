import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User
{
	private Connection connection;
	private Scanner scanner;
	public User(Connection connection, Scanner scanner) {
		
		this.connection = connection;
		this.scanner = scanner;
	}
	
	
 public void  register() 
 {		
	try { 
	 scanner.nextLine();
	 System.out.println("Full Name");
	 String full_name=scanner.nextLine(); 
	 System.out.println("Email");
	 String email=scanner.nextLine();
	 System.out.println("password");
	 String password=scanner.nextLine();
	 System.out.println("getting ready1");
	 if(UserExists(email))
	 {
		 System.out.println("User already exits for this mail id ");
		 
	     return ;
	     
	 }
	
	 String sql = " insert into Users (full_name, email, password)"
			    + " values (?, ?, ?)";
	 PreparedStatement preparedStmt = connection.prepareStatement(sql);
	  preparedStmt.setString (1, full_name);
	  preparedStmt.setString (2, email);
	  preparedStmt.setString (3, password);
	  int affectedrow=preparedStmt.executeUpdate();
	  if(affectedrow==1)
	  {
		 System.out.println("sucessfully filed");
	  }
	  else
	  {
		  System.out.println("User Not Inserted");
	  }
	}
	catch(Exception e)
	{
		System.out.println("exception found ");
	}
		
	}
 
 public boolean UserExists(String email) {
	    try {
	        String sql = "SELECT * FROM users WHERE email = ?";
	        PreparedStatement preparedStmt = connection.prepareStatement(sql);
	        preparedStmt.setString(1, email);

	        ResultSet resultquery = preparedStmt.executeQuery();
	        if (resultquery.next()) {
	            System.out.println("User exists");
	            return true;
	        } else {
	            System.out.println("User does not exist");
	            return false;
	        }
	    } catch (SQLException e) {
	       
	        e.printStackTrace();
	        return false; // or throw an exception
	    }
	}

    public String login() 
    {
    	
   	 scanner.nextLine(); 
   	 System.out.println("Email");
   	 String email=scanner.nextLine();
   	 System.out.println("password");
   	 String password=scanner.nextLine();
   	 try {
   		 System.out.println("in the user class"); 
   	 String sql ="select * from users where email=? AND password=?";
   	 PreparedStatement preparedStmt = connection.prepareStatement(sql);
   	  preparedStmt.setString (1, email);
   	  preparedStmt.setString (2, password);
   	  ResultSet Result=preparedStmt.executeQuery();
   	 
   	  if(Result.next())
   	  {
   		 System.out.println("sucessfully login");
   		 return email;
   	  }
   	  else
   	  {
   		  System.out.println("User Not Exit");
   		  return null;
   	  }
   	 }
   	 catch(Exception e)
   	 {
   		 System.out.println("Getting Exception");
   		 
   	 }
   	 return null;
    }
}
