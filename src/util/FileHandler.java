package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {

    public static List<String> lerArquivo(String caminho) {
        List<String> linhas = new ArrayList<>();
        File arquivo = new File(caminho);
        try (Scanner scanner = new Scanner(arquivo)) {
            while (scanner.hasNextLine()) {
                linhas.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        }
        return linhas;
    }
}