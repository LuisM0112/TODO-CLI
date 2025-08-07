package com.todo.cli.helpers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

import com.todo.cli.models.StateModel;
import com.todo.cli.models.TaskModel;
import com.todo.cli.models.definitions.State;
import com.todo.cli.models.definitions.Task;

public class Printer {
  
  public static void help(){
    String text = """
    ---------------------------------------------------
      Create default state: --init
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
      Output file: -o <file format>
    ---------------------------------------------------
    """;
    System.out.println(text);
  }

  public static void printTasksTable(List<Task> taskList) {
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
    
    for (Task task : taskList) {
      System.out.printf(format, task.taskNumber, task.description, task.state);
    }
    
    System.out.println(line);
  }

  public static void printStatesTable(List<State> stateList) {
    int idWidth = 5;
    int nameWidth = 20;
    
    String headingFormat = "| %-"+idWidth+"s | %-"+nameWidth+"s |\n";
    String format = "| %0"+idWidth+"d | %-"+nameWidth+"s |\n";
    String line = "+" + "-".repeat(idWidth + 2) + "+"
                      + "-".repeat(nameWidth + 2) + "+";

    System.out.println(line);
    System.out.printf(headingFormat, "Id", "Name");
    System.out.println(line);

    for (State state : stateList) {
      System.out.printf(format, state.id, state.name);
    }

    System.out.println(line);
  }

  public static void outputFile(String fileFormat, StateModel stateModel, TaskModel taskModel) {
    switch (fileFormat) {
      case "csv":
        List<State> stateList = stateModel.getAll();
        List<Task> taskList = taskModel.getAll();
        Printer.writeCSV(stateList, taskList);
        System.out.println("File created.");
        break;
    
      default:
        System.err.println("Format not supported: "+fileFormat);
        break;
    }
  }

  private static void writeCSV(List<State> stateList, List<Task> taskList) {
    try (
        FileWriter csvFile = new FileWriter("todo.csv");
        BufferedWriter buffer = new BufferedWriter(csvFile)
      ) {
      buffer.write(String.format("id;name%n"));
      for (State state : stateList) {
        buffer.write(String.format("%d;%s%n", state.id, state.name));
      }
      buffer.write(String.format("id;taskNumber;description;state;date%n"));
      for (Task task : taskList) {
        buffer.write(String.format(
          "%d;%d;%s;%s;%s%n",
          task.id,
          task.taskNumber,
          task.description,
          task.state,
          task.date
        ));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
