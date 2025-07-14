package cli.interfaces;

@FunctionalInterface
public interface FlagHandler {
  /**
   * Method that will be called by the flag
   * @param args Arguments the method will be taking from the remaining args
   */
  void handle(String[] args);
}
