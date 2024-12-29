package flashcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

  @Mock
  private UserInputController userInputController;
  private CardService cardService;
  @Mock
  private Printer printer;
  String term = "term";
  String definition = "def";

  @BeforeEach
  void setUp() {

    cardService = new CardService(userInputController, printer);
  }

  @Test
  void testAddCard() {
    when(userInputController.get())
        .thenReturn(term)
        .thenReturn(definition);
    cardService.tryToAddNewCard();

    assertEquals(1, cardService.getCards().size());
    Optional<Card> addedCard = cardService.getCards().stream().findFirst();
    assertTrue(addedCard.isPresent());
    assertEquals(term, addedCard.get().getTerm());
    assertEquals(definition, addedCard.get().getDefinition());

    verify(printer).addCardSuccess(term,definition);
    verify(printer, never()).notUniqueTerm(anyString());
    verify(printer, never()).notUniqueDefinition(anyString());
  }

  @Test
  void testAddCardWhenTermIsExists() {
    cardService.getCards().add(new Card(term, definition));

    when(userInputController.get())
        .thenReturn(term)
        .thenReturn(definition);

    cardService.tryToAddNewCard();
    verify(printer).notUniqueTerm(term);

    assertEquals(1, cardService.getCards().size());
  }

  @Test
  void testAddCardWhenDefinitionExists() {
    cardService.getCards().add(new Card("someTerm", definition));

    when(userInputController.get())
        .thenReturn(term)
        .thenReturn(definition);

    cardService.tryToAddNewCard();

    verify(printer).theDefinitionOfCard();
    assertEquals(1, cardService.getCards().size());
  }


  @Test
  void testRemoveCardSuccess() {
    Card card = new Card(term, definition);
    cardService.getCards().add(card);

    when(userInputController.get()).thenReturn(term);
    cardService.removeCard();

    verify(userInputController).get();
    verify(printer).removeCardSuccess();
    assertTrue(cardService.getCards().isEmpty());
  }

  @Test
  void testRemoveCardFailure() {
    Card card = new Card(term, definition);
    cardService.getCards().add(card);

    when(userInputController.get()).thenReturn("example");
    cardService.removeCard();

    verify(userInputController).get();
    verify(printer).removeCardFail("example");
    assertEquals(1, cardService.getCards().size());
  }

  @Test
  void testCardsCorrectAnswer() {
    Card card = new Card(term, definition);
    cardService.getCards().add(card);

    when(userInputController.get()).thenReturn("1", definition);

    cardService.testCards();

    verify(userInputController, times(2)).get();
    assertEquals(0, cardService.getCards().stream().limit(1).toList().get(0).getMistakes());
  }

  @Test
  void testCardsWrongAnswer() {
    Card card = new Card(term, definition);
    cardService.getCards().add(card);

    when(userInputController.get()).thenReturn("1", "def2");

    cardService.testCards();

    verify(userInputController, times(2)).get();
    assertEquals(1, cardService.getCards().stream().toList().get(0).getMistakes());
  }

  @Test
  void testCardWrongAnswerMatchAnotherDefinition() {
    Card card = new Card(term, definition);
    Card card2 = new Card("term2", "def2");
    cardService.getCards().add(card);
    cardService.getCards().add(card2);

    when(userInputController.get()).thenReturn("1","def2");

    cardService.testCards();

    verify(userInputController, times(2)).get();
    verify(printer).rightAnswerForAnotherCard(definition, card2.getTerm());
    }


  @Test
  void testNoCardsPrintHardestCards() {
    cardService.printHardestCards();

    verify(printer, times(1)).noCardsWithErrors();
    verify(printer, never()).oneHardestCard(any(Card.class));
    verify(printer, never()).hardestCards(anyList());
  }

  @Test
  void testOneCardWithMistakesPrintHardestCards() {
    Card card = new Card("Term1", "Definition1");
    card.setMistakes(3);
    cardService.getCards().add(card);

    cardService.printHardestCards();

    verify(printer, times(1)).oneHardestCard(card);
    verify(printer, never()).hardestCards(anyList());
    verify(printer, never()).noCardsWithErrors();
  }

  @Test
  void testMultipleCardsPrintHardestCards() {
    Card card1 = new Card("Term1", "Definition1");
    card1.setMistakes(5);

    Card card2 = new Card("Term2", "Definition2");
    card2.setMistakes(5);

    cardService.getCards().add(card1);
    cardService.getCards().add(card2);

    cardService.printHardestCards();

    verify(printer).hardestCards(anyList());
    verify(printer, never()).noCardsWithErrors();
  }


  @Test
  void testResetStats() {
    Card card = new Card(term, definition);
    card.setMistakes(2);
    cardService.getCards().add(card);

    cardService.resetStats();
    assertEquals(0, cardService.getCards().stream().toList().get(0).getMistakes());
  }

}