package myapps.reader;

import myapps.reader.application.CsvMigrator;
import myapps.reader.application.Migrator;
import myapps.reader.application.XmlMigrator;
import myapps.reader.infrastructure.DbManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class Main {


    private String csvPath = "./dane-osoby.txt";
    private String xmlPath = "./dane-osoby.xml";
    private Migrator migrator;

    public static void main(String[] args) throws IOException {

        new Main().start();
    }

    private void start() throws IOException {

        try {
            DbManager.initDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String path = csvPath;

        BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String firstLine = bufferedReader.readLine();


        if (firstLine.contains("<?xml"))
            migrator = new XmlMigrator(path);
        else
            migrator = new CsvMigrator(path);

        try {
            migrator.migrate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
