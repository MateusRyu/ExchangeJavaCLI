package br.dev.ryu.ExchangeJavaCLI.presenter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static BufferedWriter writer;

    public enum LogLevel {
        DEBUG, INFO, WARNING, ERROR
    }

    public static void setDateTimeFormatter(String dateTimePattern) {
        FORMATTER = DateTimeFormatter.ofPattern(dateTimePattern);
    }

    public static void open(String log_file) throws IOException {
        writer = new BufferedWriter(new FileWriter(log_file, true));
    }

    public static void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }

    public static synchronized void log(String message, LogLevel level) {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(FORMATTER);
        String logMessage = String.format("[%s] [%s] %s", formattedTime, level, message);

        try {
            writer.write(logMessage);
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error: Could not write the log file: " + e.getMessage());
        }
    }

    public static void debug(String message) {
        log(message, LogLevel.DEBUG);
    }

    public static void info(String message) {
        log(message, LogLevel.INFO);
    }

    public static void warning(String message) {
        log(message, LogLevel.WARNING);
    }

    public  static  void error(String message) {
        log(message, LogLevel.ERROR);
    }
}
