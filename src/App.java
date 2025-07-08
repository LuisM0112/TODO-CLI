import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cli.Flag;
import controllers.StateController;
import database.Database;
import models.StateModel;

public class App {

  public static int takeArgs(int argsNum, String[] args) {
    return 1;
  }

  public static void main(String[] args) {

    args = new String[]{};

    String url = "jdbc:sqlite:TODO.db";

    Map<String, Flag> flags = new HashMap<>();

    try (Connection dbConnection = DriverManager.getConnection(url)) {
      
      
      if (dbConnection == null) {
        return;
      }
      
      Database.initialize(dbConnection);
      
      StateModel stateModel = new StateModel(dbConnection);
      
      StateController stateController = new StateController(stateModel);

      if (args.length < 1) {
        stateController.getAll();
        return;
      }

      flags.put("-n", new Flag(1, (a) -> {
        stateController.create(a[0]);
      }));

      flags.put("-u", new Flag(2, (a) -> {
        stateController.update(a[0], a[1]);
      }));

      flags.put("-d", new Flag(1, (a) -> {
        stateController.delete(a[0]);
      }));

      for (int i = 0; i < args.length;) {

        String flagString = args[i];
        Flag flag = flags.get(flagString);

        if (flag == null) {
          System.err.println("Unknown flag: " + flagString);
          return;
        }

        String[] remainingArgs = new String[args.length - i - 1];
        System.arraycopy(args, i + 1, remainingArgs, 0, remainingArgs.length);

        int consumed = flag.execute(remainingArgs);
        i += consumed + 1;
      }

    } catch (SQLException e) {
      System.err.println("SQLite error: " + e.getMessage());
    }
  }
}
