import JDBC.JDBCUtil;

import java.sql.*;

public class DrinkManager
{
	private int currentSize = 0;//NUM OF DRINKS CURRENTLY WORKING WITH
	private int num_Drinks = 100;//MAX NUM OF DRINKS
	int validation = 0;//VALIDATES IF DRINK HAS BEEN ADDED TO DATABASE
	private Drink[] Drinks = new Drink[num_Drinks];//LIST OF DRINK CURRENTLY WORKING WITH
	//private Drink currentDrink = new Drink();//CURRENT DRINK WORKING WITH
	
	private Connection conn = null;

	//DEFAULT CONSTRUCTOR
	public DrinkManager() {};
	
    //CONNECTS AND INSERTS DRINK INTO THE DATABASE
    public boolean insertDrink(Drink currentDrink) throws ClassNotFoundException
    { 
        try {
        	
        	//SETS UP AND CONNECTS TO THE DATBASE
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = JDBCUtil.getConnection();
            
            //MAKES ENTRY PROMPT
            String SQL = "insert into drinks values (?,?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(SQL);

            //ENTERS DATA INTO PROPER COLUMN
            stmt.setString(1, currentDrink.getDrinkName());
            stmt.setString(2, currentDrink.getIngredients());
            stmt.setString(3, currentDrink.getQuantities());
            stmt.setInt(4, currentDrink.getRating());
            
            //PUSHS/CHECKS THAT DATABASE HAS BEEN UPDATED
            validation = stmt.executeUpdate();
            conn.commit();

        }
        catch (SQLException e)//CATCHES ERROR
        {
            System.out.println(e.getMessage());
            JDBCUtil.rollback(conn);
        }
        finally {//CLOSES THE CURRENT CONNECTION WITH THE DATABASE
            JDBCUtil.closeConnection(conn);
        }
        
        if(validation > 0)return true;//DRINK WAS ADDED TO DATABASE
        
		return false;//DRINK WASN'T ADDED TO DATABASE
    }
    
    public boolean insertRate(Drink currentDrink, int r) throws ClassNotFoundException{
    	try {
        	
        	//SETS UP AND CONNECTS TO THE DATBASE
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = JDBCUtil.getConnection();
            
            //MAKES ENTRY PROMPT
            String SQL = "update Drinks set Rating = ? where Drink_Name = ?";
            PreparedStatement stmt = conn.prepareStatement(SQL);

            //ENTERS DATA INTO PROPER COLUMN
            stmt.setInt(1, r);
            stmt.setString(2, currentDrink.getDrinkName());
            
            //PUSHS/CHECKS THAT DATABASE HAS BEEN UPDATED
            validation = stmt.executeUpdate();
            conn.commit();

        }
        catch (SQLException e)//CATCHES ERROR
        {
            System.out.println(e.getMessage());
            JDBCUtil.rollback(conn);
        }
        finally {//CLOSES THE CURRENT CONNECTION WITH THE DATABASE
            JDBCUtil.closeConnection(conn);
        }

    	if(validation > 0)return true;//RATING WAS ADDED TO DATABASE
        
		return false;//RATING WASN'T ADDED TO DATABASE
    }
    
    //GETS DRINKS FROM THE DATABASE
    public Drink[] getDrinks(String ES)throws ClassNotFoundException{
    	currentSize = 0;
    	try {
    		
    		//SETS UP AND CONNECTS TO THE DATBASE
    		conn = JDBCUtil.getConnection();
	        ResultSet rs;
	    	Statement stmt = conn.createStatement();
	    	rs = stmt.executeQuery(ES);
	    	
	    	while ( rs.next() ) {//WHILE THERE IS ROWS TO BE READ
	    		 Drinks[currentSize] = new Drink(rs.getString("Drink_Name"),
	    				 rs.getString("Ingredients"), rs.getString("Quantity"),
	    				 rs.getInt("Rating"));
	    		 currentSize++;
	    	}
       	
    	}
    	catch (SQLException e) {//CATCHES ERROR
    		e.printStackTrace();
    	}
    	finally {//CLOSES THE CURRENT CONNECTION WITH THE DATABASE
            JDBCUtil.closeConnection(conn);
        }

    	return Drinks;//RETURNS ARRAY FILLED WITH DRINKS
    }
    
    public int getCurrentSize() {//RETURNS THE SIZE OF THE DRINK ARRAY
    	return this.currentSize;
    }
    
    public int getNum_Drinks() {//RETURNS THE SIZE OF THE DRINK ARRAY
    	return this.num_Drinks;
    }
    
}