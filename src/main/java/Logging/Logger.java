package Logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger implements ILogger {
    private String fileName = "";

    private BufferedWriter writer = null;

    public String GetFileName() {
        return fileName;
    }

    public void SetFileName(String fileName) {
        this.fileName = fileName;
    }

    public Logger(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void WriteToFile(String message) {
        try {
            FileWriter fStream = new FileWriter(fileName, true);
            writer = new BufferedWriter(fStream);
            writer.write(message + "\n");

            writer.close();
        } catch (IOException e) {
            // Handle it later
            System.err.println("Error: " + e.getMessage());
        }
    }
}
