package cli.interfaces;

@FunctionalInterface
public interface FlagHandler {
  void handle(String[] args);
}
