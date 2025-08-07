package com.todo.cli.models.definitions;

public class Task {
  public int id;
  public int taskNumber;
  public String description;
  public String state;
  public String date;

  public Task(int id, int taskNumber, String description, String state, String date) {
    this.id = id;
    this.taskNumber = taskNumber;
    this.description = description;
    this.state = state;
    this.date = date;
  }
}
