import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager
{
	private Connection connection;
	private Scanner scanner;
	public AccountManager(Connection connection, Scanner scanner) 
	{
	
		this.connection = connection;
		this.scanner = scanner;
	}
	public void credit_account(long accountno)
	{
		System.out.println("Enter the amount");
		double amount=scanner.nextDouble();
		System.out.println("Enter the pin");
		String pin=scanner.nextLine();
		
		try 
		{
			connection.setAutoCommit(false);
			String query="select balance from accounts where account_number=? AND security_pin=?";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setLong(1, accountno);
			preparedStmt.setString(2, pin);	
			ResultSet result=preparedStmt.executeQuery();
			
			if(result.next())
			{
				  String query1="Update balance=balance + ? from accounts where account_number=?";
				  PreparedStatement preparedStmt1 = connection.prepareStatement(query);
				  preparedStmt1.setDouble(1, amount);
				  preparedStmt1.setLong(2,accountno);
				  int rowsaffected=preparedStmt1.executeUpdate();
				 System.out.println("Amount debited"+amount +"succesfully");
					  connection.commit();
					  connection.setAutoCommit(true);
					  
			}else System.out.println("wrong Pin or account no enterred");
			
				  
				    
			}catch(SQLException e) { e.printStackTrace(); }
			
			
		}
	

	
	public void debit_account(long accountno)
	{
		System.out.println("Enter the amount");
		double amount=scanner.nextDouble();
		System.out.println("Enter the pin");
		String pin=scanner.nextLine();
		
		try 
		{
			connection.setAutoCommit(false);
			String query="select balance from accounts where account_number=? AND security_pin=?";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setLong(1, accountno);
			preparedStmt.setString(2, pin);	
			ResultSet result=preparedStmt.executeQuery();
			
			if(result.next())
			{
			  if(result.getDouble("balance")>=amount)
			  {
				  String query1="Update balance=balance-? from accounts where account_number=?";
				  PreparedStatement preparedStmt1 = connection.prepareStatement(query1);
				  preparedStmt1.setDouble(1, amount);
				  preparedStmt1.setLong(2,accountno);
				  int rowsaffected=preparedStmt1.executeUpdate();
				  if(rowsaffected>0)
				  {
					  System.out.println("Amount debited"+amount +"succesfully");
					  connection.commit();
					  connection.setAutoCommit(true);
					  return;
				
				  }else {
					  System.out.println("transaction failed ");
					  connection.rollback();
					  connection.setAutoCommit(true); }}}
				  
			  else {
					 System.out.println("Insufficeint Balance!"); 
				   }
			  
				 {
				 System.out.println("Invalid pin");
				 }
				  
				  
				    
			}catch(SQLException e) { e.printStackTrace(); }
			
			
		}
	
	  public void checkBalance(long account_number){
	       // scanner.nextLine();
	        System.out.print("Enter Security Pin: ");
	        String security_pin = scanner.nextLine();
	        try{
	            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM Accounts WHERE account_number = ? AND security_pin = ?");
	            preparedStatement.setLong(1, account_number);
	            preparedStatement.setString(2, security_pin);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if(resultSet.next()){
	                double balance = resultSet.getDouble("balance");
	                System.out.println("Balance: "+balance);
	            }else{
	                System.out.println("Invalid Pin!");
	            }
	        }catch (SQLException e){
	            e.printStackTrace();}
	        }
	 public void transfer(long senderaccountno)
	 {
		 System.out.println("Enter the receiver account no");
		 long receiveraccount=scanner.nextLong();
		 System.out.println("Enter the security pin for your account");
		 String pin= scanner.nextLine();
		 System.out.println("Enter the amount");
		 Double amount=scanner.nextDouble();
		
			 try
			 {
			connection.setAutoCommit(false);	 
			if(senderaccountno !=0 && receiveraccount !=0)
		     {
			 String query="select balance from accounts where senderaccountno=? AND securiy=? ";
			 PreparedStatement preparedStmt = connection.prepareStatement(query);
			  preparedStmt.setDouble(1, senderaccountno);
			  preparedStmt.setString(2,pin);
			  ResultSet result=preparedStmt.executeQuery();
			   if(result.next())
			   {
				   double current_balance =result.getDouble("balance");
				   if(current_balance>=amount)
				   {
					   String creditquery="Update Accounts SET balance=balance+? WHERE account_number=?";
					   String debitquery="Update Accounts SET balance=balance-? WEHERE account_number=?";
					   PreparedStatement creditStmt = connection.prepareStatement(query);
					   PreparedStatement debitStmt = connection.prepareStatement(query);
					   creditStmt.setDouble(1, amount);
					   creditStmt.setLong(2, receiveraccount);
					   debitStmt.setDouble(1, amount);
					   debitStmt.setLong(2, senderaccountno);
					   int rowaffected1 = creditStmt.executeUpdate();
					   int rowaffected2 = debitStmt.executeUpdate();
					   if(rowaffected1>0 && rowaffected2>0)
					   {
						   System.out.println("Transaction Scuccesfull !");
						   System.out.println("The Amount " +amount +"is transferred");
						   connection.setAutoCommit(true);
						   return ;
					   }
					    else
					    {  System.out.println("Transaction Failed Try Again!");
					       connection.setAutoCommit(true);
					       connection.rollback();
					    }
					   
					   }
				      else
				      {  System.out.println("Insufficient Balance"); }   
				   
			           }
			           else {  System.out.println("Account number or password is incorret please try again"); }
			  
		               }
						else {System.out.println("information entered incorectly"); }
			 			}
			 			catch(SQLException e)
			  			{
			 			e.printStackTrace();
			  			}
			  
	    }
      }
