package services;

import models.Character;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventService {
    private static final String EVENTS_FILE = "evenements.txt";
    private static final String JOURNAL_FILE = "eventRecord.txt";

    public String generateEvent() throws IOException {
        List<String> events = readEvents();
        if (events.isEmpty()) {
            throw new IOException("Le fichier des événements est vide ou introuvable.");
        }
        Random random = new Random();
        return events.get(random.nextInt(events.size()));
    }

    public int simulateEventImpact() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    private List<String> readEvents() throws IOException {
        List<String> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(EVENTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                events.add(line);
            }
        }
        return events;
    }

    public void generateJournal(String events, int damages) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JOURNAL_FILE, true))) {
            writer.write( events +"\n");
            writer.write(damages +"\n");
        }
    }
}
