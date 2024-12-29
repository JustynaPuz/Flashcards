package flashcards;

import java.util.List;

public class Printer {

  public void menu() {
    System.out.println(
        "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
  }

  public void addCardSuccess(String term, String definition) {
    System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
  }

  public void removeCardSuccess() {
    System.out.println("The card has been removed.");
  }

  public void removeCardFail(String term) {
    System.out.println("Can't remove \"" + term + "\": there is no such card.");
  }

  public void printCardDefinitionOf(String term) {
    System.out.println("Print the definition of \"" + term + "\":");
  }

  public void rightAnswerForAnotherCard(String rightDefinition, String rightTerm) {
    System.out.println("Wrong. The right answer is \"" + rightDefinition
        + "\", but your definition is correct for \"" + rightTerm + "\".");
  }

  public void wrongAnswer(String definition) {
    System.out.println("Wrong. The right answer is \"" + definition + "\".");
  }

  public void oneHardestCard(Card card) {
    System.out.println(
        "The hardest card is \"" + card.getTerm() + "\". You have " + card.getMistakes()
            + " errors answering it.");

  }

  public void hardestCards(List<Card> cards) {
    System.out.println("The hardest cards are");

    for (Card card : cards) {
      System.out.println("\"" + card.getTerm() + "\", ");
    }
  }

  public void noCardsWithErrors() {
    System.out.println("There are no cards with errors.");
  }

  public void resetStats() {
    System.out.println("Card statistics have been reset.");
  }

  public void savedCards(int size) {
    System.out.println(size + " cards have been saved.");
  }

  public void notUniqueTerm(String term) {
    System.out.println("The card \"" + term + "\" already exists.");
  }

  public void notUniqueDefinition(String definition) {
    System.out.println("The definition \"" + definition + "\" already exists.");
  }

  public void fileName() {
    System.out.println("File name:");
  }

  public void bye() {
    System.out.println("Bye bye!");
  }

  public void blankLine() {
    System.out.println();
  }

  public void theCard() {
    System.out.println("The card:");
  }

  public void theDefinitionOfCard() {
    System.out.println("The definition of the card:");
  }

  public void whichCard() {
    System.out.println("Which card?");
  }

  public void howManyTimesToAsk() {
    System.out.println("How many times to ask?");
  }

  public void correct() {
    System.out.println("Correct!");
  }

  public void fileNotFound() {
    System.out.println("File not found.");
  }

  public void nCardLoaded(int counter) {
    System.out.println(counter + " cards have been loaded.");
  }

  public void printString(String str) {
    System.out.println(str);
  }

  public void wrongFileName() {
    System.out.println("Wrong file name");
  }

  public void logSaved() {
    System.out.println("The log has been saved.");
  }

  public void errorSavingLog(String error) {
    System.err.println("Error saving the log: " + error);
  }

  public void wrongInput() {
    System.out.println("Wrong input");
  }

}


