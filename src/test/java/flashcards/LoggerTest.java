package flashcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {
    Logger logger;
    @BeforeEach
    public void setup(){
        logger = new Logger();
    }

    @Test
    public void testLoggingOutput() {
        System.out.println("Test log entry");

        logger.stopLogging();

        String loggedContent = logger.getResultStream().toString();
        assertTrue(loggedContent.contains("Test log entry"));
    }

    @Test
    public void testSaveLogToFile() throws IOException {
        Path tempFile = Files.createTempFile("testLog", ".txt");
        tempFile.toFile().deleteOnExit();

        System.out.println("First log entry");
        System.out.println("Second log entry");

        logger.saveLogToFile(tempFile.toString());

        String fileContent = Files.readString(tempFile);
        assertTrue(fileContent.contains("First log entry"));
        assertTrue(fileContent.contains("Second log entry"));
    }

    @Test
    public void testStopLogging() {
        System.out.println("This should be logged");

        logger.stopLogging();

        System.out.println("This should not be logged");

        String loggedContent = logger.getResultStream().toString();
        assertTrue(loggedContent.contains("This should be logged"));
        assertFalse(loggedContent.contains("This should not be logged"));
    }
}