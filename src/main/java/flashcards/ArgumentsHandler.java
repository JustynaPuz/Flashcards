package flashcards;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ArgumentsHandler {
    private final CardService cardService;
    private final FileReadWriteHandler fileReadWriteHandler;
    private final Logger logger;
    private Optional<Path> exportPath = Optional.empty();
    private final UserInputController userInputController;
    private boolean running = true;
    private final Printer printer;

    public ArgumentsHandler(String[] args, UserInputController userInputController, CardService cardService, FileReadWriteHandler fileReadWriteHandler, Logger logger) {
        this.userInputController = userInputController;
        this.cardService = cardService;
        this.fileReadWriteHandler = fileReadWriteHandler;
        this.logger = logger;
        this.printer = new Printer();
        this.readArguments(args);
    }

    public void menu() {
        while (running) {
            printer.menu();
            switch (userInputController.get()) {
                case "add":
                    this.cardService.tryToAddNewCard();
                    break;
                case "remove":
                    this.cardService.removeCard();
                    break;
                case "import":
                    this.fileReadWriteHandler.importCards();
                    break;
                case "export":
                    this.fileReadWriteHandler.exportCards(Optional.empty());
                    break;
                case "ask":
                    this.cardService.testCards();
                    break;
                case "exit":
                    exit();
                    break;
                case "log":
                    log();
                    break;
                case "hardest card":
                    this.cardService.printHardestCards();
                    break;
                case "reset stats":
                    this.cardService.resetStats();
            }
            printer.blankLine();
        }
    }

    private void log() {
        printer.fileName();
        Path logPath = Paths.get(userInputController.get());
        this.logger.saveLogToFile(logPath.toString());
    }

    private void exit() {
        printer.bye();
        this.logger.stopLogging();
        if (this.exportPath.isPresent()) {
            fileReadWriteHandler.exportCards(exportPath);
        }
        running = false;
    }

    private void readArguments(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case "-import":
                    this.fileReadWriteHandler.importCards();
                    break;
                case "-export":
                    ++i;
                    this.exportPath = Optional.of(Paths.get(args[i]));
            }
        }
    }
}
