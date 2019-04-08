package myapps.reader;

import myapps.reader.application.CsvMigrator;
import myapps.reader.application.Migrator;
import myapps.reader.application.XmlMigrator;
import myapps.reader.infrastructure.DbManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

        String path = xmlPath;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
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
