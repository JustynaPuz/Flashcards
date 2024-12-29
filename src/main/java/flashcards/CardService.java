package flashcards;

import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class CardService {

  @Getter
  private final Set<Card> cards = new LinkedHashSet<>();
  private final UserInputController userInputController;
  private final Printer printer;

  public CardService(UserInputController userInputController, Printer printer) {
    this.userInputController = userInputController;
    this.printer = printer;
  }

  public void tryToAddNewCard() {
    printer.theCard();
    String term = userInputController.get();

    if (!cardWithTermExist(term)) {
      printer.theDefinitionOfCard();
      String definition = userInputController.get();
      if (!cardWithDefinitionExist(definition)) {
        this.cards.add(new Card(term, definition));
        printer.addCardSuccess(term, definition);
      } else {
        printer.notUniqueDefinition(definition);
      }
    } else {
      printer.notUniqueTerm(term);
    }
  }

  public boolean cardWithTermExist(String term) {
    return cards.stream().map(Card::getTerm).anyMatch(x -> x.equals(term));
  }

  public boolean cardWithDefinitionExist(String definition) {
    return cards.stream().map(Card::getDefinition).anyMatch(x -> x.equals(definition));
  }

  public void removeCard() {
    printer.whichCard();
    String term = userInputController.get();
    if (cardWithTermExist(term)) {
      Optional<Card> cardToRemove = this.cards.stream().filter((c) -> c.getTerm().equals(term))
          .findFirst();
      this.cards.remove(cardToRemove.get());
      printer.removeCardSuccess();
    } else {
      printer.removeCardFail(term);
    }
  }

  public void testCards() {
    printer.howManyTimesToAsk();
    int number;
    try {
      number = Integer.parseInt(userInputController.get());
    } catch (NumberFormatException e) {
      printer.wrongInput();
      return;
    }

    List<Card> cardsToTest = cards.stream().limit(number).toList();

    for (int i = 0; i < number; i++) {
      printer.printCardDefinitionOf(cardsToTest.get(i).getTerm());
      String userAnswer = userInputController.get();
      if (this.checkAnswer(userAnswer, cardsToTest.get(i))) {
        printer.correct();
      }
    }
  }

  private boolean checkAnswer(String answer, Card card) {
    if (card.getDefinition().equals(answer)) {
      return true;
    } else {
      card.increaseMistakesCount();
      if (cardWithDefinitionExist(answer)) {
        Card guessedCard = this.cards.stream().filter((c) -> c.getDefinition().equals(answer))
            .findFirst().get();
        printer.rightAnswerForAnotherCard(card.getDefinition(), guessedCard.getTerm());
      } else {
        printer.wrongAnswer(card.getDefinition());
      }
      return false;
    }
  }

  public void printHardestCards() {
    int max = this.cards.stream().mapToInt(Card::getMistakes).max().orElse(0);
    List<Card> hardestCards = this.cards.stream().filter((c) -> c.getMistakes() == max).toList();
    if (hardestCards.size() == 1) {
      printer.oneHardestCard(hardestCards.get(0));
    } else if (!hardestCards.isEmpty() && max != 0) {
      printer.hardestCards(hardestCards);
    } else {
      printer.noCardsWithErrors();
    }
  }

  public void resetStats() {
    this.cards.forEach((card) -> card.setMistakes(0));
    printer.resetStats();
  }

}
