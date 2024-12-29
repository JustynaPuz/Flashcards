package flashcards;

import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileReadWriteHandlerTest {
    @Mock
    private UserInputController userInputController;
    @Mock
    private CardService cardService;
    private FileReadWriteHandler fileReadWriteHandler;

    @BeforeEach
    void setUp() {
        fileReadWriteHandler = new FileReadWriteHandler(cardService,userInputController);
    }

    @Test
    void testImportCards() throws IOException {
        File tempFile = File.createTempFile("testCardsImport", ".txt");
        tempFile.deleteOnExit();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("term;def;0\n");
            writer.write("term1;def1;2\n");
        }

        when(userInputController.get()).thenReturn(tempFile.getAbsolutePath());
        Set<Card> cards = new LinkedHashSet<>();
        when(cardService.getCards()).thenReturn(cards);

        fileReadWriteHandler.importCards();

        verify(cardService).getCards();
        assertEquals(2, cards.size());
        assertEquals("term", cards.stream().toList().get(0).getTerm());
        assertEquals("term1", cards.stream().toList().get(1).getTerm());
        assertEquals("def", cards.stream().toList().get(0).getDefinition());
        assertEquals("def1", cards.stream().toList().get(1).getDefinition());

    }

    @Test
    void testImportCardsDuplicate() throws IOException {
        File tempFile = File.createTempFile("testCardsImport", ".txt");
        tempFile.deleteOnExit();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("term;def;0\n");
            writer.write("term2;def2;0\n");
            writer.write("term2;def3;3\n");
        }

        when(userInputController.get()).thenReturn(tempFile.getAbsolutePath());
      Set<Card> cards = new LinkedHashSet<>();
        when(cardService.getCards()).thenReturn(cards);
        when(cardService.cardWithTermExist("term")).thenReturn(false);
        when(cardService.cardWithTermExist("term2")).thenReturn(false, true);

        fileReadWriteHandler.importCards();

        assertEquals(2, cards.size());
        assertEquals("term", cards.stream().toList().get(0).getTerm());
        assertEquals("term2", cards.stream().toList().get(1).getTerm());
        assertEquals("def", cards.stream().toList().get(0).getDefinition());
        assertEquals("def3", cards.stream().toList().get(1).getDefinition());
        assertEquals(0, cards.stream().toList().get(0).getMistakes());
        assertEquals(3, cards.stream().toList().get(1).getMistakes());

    }



    @Test
    public void testExportCardsWithProvidedPath() throws IOException {
        File tempFile = File.createTempFile("exportTestCards", ".txt");
        tempFile.deleteOnExit();

        Set<Card> cards = Set.of(
                new Card("Term1", "Definition1", 3),
                new Card("Term2", "Definition2", 5)
        );

        when(cardService.getCards()).thenReturn(cards);
        fileReadWriteHandler.exportCards(Optional.of(tempFile.toPath()));

        String fileContent = java.nio.file.Files.readString(tempFile.toPath());
        assert fileContent.contains("Term1;Definition1;3");
        assert fileContent.contains("Term2;Definition2;5");
    }

    @Test
    public void testExportCardsWithUserInputPath() throws IOException {
        File tempFile = File.createTempFile("exportTestCardsInput", ".txt");
        tempFile.deleteOnExit();

        when(userInputController.get()).thenReturn(tempFile.getAbsolutePath());

        Set<Card> cards = Set.of(
            new Card("Term1", "Definition1", 3),
            new Card("Term2", "Definition2", 5)
        );

        when(cardService.getCards()).thenReturn(cards);
        fileReadWriteHandler.exportCards(Optional.empty());

        String fileContent = java.nio.file.Files.readString(tempFile.toPath());
        assert fileContent.contains("Term1;Definition1;3");
        assert fileContent.contains("Term2;Definition2;5");
    }


}