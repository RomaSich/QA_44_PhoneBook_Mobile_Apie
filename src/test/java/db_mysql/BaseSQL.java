package db_mysql;

import java.sql.*;
import java.util.Random;

public class BaseSQL {
    static Connection connection;
    static Statement statement;

    static final String URL = "jdbc:mysql://localhost:3306/world";
    static final String USER = "root";
    static final String Password = "Pom7206079@";

    public static void main(String[] args) {
        startConnection();
        try {
            int quantity;
            statement = connection.createStatement();
            int id = new Random().nextInt(100)+20;
           quantity = statement.executeUpdate("INSERT INTO table1 (id, name, family, email)"+
                   "VALUES('"+id+"',rom1,med12");
        ResultSet resultSet = statement.executeQuery("select * from table1");
        while (resultSet.next())
        {
            System.out.print(resultSet.getString("id")+" ");
            System.out.print(resultSet.getString(2)+" ");
            System.out.print(resultSet.getString(3)+" ");
            System.out.println(resultSet.getString("email")+" ");
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    static void startConnection()
    {
        try {
            connection= DriverManager.getConnection(URL,USER,Password);
            if(!connection.isClosed()) {
                System.out.println("create new connection");
                connection.close();
            }
            else
                System.out.println("connection don't create");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
