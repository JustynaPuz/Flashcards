//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package flashcards;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final CardService cardService;
    private InputManager inputManager;

    public FileManager(CardService cardService) {

        this.cardService = cardService;
        this.inputManager = new InputManager();
    }

    public void importCards() {
        System.out.println("File name:");
        Path inputPath = Paths.get(inputManager.get());
        int counter = 0;
        List<Card> cards = this.cardService.getCards();
        List<String> lines = new ArrayList();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(inputPath)));
            lines = reader.lines().toList();
            reader.close();
        } catch (IOException var9) {
            System.out.println("File not found.");
        }

        for(String line : lines) {
            System.out.println(line);
            String[] splitLine = line.split(";");
            Card card = new Card(splitLine[0], splitLine[1], Integer.parseInt(splitLine[2]));
            if (cards.stream().noneMatch((c) -> c.getTerm().equals(card.getTerm()) || c.getDefinition().equals(card.getDefinition()))) {
                cards.add(card);
                ++counter;
            } else if (cards.stream().anyMatch((c) -> c.getTerm().equals(card.getTerm()))) {
                cards.stream().filter((c) -> c.getTerm().equals(card.getTerm())).forEach((c) -> {
                    c.setDefinition(card.getDefinition());
                    c.setMistakes(card.getMistakes());
                });
            }
        }

        System.out.println(counter + " cards have been loaded.");
    }

    public void exportCards() {
        System.out.println("File name:");
        Path outputPath = Paths.get(inputManager.get());

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(outputPath), true));

            for(Card card : this.cardService.getCards()) {
                writer.write(card.toString());
            }

            writer.close();
        } catch (IOException var5) {
            System.out.println("Wrong file name");
        }

        System.out.println(this.cardService.getCards().size() + " cards have been saved.");
    }
}
