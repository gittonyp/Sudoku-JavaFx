Sudoku Game

This is a Sudoku game built using JavaFX for the user interface and Java for the game logic. The project features a Sudoku generator and solver, allowing the user to play, check, and solve Sudoku puzzles directly in the application.
Features

    Sudoku Puzzle Generation: The game generates a solvable Sudoku puzzle with a random number of missing digits.
    Sudoku Solver: The application can solve a given Sudoku puzzle using a backtracking algorithm.
    Puzzle Validation: You can check whether the current solution entered by the user is correct or not.
    User Interface: The game is powered by a user-friendly JavaFX interface, where users can interact with the Sudoku grid.

Components
1. SudokuGameUi - The JavaFX UI Class

    Displays the Sudoku grid on a JavaFX GridPane.
    Handles user input and interaction (e.g., solving the puzzle, checking the solution).
    Provides an intuitive interface with a "Solve" button to automatically fill in the solution and a "Check" button to verify the user's current solution.
    Ensures that users can only input valid digits (1-9) into empty cells.

2. Sudoku - The Sudoku Puzzle Generator

    Responsible for generating the initial puzzle grid by filling diagonal blocks and solving the puzzle.
    Randomly removes a number of cells to create a valid puzzle for the player.
    Includes methods for validating if a number can be placed in a cell without violating Sudoku rules (row, column, and 3x3 box).

3. SudokuSolver - The Sudoku Solver

    Implements the backtracking algorithm to solve the Sudoku puzzle.
    Provides a method checksolution to validate the current board against Sudoku rules (no duplicates in rows, columns, or boxes).
    Uses recursion to attempt solving the puzzle and backtracks when a solution is not possible.

Prerequisites

Before running the project, make sure you have the following installed:

    Java 19 or higher
    Maven (for managing dependencies)
    JavaFX SDK 19

Getting Started

    Clone the repository:

git clone https://github.com/yourusername/sudoku.git
cd sudoku

Build the project: Use Maven to build the project:

mvn clean install

Run the application: To launch the Sudoku game, use the following Maven command:

    mvn javafx:run

    Play the Game:
        The Sudoku puzzle will appear in a JavaFX window.
        Enter your solution in the empty cells, and press "Check" to verify if it's correct.
        Press "Solve" to have the application automatically solve the puzzle for you.

Project Structure

The project follows a basic Maven structure:

    src/main/java/com/example/sudoku: Contains the Java source files (SudokuGameUi, Sudoku, SudokuSolver).
    src/main/resources: Contains any required resources (if applicable).
    pom.xml: Maven project configuration file, including dependencies for JavaFX, JUnit (for testing), and the JavaFX Maven plugin.

Dependencies

The project uses the following key dependencies:

    JavaFX:
        javafx-controls for the UI components.
        javafx-fxml if you need FXML support in the future.
    JUnit for unit testing.

Dependencies are managed via Maven, and the project should build and run without any additional setup once you have Maven and JavaFX properly installed.
Running Tests

Unit tests can be executed using Maven's test phase:

mvn test

The project includes basic test configurations for verifying Sudoku validation and solving functionalities.
Contributing

If you'd like to contribute to the project, feel free to fork the repository and create a pull request with your changes. When contributing, please ensure your code is well-documented and follows the project’s style conventions.
