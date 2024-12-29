//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package flashcards;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Card {

  private final String term;
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

  public void increaseMistakesCount() {
    this.mistakes++;
  }

  public String toString() {
    return this.term + ";" + this.definition + ";" + this.mistakes + "\n";
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return mistakes == card.mistakes && Objects.equals(term, card.term)
        && Objects.equals(definition, card.definition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(term, definition);
  }
}
