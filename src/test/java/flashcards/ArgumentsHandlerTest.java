package flashcards;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArgumentsHandlerTest {
    @Mock
    private CardService cardService;
    @Mock
    private FileReadWriteHandler fileReadWriteHandler;
    @Mock
    private Logger logger;
    @Mock
    private UserInputController userInputController;
    @TempDir
    Path tempDir;

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
        verify(cardService).tryToAddNewCard();
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
        verify(fileReadWriteHandler).exportCards(Optional.empty());
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

        Optional<Path> exportPath = Optional.of(Paths.get("data.txt"));
        String[] args = {"-export", String.valueOf(exportPath.get())};

        argumentsHandler = new ArgumentsHandler(args, userInputController, cardService, fileReadWriteHandler, logger);
        when(userInputController.get()).thenReturn("exit");
        argumentsHandler.menu();
        verify(fileReadWriteHandler).exportCards(exportPath);

    }

}