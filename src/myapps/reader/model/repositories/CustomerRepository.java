package myapps.reader.model.repositories;

import myapps.reader.infrastructure.DbManager;
import myapps.reader.model.Contact;
import myapps.reader.model.Customer;

import java.sql.*;
import java.util.Set;

public class CustomerRepository {


    public void save(Customer customer) throws SQLException {
        Connection connection = DbManager.getConnection();

        String InsertCustomer = "INSERT INTO CUSTOMERS(NAME, SURNAME, AGE, CITY) values (?,?,?,?)";
        String InsertContacts = "INSERT INTO CONTACTS(ID_CUSTOMER, TYPE, CONTACT) values (?,?,?)";

        PreparedStatement insertCustomerStatement = connection.prepareStatement(InsertCustomer, Statement.RETURN_GENERATED_KEYS);
        insertCustomerStatement.setString(1, customer.getName());
        insertCustomerStatement.setString(2, customer.getSurname());
        if (customer.getAge() != null)
            insertCustomerStatement.setInt(3, customer.getAge());
        else
            insertCustomerStatement.setNull(3, Types.INTEGER);
        insertCustomerStatement.setString(4, customer.getCity());
        insertCustomerStatement.executeUpdate();

        ResultSet rs = insertCustomerStatement.getGeneratedKeys();
        rs.next();
        Integer customerId = rs.getInt("ID");
        connection.commit();
        insertCustomerStatement.close();


        PreparedStatement insertContactStatement = null;
        Set<Contact> contacts = customer.getContacts();

        for (Contact contact : contacts) {
            insertContactStatement = connection.prepareStatement(InsertContacts);
            insertContactStatement.setInt(1, customerId);
            insertContactStatement.setInt(2, contact.getType());
            insertContactStatement.setString(3, contact.getContact());
            insertContactStatement.executeUpdate();
        }
        connection.commit();
        insertCustomerStatement.close();

        connection.close();
    }
}
