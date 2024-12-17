//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package flashcards;

import java.io.*;

public class Logger {
    private final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
    private final ByteArrayOutputStream capturedInput = new ByteArrayOutputStream();
    private final ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
    private final PrintStream originalOut;
    private final InputStream originalIn;
    private final PrintStream dualOut;
    private final InputStream dualIn;

    public Logger() {
        this.originalOut = System.out;
        this.originalIn = System.in;
        this.dualOut = new PrintStream(new CombinedOutputStream(this.originalOut, this.capturedOutput, this.resultStream));
        this.dualIn = new TeeInputStream(this.originalIn, this.capturedInput, this.resultStream);
        System.setOut(this.dualOut);
        System.setIn(this.dualIn);
    }

    public void saveLogToFile(String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            this.resultStream.writeTo(fileOut);
            System.out.println("The log has been saved.");
        } catch (IOException e) {
            System.err.println("Error saving the log: " + e.getMessage());
        }

    }

    public void stopLogging() {
        System.setOut(this.originalOut);
        System.setIn(this.originalIn);
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

        @Override
        public void close() throws IOException {
            for (OutputStream stream : streams) {
                stream.close();
            }
        }
    }

    private static class TeeInputStream extends InputStream {
        private final InputStream original;
        private final OutputStream[] branches;

        public TeeInputStream(InputStream original, OutputStream... branches) {
            this.original = original;
            this.branches = branches;
        }

        @Override
        public int read() throws IOException {
            int b = original.read();
            if (b != -1) {
                for (OutputStream branch : branches) {
                    branch.write(b);
                }
            }
            return b;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int bytesRead = original.read(b, off, len);
            if (bytesRead != -1) {
                for (OutputStream branch : branches) {
                    branch.write(b, off, bytesRead);
                }
            }
            return bytesRead;
        }

        @Override
        public void close() throws IOException {
            original.close();
            for (OutputStream branch : branches) {
                branch.close();
            }
        }
    }
}

