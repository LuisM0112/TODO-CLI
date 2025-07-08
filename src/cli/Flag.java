package cli;

import cli.interfaces.FlagHandler;

public class Flag {
  int argCount;
  FlagHandler handler;

  public Flag(int argCount, FlagHandler handler) {
    this.argCount = argCount;
    this.handler = handler;
  }

  /**
   * Execute flag's handler
   * 
   * @param remainingArgs All the args after the flag
   * @return (int) How many args where used without counting the flag
   */
  public int execute(String[] remainingArgs) {
    if (remainingArgs.length < argCount) {
      System.err.println("Error: Expected " + argCount + " arguments.");
      return 0;
    }

    String[] args = new String[argCount];

    System.arraycopy(remainingArgs, 0, args, 0, argCount);

    handler.handle(args);

    return argCount;
  }
}
