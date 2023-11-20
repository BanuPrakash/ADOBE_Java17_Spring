package  com.adobe.api.impl;
import com.adobe.api.LogService;

public class LogStdOut implements LogService {
    public void log(String msg) {
        System.out.println("STDOUT: " + msg);
    }
}