package flashcards;

public final class FileReadWriteHandlerFactory {

  private FileReadWriteHandlerFactory() {
  }

  public static FileReadWriteHandler createWithController(CardService cardService, UserInputController userInputController) {
    return new FileReadWriteHandler(cardService, userInputController);
  }
}
