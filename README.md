## Requisitos
- Java 8+ instalado
- No se necesita instalar SQLite: el `.jar` ya lo incluye

## Compilar
javac -cp "lib/sqlite-jdbc-3.50.2.0.jar" -d bin src/App.java

## Ejecutar
java -cp "bin:lib/sqlite-jdbc-3.50.2.0.jar" App
