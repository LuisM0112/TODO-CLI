import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import database.Database;
import cli.Flag;
import controllers.StateController;
import controllers.TaskController;
import models.StateModel;
import models.TaskModel;

public class App {
  public static void main(String[] args) {

    args = new String[]{};

    String url = "jdbc:sqlite:TODO.db";

    Map<String, Flag> flags = new HashMap<>();

    try (Connection dbConnection = DriverManager.getConnection(url)) {

      if (dbConnection == null) {
        System.err.println("Failed to connect to the database");
        return;
      }

      Database.initialize(dbConnection);

      StateModel stateModel = new StateModel(dbConnection);
      TaskModel taskModel = new TaskModel(dbConnection, stateModel);

      StateController stateController = new StateController(stateModel);
      TaskController taskController = new TaskController(taskModel);

      if (args.length < 1) {
        taskController.getAll();
        return;
      }

      // Task
      flags.put("-g", new Flag(1, (a) -> {
        taskController.getById(a[0]);
      }));

      flags.put("-n", new Flag(2, (a) -> {
        taskController.create(a[0], a[1]);
      }));

      flags.put("-u", new Flag(2, (a) -> {
        taskController.update(a[0], a[1]);
      }));

      flags.put("-s", new Flag(2, (a) -> {
        taskController.changeState(a[0], a[1]);
      }));

      flags.put("-d", new Flag(1, (a) -> {
        taskController.delete(a[0]);
      }));

      // State
      flags.put("-gState", new Flag(0, (_) -> {
        stateController.getAll();
      }));

      flags.put("-nState", new Flag(1, (a) -> {
        stateController.create(a[0]);
      }));

      flags.put("-uState", new Flag(2, (a) -> {
        stateController.update(a[0], a[1]);
      }));

      flags.put("-dState", new Flag(1, (a) -> {
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
