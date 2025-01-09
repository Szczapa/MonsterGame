package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InitSystem {
    public boolean fileCheck() throws IOException {
        String eventsPath = "evenements.txt";
        String monsterPath = "monstres.txt";

        checkFile(monsterPath);
        checkFile(eventsPath);

        return true;
    }

    private void checkFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IOException("Le fichier " + filePath + " est introuvable.");
        }

        try (BufferedReader br = Files.newBufferedReader(path)) {
            if (br.readLine() == null) {
                throw new IOException("Le fichier " + filePath + " est vide.");
            }
        } catch (IOException e) {
            throw new IOException("Impossible d'accéder au fichier " + filePath + " : " + e.getMessage());
        }
    }
}
