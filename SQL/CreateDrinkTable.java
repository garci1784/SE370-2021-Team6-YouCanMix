/**
 * Created by Jorge Ezequiel Garcia Lopez
 * Software Engineering Student at CSUSM Fall 2021
 */
package SQL;

import JDBC.JDBCUtil;

import java.sql.*;

public class CreateDrinkTable {

    public CreateDrinkTable() {

        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();

            // create sql string
            String tableName = "drinks";
            String SQL = "create table " + tableName + " ( " +
                    "drink_name varchar(20) not null, " +
                    "ingredients varchar(250) not null, " +
                    "rating integer not null, " +
                    "primary key(drink_name))";
            boolean tableExists = tableExistsSQL(conn, tableName);
            Statement stmt = null;
            if (!tableExists)
            {
                try {
                    stmt = conn.createStatement();
                    stmt.executeUpdate(SQL);
                } finally {
                    JDBCUtil.closeStatement(stmt);
                }
                //Commit the transaction
                JDBCUtil.commit(conn);

                System.out.println("Drinks table created");
            }
            else
            {
                System.out.println("Drinks table already created");
            }

        } catch (SQLException e) {
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