/**
 * Created by Jorge Ezequiel Garcia Lopez
 * Software Engineering Student at CSUSM Fall 2021
 */
package SQL;

import JDBC.JDBCUtil;

import java.sql.*;

public class CreateSQLTables {

    public CreateSQLTables() {

        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();

            // create sql strings
            String drinkTableName = "drinks";
            String ingredientsTableName = "ingredients";
            String SQLDrinksTable = "create table " + drinkTableName + " ( " +
                    "drink_name varchar(30) not null, " +
                    //TODO remove ingredients from drink table
                    //"ingredients varchar(30) not null, " +
                    "rating integer not null, " +
                    "primary key(drink_name))";
            String SQLIngredientsTable = "create table " + ingredientsTableName + " ( " +
                    "drink_name varchar(30) not null, " +
                    "ingredients varchar(30) not null, " +
                    "quantity integer not null)";
            boolean drinksTableExists = tableExistsSQL(conn, drinkTableName);
            boolean ingredientsTableExists = tableExistsSQL(conn, ingredientsTableName);
            Statement stmt = null;
            if (!drinksTableExists && !ingredientsTableExists)
            {
                try {
                    stmt = conn.createStatement();
                    stmt.executeUpdate(SQLDrinksTable);
                    stmt.executeUpdate(SQLIngredientsTable);
                } finally {
                    JDBCUtil.closeStatement(stmt);
                }
                //Commit the transaction
                JDBCUtil.commit(conn);

                System.out.println("Drinks table created");
                System.out.println("Ingredients table created");
            }
            else
            {
                System.out.println("Drinks table already created");
                System.out.println("Ingredients table already created");
            }

        }  // end try
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(conn);
        }
    }

    static boolean tableExistsSQL (Connection connection, String tableName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
                + "FROM information_schema.tables "
                + "WHERE table_name = ?"
                + "LIMIT 1;");
        preparedStatement.setString(1, tableName);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }
}