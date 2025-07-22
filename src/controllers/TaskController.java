package controllers;

import java.util.ArrayList;

import Helpers.Printer;
import Helpers.Messages;
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
    this.taskModel.create(description, stateName);
    System.out.println(Messages.Task.created);
  }

  public void update(String taskNumber, String newDescription) {
    this.taskModel.update(
      Integer.parseInt(taskNumber),
      newDescription
    );
    System.out.println(Messages.Task.updated);
  }
  public void changeState(String taskNumber, String newStateName) {
    this.taskModel.changeState(
      Integer.parseInt(taskNumber),
      newStateName
    );
    System.out.println(Messages.Task.stateChanged);
  }

  public void delete(String taskNumber) {
    this.taskModel.delete(Integer.parseInt(taskNumber));
    System.out.println(Messages.Task.deleted);
    this.taskModel.updateTasksNumbers();
  }
}
