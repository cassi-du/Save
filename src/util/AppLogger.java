package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppLogger {
    private static final String LOG_FILE_PATH = "C:\\Users\\cassi\\Documents\\Projeto-final-desenvolvimento-main\\logs.txt";

    public static void log(String level, String user, String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = String.format("[%s] [%s] [User: %s] %s", timestamp, level, user, message);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }

    public static void info(String user, String message) {
        log("INFO", user, message);
    }

    public static void warn(String user, String message) {
        log("WARN", user, message);
    }

    public static void error(String user, String message) {
        log("ERROR", user, message);
    }
}
