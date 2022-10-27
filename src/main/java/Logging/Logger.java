package Logging;

public class Logger implements ILogger {
    private String fileName = "";

    public String GetFileName() {
        return fileName;
    }

    public void SetFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void WriteToFile(String fileName) {
        // Writing sth to file with path fileName
    }
}
