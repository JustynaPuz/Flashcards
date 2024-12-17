//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package flashcards;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserInputController {
    private static final Scanner scanner;

    public UserInputController() {
    }

    public String get() {
        while (true) {
            String value = scanner.nextLine();
            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Wrong input");
        }
    }

    public Optional<String> getUniqueTerm(List<Card> cards) {
        String term = get();
        if (cards.stream().map(Card::getTerm).anyMatch(x -> x.equals(term))) {
            Printer.notUniqueTerm(term);
            return Optional.empty();
        } else {
            return Optional.of(term);
        }
    }

    public Optional<String> getUniqueDefinition(List<Card> cards) {
        String definition = get();
        if (cards.stream().map(Card::getDefinition).anyMatch(x -> x.equals(definition))) {
            Printer.notUniqueDefinition(definition);
            return Optional.empty();
        } else {
            return Optional.of(definition);
        }
    }

    static {
        scanner = new Scanner(System.in);
    }
}
