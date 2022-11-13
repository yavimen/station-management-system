package Logging;

public interface ILogger {
    void WriteToFile(String fileName);
    ILogger GetInstance();
}
