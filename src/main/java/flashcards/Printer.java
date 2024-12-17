package flashcards;

import java.util.List;

public class Printer {

    public static void menu() {
        System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");

    }

    public static void addCardSuccess(String term, String definition) {
        System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.");

    }

    public static void removeCardSuccess() {
        System.out.println("The card has been removed.");
    }

    public static void removeCardFail(String term) {
        System.out.println("Can't remove \"" + term + "\": there is no such card.");
    }

    public static void printCardDefinition(String term) {
        System.out.println("Print the definition of \"" + term + "\":");

    }

    public static void rightAnswerForAnotherCard(String rightDefinition, String rightTerm) {
        System.out.println("Wrong. The right answer is \"" + rightDefinition + "\", but your definition is correct for \"" + rightTerm + "\".");

    }

    public static void wrongAnswer(String definition) {
        System.out.println("Wrong. The right answer is \"" + definition + "\".");

    }

    public static void oneHardestCard(Card card) {
        System.out.println("The hardest card is \"" + card.getTerm() + "\". You have " + card.getMistakes() + " errors answering it.");

    }

    public static void hardestCards(List<Card> cards) {
        System.out.println("The hardest cards are");

        for (Card card : cards) {
            System.out.println("\"" + card.getTerm() + "\", ");
        }
    }

    public static void noCardsWithErrors() {
        System.out.println("There are no cards with errors.");
    }

    public static void resetStats() {
        System.out.println("Card statistics have been reset.");

    }

    public static void savedCards(int size) {
        System.out.println(size + " cards have been saved.");
    }

    public static void notUniqueTerm(String term) {
        System.out.println("The card \"" + term + "\" already exists.");

    }
    public static void notUniqueDefinition(String definition) {
        System.out.println("The definition \"" + definition + "\" already exists.");

    }
}


