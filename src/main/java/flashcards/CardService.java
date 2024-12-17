//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package flashcards;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class CardService {
    private final List<Card> cards = new ArrayList();
    private final InputManager inputManager;
    public CardService() {
        inputManager = new InputManager();
    }

    public void addCard() {
        System.out.println("The card:");
        Optional<String> term = inputManager.getUniqueTerm(this.cards);
        if (term.isPresent()) {
            System.out.println("The definition of the card:");
            Optional<String> definition = inputManager.getUniqueDefinition(this.cards);
            if (definition.isPresent()) {
                this.cards.add(new Card(term.get(), definition.get()));
                System.out.println("The pair (\"" + term.get() + "\":\"" + definition.get() + "\") has been added.");
            }
        }
    }

    public void removeCard() {
        System.out.println("Which card?");
        String term = inputManager.get();
        Optional<Card> cardToRemove = this.cards.stream().filter((c) -> c.getTerm().equals(term)).findFirst();
        if (cardToRemove.isPresent()) {
            this.cards.remove(cardToRemove.get());
            System.out.println("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + term + "\": there is no such card.");
        }

    }

    public void testCards() {
        System.out.println("How many times to ask?");
        int number = Integer.parseInt(inputManager.get());

        for(int i = 0; i < number; ++i) {
            Card card = this.cards.get(i);
            System.out.println("Print the definition of \"" + card.getTerm() + "\":");
            String userAnswer = inputManager.get();
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
            Stream var10000 = this.cards.stream().map(Card::getDefinition);
            Objects.requireNonNull(answer);
            if (var10000.anyMatch(answer::equals)) {
                Card guessedCard = (Card)this.cards.stream().filter((c) -> c.getDefinition().equals(answer)).findFirst().get();
                PrintStream var4 = System.out;
                String var10001 = card.getDefinition();
                var4.println("Wrong. The right answer is \"" + var10001 + "\", but your definition is correct for \"" + guessedCard.getTerm() + "\".");
            } else {
                System.out.println("Wrong. The right answer is \"" + card.getDefinition() + "\".");
            }

            return false;
        }
    }

    public void printHardestCards() {
        int max = this.cards.stream().mapToInt(Card::getMistakes).max().orElse(0);
        List<Card> hardestCards = this.cards.stream().filter((c) -> c.getMistakes() == max).toList();
        if (hardestCards.size() == 1) {
            PrintStream var10000 = System.out;
            String var10001 = ((Card)hardestCards.get(0)).getTerm();
            var10000.println("The hardest card is \"" + var10001 + "\". You have " + ((Card)hardestCards.get(0)).getMistakes() + " errors answering it.");
        } else if (!hardestCards.isEmpty() && max != 0) {
            System.out.println("The hardest cards are");

            for(Card card : hardestCards) {
                System.out.println("\"" + card.getTerm() + "\", ");
            }
        } else {
            System.out.println("There are no cards with errors.");
        }

    }

    public void resetStats() {
        this.cards.forEach((card) -> card.setMistakes(0));
        System.out.println("Card statistics have been reset.");
    }

    public List<Card> getCards() {
        return this.cards;
    }
}
