//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package flashcards;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Card {
    private String term;
    @Setter
    private String definition;
    @Setter
    private int mistakes;

    public Card(String term, String definition) {
        this.term = term;
        this.definition = definition;
        this.mistakes = 0;
    }

    public Card(String term, String definition, int mistakes) {
        this.term = term;
        this.definition = definition;
        this.mistakes = mistakes;
    }

    public void addMistake() {
        ++this.mistakes;
    }

    public String toString() {
        return this.term + ";" + this.definition + ";" + this.mistakes + "\n";
    }


}
