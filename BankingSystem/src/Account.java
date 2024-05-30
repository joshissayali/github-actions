import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Account {
	private Connection connection;
	private Scanner scanner;
	public Account(Connection connection, Scanner scanner) {
	
		this.connection = connection;
		this.scanner = scanner;
	}
	
	public long open_account(String email)
	{
		if(!accountexits(email))
		{
			scanner.nextLine();
			System.out.println("enter full name");
			String full_name=scanner.nextLine();
			System.out.print("the initial amount");
			double balance= scanner.nextDouble();
			scanner.nextLine();
			System.out.print("Enter Seccurity pin");
			String security=scanner.nextLine();
			String query= "Insert into accounts (account_number,full_name,email,balance,security_pin) values(?,?,?,?,?)";
			try
			{
				long accountno= generateaccountno();
				PreparedStatement preparedStmt = connection.prepareStatement(query);
				preparedStmt.setLong(1, accountno);
				preparedStmt.setString(2, full_name);
				preparedStmt.setString(3, email);
				preparedStmt.setDouble(4, balance);
				preparedStmt.setString(5, security);
				int excutionvalue=preparedStmt.executeUpdate();
				if(excutionvalue==1)
				{
					System.out.println("Account opened succesfuly");
					return accountno;
				}
				else
				{
					System.out.println("Failed to open the new account");
				}
			}
		
		   catch(SQLException e)
		   {
			e.printStackTrace();
		   }
			
			throw new RuntimeException("Account Alreadt Exists");
			
		}
		return 0;
		
		
		
	}
	
	public boolean accountexits(String email)
	{
		try {
		
		String query="Select account_number from customer where email=?";
		 PreparedStatement preparedStmt = connection.prepareStatement(query);
		  preparedStmt.setString (1, email);
		 boolean value=preparedStmt.execute();
		 if(value)
		 {
			 return true;
		 }
		 else return false;
		}
		catch( SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
	public long generateaccountno()
	{
		try {
		Statement statement=connection.createStatement();
		ResultSet resultset=statement.executeQuery("select account_number from accounts orderby desc limit 1");
		if(resultset.next())
		{
			long value=resultset.getLong("account_number");
			return value+1;
		}
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		 return 100000100;
		
	}
	
	public long getaccountnumber(String email)
	{
		try 
		{ 
			String query="Select account_number from customer where email=?";
			 PreparedStatement preparedStmt = connection.prepareStatement(query);
			 ResultSet value=preparedStmt.executeQuery();
			 if(value!=null)
			 {
				 return value.getLong("account_number");
			 }
			 
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		throw new RuntimeException("Account Number Not Found");
	}
	
	
}
