/**
 * Created by Jorge Ezequiel Garcia Lopez
 * Software Engineering Student at CSUSM Fall 2021
 */
package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil
{
    public static Connection getConnection() throws SQLException
    {
        // Construct the connection URL
        String dbURL = "jdbc:mysql://localhost:3306/YouCanMix";
        String userId = "root";
        String password = "$$YouCanMix";

        // get a connection
        Connection conn = DriverManager.getConnection(dbURL, userId, password);

        // Set the auto-commit off
        conn.setAutoCommit(false);

        return conn;
    }

    public static void closeConnection(Connection conn)
    {
        try {
            if (conn != null)
            {
                conn.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void closeStatement(Statement stmt)
    {
        try {
            if (stmt != null)
            {
                stmt.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rs)
    {
        try {
            if (rs != null)
            {
                rs.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void commit (Connection conn)
    {
        try {
            if (conn != null)
            {
                conn.commit();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void rollback (Connection conn)
    {
        try {
            if (conn != null)
            {
                conn.rollback();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
