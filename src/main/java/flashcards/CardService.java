//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package flashcards;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardService {
    @Getter
    private final List<Card> cards = new ArrayList<>();
    private final UserInputController userInputController;

    public CardService() {
        userInputController = new UserInputController();
    }

    public void addCard() {
        System.out.println("The card:");
        Optional<String> term = userInputController.getUniqueTerm(this.cards);
        if (term.isPresent()) {
            System.out.println("The definition of the card:");
            Optional<String> definition = userInputController.getUniqueDefinition(this.cards);
            if (definition.isPresent()) {
                this.cards.add(new Card(term.get(), definition.get()));
                Printer.addCardSuccess(term.get(), definition.get());
            }
        }
    }

    public void removeCard() {
        System.out.println("Which card?");
        String term = userInputController.get();
        Optional<Card> cardToRemove = this.cards.stream().filter((c) -> c.getTerm().equals(term)).findFirst();
        if (cardToRemove.isPresent()) {
            this.cards.remove(cardToRemove.get());
            Printer.removeCardSuccess();
        } else {
            Printer.removeCardFail(term);
        }

    }

    public void testCards() {
        System.out.println("How many times to ask?");
        int number = Integer.parseInt(userInputController.get());

        for (int i = 0; i < number; ++i) {
            Card card = this.cards.get(i);
            Printer.printCardDefinition(card.getTerm());
            String userAnswer = userInputController.get();
            if (this.checkAnswer(userAnswer, this.cards.get(i))) {
                System.out.println("Correct!");
            }
        }

    }

    private boolean checkAnswer(String answer, Card card) {
        if (card.getDefinition().equals(answer)) {
            return true;
        } else {
            card.addMistake();
            if (this.cards.stream().map(Card::getDefinition).anyMatch(x -> x.equals(answer))) {
                Card guessedCard = this.cards.stream().filter((c) -> c.getDefinition().equals(answer)).findFirst().get();
                Printer.rightAnswerForAnotherCard(card.getDefinition(), guessedCard.getTerm());
            } else {
                Printer.wrongAnswer(card.getDefinition());
            }

            return false;
        }
    }

    public void printHardestCards() {
        int max = this.cards.stream().mapToInt(Card::getMistakes).max().orElse(0);
        List<Card> hardestCards = this.cards.stream().filter((c) -> c.getMistakes() == max).toList();
        if (hardestCards.size() == 1) {
            Printer.oneHardestCard(hardestCards.get(0));
        } else if (!hardestCards.isEmpty() && max != 0) {
            Printer.hardestCards(hardestCards);
        } else {
            Printer.noCardsWithErrors();
        }

    }

    public void resetStats() {
        this.cards.forEach((card) -> card.setMistakes(0));
        Printer.resetStats();
    }

}
