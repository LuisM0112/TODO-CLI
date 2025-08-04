import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import database.Database;
import helpers.Printer;
import cli.Flag;
import controllers.StateController;
import controllers.TaskController;
import models.StateModel;
import models.TaskModel;

public class App {
  public static void main(String[] args) {

    // args = new String[]{"--init", "-n", "Testing task", "default", "-l", "-lS"};

    String dbUrl = "jdbc:sqlite:TODO.db";

    Map<String, Flag> flags = new HashMap<>();

    try (Connection dbConnection = DriverManager.getConnection(dbUrl)) {

      if (dbConnection == null) {
        System.err.println("Failed to connect to the database");
        return;
      }

      StateModel stateModel = new StateModel(dbConnection);
      TaskModel taskModel = new TaskModel(dbConnection, stateModel);

      StateController stateController = new StateController(stateModel);
      TaskController taskController = new TaskController(taskModel);

      if (args.length < 1) {
        taskController.getAll();
        return;
      }

      flags.put("--init", new Flag(0, (_) -> {
        Database.initialize(dbConnection);
      }));

      flags.put("-h", new Flag(0, (_) -> {
        Printer.help();
      }));

      // Task
      flags.put("-l", new Flag(0, (_) -> {
        taskController.getAll();;
      }));

      flags.put("-g", new Flag(1, (a) -> {
        taskController.getByTaskNumber(a[0]);
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
      flags.put("-lS", new Flag(0, (_) -> {
        stateController.getAll();
      }));

      flags.put("-gS", new Flag(1, (a) -> {
        stateController.getById(a[0]);
      }));

      flags.put("-nS", new Flag(1, (a) -> {
        stateController.create(a[0]);
      }));

      flags.put("-uS", new Flag(2, (a) -> {
        stateController.update(a[0], a[1]);
      }));

      flags.put("-dS", new Flag(1, (a) -> {
        stateController.delete(a[0]);
      }));

      flags.put("-o", new Flag(1, (a) -> {
        Printer.outputFile(a[0], stateModel, taskModel);
      }));

      for (int i = 0; i < args.length;) {

        String flagString = args[i];
        Flag flag = flags.get(flagString);

        if (flag == null) {
          System.err.println("Unknown flag: " + flagString);
          System.out.println("See help: -h");
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
