package models.definitions;

public class Task {
  public int id;
  public String name;
  public String description;
  public String state;

  public Task(int id, String name, String description, String state) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.state = state;
  }
}
