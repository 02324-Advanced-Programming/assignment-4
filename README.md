# RoboRally

## 4a
To complete Assignment 4a, we started by downloading the provided project (`assignment4.zip`) from DTU Learn and setting it up in IntelliJ. This involved extracting the ZIP file and opening the project using its `pom.xml` file to ensure Maven dependencies were properly configured. Additionally, we initialized a Git repository and pushed the project to a remote repository for version control and collaboration.

Next, we focused on understanding the project structure, its architecture, and the core concepts and rules of RoboRally. We discussed the key classes in the `model` and `controller` packages relevant to this assignment in further detail:

- **Board**: Represents the game board and manages spaces and players. This is where the game state is maintained.
- **Space**: Represents individual fields on the board where players can move.
- **Player**: Represents a player’s robot and relevant information, including movement methods.
- **GameController**: Manages game logic, including player turns and movement.

We added JavaDoc comments to these classes and their relevant methods to improve clarity and maintainability.

### Implementing Player Movement
To enable player movement when clicking an empty space:
1. Located the `moveCurrentPlayerToSpace()` method in `GameController`, which was marked with a TODO comment.
2. Retrieved the current player using `Board.getCurrentPlayer()`.
3. Checked if the clicked space was empty.
4. If the space was empty, updated the player’s position and incremented the move counter.
5. Updated the board state by setting the next player as the current player.
6. Tested this functionality using the provided unit test and manually verified expected behavior in the GUI.

### Move Counter Implementation
We introduced a move counter in the `Board` class:
1. Added an integer attribute `private int counter;`.
2. Created getter and setter methods (`getCounter()` and `setCounter(int count)`).
3. Modified `setCounter()` to increment the counter and call `notifyChange()` to update the GUI.
4. Integrated counter increment into `moveCurrentPlayerToSpace()` so that every successful move increases the count.

To reflect the move counter in the game’s status bar:
1. Modified `Board.getStatusMessage()` to append the move count.
2. Ensured the GUI updates properly when a move is made.

We manually tested the implementation by running the game and ensuring:
- Players could move to empty spaces.
- The move counter updated correctly.
- The correct player’s turn was displayed.
- The unit test passed successfully.

## 4b
In this solution, we implemented the `BoardFactory` class to support multiple boards for the RoboRally game.

### BoardFactory Implementation
The primary goal for the `BoardFactory` was to implement two methods:

- **`getBoardNames()`**: Provides a list of all available board names as strings. This is used in the app to display available options when starting a new game.
- **`createBoard(String name)`**: Creates a new board based on the provided name. It retrieves the corresponding creation function from the internal map (`boardCreators`) and uses it to create the desired board. If the name is not recognized, the method throws an exception to handle invalid inputs.

### Board Management with a Map
The `boardCreators` map registers and manages board creation functions. Each board name is associated with its respective creation function, making it easy to add new boards in the future. New board types can be registered using the `registerBoard()` method, mapping the name to its creation function.

### Boards
In the private constructor of `BoardFactory`, predefined boards are registered using `registerBoard()`. Each type is linked to its corresponding creation function, such as `createDefaultBoard` or `createAdvancedBoard`.

### Integrating BoardFactory into AppController
In the `newGame()` method:
1. A choice dialog is displayed to the user, showing the available board names from `getBoardNames()`.
2. The user’s selection is passed to `createBoard(String name)` to create the corresponding board.
3. The new game is then started on the created board.

### Updating the Board with Walls, Conveyor Belts, and Checkpoints

**Walls Implementation:**
- The `drawWalls()` method adds visual representations of walls on the board.
- Walls are drawn based on their heading (`NORTH`, `SOUTH`, `EAST`, `WEST`).
- The `drawWalls()` method is called once in the `SpaceView` constructor.

**Conveyor Belts:**
- The `drawConveyorBelt()` method draws a gray arrow representing the conveyor belt's heading.

**Checkpoints:**
- A new `CheckPoint` class was created to represent checkpoints, similar to `ConveyorBelt`.
- The `drawCheckPoint()` method in `SpaceView` draws a yellow circle in the center of a space with the checkpoint number inside it.

## 4c
This part of the assignment focuses on robot movement, including interactions with other robots, walls, and conveyor belts. The `GameController` is responsible for coordinating movement and enforcing game rules.

### Stepwise Movement and Checking for `getNeighbour()` Spaces
- Before a robot moves to a field, the `getNeighbour(space, heading)` function checks whether the space is empty.
- Walls between the start and destination spaces are also checked.
- Robot rotation is handled using `next()` and `prev()` functions, updating the textual property of the robot to match its heading.

### Pre-Programmed Movement with Command Cards
Robot movement consists of three phases:
1. **Finish Programming:** The user drags command cards into the register slots and clicks “Finish Programming.” The `finishButton.setOnAction()` method saves the commands.
2. **Execute Program:** The `executeButton` triggers `executePrograms()`, processing all programmed commands.
3. **Execute Current Register:** If the user wants to execute a single command at a time, they click `stepButton`, which runs `executeStep()`, executing one command at a time.

## 4d
### Pushing Robots
Robot pushing is handled using the recursive `moveToSpace()` method:
- If a robot is in the target space, `moveToSpace()` recursively moves the obstructing robot forward.
- If no space is available, an `ImpossibleMoveException` is thrown.
- Once the path is clear, the pusher robot moves into the space.

### Executing Next Step
- Commands are executed in order, transitioning between players until all commands are executed.
- After command execution, `executeFieldActions()` processes board-specific actions.
- If a player wins, a pop-up message is displayed.

## 4e
### Winning the Game
- The `Checkpoint` class tracks player progress through checkpoints.
- When a player reaches the final checkpoint, the game transitions to the `WINNER` phase, displaying a pop-up message.

### Interactive Command Cards
- If a player plays a `TURN_LEFT` or `TURN_RIGHT` card, the game enters interactive mode.
- The player selects the desired direction, after which the game resumes normal execution.
- The `GameController` and `PlayerView` were modified to support this phase.

### Wrap-Around Movement
- If a player moves off the board, they reappear on the opposite side, maintaining their heading.

