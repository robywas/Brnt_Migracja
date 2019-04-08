package myapps.reader.application;

import myapps.reader.model.Contact;
import myapps.reader.model.commands.CreateCustomerCommand;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class XmlMigrator extends Migrator {

    private BufferedReader bufferedReader;
    private CreateCustomerHandler createCustomerHandler;


    public XmlMigrator(String path) {
        try {
            this.bufferedReader = new BufferedReader
                    (new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void migrate() throws IOException, SQLException {
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("<person>")) {
                StringBuilder collectedData = collectData();
                CreateCustomerCommand createCustomerCommand = collectDataFromXml(collectedData);
                createCustomerHandler = new CreateCustomerHandler();
                createCustomerHandler.handle(createCustomerCommand);

            }
        }
    }

    private CreateCustomerCommand collectDataFromXml(StringBuilder stringBuilder) {
        CreateCustomerCommand createCustomerCommand = new CreateCustomerCommand();
        Set<Contact> contacts = new HashSet<>();
        String[] data = stringBuilder.toString().split("\n");
        for (String d : data) {
            if (!(d.contains("contacts>"))) {
                if (d.contains("<name>"))
                    createCustomerCommand.setName(extractValue(d));
                else if (d.contains("<surname>"))
                    createCustomerCommand.setSurname(extractValue(d));
                else if (d.contains("<age>")) {
                    String a = extractValue(d);
                    if (a == null)
                        a = "";
                    createCustomerCommand.setAge(a);
                } else if (d.contains("<city>"))
                    createCustomerCommand.setCity(extractValue(d));
                else {
                    String s = extractValue(d);
                    if (!(d.contains("contacts>")) && !(s.isEmpty())) {
                        contacts.add(new Contact(s));
                    }
                    createCustomerCommand.setContacts(contacts);
                }
            }
        }

        return createCustomerCommand;
    }

    private StringBuilder collectData() throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while (!(line = bufferedReader.readLine()).trim().contains("</person>")) {
            sb.append(line).append("\n");
        }
        return sb;
    }


    private String extractValue(String d) {
        return d.substring(d.indexOf(">") + 1, d.lastIndexOf("</"));
    }


//    String[] data = personalData.toString().split("\n");
//        for (String d : data) {
//        validate(d);
//    }


}

