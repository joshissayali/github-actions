import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp
{

	
			private static final String url="jdbc:mysql://localhost:3306/bankingdetails";
			
			private static final String Username="root";
			
			private static final String Password="y2kk2y#68-";
			
			public static void main(String args[]) throws ClassNotFoundException,SQLException
			{
				try
				{
					Class.forName( "com.mysql.cj.jdbc.Driver");
					System.out.print("succesfully connected");
				}
				catch(ClassNotFoundException e)
				{
					System.out.println("class not found ");
				}
			
			try
			{
				Connection connection=DriverManager.getConnection(url, Username, Password);
				Scanner scanner=new Scanner(System.in);
				User user=new User(connection,scanner);
				Account account=new Account(connection,scanner);
				AccountManager accountmanager=new AccountManager(connection,scanner);
				
				
		
			
		   String email;
		   long accountnumber;
		   while(true)
		   {
			   System.out.println("Welcom to the Banking Statement");
			   System.out.println();
			   System.out.println("1. Register");
			   System.out.println("2. Login");
			   System.out.println("3. Exit");
			   System.out.println("Enter Your Choice");
		       int choice1 = scanner.nextInt();
			   switch(choice1)
			   {
			   case 1: 
				    user.register();
				    break;
				    
			   case 2:
				   email=user.login(); 
				   if(email!=null)
				   {
					   System.out.println();
					   System.out.println("User Loged In");
					   if(!account.accountexits(email))
					   {
						   System.out.println();
						   System.out.println("1. Open A New Bank Account Number");
						   System.out.println("2. Exit");
						  if(scanner.nextInt()==1)
						  {
							   accountnumber=account.generateaccountno();
							   System.out.println("Account Generated Succesfully");
							   System.out.println("Account Number :" +accountnumber);
							 
						  }
						  else if(scanner.nextInt()==2)
						  {
							  System.out.println("Thank You For Using Banking System");
							  break;
						  }
						  else
						  {
							  System.out.println("Enter the Correct Choice");
						  }
						   
					   }
				   
				   else
				   {
					   user.register();
				   }
				   accountnumber = account.getaccountnumber(email);
                   int choice2 = 0;
				  
				   while (choice2 != 5) {
                       System.out.println();
                       System.out.println("1. Debit Money");
                       System.out.println("2. Credit Money");
                       System.out.println("3. Transfer Money");
                       System.out.println("4. Check Balance");
                       System.out.println("5. Log Out");
                       System.out.println("Enter your choice: ");
                       choice2 = scanner.nextInt();
                       switch (choice2) {
                           case 1:
                               accountmanager.debit_account(accountnumber);
                               break;
                           case 2:
                               accountmanager.credit_account(accountnumber);
                               break;
                           case 3:
                               accountmanager.transfer(accountnumber);
                               break;
                           case 4:
                               accountmanager.checkBalance(accountnumber);
                               break;
                           case 5:
                               break;
                           default:
                               System.out.println("Enter Valid Choice!");
                               break;
                       }
				    }
				   }
						   
				   
			   case 3: 
				   System.out.println("Thank You For Using Banking System");
				   System.out.println("Existing System");
				   return ;
			   default: 
				System.out.println("Please Enter The Write Choice");
				     
			}
			   
			   
		   }
	}
			
			catch(SQLException e)
			{
				e.getStackTrace();
			}
			
		   catch(Exception e )
			{
				e.printStackTrace();
			}
			
			
}
}
