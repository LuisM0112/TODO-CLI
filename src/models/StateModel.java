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
    ArrayList<State> stateList = new ArrayList<State>();

    try (Statement stmt = dbConnection.createStatement()) {
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

  public State getByName(String name) {
    State state = null;

    String sqlSelect = "SELECT * FROM states WHERE name = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlSelect)) {
      stmt.setString(1, name);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        state = new State(rs.getInt("id"), rs.getString("name"));
      }
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }

    return state;
  }

  public void create(String name) {
    String sqlInsert = "INSERT INTO states(name) VALUES(?)";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlInsert)) {
      stmt.setString(1, name);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }

  public void update(String prevName, String newName) {
    String sqlUpdate = "UPDATE states SET name = ? WHERE name = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlUpdate)) {
      stmt.setString(1, newName);
      stmt.setString(2, prevName);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }

  public void delete(String name) {
    String sqlDelete = "DELETE FROM states WHERE name = ?";
    try (PreparedStatement stmt = dbConnection.prepareStatement(sqlDelete)) {
      stmt.setString(1, name);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }
}
