package controllers;

import java.util.ArrayList;

import models.TaskModel;
import models.definitions.Task;

public class TaskController {
  private TaskModel taskModel;

  public TaskController(TaskModel taskModel) {
    this.taskModel = taskModel;
  }

  public void getAll() {
    ArrayList<Task> taskList = this.taskModel.getAll();
    System.out.println("Task List");
    System.out.println("==========");
    for (Task task : taskList) {
      System.out.println(task.id + ": " + task.description + ". " + task.state);
    }
  }

  public void getById(String taskId) {
    Task task = this.taskModel.getById(Integer.parseInt(taskId));
    System.out.println(task.id + ": " + task.description + ". " + task.state);
  }

  public void create(String description, String stateName) {
    this.taskModel.create(description, stateName);
    System.out.println("Task added: " + description);
  }

  public void update(String taskId, String newDescription) {
    this.taskModel.update(
      Integer.parseInt(taskId),
      newDescription
    );
    System.out.println("Task updated: " + taskId + " to "+ newDescription);
  }
  public void changeState(String taskId, String newStateName) {
    this.taskModel.changeState(
      Integer.parseInt(taskId),
      newStateName
    );
    System.out.println("Task state updated to: " + newStateName);
  }

  public void delete(String taskId) {
    this.taskModel.delete(Integer.parseInt(taskId));
    System.out.println("Task deleted: " + taskId);
  }
}
