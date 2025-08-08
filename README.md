# TODO-CLI
A simple TODO cli made with java

## Requirements
- Java 8+ installed

## 🧩 Download

- Download the latest release in: [Releases](https://github.com/LuisM0112/TODO-CLI/releases).

## ▶️ Usage

| Action               | Command                              |
|----------------------|--------------------------------------|
| Help                 | `-h`                                 |
| Create default state | `--init`                             |
| List tasks           | `-l`                                 |
| Get task             | `-g <task number>`                   |
| New task             | `-n <task description> <state>`      |
| Update task          | `-u <task number> <new description>` |
| Delete task          | `-d <task number>`                   |
| List states          | `-lS`                                |
| Get state            | `-gS <state id>`                     |
| New state            | `-nS <state name>`                   |
| Update state         | `-uS <state id> <new name>`          |
| Delete state         | `-dS <state id>`                     |
| Output file          | `-o <file format>`                   |

Supported file formats: csv

### Run example:

```bash
java -jar todo-cli.jar -nS "pending" -n "New task" pending -l
```

## Source

### Build tool
- Maven

### Dependencies
- sqlite-jdbc - 3.50.2.0
  - [Maven repository](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc)
  - [GitHub](https://github.com/xerial/sqlite-jdbc)

## Package
`mvn clean package`
