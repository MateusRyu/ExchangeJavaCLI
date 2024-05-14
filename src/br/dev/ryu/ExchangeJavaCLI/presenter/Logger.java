package br.dev.ryu.ExchangeJavaCLI.presenter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String logFile;
    private BufferedWriter writer;

    public enum LogLevel {
        DEBUG, INFO, WARNING, ERROR
    }

    public Logger(String logFile) throws IOException {
        this.logFile = logFile;
        open();
    }

    public static void setDateTimeFormatter(String dateTimePattern) {
        FORMATTER = DateTimeFormatter.ofPattern(dateTimePattern);
    }

    public void open() throws IOException {
        this.writer = new BufferedWriter(new FileWriter(this.logFile, true));
    }

    public void close() throws IOException {
        if (this.writer != null) {
            this.writer.close();
        }
    }

    public synchronized void log(String message, LogLevel level) {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(FORMATTER);
        String logMessage = String.format("[%s] [%s] %s", formattedTime, level, message);

        try {
            this.writer.write(logMessage);
            this.writer.flush();
        } catch (IOException e) {
            System.err.println("Error: Could not write the log file: " + e.getMessage());
        }
    }

    public void debug(String message) {
        log(message, LogLevel.DEBUG);
    }

    public void info(String message) {
        log(message, LogLevel.INFO);
    }

    public void warning(String message) {
        log(message, LogLevel.WARNING);
    }

    public  void error(String message) {
        log(message, LogLevel.ERROR);
    }
}
