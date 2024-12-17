package flashcards;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CardTest {
    @Spy
    Card card;

    @Test
    void addMistake() {
        card.addMistake();
        assertEquals(1, card.getMistakes());
    }

    @Test
    void testToString() {
    }

    @Test
    void getTerm() {
    }

    @Test
    void setTerm() {
    }

    @Test
    void getDefinition() {
    }

    @Test
    void setDefinition() {
    }

    @Test
    void getMistakes() {
    }

    @Test
    void setMistakes() {
    }
}