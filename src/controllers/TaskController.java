package controllers;

import java.util.ArrayList;

import helpers.Messages;
import helpers.Printer;
import models.TaskModel;
import models.definitions.Task;

public class TaskController {
  private TaskModel taskModel;

  public TaskController(TaskModel taskModel) {
    this.taskModel = taskModel;
  }

  public void getAll() {
    ArrayList<Task> taskList = this.taskModel.getAll();
    Printer.printTasksTable(taskList);
  }

  public void getByTaskNumber(String taskNumber) {
    Task task = this.taskModel.getByTaskNumber(Integer.parseInt(taskNumber));
    System.out.println("id: "+ task.id +" | Nº "+ task.taskNumber + " | " + task.description + " | " + task.state +" | "+ task.date);
  }

  public void create(String description, String stateName) {
    int resultTaskNumber = this.taskModel.create(description, stateName);
    if (resultTaskNumber == 0) {
      return;
    }
    System.out.println(Messages.Task.created);
  }

  public void update(String taskNumber, String newDescription) {
    int resultTaskNumber = this.taskModel.update(
      Integer.parseInt(taskNumber),
      newDescription
    );
    if (resultTaskNumber == 0) {
      return;
    }
    System.out.println(Messages.Task.updated);
  }
  public void changeState(String taskNumber, String newStateName) {
    int resultTaskNumber = this.taskModel.changeState(
      Integer.parseInt(taskNumber),
      newStateName
    );
    if (resultTaskNumber == 0) {
      return;
    }
    System.out.println(Messages.Task.stateChanged);
  }

  public void delete(String taskNumber) {
    int resultTaskNumber = this.taskModel.delete(Integer.parseInt(taskNumber));
    if (resultTaskNumber == 0) {
      return;
    }
    System.out.println(Messages.Task.deleted);
    this.taskModel.updateTasksNumbers();
  }
}
