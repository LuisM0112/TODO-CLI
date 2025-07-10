package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class Database {
  public static void initialize(Connection conn) {

    Statement stmt;
    String sqlCreate = """
      CREATE TABLE IF NOT EXISTS states (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL
      );
      CREATE TABLE IF NOT EXISTS tasks (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        description TEXT,
        state_id INTEGER NOT NULL,
        FOREIGN KEY (state_id) REFERENCES states (id)
      );
    """;

    try {
      stmt = conn.createStatement();
      stmt.execute(sqlCreate);
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Error con SQLite: " + e.getMessage());
    }
  }
}
