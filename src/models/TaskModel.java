package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import models.definitions.State;
import models.definitions.Task;

public class TaskModel {
  
  private Connection dbConnection;
  StateModel stateModel;

  public TaskModel(Connection dbConnection, StateModel stateModel) {
    this.dbConnection = dbConnection;
    this.stateModel = stateModel;
  }

  public ArrayList<Task> getAll() {
    ArrayList<Task> taskList = new ArrayList<Task>();

    try (Statement stmt = dbConnection.createStatement()) {
      ResultSet rs = stmt.executeQuery("SELECT * FROM tasks");
      while (rs.next()) {
        taskList.add(new Task(
          rs.getInt("id"),
          rs.getString("name"),
          rs.getString("description"),
          rs.getString("stateName")
        ));
      }
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }

    return taskList;
  }

  public void create(String name, String description, String stateName) {
    State state = stateModel.getByName(stateName);
    if (state == null) {
      System.out.println("State not found: " + stateName);
      return;
    }

    String sqlInsert = "INSERT INTO tasks(name, description, state_id) VALUES(?, ?, ?)";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlInsert)) {
      stmt.setString(1, name);
      stmt.setString(2, description);
      stmt.setInt(3, state.id);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }

  public void update(String prevName, String newName, String newDescription) {
    String sqlUpdate = "UPDATE tasks SET name = ?, description = ? WHERE name = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlUpdate)) {
      stmt.setString(1, newName);
      stmt.setString(2, newDescription);
      stmt.setString(3, prevName);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }

  public void changeState(String taskName, String newStateName) {
    State newState = stateModel.getByName(newStateName);
    if (newState == null) {
      System.out.println("State not found: " + newStateName);
      return;
    }

    String sqlUpdate = "UPDATE tasks SET state_id = ? WHERE name = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlUpdate)) {
      stmt.setInt(1, newState.id);
      stmt.setString(2, taskName);
      int rowsAffected = stmt.executeUpdate();
      stmt.close();
      if (rowsAffected == 0) {
        System.out.println("Task not found: " + taskName);
      }
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }

  public void delete(String name) {
    String sqlDelete = "DELETE FROM tasks WHERE name = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlDelete)) {
      stmt.setString(1, name);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }
}
