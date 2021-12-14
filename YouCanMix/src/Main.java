import javax.swing.*;
import SQL.CreateSQLTables;


public class Main{
	//starts the program
	
	
	public static void main(String[] args)throws ClassNotFoundException{
		
		
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
			      	new DrinkGUI().setVisible(true);
			}
		});
		//Creates drinks table in MySQL DB
		//CreateSQLTables drinkTable = new CreateSQLTables();
	}
}
