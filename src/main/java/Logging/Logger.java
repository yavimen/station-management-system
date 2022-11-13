package Logging;

import org.apache.juli.logging.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

public class Logger{
    static protected Logger instance;
    private String fileName = "";

    private BufferedWriter writer = null;

    public String GetFileName() {
        return fileName;
    }

    public void SetFileName(String fileName) {
        this.fileName = fileName;
    }

    protected Logger(String fileName) {
        this.fileName = fileName;
    }

    public void WriteToFile(String message) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        synchronized (fileName){
            try {
                FileWriter fStream = new FileWriter(fileName, true);
                writer = new BufferedWriter(fStream);
                writer.write(timestamp.toString()+": "+ message + "\n");

                writer.close();
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    public static Logger GetInstance() {
        if(instance == null)
            instance = new Logger("log.txt");
        return instance;
    }
}
