package services;

import models.Character;
import java.io.IOException;
import java.util.List;
import java.util.Random;
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

        boolean gameOver = false;
        Random random = new Random();

        while (!gameOver) {
            if (random.nextBoolean()) {
                gameOver = fight(playerCharacter);
            } else {
                String[] eventData = eventService.generateEvent();
                String event = eventData[0];
                String status = eventData[1];

                System.out.println("\nÉvénement \uD83E\uDD28: " + event);

                int impact = eventService.simulateEventImpact(status);
                eventService.generateJournal(event, impact);

                gameOver = !characterService.updateCharacter(playerCharacter, impact);

                System.out.println("Santé restante : " + playerCharacter.getPv()+"❤\uFE0F");
            }
        }
        System.out.println("--- Fin de l'aventure \uD83D\uDC94 ---");
    }

    private boolean fight(Character playerCharacter) throws IOException {
        List<Character> monsters = characterService.loadMonsters();
        if (monsters.isEmpty()) {
            System.out.println("Aucun monstre trouvé !");
            return false;
        }

        Random random = new Random();
        Character monster = monsters.get(random.nextInt(monsters.size()));

        System.out.println("\n⚔️ Un " + monster.getName() + " apparaît ! Il a " + monster.getPv() + " PV et " + monster.getForce() + " de force !\n");

        while (playerCharacter.getPv() > 0 && monster.getPv() > 0) {
            int playerAttack = random.nextInt(10) + 5;
            monster.setPv(monster.getPv() - playerAttack);
            System.out.println("🗡️ Vous attaquez le " + monster.getName() + " et lui infligez " + playerAttack + " dégâts !");

            if (monster.getPv() <= 0) {
                System.out.println("🏆 Vous avez vaincu le " + monster.getName() + " !");
                return false;
            }

            int monsterAttack = monster.getForce();
            playerCharacter.setPv(playerCharacter.getPv() - monsterAttack);
            System.out.println("💀 Le " + monster.getName() + " vous attaque et inflige " + monsterAttack + " dégâts !");

            if (playerCharacter.getPv() <= 0) {
                System.out.println("☠️ Vous avez été tué par le " + monster.getName() + "...\n");
                return true;
            }

            System.out.println("❤️ Santé restante : " + playerCharacter.getPv()+"\n");
        }

        return false;
    }

}
