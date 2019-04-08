package myapps.reader.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbManager {

    private static final String CREATE_CUSTOMERS_TABLE = "CREATE TABLE IF NOT EXISTS CUSTOMERS(ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255), SURNAME VARCHAR (255), age int null, CITY VARCHAR(255))";
    private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS CONTACTS(ID INT AUTO_INCREMENT PRIMARY KEY, ID_CUSTOMER INT, TYPE INT, CONTACT VARCHAR(50) , foreign key (ID_CUSTOMER) references CUSTOMERS(ID))";

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:./database";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "admin";


    public static void initDatabase() throws SQLException {
        Connection connection = getConnection();
        createTable(connection, CREATE_CUSTOMERS_TABLE);
        createTable(connection, CREATE_CONTACTS_TABLE);
        connection.close();

    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }


    private static void createTable(Connection connection, String createQuery) {
        PreparedStatement createPreparedStatement;

        try {
            createPreparedStatement = connection.prepareStatement(createQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
