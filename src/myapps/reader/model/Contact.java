package myapps.reader.model;

public class Contact {


    private Long id;
    private Long id_customer;
    private int type;
    private String contact;


    public Contact(String contact) {
        this.contact = contact;
        this.type = validate(contact);

    }

    public Long getId() {
        return id;
    }

    public Long getId_customer() {
        return id_customer;
    }

    public int getType() {
        return type;
    }

    public String getContact() {
        return contact;
    }

    private int validate(String contact) {
        if (Validator.isEmail(contact))
            return 1;
        else if (Validator.isPhone(contact))
            return 2;
        else if (Validator.isJabber(contact))
            return 3;
        else
            return 0;

    }

    @Override
    public String toString() {
        return "Contact{" +
                "type=" + type +
                ", contact='" + contact + '\'' +
                '}';
    }
}
