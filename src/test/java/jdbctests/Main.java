package jdbctests;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String dbUrl = "jdbc:oracle:thin:@23.23.52.36:1521:xe";
        String dbUsername = "hr";
        String dbPassword = "hr";

        //connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);

        //creating statement object
        Statement statement = connection.createStatement();

        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("Select * from regions");

        //move pointer to first row
        resultSet.next(); //move your pointer to the next row

        System.out.println(resultSet.getString("region_name"));
        System.out.println(resultSet.getString(2)); //you can reach by using index number. Index number starts from 1

        resultSet.next();//move pointer second row
        System.out.println(resultSet.getString("region_name"));

        System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2));

        //practical way
        while(resultSet.next()){ //resultSet.next() returns to boolean
            System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2));
            //my pointer in 3rd row because of that it will print 3rd and 4rd
        }

        //***********************************
        resultSet = statement.executeQuery("Select * from employees");

        while(resultSet.next()){
            System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2)  + " - " + resultSet.getString(3));
      }
        //***********************************
        resultSet = statement.executeQuery("Select * from departments");
        while(resultSet.next()){
            System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2)  + " - " + resultSet.getString(3)+ " - " + resultSet.getString(4));
        }


        //close all conncetions
        resultSet.close();
        statement.close();
        connection.close();

    }
}
