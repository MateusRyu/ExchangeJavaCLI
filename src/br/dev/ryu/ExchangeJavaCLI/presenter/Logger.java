package br.dev.ryu.ExchangeJavaCLI.presenter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static String LOG_FILE;
    private static DateTimeFormatter FORMATTER;

    public enum LogLevel {
        DEBUG, INFO, WARNING, ERROR
    }

    public static void setup(String log_file, String dateTimePattern) {
        LOG_FILE = log_file;
        FORMATTER = DateTimeFormatter.ofPattern(dateTimePattern);
    }

    public static synchronized void log(String message, LogLevel level) {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(FORMATTER);

        String logMessage = String.format("[%s] [%s] %s", formattedTime, level, message);

        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logMessage);
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
