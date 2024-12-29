package flashcards;

public final class CardServiceFactory {

  private CardServiceFactory() {
  }

  public static CardService createDefault() {
    UserInputController userInputController = new UserInputController();
    Printer printer = new Printer();
    return new CardService(userInputController, printer);
  }
}

