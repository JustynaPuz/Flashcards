//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package flashcards;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ArgumentsHandler {
    private final CardService cardService;
    private final FileReadWriteHandler fileReadWriteHandler;
    private final Logger logger;
    private Path exportPath;
    private UserInputController userInputController;
    private boolean running = true;

    public ArgumentsHandler(String[] args) {
        this.userInputController = new UserInputController();
        this.cardService = new CardService();
        this.fileReadWriteHandler = new FileReadWriteHandler(this.cardService);
        this.logger = new Logger();
        this.readArguments(args);
    }

    public ArgumentsHandler(String[] args, UserInputController userInputController, CardService cardService, FileReadWriteHandler fileReadWriteHandler, Logger logger) {
        this.userInputController = userInputController;
        this.cardService = cardService;
        this.fileReadWriteHandler = fileReadWriteHandler;
        this.logger = logger;
        this.readArguments(args);
    }

    public void menu() {

        while (running) {
            Printer.menu();
            switch (userInputController.get()) {
                case "add":
                    this.cardService.addCard();
                    break;
                case "remove":
                    this.cardService.removeCard();
                    break;
                case "import":
                    this.fileReadWriteHandler.importCards();
                    break;
                case "export":
                    this.fileReadWriteHandler.exportCards();
                    break;
                case "ask":
                    this.cardService.testCards();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    this.logger.stopLogging();
                    if (this.exportPath != null) {
                        fileReadWriteHandler.exportCards();
                    }
                    running = false;
                    break;
                case "log":
                    System.out.println("File name: ");
                    Path logPath = Paths.get(userInputController.get());
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
        for (int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case "-import":
                    this.fileReadWriteHandler.importCards();
                    break;
                case "-export":
                    ++i;
                    this.exportPath = Paths.get(args[i]);
            }
        }

    }
}
