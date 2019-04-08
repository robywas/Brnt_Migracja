package myapps.reader.model;


import myapps.reader.model.commands.CreateCustomerCommand;

import java.util.HashSet;
import java.util.Set;

public class Customer {

    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String city;
    private Set<Contact> contacts = new HashSet<>();


    public Customer(CreateCustomerCommand cmd) {
        if (!(cmd.getName().isEmpty()))
            this.name = cmd.getName();
        if (!(cmd.getSurname().isEmpty()))
            this.surname = cmd.getSurname();
        if (!(cmd.getAge().isEmpty()))
            this.age = Integer.valueOf(cmd.getAge());
        if (!(cmd.getCity().isEmpty()))
            this.city = cmd.getCity();
        if (cmd.getContacts() != null)
            this.contacts = cmd.getContacts();
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }


    @Override
    public String toString() {

        return "Customer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", contacts=" + contacts.toString() +
                '}';
    }


}
