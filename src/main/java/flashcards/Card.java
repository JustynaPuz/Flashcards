//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package flashcards;

import lombok.Getter;

@Getter
public class Card {
    private String term;
    private String definition;
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


    public void setDefinition(String definition) {
        this.definition = definition;
    }


    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }
}
