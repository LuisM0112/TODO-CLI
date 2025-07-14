package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Helpers.Messages;
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
        State taskState = stateModel.getById(rs.getInt("state_id"));
        taskList.add(new Task(
          rs.getInt("id"),
          rs.getString("description"),
          taskState.name
        ));
      }
      stmt.close();
    } catch (Exception e) {
      System.out.println("Get task list error: " + e.getMessage());
    }

    return taskList;
  }

  public Task getById(int taskId) {
    Task task = null;

    String sqlSelect = "SELECT * FROM tasks WHERE id = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlSelect)) {
      stmt.setInt(1, taskId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        State taskState = stateModel.getById(rs.getInt("state_id"));
        task = new Task(rs.getInt("id"), rs.getString("description"), taskState.name);
      }
      stmt.close();
    } catch (Exception e) {
      System.out.println("Get task error: " + e.getMessage());
    }

    return task;
  }

  public void create(String description, String stateName) {
    State state = stateModel.getByName(stateName);
    if (state == null) {
      System.err.println(Messages.State.notFound);
      return;
    }

    String sqlInsert = "INSERT INTO tasks(description, state_id) VALUES(?, ?)";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlInsert)) {
      stmt.setString(1, description);
      stmt.setInt(2, state.id);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Create task error: " + e.getMessage());
    }
  }

  public void update(int taskId, String newDescription) {
    if (getById(taskId) == null) {
      System.err.println(Messages.Task.notFound);
      return;
    }
    String sqlUpdate = "UPDATE tasks SET description = ? WHERE id = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlUpdate)) {
      stmt.setString(1, newDescription);
      stmt.setInt(2, taskId);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Update task error: " + e.getMessage());
    }
  }

  public void changeState(int taskId, String newStateName) {
    if (getById(taskId) == null) {
      System.err.println(Messages.Task.notFound);
      return;
    }

    State newState = stateModel.getByName(newStateName);
    if (newState == null) {
      System.err.println(Messages.State.notFound);
      return;
    }
    String sqlUpdate = "UPDATE tasks SET state_id = ? WHERE id = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlUpdate)) {
      stmt.setInt(1, newState.id);
      stmt.setInt(2, taskId);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Change task state error: " + e.getMessage());
    }
  }

  public void delete(int taskId) {
    if (getById(taskId) == null) {
      System.err.println(Messages.Task.notFound);
      return;
    }
    String sqlDelete = "DELETE FROM tasks WHERE id = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlDelete)) {
      stmt.setInt(1, taskId);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Delete task error: " + e.getMessage());
    }
  }
}
