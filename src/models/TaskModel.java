package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import models.definitions.Task;

public class TaskModel {
  
  private Connection dbConnection;

  public TaskModel(Connection dbConnection) {
    this.dbConnection = dbConnection;
  }

  public ArrayList<Task> getAll() {
    Statement stmt;
    ArrayList<Task> taskList = new ArrayList<Task>();

    try {
      stmt = dbConnection.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT * FROM tasks");
      while (rs.next()) {
        taskList.add(new Task(
          rs.getInt("id"),
          rs.getString("name"),
          rs.getString("description"),
          rs.getString("name")
        ));
      }
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }

    return taskList;
  }

  public void create(String name, String description, String stateName) {
    Statement stmt;
    String sqlInsert = "INSERT INTO tasks(name, description, state) VALUES(?, ?, ?)";
    try {
      stmt = dbConnection.createStatement();
      PreparedStatement pStmt = dbConnection.prepareStatement(sqlInsert);

      pStmt.setString(1, name);
      pStmt.setString(2, description);
      pStmt.setString(3, stateName);
      pStmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }

  public void update(String prevName, String newName) {
    Statement stmt;
    String sqlUpdate = "UPDATE tasks SET name = ? WHERE name = ?";
    try {
      stmt = dbConnection.createStatement();
      PreparedStatement pStmt = dbConnection.prepareStatement(sqlUpdate);

      pStmt.setString(1, newName);
      pStmt.setString(2, prevName);
      pStmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }

  public void delete(String name) {
    Statement stmt;
    String sqlDelete = "DELETE FROM tasks WHERE name = ?";
    try {
      stmt = dbConnection.createStatement();
      PreparedStatement pStmt = dbConnection.prepareStatement(sqlDelete);

      pStmt.setString(1, name);
      pStmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }
}
