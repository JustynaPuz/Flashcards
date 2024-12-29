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
import java.util.Optional;
import java.util.Set;

public class FileReadWriteHandler {

  private final CardService cardService;
  private UserInputController userInputController;
  private Printer printer;

  public FileReadWriteHandler(CardService cardService, UserInputController userInputController) {
    this.cardService = cardService;
    this.userInputController = userInputController;
    this.printer = new Printer();
  }

  public void importCards() {
    printer.fileName();
    Path inputPath = Paths.get(userInputController.get());
    int counter = 0;
    Set<Card> cards = this.cardService.getCards();
    List<String> lines = readFileLines(inputPath);

    for (String line : lines) {
      printer.printString(line);
      String[] splitLine = line.split(";");
      if (splitLine.length < 3) {
        continue;
      }
      Card card = new Card(splitLine[0], splitLine[1], Integer.parseInt(splitLine[2]));

      counter += addOrUpdateCard(cards, card);
    }
    printer.nCardLoaded(counter);
  }

  private List<String> readFileLines(Path inputPath) {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath.toFile()))) {
      return reader.lines().toList();
    } catch (IOException e) {
      printer.fileNotFound();
      return new ArrayList<>();
    }
  }

  private int addOrUpdateCard(Set<Card> cards, Card newCard) {
    if (cardService.cardWithTermExist(newCard.getTerm())) {
      cards.removeIf(c -> c.getTerm().equals(newCard.getTerm()));
      cards.add(newCard);
      return 0;
    } else {
      cards.add(newCard);
      return 1;
    }
  }


  public void exportCards(Optional<Path> path) {
    Path outputPath;
    if (path.isEmpty()) {
      printer.fileName();
      outputPath = Paths.get(userInputController.get());
    } else {
      outputPath = path.get();
    }
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(outputPath), true));
      for (Card card : this.cardService.getCards()) {
        writer.write(card.toString());
      }
      writer.close();
    } catch (IOException e) {
      printer.wrongFileName();
    }
    printer.savedCards(this.cardService.getCards().size());
  }

}
