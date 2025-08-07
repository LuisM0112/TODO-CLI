package com.todo.cli.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class Database {
  public static void initialize(Connection conn) {

    String sqlCreateStates = """
      CREATE TABLE IF NOT EXISTS states (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL UNIQUE
        );
        """;
        String sqlCreateTasks = """
          CREATE TABLE IF NOT EXISTS tasks (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            task_number INTEGER NOT NULL UNIQUE,
            description TEXT,
            creation_date TEXT NOT NULL,
            state_id INTEGER NOT NULL,
            FOREIGN KEY (state_id) REFERENCES states (id)
            );
            """;
            
    Statement stmt;

    try {
      stmt = conn.createStatement();
      stmt.execute(sqlCreateStates);
      stmt.execute(sqlCreateTasks);
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }
  public static void createDefaultState(Connection conn){
    String sqlAddDefaultState = "INSERT INTO states(name) VALUES(\"default\");";

    Statement stmt;

    try {
      stmt = conn.createStatement();
      stmt.executeUpdate(sqlAddDefaultState);
      stmt.close();
      System.out.println("Default state added");
    } catch (SQLException e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }
}
