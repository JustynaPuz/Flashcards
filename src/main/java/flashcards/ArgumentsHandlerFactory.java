package flashcards;

public final class ArgumentsHandlerFactory {
  private ArgumentsHandlerFactory() {
  }

  public static ArgumentsHandler create(String[] args) {
    Logger logger = new Logger();
    CardService cardService = CardServiceFactory.createDefault();
    UserInputController userInputController = new UserInputController();
    FileReadWriteHandler fileReadWriteHandler = FileReadWriteHandlerFactory.createWithController(cardService,userInputController);

    return new ArgumentsHandler(
        args,
        userInputController,
        cardService,
        fileReadWriteHandler,
        logger
    );
  }
}

