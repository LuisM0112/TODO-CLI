package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import helpers.Messages;
import models.definitions.State;
import models.definitions.Task;

public class TaskModel {
  
  private Connection dbConnection;
  StateModel stateModel;

  public TaskModel(Connection dbConnection, StateModel stateModel) {
    this.dbConnection = dbConnection;
    this.stateModel = stateModel;
  }

  private String getDate(String dateRaw) {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
    LocalDateTime dateTime = LocalDateTime.parse(dateRaw);
    String date = dateTime.format(dateFormat);
    return date;
  }

  private int getLastTaskNumber() {
    int taskNumber = 0;

    String sqlSelect = "SELECT MAX(task_number) AS task_number FROM tasks";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlSelect)) {
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        taskNumber = rs.getInt("task_number");
      }
      stmt.close();
    } catch (Exception e) {
      System.out.println("Get task number error: " + e.getMessage());
    }

    return taskNumber;
  }

  private void updateTaskNumber(int taskId, int taskNumber) {
    String sqlUpdate = "UPDATE tasks SET task_number = ? WHERE id = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlUpdate)) {
      stmt.setInt(1, taskNumber);
      stmt.setInt(2, taskId);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Update task number error: " + e.getMessage());
    }
  }

  public void updateTasksNumbers() {
    ArrayList<Task> taskList = getAll();
    for (int i = 0; i < taskList.size(); i++) {
      Task task = taskList.get(i);
      int expectedTaskNumber = i + 1;
      if (task.taskNumber != expectedTaskNumber) {
        task.taskNumber = expectedTaskNumber;
        updateTaskNumber(task.id, task.taskNumber);
      }
    }
  }

  public ArrayList<Task> getAll() {
    ArrayList<Task> taskList = new ArrayList<Task>();

    try (Statement stmt = dbConnection.createStatement()) {
      ResultSet rs = stmt.executeQuery("SELECT * FROM tasks");
      while (rs.next()) {
        State taskState = stateModel.getById(rs.getInt("state_id"));
        String dateRaw = rs.getString("creation_date");
        taskList.add(new Task(
          rs.getInt("id"),
          rs.getInt("task_number"),
          rs.getString("description"),
          taskState.name,
          getDate(dateRaw)
        ));
      }
      stmt.close();
    } catch (Exception e) {
      System.out.println("Get task list error: " + e.getMessage());
    }

    return taskList;
  }

  public Task getByTaskNumber(int taskNumber) {
    Task task = null;

    String sqlSelect = "SELECT * FROM tasks WHERE task_number = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlSelect)) {
      stmt.setInt(1, taskNumber);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        State taskState = stateModel.getById(rs.getInt("state_id"));
        String dateRaw = rs.getString("creation_date");
        task = new Task(
          rs.getInt("id"),
          rs.getInt("task_number"),
          rs.getString("description"),
          taskState.name,
          getDate(dateRaw)
        );
      }
      stmt.close();
    } catch (Exception e) {
      System.out.println("Get task error: " + e.getMessage());
    }

    return task;
  }

  public int create(String description, String stateName) {
    State state = stateModel.getByName(stateName);
    if (state == null) {
      System.err.println(Messages.State.notFound);
      return 0;
    }

    int taskNumber = getLastTaskNumber() + 1;
    String date = LocalDateTime.now().toString();

    String sqlInsert = "INSERT INTO tasks(task_number, description, creation_date, state_id) VALUES(?, ?, ?, ?)";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlInsert)) {
      stmt.setInt(1, taskNumber);
      stmt.setString(2, description);
      stmt.setString(3, date);
      stmt.setInt(4, state.id);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Create task error: " + e.getMessage());
    }
    return taskNumber;
  }

  public int update(int taskNumber, String newDescription) {
    if (getByTaskNumber(taskNumber) == null) {
      System.err.println(Messages.Task.notFound);
      return 0;
    }
    String sqlUpdate = "UPDATE tasks SET description = ? WHERE task_number = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlUpdate)) {
      stmt.setString(1, newDescription);
      stmt.setInt(2, taskNumber);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Update task error: " + e.getMessage());
    }
    return taskNumber;
  }

  public int changeState(int taskNumber, String newStateName) {
    if (getByTaskNumber(taskNumber) == null) {
      System.err.println(Messages.Task.notFound);
      return 0;
    }

    State newState = stateModel.getByName(newStateName);
    if (newState == null) {
      System.err.println(Messages.State.notFound);
      return 0;
    }
    String sqlUpdate = "UPDATE tasks SET state_id = ? WHERE task_number = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlUpdate)) {
      stmt.setInt(1, newState.id);
      stmt.setInt(2, taskNumber);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Change task state error: " + e.getMessage());
    }
    return taskNumber;
  }

  public int delete(int taskNumber) {
    if (getByTaskNumber(taskNumber) == null) {
      System.err.println(Messages.Task.notFound);
      return 0;
    }
    String sqlDelete = "DELETE FROM tasks WHERE task_number = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlDelete)) {
      stmt.setInt(1, taskNumber);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Delete task error: " + e.getMessage());
    }
    return taskNumber;
  }
}
