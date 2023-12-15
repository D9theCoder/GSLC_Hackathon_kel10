package main;

import java.io.*;

public class Connection {
    private BufferedReader fileScanner;
    private BufferedWriter fileWrite;
    private File database;

    public Connection(File database) {
        try {
            this.database = database;
            fileScanner = new BufferedReader(new FileReader(database));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!\n");
        }
    }

    public String readFile() {
        String RowData = null;
        try {
            if ((RowData = fileScanner.readLine()) != null) {
                return RowData;
            }
        } catch (IOException a) {
            a.printStackTrace();
        }
        return RowData;
    }

    public boolean writeFile(String rowData) {
        try {
            fileWrite = new BufferedWriter(new FileWriter(database, true));
            fileWrite.write(rowData);
            fileWrite.flush();
            return true;
        } catch (IOException a) {
            a.printStackTrace();
        }
        return false;
    }
}