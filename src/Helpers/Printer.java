package helpers;

import java.util.List;

import models.definitions.State;
import models.definitions.Task;

public class Printer {
  public static void printTasksTable(List<Task> tasks) {
    int numberWidth = 5;
    int descriptionWidth = 50;
    int stateWidth = 15;

    String headingFormat = "| %-"+numberWidth+"s | %-"+descriptionWidth+"s | %-"+stateWidth+"s |\n";
    String format = "| %0"+numberWidth+"d | %-"+descriptionWidth+"s | %-"+stateWidth+"s |\n";
    String line = "+" + "-".repeat(numberWidth + 2) + "+"
                      + "-".repeat(descriptionWidth + 2) + "+"
                      + "-".repeat(stateWidth + 2) + "+";

    System.out.println(line);
    System.out.printf(headingFormat, "Nº", "Description", "State");
    System.out.println(line);
    
    for (Task task : tasks) {
      System.out.printf(format, task.taskNumber, task.description, task.state);
    }
    
    System.out.println(line);
  }

  public static void printStatesTable(List<State> states) {
    int idWidth = 5;
    int nameWidth = 20;
    
    String headingFormat = "| %-"+idWidth+"s | %-"+nameWidth+"s |\n";
    String format = "| %0"+idWidth+"d | %-"+nameWidth+"s |\n";
    String line = "+" + "-".repeat(idWidth + 2) + "+"
                      + "-".repeat(nameWidth + 2) + "+";

    System.out.println(line);
    System.out.printf(headingFormat, "Id", "Name");
    System.out.println(line);

    for (State state : states) {
      System.out.printf(format, state.id, state.name);
    }

    System.out.println(line);
  }

  public static void help(){
    String text = """
    ---------------------------------------------------
      Help: -h
    ---------------------------------------------------
      List tasks: -l
      Get task: -g <task number>
      New task: -n <task description> <state>
      Update task: -u <task number> <new description>
      Change task state: -s <task number> <new state>
      Delete task: -d <task number>
    ---------------------------------------------------
      List states: -lS
      Get state: -gS <state id>
      New state: -nS <state name>
      Update state: -uS <state id> <new name>
      Delete state: -dS <state id>
    ---------------------------------------------------
    """;
    System.out.println(text);
  }
}
