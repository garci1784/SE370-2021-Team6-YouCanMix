/**
 * Created by Jorge Ezequiel Garcia Lopez
 * Software Engineering Student at CSUSM Fall 2021
 */
import JDBC.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Drink
{
    private String drinkName;
    private String ingredients;
    private int quantity;
    private int rating;

    public Drink(String drinkName, String ingredients, int quantity) {
        if (drinkName != null && ingredients != null && quantity > 0)
        {
            this.drinkName = drinkName;
            this.ingredients = ingredients;
            this.quantity = quantity;
        }
        rating = 5;
    }

    public void insertDrink()
    {
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            System.out.println("Inside of Drink::insertDrink");
            String SQL = "insert into drinks values (?,?);";
            PreparedStatement stmt = conn.prepareStatement(SQL);

            System.out.println("Drink name: " + drinkName +
                                " Ingredients: " + ingredients +
                                " quantity: " + quantity +
                                " Rating: " + rating);

            stmt.setString(1, drinkName);
            stmt.setInt(2, rating);
            int successfulUpload = stmt.executeUpdate();
            if (successfulUpload > 0) {
                System.out.println("Num of changes: " + successfulUpload);
                System.out.println("Drink Inserted Successfully into the Database");
            }
            else
                System.out.println("Drink was NOT Inserted into the Database");
            // Commit Transaction
            insertIngredients(conn);
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
            stmt.setInt(3, quantity);
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
    }
}
