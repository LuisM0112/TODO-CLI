package models.definitions;

public class Task {
  public int id;
  public String description;
  public String state;
  public String date;

  public Task(int id, String description, String state, String date) {
    this.id = id;
    this.description = description;
    this.state = state;
    this.date = date;
  }
}
