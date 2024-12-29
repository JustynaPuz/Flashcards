//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package flashcards;

import java.util.Scanner;

public class UserInputController {

  private static Scanner scanner;
  private Printer printer = new Printer();

  public UserInputController() {
    if (scanner == null) {
      scanner = new Scanner(System.in);
    }
  }

  public String get() {
    while (true) {
      String value = scanner.nextLine();
      if (!value.isEmpty()) {
        return value;
      }
      printer.wrongInput();
    }
  }
}
