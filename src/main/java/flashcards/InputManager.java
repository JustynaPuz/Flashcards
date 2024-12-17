//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package flashcards;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class InputManager {
    private static final Scanner scanner;

    public InputManager() {
    }

    public String get() {
        while(true) {
            String value = scanner.nextLine();
            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Wrong input");
        }
    }

    public Optional<String> getUniqueTerm(List<Card> cards) {
        String term = get();
        Stream var10000 = cards.stream().map(Card::getTerm);
        Objects.requireNonNull(term);
        if (var10000.anyMatch(term::equals)) {
            System.out.println("The card \"" + term + "\" already exists.");
            return Optional.empty();
        } else {
            return Optional.of(term);
        }
    }

    public Optional<String> getUniqueDefinition(List<Card> cards) {
        String definition = get();
        Stream var10000 = cards.stream().map(Card::getDefinition);
        Objects.requireNonNull(definition);
        if (var10000.anyMatch(definition::equals)) {
            System.out.println("The definition \"" + definition + "\" already exists.");
            return Optional.empty();
        } else {
            return Optional.of(definition);
        }
    }

    static {
        scanner = new Scanner(System.in);
    }
}
