package services;

import models.Character;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CharacterService {
    private static final String CHARACTER_FILE = "characters.dat";
    private static final String MONSTER_FILE = "monstres.txt";

    public Character createCharacter(Scanner scanner) throws IOException {
        System.out.print("Entrez le nom de votre héros : ");
        String name = scanner.nextLine();

        System.out.print("Entrez la force (1-100) : ");
        int force = scanner.nextInt();

        System.out.print("Entrez la santé (1-100) : ");
        int pv = scanner.nextInt();
        scanner.nextLine();

        Character character = new Character(name, pv, force);
        saveCharacter(character);
        System.out.println("Personnage créé avec succès et sauvegardé.");
        return character;
    }

    public void saveCharacter(Character character) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CHARACTER_FILE))) {
            oos.writeObject(character);
        }
    }

    public Character loadCharacter() throws IOException, ClassNotFoundException {
        File file = new File(CHARACTER_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("Aucun personnage sauvegardé trouvé. Veuillez créer un nouveau personnage.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Character) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement du personnage : " + e.getMessage());
            return null;
        }
    }


    public boolean updateCharacter(Character character, int impact) {
        if (impact>0){
            System.out.println("Pv rendu " +impact+"❤\uFE0F");
        } else {
            System.out.println("Pv perdu"+impact+"💔");
        }
        int updatedHealth = character.getPv() + impact;
        character.setPv(Math.max(0, updatedHealth));
        return updatedHealth > 0;
    }

    public List<Character> loadMonsters() throws IOException {
        List<Character> monsters = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(MONSTER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    String name = parts[0];
                    int pv = Integer.parseInt(parts[1]);
                    int force = Integer.parseInt(parts[2]);
                    monsters.add(new Character(name, pv, force));
                }
            }
        }

        return monsters;
    }

}
