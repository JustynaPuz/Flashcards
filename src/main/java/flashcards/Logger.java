package flashcards;

import java.io.PrintStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import lombok.Getter;

public class Logger {

  @Getter
  private final ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
  private final PrintStream originalOut;
  private final InputStream originalIn;
  private Printer printer = new Printer();

  public Logger() {
    this.originalOut = System.out;
    this.originalIn = System.in;

    PrintStream dualOut = new PrintStream(new CombinedOutputStream(originalOut, resultStream));
    System.setOut(dualOut);

    InputStream dualIn = new CombinedInputStream(originalIn, resultStream);
    System.setIn(dualIn);
  }

  public void saveLogToFile(String fileName) {
    try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
      resultStream.writeTo(fileOut);
      printer.logSaved();
    } catch (IOException e) {
      printer.errorSavingLog(e.getMessage());
    }
  }

  public void stopLogging() {
    System.setOut(originalOut);
    System.setIn(originalIn);
  }

  private static class CombinedOutputStream extends OutputStream {

    private final OutputStream[] streams;

    public CombinedOutputStream(OutputStream... streams) {
      this.streams = streams;
    }

    @Override
    public void write(int b) throws IOException {
      for (OutputStream stream : streams) {
        stream.write(b);
      }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      for (OutputStream stream : streams) {
        stream.write(b, off, len);
      }
    }

    @Override
    public void flush() throws IOException {
      for (OutputStream stream : streams) {
        stream.flush();
      }
    }
  }

  private static class CombinedInputStream extends InputStream {

    private final InputStream originalIn;
    private final OutputStream resultStream;

    public CombinedInputStream(InputStream originalIn, OutputStream resultStream) {
      this.originalIn = originalIn;
      this.resultStream = resultStream;
    }

    @Override
    public int read() throws IOException {
      int inputByte = originalIn.read();
      if (inputByte != -1) {
        resultStream.write(inputByte);
        resultStream.flush();
      }
      return inputByte;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
      int bytesRead = originalIn.read(b, off, len);
      if (bytesRead > 0) {
        resultStream.write(b, off, bytesRead);
        resultStream.flush();
      }
      return bytesRead;
    }
  }

}
