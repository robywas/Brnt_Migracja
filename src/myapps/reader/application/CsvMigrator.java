package myapps.reader.application;

import myapps.reader.model.Contact;
import myapps.reader.model.commands.CreateCustomerCommand;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CsvMigrator extends Migrator {

    private BufferedReader bufferedReader;
    private CreateCustomerHandler createCustomerHandler;

    public CsvMigrator(String path) {
        try {
            this.bufferedReader = new BufferedReader
                    (new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void migrate() throws IOException, SQLException {
        String csvLine;
            while ((csvLine = bufferedReader.readLine()) != null) {
                createCustomerHandler = new CreateCustomerHandler();
                CreateCustomerCommand createCustomerCommand = collectDataFromCsvline(csvLine);
                createCustomerHandler.handle(createCustomerCommand);
            }
    }

    private CreateCustomerCommand collectDataFromCsvline(String csvLine) {
        CreateCustomerCommand ccc = new CreateCustomerCommand();
        String[] lineSplit = csvLine.split(",");
        ccc.setName(lineSplit[0]);
        ccc.setSurname(lineSplit[1]);
        ccc.setAge(lineSplit[2]);
        ccc.setCity(lineSplit[3]);
        if (lineSplit.length > 4) {
            Set<Contact> contacts = new HashSet<>();
            for (int i = 4; i < lineSplit.length; i++) {
                contacts.add(new Contact(lineSplit[i]));
            }
            ccc.setContacts(contacts);
        }
        return ccc;
    }



}
