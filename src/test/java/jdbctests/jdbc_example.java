package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;

public class jdbc_example {
    String dbUrl = "jdbc:oracle:thin:@23.23.52.36:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void test1() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

//to use other methods absolute,getRow.. I need to make changes on Statement
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet resultSet = statement.executeQuery("Select * from departments");

        //how many rows we have
        resultSet.last();
        int rowCount = resultSet.getRow();
        System.out.println(rowCount);

        //now I need to get back first row again
        resultSet.beforeFirst();

        //print all info
        while (resultSet.next()) {
            System.out.println(resultSet.getString(2));
        }
/////////////////////////////////////////////////////////////
        resultSet = statement.executeQuery("Select * From Countries");

        while (resultSet.next()) {
            System.out.println(resultSet.getString(2));
        }

        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void MetaData() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet resultSet = statement.executeQuery("Select * from employees");

        //get the database related data inside the dbMetadata object
        DatabaseMetaData data = connection.getMetaData();

        System.out.println(data.getUserName()); //it gives HR
        System.out.println(data.getDatabaseProductName()); //Oracle
        System.out.println(data.getDatabaseProductVersion()); //Version
        System.out.println(data.getDriverName()); //Oracle JDBC driver
        System.out.println(data.getDriverVersion());

        //get the resultSet metaDAta
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        //--how many columns and column names
        System.out.println(resultSetMetaData.getColumnCount());
        System.out.println(resultSetMetaData.getColumnName(1)); //important!! column index starts from 1
        System.out.println(resultSetMetaData.getColumnName(2));
        System.out.println(resultSetMetaData.getColumnName(3));
        System.out.println("**********************");
        //print dynamically
        for (int i = 1; i < (resultSetMetaData.getColumnCount()+1); i++) {
            System.out.println(resultSetMetaData.getColumnName(i));
        }


        resultSet.close();
        statement.close();
        connection.close();


    }
}
