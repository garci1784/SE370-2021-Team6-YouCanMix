package SQL;

import JDBC.JDBCUtil;

import java.sql.*;

public class CreateSQLTables {

    public CreateSQLTables() throws ClassNotFoundException {

        Connection conn = null;
        try {
        	
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = JDBCUtil.getConnection();

            // create sql string
            String drinkTableName = "Drinks"; 
            //String ingredientsTableName = "Ingredients";
            
            String SQL = "create table " + drinkTableName + " ( " +
            		"Drink_Name varchar(30) not null, " +
            		"Ingredients varchar(100) not null, " +
            		"Quantity varchar(100) not null," +
            		"Rating integer not null, " +
                    "primary key(Drink_Name))";
            
            /*String SQLIngredientsTable = "create table " + ingredientsTableName + " ( " +
            		"drink_name varchar(30) not null, " +
                    "ingredients varchar(30) not null, " +
            		"quantity varchar(30) not null";*/
            
            boolean drinksTableExists = tableExistsSQL(conn, drinkTableName);
            //boolean ingredientsTableExists = tableExistsSQL(conn, ingredientsTableName);

            Statement stmt = null;
                        
            if (!drinksTableExists){
                try {
                    stmt = conn.createStatement();
                    stmt.executeUpdate(SQL);
                    //stmt.executeUpdate(SQLIngredientsTable);
                } 
                finally {
                    JDBCUtil.closeStatement(stmt);
                }
                //Commit the transaction
                JDBCUtil.commit(conn);

                System.out.println("Drinks table created");
                //System.out.println("Ingredients table created");

            }
            else{
                System.out.println("Drinks table already created");
                //System.out.println("Ingredients table already created");

            }

        	}//end try
        	catch (SQLException e) {
        		e.printStackTrace();
        	} 
        	finally {
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