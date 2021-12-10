import JDBC.JDBCUtil;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class Drink
{
	
    private String drinkName;
    private String ingredients;
    private String quantity;
    private int rating;
    
    public Drink() {
    	this.drinkName = "";
        this.ingredients = "";
        this.quantity = "";
        this.rating = 0;
    }

    public Drink(String drinkName, String ingredients, String quantity) {
        if (drinkName != null && ingredients != null && quantity != null)
        {
            this.drinkName = drinkName;
            this.ingredients = ingredients;
            this.quantity = quantity;
        }
        rating = 5;
    }

    public void insertDrink() throws ClassNotFoundException
    {
        Connection conn = null;
        try {
        	
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = JDBCUtil.getConnection();
            System.out.println("Inside of Drink::insertDrink");
            String SQL = "insert into drinks values (?,?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(SQL);

            System.out.println("Drink name: " + drinkName +
                                " Ingredients: " + ingredients +
                                " quantity: " + quantity +
                                " Rating: " + rating);

            stmt.setString(1, drinkName);
            stmt.setString(2, ingredients);
            stmt.setString(3, quantity);
            stmt.setInt(4, rating);

            int successfulUpload = stmt.executeUpdate();
            if (successfulUpload > 0) {
                System.out.println("Num of changes: " + successfulUpload);
                System.out.println("Drink Inserted Successfully into the Database");
            }
            else
                System.out.println("Drink was NOT Inserted into the Database");
            // Commit Transaction
            //insertIngredients(conn);
            conn.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            JDBCUtil.rollback(conn);
        }
        finally {
            JDBCUtil.closeConnection(conn);
        }
    }
    
    public void getDrink()throws ClassNotFoundException{
    	
    	try {
        Connection conn = JDBCUtil.getConnection();

    	
    	Statement stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery("SELECT * FROM Drinks");
    	while ( rs.next() ) {
            String drinkName = rs.getString("Drink_Name");
            System.out.println(drinkName);
        }
        conn.close();
    	
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    
    }
    
    
    
/*
    private void insertIngredients(Connection conn)
    {
        try
        {
            System.out.println("Inside of Drink::insertIngredients");
            String SQL = "insert into ingredients values (?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(SQL);

            System.out.println("Drink name: " + drinkName +
                    " Ingredients: " + ingredients +
                    " quantity: " + quantity);

            stmt.setString(1, drinkName);
            stmt.setString(2, ingredients);
            stmt.setString(3, quantity);
            int successfulUpload = stmt.executeUpdate();
            if (successfulUpload > 0) {
                System.out.println("Num of changes: " + successfulUpload);
                System.out.println("Ingredients Inserted Successfully into the Database");
            }
            else
                System.out.println("Ingredients were NOT Inserted into the Database");

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            JDBCUtil.rollback(conn);

        }
    }*/
}