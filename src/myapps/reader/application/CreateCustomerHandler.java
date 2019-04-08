package myapps.reader.application;

import myapps.reader.model.Customer;
import myapps.reader.model.Validator;
import myapps.reader.model.commands.CreateCustomerCommand;
import myapps.reader.model.repositories.CustomerRepository;

import java.sql.SQLException;

public class CreateCustomerHandler {

    CustomerRepository customerRepository = new CustomerRepository();

    public CreateCustomerHandler() {
    }


    public void handle(CreateCustomerCommand cmd) throws SQLException {


        validateAgeValue(cmd.getAge());
        Customer customer = new Customer(cmd);
        customerRepository.save(customer);
    }

    private void validateAgeValue(String age) {
        if (((!(age.trim().isEmpty()) && Validator.isNumeric(age)))) {
            throw new RuntimeException("AGE must be numeric");
        }
    }


}
