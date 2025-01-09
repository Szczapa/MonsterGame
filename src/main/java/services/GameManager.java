package services;

import models.Character;
import java.io.IOException;
import java.util.Scanner;

public class GameManager {
    private final EventService eventService = new EventService();
    private final CharacterService characterService = new CharacterService();

    public void startGame() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        Character playerCharacter = null;

        while (playerCharacter == null) {
            System.out.println("\n --- Bienvenue dans le jeu d'aventure textuelle ! ---");
            System.out.println("1. Créer un nouveau personnage");
            System.out.println("2. Charger un personnage existant");
            System.out.print("Choisissez une option : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                playerCharacter = characterService.createCharacter(scanner);
            } else if (choice == 2) {
                playerCharacter = characterService.loadCharacter();
            } else {
                System.out.println("Option invalide. Veuillez réessayer.");
            }
        }

        // Boucle principale du jeu
        boolean gameOver = false;
        while (!gameOver) {
            String event = eventService.generateEvent();
            System.out.println("Événement : " + event);

            int damage = eventService.simulateEventImpact();
            gameOver = !characterService.updateCharacter(playerCharacter, damage);

            System.out.println("Santé restante : " + playerCharacter.getPv());
        }

        characterService.saveCharacter(playerCharacter);
        eventService.generateJournal(playerCharacter);
        System.out.println("--- Fin de l'aventure ---");
    }

}
