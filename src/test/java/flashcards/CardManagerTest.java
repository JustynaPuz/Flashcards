package flashcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardManagerTest {
    @Mock
    private CardService cardService;
    @Mock
    private FileManager fileManager;
    @Mock
    private Logger logger;
    @Mock
    private InputManager inputManager;

    private CardManager cardManager;

    @BeforeEach
    void SetUp() {
        String[] args = {};
        cardManager = new CardManager(args, inputManager, cardService, fileManager, logger);

    }

    @Test
    void testMenuAdd() {
        when(inputManager.get()).thenReturn("add", "exit");
        cardManager.menu();
        verify(cardService).addCard();
        verify(logger).stopLogging();
    }

    @Test
    void testMenuRemove() {
        when(inputManager.get()).thenReturn("remove", "exit");
        cardManager.menu();
        verify(cardService).removeCard();
        verify(logger).stopLogging();
    }

    @Test
    void testMenuImport() {
        when(inputManager.get()).thenReturn("import", "exit");
        cardManager.menu();
        verify(fileManager).importCards();
        verify(logger).stopLogging();
    }
    @Test
    void testMenuExport() {
        when(inputManager.get()).thenReturn("export", "exit");
        cardManager.menu();
        verify(fileManager).exportCards();
        verify(logger).stopLogging();
    }

    @Test
    void testMenuAsk() {
        when(inputManager.get()).thenReturn("ask", "exit");
        cardManager.menu();
        verify(cardService).testCards();
        verify(logger).stopLogging();
    }

    @Test
    void testMenuLog() {
        when(inputManager.get()).thenReturn("log", "exit");
        cardManager.menu();
        verify(logger).saveLogToFile(Mockito.anyString());
        verify(logger).stopLogging();
    }

    @Test
    void testMenuHardestCard() {
        when(inputManager.get()).thenReturn("hardest card", "exit");
        cardManager.menu();
        verify(cardService).printHardestCards();
        verify(logger).stopLogging();
    }

    @Test
    void testMenuResetStats() {
        when(inputManager.get()).thenReturn("reset stats", "exit");
        cardManager.menu();
        verify(cardService).resetStats();
        verify(logger).stopLogging();
    }

    @Test
    void testImportInArguments() {
        String[] args = {"-import", "data.txt"};
        cardManager = new CardManager(args, inputManager, cardService, fileManager, logger);
        verify(fileManager).importCards();

    }

    @Test
    void testExportInArguments() {
        String[] args = {"-export", "data.txt"};
        cardManager = new CardManager(args, inputManager, cardService, fileManager, logger);
        List<Card> cards = List.of(
                new Card("term1", "definition1"),
                new Card("term2", "definition2")
        );
        when(cardService.getCards()).thenReturn(cards);
        when(inputManager.get()).thenReturn("exit");
        cardManager.menu();
        File file = new File("data.txt");
        assertTrue(file.exists());
        verify(logger).stopLogging();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String content = reader.lines().collect(Collectors.joining("\n"));
            assertTrue(content.contains("term1;definition1;0"));
            assertTrue(content.contains("term2;definition2;0"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        file.delete();

    }

}