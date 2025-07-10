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
      System.out.println(task.id + ": " + task.name);
    }
  }

  public void create(String name, String description, String stateName) {
    this.taskModel.create(name, description, stateName);
    System.out.println("Task added: " + name);
  }

  public void update(String prevName, String newName, String newDescription) {
    this.taskModel.update(prevName, newName, newDescription);
    System.out.println("Task updated: " + prevName + " to "+ newName);
  }
  public void changeState(String taskName, String newStateName) {
    this.taskModel.changeState(taskName, newStateName);
    System.out.println("Task state updated to: " + newStateName);
  }

  public void delete(String name) {
    this.taskModel.delete(name);
    System.out.println("Task deleted: " + name);
  }
}
