package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import models.definitions.State;

public class StateModel {

  private Connection dbConnection;

  public StateModel(Connection dbConnection) {
    this.dbConnection = dbConnection;
  }

  public ArrayList<State> getAll() {
    Statement stmt;
    ArrayList<State> stateList = new ArrayList<State>();

    try {
      stmt = dbConnection.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT * FROM states");
      while (rs.next()) {
        stateList.add(new State(
          rs.getInt("id"),
          rs.getString("name")
        ));
      }
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }

    return stateList;
  }

  public void create(String name) {
    Statement stmt;
    String sqlInsert = "INSERT INTO states(name) VALUES(?)";
    try {
      stmt = dbConnection.createStatement();
      PreparedStatement pStmt = dbConnection.prepareStatement(sqlInsert);

      pStmt.setString(1, name);
      pStmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }

  public void update(String prevName, String newName) {
    Statement stmt;
    String sqlUpdate = "UPDATE states SET name = ? WHERE name = ?";
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
    String sqlDelete = "DELETE FROM states WHERE name = ?";
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
