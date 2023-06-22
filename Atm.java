/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package atmm;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// ATM.java
// Represents an automated teller machine
public class Atmm 
{
   private boolean userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser
   private DepositSlot depositSlot; // ATM's deposit slot
   private BankDatabase bankDatabase; // account information database
   private int AdminCheck;
   private String userinput = "";
   private int position = 0;
   private static Atmm uniqueinstance;
   Iterator Users =  BankDatabase.createIterator();
  
   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int DEPOSIT = 3;
   private static final int TRANSFER = 4;
   private static final int EXIT = 5;

   // no-argument ATM constructor initializes instance variables
   public Atmm() 
   {
	   
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      
      keypad = new Keypad(); // create keypad 
      
      cashDispenser = new CashDispenser(); // create cash dispenser
      depositSlot = new DepositSlot(); // create deposit slot
      bankDatabase = new BankDatabase(); // create acct info database
   } // end no-argument ATM constructor

   // start ATM 
   public void run()
   {
      // welcome and authenticate user; perform transactions

         // loop while user is not yet authenticated
             
            startlogin(); // authenticate user
         } // end while
        //else
         //performTransactions(); // user is now authenticated 
         //userAuthenticated = false; // reset before next ATM session
         //currentAccountNumber = 0; // reset before next ATM session 
         //screen.displayMessageLine("\nThank you! Goodbye!");
       // end while   
    // end method run

   // attempts to authenticate user against database
   void startlogin() 
   {
	   
	   
	   position = 0;
	   screen.createlogin();
	   userinput = "";
	   
	   authenticate check = new authenticate();
  	   screen.Mainframe.revalidate();
  	 screen.Inputfield2.setText("");
  	   keypad.setbuttons();
  	   addkeypadlisteners();
  	 
  	   screen.Mainframe.add( keypad.addkeypad(), BorderLayout.CENTER);
	   
	   screen.Mainframe.revalidate();
	   keypad.BEnter.addActionListener( check );
	   screen.Mainframe.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	   screen.Mainframe.setSize( 400, 280 ); // set frame size
	   screen.Mainframe.setVisible( true );
	   screen.Mainframe.revalidate();
   }
	
	   // set userAuthenticated to boolean value returned by database
	   public void authenticateuser(int pin){
      userAuthenticated = 
         bankDatabase.authenticateUser(pin);
      
      // check whether authentication succeeded
      if (userAuthenticated)
      {
    	 int accountNumber = bankDatabase.getaccpin(pin);
    	  AdminCheck = bankDatabase.getadmin(pin);
    	  if (AdminCheck == 0){
    	  currentAccountNumber = accountNumber;
    	  screen.Mainframe.getContentPane().removeAll();
    	  screen.Mainframe.revalidate();
    	  createmenu();
    	  screen.Mainframe.add( keypad.addkeypad(), BorderLayout.CENTER);
    	  screen.Mainframe.revalidate();
    	  }
    	  
    	  else
    		  
    	  createAdminGUI();
    	  Iterator UserIterator = BankDatabase.createIterator(); 
    	  Addcheck check = new Addcheck();
    	  Deletecheck check2 = new Deletecheck();
    	  screen.button2.addActionListener(check);
    	  screen.button3.addActionListener(check2);
    	  
         //currentAccountNumber = accountNumber; // save user's account #
         
      } // end if
      else
         screen.messageJLabel3.setText(
             "Invalid account number or PIN. Please try again.");
   } // end method authenticateUser

	   private class authenticate implements ActionListener
	   {
	      public void actionPerformed( ActionEvent e )
	      {
	   	   
	         //int accnum = Integer.parseInt( screen.Inputfield1.getText() );
	        int PIN = Integer.parseInt( screen.Inputfield2.getText() );
	         //Get the PIN from the GUI text field.
	        authenticateuser(PIN);
	      }
	   }
	   private class Addcheck implements ActionListener
	   {
	      public void actionPerformed( ActionEvent e )
	      {
	    	  //Action Listener for adding user.
	   	      BankDatabase.Adduser();
	         
	      }
	   }
	   private class Deletecheck implements ActionListener
	   {
	      public void actionPerformed( ActionEvent e )
	      {
	    	  //Action Listener for deleting a user.
	   	      BankDatabase.Deleteuser(position);
	   	      position = position - 1;
	         
	      }
	   }
	   //creating the main menu GUI
	   public void createmenu(){
		  screen.setSize( 300, 150 );
	    	  balancecheck check1 = new balancecheck();
	    	  Depositcheck check2 = new Depositcheck();
	    	  Withdrawcheck check3 = new Withdrawcheck();
                  Transfercheck check4 = new Transfercheck();
	    	  Exitcheck check5 = new Exitcheck();
	    	  screen.Mainframe.getContentPane().removeAll();
	    	  screen.Mainframe.revalidate();
	    	  //Add the keypad panel to the GUI
	    	  screen.Mainframe.add( keypad.addkeypad(), BorderLayout.CENTER);
	    	  screen.createmenu();
	    	  Account Account1 = bankDatabase.getAccount(currentAccountNumber);
	    	  screen.messageJLabel.setText("Welcome " + Account1.getUsername() + "                                                                                   ");
	    	  
	    	  keypad.B1.addActionListener( check1 );
	    	  keypad.B2.addActionListener(check3);
	    	  keypad.B3.addActionListener(check2);
	    	  keypad.B4.addActionListener(check4);
                  keypad.B5.addActionListener(check5);
	    	  screen.Mainframe.revalidate();
	   }
   // display the main menu and perform transactions
	   //the four below actionlisters are the main menu options.
	   private class balancecheck implements ActionListener
	   {
	      public void actionPerformed( ActionEvent e )
	      {
	    	  userinput = "";
	    	  performTransactions(1);
	      }
	      }
	   
	   private class Depositcheck implements ActionListener
	   {
	      public void actionPerformed( ActionEvent e )
	      {
	    	  userinput = "";
	    	  performTransactions(3);
	      }
	      }
	   
	   private class Withdrawcheck implements ActionListener
	   {
	      public void actionPerformed( ActionEvent e )
	      {
	    	  userinput = "";
	    	  performTransactions(2);
	      }
	      }
           
           private class Transfercheck implements ActionListener
           {
               public void actionPerformed(ActionEvent e)
               {
                   userinput = "";
                   performTransactions(5);
               }
           }
	   
	   private class Exitcheck implements ActionListener
	   {
	      public void actionPerformed( ActionEvent e )
	      {
//	    	  startlogin();
                  System.exit(0);
	      }
	      }
	   
	   
	   private void performTransactions(int a) 
	   {
		        
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      
     
               currentTransaction = 
                  createTransaction(a);
               keypad.setbuttons();
          	 addkeypadlisteners();
          	 
           
           userinput = "";
           screen.Inputfield2.setText(userinput);
               currentTransaction.execute();
               
               
               
               
               Backcheck Back = new Backcheck();
               screen.Exit.addActionListener(Back);
               screen.Mainframe.revalidate();
               
	   }
	   public class Backcheck implements ActionListener
	   {
	      public void actionPerformed( ActionEvent e )
	      {
	    	  //This takes the user back to the main menu.
	    	  
	    	  
	        createmenu();
	        screen.Mainframe.add( keypad.addkeypad(), BorderLayout.CENTER);
	        screen.Mainframe.revalidate();
	        userinput="";
	    	  screen.Inputfield2.setText(userinput);
	      }
	   }

   
   private Transaction createTransaction(int type)
   {
      Transaction temp = null; // temporary Transaction variable
      screen.getContentPane().removeAll();
      screen.revalidate();
      
      // determine which type of Transaction to create     
      
         if(type == 1) // create new BalanceInquiry transaction
            temp = new BalanceInquiry(
               currentAccountNumber, screen, bankDatabase);
                 else if(type == 2)// create new Withdrawal transaction
            temp = new Withdrawal(currentAccountNumber, screen, 
               bankDatabase, keypad, cashDispenser);
         else if(type == 3){ // create new Deposit transaction
        	 screen.setSize( 500, 300 );
            temp = new Deposit(currentAccountNumber, screen, 
               bankDatabase, keypad, depositSlot);}
       // end switch

      return temp; // return the newly created object
   }
    // end method createTransaction
   
   //This creates the 'admin' screen if the Isadmin field is set to 1.
   public void createAdminGUI(){
	   
	   screen.Mainframe.getContentPane().removeAll();
	   Nextcheck Ncheck = new Nextcheck();
	   Prevcheck Pcheck = new Prevcheck();
	   Exitcheck check4 = new Exitcheck();
	   screen.Mainframe.revalidate();
	   screen.createAdminpage();
	   screen.button1.addActionListener(Ncheck);
	   screen.button4.addActionListener(Pcheck);
	   screen.Exit.addActionListener(check4);
	   screen.Mainframe.revalidate();
	   
   }
   
   
   //This code adds action listeners to the keypad.
   public void addkeypadlisteners(){
	   BCheck BC = new BCheck();
	   BClear BC1 = new BClear();
	   keypad.B1.addActionListener(BC);
	  	keypad.B2.addActionListener(BC);
	  	keypad.B3.addActionListener(BC);
	  	keypad.B4.addActionListener(BC);
	  	keypad.B5.addActionListener(BC);
	  	keypad.B6.addActionListener(BC);
	  	keypad.B7.addActionListener(BC);
	  	keypad.B8.addActionListener(BC);
	  	keypad.B9.addActionListener(BC);
	  	keypad.B0.addActionListener(BC);
	  	keypad.BClear.addActionListener(BC1);
   }
   
   //This code checks what button was pressed on the keypad.
   public class BCheck implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
    	  JButton b = (JButton)e.getSource();
		String label = b.getLabel();
        userinput = userinput + label;
        //update the text field using the user's input.
        screen.Inputfield2.setText(userinput);
        
      }
   }
   public class BClear implements ActionListener
   {
      public void actionPerformed( ActionEvent e)
      {
    	  //Clear the input field.
    	  userinput = "";
    	  screen.Inputfield2.setText(userinput);
      }
      }
   
   //Action listener used for the literator pattern
   public class Nextcheck implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
    	  
		IterateUser(BankDatabase.createIterator()); 
      }
      }
 //Action listener used for the literator pattern
   public class Prevcheck implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
    	  
		prevIterateUser(BankDatabase.createIterator()); 
      }
      }
   
 
   
 //Action listener used for the literator pattern
   public void IterateUser(Iterator Iterator){
	  if(Iterator.hasNext(position) == true) {
		  position = position + 1;
		  //Display the current user in the GUI message labels.
		   Account AccountItem = (Account)Iterator.next(position);
			screen.messageJLabel2.setText("Username: " + AccountItem.getUsername());
			screen.messageJLabel3.setText("Avaliable Balance: " + AccountItem.getAvailableBalance());
			screen.messageJLabel4.setText("Avaliable Balance: " + AccountItem.getTotalBalance());
			}
	  
		
   }
 //Action listener used for the literator pattern
   public void prevIterateUser(Iterator Iterator){
		  if(Iterator.hasPrev(position) == true) {
			  position = position - 1;
			   Account AccountItem = (Account)Iterator.next(position);
				screen.messageJLabel2.setText("Username: " + AccountItem.getUsername());
				screen.messageJLabel3.setText("Avaliable Balance: " + AccountItem.getAvailableBalance());
				screen.messageJLabel4.setText("Avaliable Balance: " + AccountItem.getTotalBalance());
				
				
				}
		  
			
	   }
   
//Code used for the Singleton pattern.
public static Atmm getinstance() {
	if (uniqueinstance == null) {
		uniqueinstance = new Atmm();
	}
	return uniqueinstance;
}
   // main method creates and runs the ATM
   public static void main(String[] args)
   {
	  
      Atmm theATM = Atmm.getinstance();
      
      theATM.run();
   } // end main

}

 class Keypad 
{
   private Scanner input; // reads data from the command line
   private String userinput;
   public static JButton B1;
   public static JButton B2;
   public static JButton B3;
   public static JButton B4;
   public static JButton B5;
   public JButton B6;
   public JButton B7;
   public JButton B8;
   public JButton B9;
   public JButton B0;
   public JButton BClear;
   public JButton BEnter;
                        
   // no-argument constructor initializes the Scanner
   public Keypad()
   {
      input = new Scanner(System.in);    
   } // end no-argument Keypad constructor

   // return an integer value entered by user 
   public int getInput()
   {
      return input.nextInt(); // we assume that user enters an integer  
   } // end method getInput
  
   public void setbuttons(){
	   
	   B1 = new JButton("1");
	   B1.setText("1");
	   B2 = new JButton("2");
	   B3 = new JButton("3");
	   B4 = new JButton("4");
	   B5 = new JButton("5");
	   B6 = new JButton("6");
	   B7 = new JButton("7");
	   B8 = new JButton("8");
	   B9 = new JButton("9");
	   B0 = new JButton("0");
	   BClear = new JButton("Clear");
	   BEnter = new JButton("Enter");
			   
   }
   
   public JPanel addkeypad(){
	   JPanel panel = new JPanel();
	   panel.setPreferredSize(new Dimension(180, 160));
	   panel.setBackground(Color.gray);
	   panel.setLayout(new FlowLayout()); 
	   panel.add(B1);
	   panel.add(B2);
	   panel.add(B3);
	   panel.add(B4);
	   panel.add(B5);
	   panel.add(B6);
	   panel.add(B7);
	   panel.add(B8);
	   panel.add(B9);
	   panel.add(BClear);
	   panel.add(B0);
	   panel.add(BEnter);
	   
	   return panel;
   }
   
   public String userinput(){
	   return userinput();
   }
   
   public void resetuserinput(){
	   userinput = "";
   }
   
} // end class Keypad  

 class CashDispenser 
{
   // the default initial number of bills in the cash dispenser
   private final static int INITIAL_COUNT = 500;
   private int count; // number of $20 bills remaining
   
   // no-argument CashDispenser constructor initializes count to default
   public CashDispenser()
   {
      count = INITIAL_COUNT; // set count attribute to default
   } // end CashDispenser constructor

   // simulates dispensing of specified amount of cash
   public void dispenseCash(int amount)
   {
      int billsRequired = amount / 20; // number of $20 bills required
      count -= billsRequired; // update the count of bills
   } // end method dispenseCash

   // indicates whether cash dispenser can dispense desired amount
   public boolean isSufficientCashAvailable(int amount)
   {
      int billsRequired = amount / 20; // number of $20 bills required

      if (count >= billsRequired )
         return true; // enough bills available
      else 
         return false; // not enough bills available
   } // end method isSufficientCashAvailable
} // end class CashDispenser

 class Account 
{
   private int accountNumber; // account number
   private int pin; // PIN for authentication
   private double availableBalance; // funds available for withdrawal
   private double totalBalance; // funds available + pending deposits
   private int admin;
   private String username;

   // Account constructor initializes attributes
   public Account(String Username, int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance, int isadmin)
   {
      setUsername(Username);
      setAccountNumber(theAccountNumber);
      setPin(thePIN);
      setAvailableBalance(theAvailableBalance);
      setTotalBalance(theTotalBalance);
      setAdmin(isadmin);
   } // end Account constructor

   // determines whether a user-specified PIN matches PIN in Account
   public boolean validatePIN(int userPIN)
   {
      if (userPIN == getPin())
         return true;
      else
         return false;
   } // end method validatePIN
   
   // returns available balance
   public double getAvailableBalance()
   {
      return availableBalance;
   } // end getAvailableBalance

   // returns the total balance
   public double getTotalBalance()
   {
      return totalBalance;
   } // end method getTotalBalance

   // credits an amount to the account
   public void credit(double amount)
   {
      setTotalBalance(getTotalBalance() + amount); // add to total balance
   } // end method credit

   // debits an amount from the account
   public void debit(double amount)
   {
      setAvailableBalance(getAvailableBalance() - amount); // subtract from available balance
      setTotalBalance(getTotalBalance() - amount); // subtract from total balance
   } // end method debit

   // returns account number
   public int getAccountNumber()
   {
      return accountNumber;  
   } // end method getAccountNumber
   
   public int getISadmin()
   {
	   return getAdmin();  
   }
   
   public int GetPin(){
	   return getPin();
   }

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public void setAccountNumber(int accountNumber) {
	this.accountNumber = accountNumber;
}

public int getPin() {
	return pin;
}

public void setPin(int pin) {
	this.pin = pin;
}

public void setAvailableBalance(double availableBalance) {
	this.availableBalance = availableBalance;
}

public void setTotalBalance(double totalBalance) {
	this.totalBalance = totalBalance;
}

public int getAdmin() {
	return admin;
}

public void setAdmin(int admin) {
	this.admin = admin;
}  
} // end class Account

 class AccountFactory extends Account {

   //This code is used for creating additional accounts to add to the database.
	public AccountFactory(String Username, int theAccountNumber, int thePIN, double theAvailableBalance,
			double theTotalBalance, int isadmin) {
		super(Username, theAccountNumber, thePIN, theAvailableBalance, theTotalBalance, isadmin);
		setUsername(Username);
		setAccountNumber(theAccountNumber);
		setPin(thePIN);
		setAvailableBalance(theAvailableBalance);
	      setTotalBalance(theTotalBalance);
	      setAdmin(isadmin);
	}
	}  //ends class accountfactory

 interface Iterator {
	//This is the interface for the Iterator pattern.
		boolean hasNext(int position);
		Object next(int position);
		boolean hasPrev(int position);
	
}  //ends interface iterator

 class AccountIterator implements Iterator {
    ArrayList<Account> accounts;
    
    public AccountIterator(ArrayList<Account> accounts2) {
		this.accounts = accounts2;
	} //This obtains an already existing ArrayList for use within this class.
    
    
    //This function returns true if the ArrayList has a space next to the current one
	public boolean hasNext(int position) {
		if (position >= accounts.size()) {
			return false;
		} else {
			return true;
		}
	}
	

	@Override //This function iterates to the next position in the ArrayList.
	public Object next(int position) {
		Account AccountItem = accounts.get(position);
		return AccountItem;
	}


	@Override
	//This function checks when the position is at 0, and prevents the user from going back even further. 
	public boolean hasPrev(int position) {
		if(position == 0)
		return false;
		else
			return true;
	}
}     //ends class account iterator

 class Screen extends JFrame
{
	public JFrame Mainframe;
	 public static JTextField Inputfield1;
	 public static JTextField Inputfield2;
	 public static JTextField Inputfield3;
	 public static JTextField Inputfield4;
	 public JLabel messageJLabel; 
	 public JLabel messageJLabel2; // displays message of game status
	 public JLabel messageJLabel3;
	 public JLabel messageJLabel4;
	 public JLabel messageJLabel5;
	 public JLabel messageJLabel8;
	 public JLabel messageJLabel9;
	 public JLabel messageJLabel10;
	 public JButton loginbutton; // creates new game
	 public JButton button1;
	 public JButton button2;
	 public JButton button3;
	 public JButton button4;
	 public JButton button5;
	 public JButton Exit;
	 public int accnum = 0;
	 public int PIN = 0;
	public JLabel messageJLabel6;
	public JLabel messageJLabel7;
	 
	
   // displays a message without a carriage return
   public void displayMessage(String message) 
   {
      System.out.print(message); 
   } // end method displayMessage

   // display a message with a carriage return
   public void displayMessageLine(String message) 
   {
      System.out.println(message);   
   } // end method displayMessageLine

   // display a dollar amount
   public void displayDollarAmount(double amount)
   {
      System.out.printf("$%,.2f", amount);   
   } // end method displayDollarAmount 
   
   //create the login GUI
public void createlogin() {
	
    Mainframe = new JFrame("ATM");
    messageJLabel4 = new JLabel("Insert your credit/debit card then                             ");
	messageJLabel = new JLabel("  Enter your PIN number:    ");
	
	 Inputfield1 = new JTextField( 10 );
	 
	 messageJLabel2 = new JLabel(" 														                  ");
	 Inputfield2 = new JTextField( 10 );
	 loginbutton = new JButton("Login");
	 messageJLabel3 = new JLabel("");
	 Mainframe.setLayout( new FlowLayout() ); // set layout
	 Mainframe.add(messageJLabel4);
	 Mainframe.add( messageJLabel ); // add first prompt
	 
	 Mainframe.add( Inputfield2 );
	 Mainframe.add( messageJLabel2 );
	 //Mainframe.add(loginbutton);
	  // add message label 
	 Mainframe.add(messageJLabel3);
	 Inputfield2.setEditable(false);
	 Mainframe.repaint();

	
}
//create the main menu GUI
public void createmenu(){
	Mainframe.getContentPane().removeAll();
	messageJLabel = new JLabel("Welcome");
	messageJLabel2 = new JLabel("1 - Balance");
	messageJLabel3 = new JLabel("2 - Withdrawal");
	messageJLabel4 = new JLabel("3 - Deposit");
        messageJLabel5 = new JLabel("4 - Transfer");
	messageJLabel6 = new JLabel("5 - Exit");
	Mainframe.setLayout( new FlowLayout() ); // set layout
	Mainframe.add(messageJLabel);
	Mainframe.add( messageJLabel2 ); // add first prompt
	Mainframe.add( messageJLabel3 ); // add second prompt
	Mainframe.add( messageJLabel4 ); // add message label 
	Mainframe.add( messageJLabel5 );
        Mainframe.add( messageJLabel6 );
	Mainframe.repaint();
}

//create the Balance GUI
public void creatBalanceGUI(){
	Mainframe.getContentPane().removeAll();
	messageJLabel = new JLabel("Balance Information:        ");
	messageJLabel2 = new JLabel("Avaliable Balance:");
	messageJLabel3 = new JLabel("Total Balance:");
	Exit = new JButton("Back");
	Mainframe.setLayout( new FlowLayout() );
	Mainframe.add(messageJLabel);
	Mainframe.add(messageJLabel2);
	Mainframe.add(messageJLabel3);
	Mainframe.add(Exit);
	Mainframe.repaint();
}

//Create the withdraw GUI
public void createWithdrawGUI(){
	Mainframe.getContentPane().removeAll();
	Mainframe.revalidate();
	messageJLabel = new JLabel("Withdraw Menu:                       ");
	messageJLabel2 = new JLabel("1 - $20 ");
	messageJLabel3 = new JLabel("2 - $40 ");
	messageJLabel4 = new JLabel("3 - $60 ");
	messageJLabel5 = new JLabel("4 - $100 ");
	messageJLabel6 = new JLabel("5 - $200 ");
	messageJLabel7 = new JLabel("            Choose an amount to withdraw");
	Exit = new JButton("Cancel");
	Mainframe.setLayout( new FlowLayout() );
	Mainframe.add(messageJLabel);
	Mainframe.add(messageJLabel2);
	Mainframe.add(messageJLabel3);
	Mainframe.add(messageJLabel4);
	Mainframe.add(messageJLabel5);
	Mainframe.add(messageJLabel6);
	Mainframe.add(Exit);
	Mainframe.add(messageJLabel7);
	Mainframe.repaint();
	
}

//Create the Deposit GUI
public void CreateDepositGUI(){
	Mainframe.getContentPane().removeAll();
	messageJLabel2 = new JLabel("Please enter a deposit amount in CENTS");
	messageJLabel3 = new JLabel("");
	Inputfield2 = new JTextField(10);
	Inputfield2.setEditable(false);
	button1 = new JButton("Deposit");
	Exit = new JButton("Cancel");
	Mainframe.add(messageJLabel2);
	Mainframe.add(messageJLabel3);
	Mainframe.add(Inputfield2);
	Mainframe.add(Exit);
	Mainframe.repaint();
}

public void CreateTransferGUI()
{
    Mainframe.getContentPane().removeAll();
    Mainframe.revalidate();
    messageJLabel = new JLabel("Transfer Menu:                       ");
    messageJLabel2 = new JLabel("Please enter the number account no of the beneficiary: ");
    messageJLabel3 = new JLabel("");
    Inputfield2 = new JTextField(10);
    Inputfield2.setEditable(false);
    messageJLabel4 = new JLabel("Please enter the amount to transfer: ");
    messageJLabel5 = new JLabel("");
    Inputfield3 = new JTextField(10);
    Inputfield3.setEditable(false);
    button2 = new JButton("Transfer");
    Exit = new JButton("Cancel");
    Mainframe.add(messageJLabel2);
    Mainframe.add(messageJLabel3);
    Mainframe.add(Inputfield3);
    Mainframe.add(Exit);
    Mainframe.add(button2);
    Mainframe.repaint();
}


public void setGUI(){
	repaint();
}

//Create the admin page GUI
public void createAdminpage(){
	messageJLabel = new JLabel("View Users:");
	messageJLabel2 = new JLabel("Account number:");
	messageJLabel3 = new JLabel("Avaliable Balance:");
	messageJLabel4 = new JLabel("Total Balance:");
	messageJLabel5 = new JLabel("________________________________________________");
	button1 = new JButton("Next");
	button4 = new JButton("Previous");
	Exit = new JButton("Back");
	Inputfield1 = new JTextField(10);
	Inputfield2 = new JTextField(10);
	Inputfield3 = new JTextField(10);
	Inputfield4 = new JTextField(10);
	Mainframe.setLayout( new FlowLayout() );
	messageJLabel6 = new JLabel("Add Account: ");
	messageJLabel7 = new JLabel("User name: ");
	Mainframe.add(messageJLabel);
	messageJLabel8 = new JLabel("          Account number: ");
	Mainframe.add(messageJLabel2);
	messageJLabel10 = new JLabel("                                       PIN: ");
	
	messageJLabel9 = new JLabel("              Balance number: ");
	button2 = new JButton("Add");
	button3 = new JButton("Delete");
	
	
	Mainframe.add(messageJLabel3);
	Mainframe.add(messageJLabel4);
	Mainframe.add(button4);
	Mainframe.add(button1);
	Mainframe.add(button3);
	Mainframe.add(messageJLabel5);
	Mainframe.add(messageJLabel6);
	Mainframe.add(messageJLabel7);
	Mainframe.add(Inputfield1);
	Mainframe.add(messageJLabel8);
	Mainframe.add(Inputfield2);
	Mainframe.add(messageJLabel10);
	Mainframe.add(Inputfield4);
	Mainframe.add(messageJLabel9);
	Mainframe.add(Inputfield3);
	
	Mainframe.add(button2);
	
	Mainframe.add(Exit);
	Mainframe.repaint();
}
} // end class Screen

 class BankDatabase
{
   static ArrayList<Account> accounts = new ArrayList<Account>() ; // array of Accounts
   
   // no-argument BankDatabase constructor initializes accounts
   public BankDatabase()
   {
      //The original array has been changed into an arraylist, this makes it easier to add/delete from the database
      Account accounts1 = new Account("Raj", 12345, 11111, 1000.0, 1200.0, 0);
      Account accounts2 = new Account("Priya", 98765, 22222, 200.0, 200.0, 0);
      Account accounts3 = new Account("Roy", 19234, 33333, 200.0, 200.0, 0);
      Account accounts4 = new Account("Manager", 99999, 00000, 0, 0, 1);
      accounts.add(accounts1);
      accounts.add(accounts2);
      accounts.add(accounts3);
      accounts.add(accounts4);
   } // end no-argument BankDatabase constructor
   
   // retrieve Account object containing specified account number
   public Account getAccount(int accountnumber)
   {
      // loop through accounts searching for matching account number
      for (Account currentAccount : accounts)
      {
         // return current account if match found
         if (currentAccount.getAccountNumber() == accountnumber)
            return currentAccount;
      } // end for

      return null; // if no matching account was found, return null
   } // end method getAccount
   
   private Account getAccountpin(int PIN)
   {
      // loop through accounts searching for matching account number
      for (Account currentAccount : accounts)
      {
         // return current account if match found
         if (currentAccount.GetPin() == PIN)
            return currentAccount;
      } // end for

      return null; // if no matching account was found, return null
   } //

   // determine whether user-specified account number and PIN match
   // those of an account in the database
   public boolean authenticateUser(int userPIN)
   {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccountpin(userPIN);

      // if account exists, return result of Account method validatePIN
      if (userAccount != null)
         return userAccount.validatePIN(userPIN);
      else
         return false; // account number not found, so return false
   } // end method authenticateUser

   // return available balance of Account with specified account number
   public double getAvailableBalance(int userAccountNumber)
   {
      return getAccount(userAccountNumber).getAvailableBalance();
   } // end method getAvailableBalance

   // return total balance of Account with specified account number
   public double getTotalBalance(int userAccountNumber)
   {
      return getAccount(userAccountNumber).getTotalBalance();
   } // end method getTotalBalance

   // credit an amount to Account with specified account number
   public void credit(int userAccountNumber, double amount)
   {
      getAccount(userAccountNumber).credit(amount);
   } // end method credit

   // debit an amount from Account with specified account number
   public void debit(int userAccountNumber, double amount)
   {
      getAccount(userAccountNumber).debit(amount);
   } // end method debit
   public int getadmin(int userAccountNumber)
   {
	   return getAccountpin(userAccountNumber).getISadmin();  
   }
   
   public static Iterator createIterator() {
		return new AccountIterator(accounts);
	}
   
  public int getaccpin(int PIN){
	   for (Account currentAccount : accounts)
	      {
	         // return current account if match found
	         if (currentAccount.GetPin() == PIN)
	            return currentAccount.getAccountNumber();
	         else
	        	 return 1;
	      } // end for
	return PIN;
   }
   
  public static void Adduser(){
	  String name = Screen.Inputfield1.getText();
	  int accountnumber = Integer.parseInt( Screen.Inputfield2.getText() );
	  int pin = Integer.parseInt( Screen.Inputfield4.getText() );
	  int balance = Integer.parseInt( Screen.Inputfield3.getText() );
	  
	  Account newaccount = new Account(name, accountnumber, pin, balance, balance, 0);
	 accounts.add(newaccount);
	 
	 Screen.Inputfield1.setText("");
	 Screen.Inputfield2.setText("");
	 Screen.Inputfield3.setText("");
	 Screen.Inputfield4.setText("");
  }

public static void Deleteuser(int position) {
	accounts.remove(position);
	
}   
} // end class BankDatabase

 abstract class Transaction
{
   private int accountNumber; // indicates account involved
   protected Screen screen; // ATM's screen
   private BankDatabase bankDatabase; // account info database

   // Transaction constructor invoked by subclasses using super()
   public Transaction(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase)
   {
      accountNumber = userAccountNumber;
      screen = atmScreen;
      bankDatabase = atmBankDatabase;
   } // end Transaction constructor

   // return account number 
   public int getAccountNumber()
   {
      return accountNumber; 
   } // end method getAccountNumber

   // return reference to screen
   public Screen getScreen()
   {
      return screen;
   } // end method getScreen

   // return reference to bank database
   public BankDatabase getBankDatabase()
   {
      return bankDatabase;
   } // end method getBankDatabase

   // perform the transaction (overridden by each subclass)
   abstract public void execute();
} // end class Transaction

 class BalanceInquiry extends Transaction
{
   // BalanceInquiry constructor
   public BalanceInquiry(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase)
   {
      super(userAccountNumber, atmScreen, atmBankDatabase);
   } // end BalanceInquiry constructor

   // performs the transaction
   @Override
   public void execute()
   {
      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();

      // get the available balance for the account involved
      double availableBalance = 
         bankDatabase.getAvailableBalance(getAccountNumber());

      // get the total balance for the account involved
      double totalBalance = 
         bankDatabase.getTotalBalance(getAccountNumber());
      
      // display the balance information on the screen
      
      
      screen.creatBalanceGUI();
      screen.messageJLabel2.setText("Avaliable Balance: " + availableBalance);
      screen.messageJLabel3.setText("Total Balance: " + totalBalance);
      screen.Mainframe.revalidate();
      
   } // end method execute 
} // end class BalanceInquiry

 class Withdrawal extends Transaction
{
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser

   // constant corresponding to menu option to cancel
   private final static int CANCELED = 6;

   // Withdrawal constructor
   public Withdrawal(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      CashDispenser atmCashDispenser)
   {
      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);
      
      // initialize references to keypad and cash dispenser
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   } // end Withdrawal constructor

   // perform transaction
   @Override
   public void execute()
   {
       // amount available for withdrawal

      // get references to bank database and screen
      
      // loop until cash is dispensed or the user cancels
      displayMenuOfAmounts();
   }
     public void transaction(int amount){
    	 BankDatabase bankDatabase = getBankDatabase();
         Screen screen = getScreen();
    	 boolean cashDispensed = false; // cash was not dispensed yet
         double availableBalance;
         // check whether user chose a withdrawal amount or canceled
         
            // get available balance of account involved
            availableBalance = 
               bankDatabase.getAvailableBalance(getAccountNumber());
      
            // check whether the user has enough money in the account 
            if (amount <= availableBalance)
            {   
               // check whether the cash dispenser has enough money
               if (cashDispenser.isSufficientCashAvailable(amount))
               {
                  // update the account involved to reflect the withdrawal
                  bankDatabase.debit(getAccountNumber(), amount);
                  
                  cashDispenser.dispenseCash(amount); // dispense cash
                  cashDispensed = true; // cash was dispensed

                  // instruct user to take cash
                  screen.messageJLabel7.setText("\nYour cash has been" +
                     " dispensed. Please take your cash now.");
               } // end if
               else // cash dispenser does not have enough cash
            	   screen.messageJLabel7.setText(
                     "\nInsufficient cash available in the ATM." +
                     "\n\nPlease choose a smaller amount.");
            } // end if
            else // not enough money available in user's account
            {
               screen.messageJLabel7.setText(
                  "\nInsufficient funds in your account." +
                  "\n\nPlease choose a smaller amount."); 
            } // end else
         } // end if
         // end else
     

    // end method execute

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private void displayMenuOfAmounts()
   {
	   
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      screen.createWithdrawGUI();
      screen.Mainframe.add( keypad.addkeypad(), BorderLayout.CENTER);
      withdraw1 check1 = new withdraw1();
      withdraw2 check2 = new withdraw2();
      withdraw3 check3 = new withdraw3();
      withdraw4 check4 = new withdraw4();
      withdraw5 check5 = new withdraw5();
      Keypad.B1.addActionListener(check1);
      Keypad.B2.addActionListener(check2);
      Keypad.B3.addActionListener(check3);
      Keypad.B4.addActionListener(check4);
      Keypad.B5.addActionListener(check5);
      
      
      
      screen.Mainframe.revalidate();
   }
   
   public class withdraw1 implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
        transaction(20);
      }
   }
   public class withdraw2 implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
        transaction(40);
      }
   }
   public class withdraw3 implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
        transaction(60);
      }
   }
   public class withdraw4 implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
        transaction(100);
      }
   }
   public class withdraw5 implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
        transaction(200);
      }
   }
} 

 class Deposit extends Transaction
{
   private double amount; // amount to deposit
   private Keypad keypad; // reference to keypad
   private DepositSlot depositSlot; // reference to deposit slot
   private final static int CANCELED = 0; // constant for cancel option

   // Deposit constructor
   public Deposit(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      DepositSlot atmDepositSlot)
   {
      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);

      // initialize references to keypad and deposit slot
      keypad = atmKeypad;
      depositSlot = atmDepositSlot;
   } // end Deposit constructor

   // perform transaction
   @Override
   public void execute()
   {
	   promptForDepositAmount();
   }
   public void makedeposit(double amount){
      BankDatabase bankDatabase = getBankDatabase(); // get reference
      Screen screen = getScreen(); // get reference
       // get deposit amount from user

      // check whether user entered a deposit amount or canceled
      if (amount != CANCELED)
      {
         // request deposit envelope containing specified amount
         screen.messageJLabel2.setText( "\nPlease insert a deposit envelope containing " + amount);

         // receive deposit envelope
         boolean envelopeReceived = depositSlot.isEnvelopeReceived();

         // check whether deposit envelope was received
         if (envelopeReceived)
         {  
        	 screen.messageJLabel2.setText("\nYour envelope has been " + 
               "received.\nNOTE: The money just deposited will not ");
              screen.messageJLabel3.setText("be available until we verify the amount of any " +
               "enclosed cash and your checks clear.");
            
            // credit account to reflect the deposit
            bankDatabase.credit(getAccountNumber(), amount); 
         } // end if
         else // deposit envelope not received
         {
        	 screen.messageJLabel2.setText("\nYou did not insert an " +
               "envelope, so the ATM has canceled your transaction.");
         } // end else
      } // end if 
      else // user canceled instead of entering amount
      {
    	  screen.messageJLabel2.setText("\nCanceling transaction...");
      } // end else
   } // end method execute

   // prompt user to enter a deposit amount in cents 
   private void promptForDepositAmount()
   {
      Screen screen = getScreen(); // get reference to screen

      // display the prompt
      screen.CreateDepositGUI(); // receive input of deposit amount
      screen.Mainframe.add( keypad.addkeypad(), BorderLayout.CENTER);
      Depositcheck check1 = new Depositcheck();  
      keypad.BEnter.addActionListener( check1 );
      screen.Mainframe.revalidate();
      // check whether the user cancelled or entered a valid amount
      
          // return dollar amount 
      } // end else
   // end method promptForDepositAmount

   private class Depositcheck implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
   	   
         double input = Integer.parseInt( screen.Inputfield2.getText() );
         double amount = input / 100;
        
         makedeposit(amount);
       
      }
   }
}
 // end class Deposit

 class DepositSlot
{
    // indicates whether envelope was received (always returns true,
    // because this is only a software simulation of a real deposit slot)
    public boolean isEnvelopeReceived()
    {
        return true; // deposit envelope was received
    } // end method isEnvelopeReceived
} 



