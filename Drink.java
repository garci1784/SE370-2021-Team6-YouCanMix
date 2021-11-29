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
    private int rating;

    public Drink(String drinkName, String ingredients) {
        this.drinkName = drinkName;
        this.ingredients = ingredients;
        rating = 5;
    }

    public void insertDrink()
    {
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            String SQL = "insert into drinks values (?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(SQL);

            System.out.println("Drink name: " + drinkName +
                                " Ingredients: " + ingredients +
                                " Rating: " + rating);

            stmt.setString(1, drinkName);
            stmt.setString(2, ingredients);
            stmt.setInt(3, rating);
            int successfulUpload = stmt.executeUpdate();
            if (successfulUpload > 0) {
                System.out.println("Num of changes: " + successfulUpload);
                System.out.println("Drink Inserted Successfully into the Database");
            }
            else
                System.out.println("Drink was NOT Inserted into the Database");
            // Commit Transaction
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
}
