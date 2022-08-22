package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class List_of_map_example {

    String dbUrl = "jdbc:oracle:thin:@23.23.52.36:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";


    @Test
    public void ListOfMap() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet resultSet = statement.executeQuery("Select * from employees");

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        System.out.println(resultSetMetaData.getColumnCount());

        //lets do it manually first
        Map<String,Object> row1 = new HashMap<>(); //secondly intentionally I choose object because we do not know whta to put in
        row1.put("first_name" , "Steven");
        row1.put("last_name" , "King");
        System.out.println(row1.toString());

        Map<String,Object> row2 = new HashMap<>();
        row2.put("first_name" , "Nina");
        row2.put("last_name" , "Kochar");
        System.out.println(row2.toString());
        System.out.println(row2.get("first_name"));

        List<Map<String,Object>> queryData = new ArrayList<>();
        queryData.add(row1);
        queryData.add(row2);
        System.out.println(queryData.toString());
        System.out.println(queryData.get(0)); //pointing first row. Do not forget List index no starts from 0


        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void dynamicList () throws SQLException {

        //create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from countries");

        //get the resultset object metadata
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        //list for keeping all rows a map
        List<Map<String,Object>> queryData = new ArrayList<>();

        //number of columns
        int columnCount = resultSetMetaData.getColumnCount();

        //how many rows I have
        int rowNumber = 0 ;

        //loop through each row
        while(resultSet.next()){
            Map<String,Object> row = new HashMap<>();

            for (int i = 1; i <=columnCount; i++) {

                row.put(resultSetMetaData.getColumnName(i),resultSet.getObject(i));

            }

            //add your map to your list
            queryData.add(row);
            rowNumber++;
        }


        //print the result
        for (Map<String, Object> row : queryData) {
            System.out.println(row.toString());
        }

        System.out.println(rowNumber);

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }




}
