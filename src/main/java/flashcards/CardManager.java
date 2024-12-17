//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package flashcards;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CardManager {
    private final CardService cardService;
    private final FileManager fileManager;
    private final Logger logger;
    private Path exportPath;
    private InputManager inputManager;
    private boolean running = true;

    public CardManager(String[] args) {
        this.inputManager = new InputManager();
        this.cardService = new CardService();
        this.fileManager = new FileManager(this.cardService);
        this.logger = new Logger();
        this.readArguments(args);
    }
    public CardManager(String[] args,InputManager inputManager, CardService cardService, FileManager fileManager, Logger logger) {
        this.inputManager = inputManager;
        this.cardService = cardService;
        this.fileManager = fileManager;
        this.logger = logger;
        this.readArguments(args);
    }

    public void menu() {

        while(running) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            switch (inputManager.get()) {
                case "add":
                    this.cardService.addCard();
                    break;
                case "remove":
                    this.cardService.removeCard();
                    break;
                case "import":
                    this.fileManager.importCards();
                    break;
                case "export":
                    this.fileManager.exportCards();
                    break;
                case "ask":
                    this.cardService.testCards();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    this.logger.stopLogging();
                    if (this.exportPath != null) {
                        this.exportCards();
                    }
                    running = false;
                    break;
                case "log":
                    System.out.println("File name: ");
                    Path logPath = Paths.get(inputManager.get());
                    this.logger.saveLogToFile(logPath.toString());
                    break;
                case "hardest card":
                    this.cardService.printHardestCards();
                    break;
                case "reset stats":
                    this.cardService.resetStats();
            }

            System.out.println();
        }
    }

    private void readArguments(String[] args) {
        for(int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case "-import":
                    this.fileManager.importCards();
                    break;
                case "-export":
                    ++i;
                    this.exportPath = Paths.get(args[i]);
            }
        }

    }

    private void exportCards() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(this.exportPath), true));

            for(Card card : this.cardService.getCards()) {
                writer.write(card.toString());
            }

            writer.close();
        } catch (IOException var4) {
            System.out.println("Wrong file name");
        }

        System.out.println(this.cardService.getCards().size() + " cards have been saved.");
    }
}
