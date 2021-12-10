import SQL.CreateSQLTables;
import javax.swing.*;

public class Main{
	//starts the program
	
	
	public static void main(String[] args)throws ClassNotFoundException{
		
		
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
			      	new GUI().setVisible(true);
			}
		});
		//Creates drinks table in MySQL DB
		//CreateSQLTables drinkTable = new CreateSQLTables();
	}
}
