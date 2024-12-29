package flashcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class argumentsHandlerTest {
    @Mock
    private CardService cardService;
    @Mock
    private FileReadWriteHandler fileReadWriteHandler;
    @Mock
    private Logger logger;
    @Mock
    private UserInputController userInputController;

    private ArgumentsHandler argumentsHandler;

    @BeforeEach
    void SetUp() {
        String[] args = {};
        argumentsHandler = new ArgumentsHandler(args, userInputController, cardService, fileReadWriteHandler, logger);

    }

    @Test
    void testMenuAdd() {
        when(userInputController.get()).thenReturn("add", "exit");
        argumentsHandler.menu();
        verify(cardService).addCard();
        verify(logger).stopLogging();
    }

    @Test
    void testMenuRemove() {
        when(userInputController.get()).thenReturn("remove", "exit");
        argumentsHandler.menu();
        verify(cardService).removeCard();
        verify(logger).stopLogging();
    }

    @Test
    void testMenuImport() {
        when(userInputController.get()).thenReturn("import", "exit");
        argumentsHandler.menu();
        verify(fileReadWriteHandler).importCards();
        verify(logger).stopLogging();
    }
    @Test
    void testMenuExport() {
        when(userInputController.get()).thenReturn("export", "exit");
        argumentsHandler.menu();
        verify(fileReadWriteHandler).exportCards();
        verify(logger).stopLogging();
    }

    @Test
    void testMenuAsk() {
        when(userInputController.get()).thenReturn("ask", "exit");
        argumentsHandler.menu();
        verify(cardService).testCards();
        verify(logger).stopLogging();
    }

    @Test
    void testMenuLog() {
        when(userInputController.get()).thenReturn("log", "exit");
        argumentsHandler.menu();
        verify(logger).saveLogToFile(Mockito.anyString());
        verify(logger).stopLogging();
    }

    @Test
    void testMenuHardestCard() {
        when(userInputController.get()).thenReturn("hardest card", "exit");
        argumentsHandler.menu();
        verify(cardService).printHardestCards();
        verify(logger).stopLogging();
    }

    @Test
    void testMenuResetStats() {
        when(userInputController.get()).thenReturn("reset stats", "exit");
        argumentsHandler.menu();
        verify(cardService).resetStats();
        verify(logger).stopLogging();
    }

    @Test
    void testImportInArguments() {
        String[] args = {"-import", "data.txt"};
        argumentsHandler = new ArgumentsHandler(args, userInputController, cardService, fileReadWriteHandler, logger);
        verify(fileReadWriteHandler).importCards();

    }

    @Test
    void testExportInArguments() {
        String[] args = {"-export", "data.txt"};
        argumentsHandler = new ArgumentsHandler(args, userInputController, cardService, fileReadWriteHandler, logger);
        List<Card> cards = List.of(
                new Card("term1", "definition1"),
                new Card("term2", "definition2")
        );
        when(cardService.getCards()).thenReturn(cards);
        when(userInputController.get()).thenReturn("exit");
        argumentsHandler.menu();
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