package services;

import models.Character;

public class MonsterService {
    public Character selectMonster(){



        return null;
    }

    public String extractMonsterStatuts(String eventLine) {
        String[] parts = eventLine.split(",");
        return parts[parts.length - 1].trim();
    }
}
