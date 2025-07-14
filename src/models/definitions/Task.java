package models.definitions;

public class Task {
  public int id;
  public String name;
  public String description;
  public String state;

  public Task(int id, String description, String state) {
    this.id = id;
    this.description = description;
    this.state = state;
  }
}
