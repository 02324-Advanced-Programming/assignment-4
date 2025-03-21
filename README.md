# Roborally

## 4a
To complete Assignment 4a, we started by downloading the provided project (assignment4.zip) from DTU Learn and setting it up in IntelliJ. This involved extracting the ZIP file and opening the project using its pom.xml file to ensure Maven dependencies were properly configured. Additionally, we initialized a Git repository and pushed the project to a remote repository for version control and collaboration.

Next we focused on understanding the project structure, the architecture and the core concepts and rules of RoboRally. 
We discussed the key classes in the model and controller package relevant to this assignment in further detail:
- **Board**: Represents the game board and manages spaces and players. This is where the data combines for the state of the game.
- **Space**: Represents individual fields on the board where players can move.
- **Player**: Represents a player’s robot and information relevant about them + additional methods.
- **GameController**: Manages game logic, including player turns and movement.

We added JavaDoc comments to these classes and their relevant methods to improve clarity and future maintainability.

To enable player movement on clicking an empty space:
1. Located the `moveCurrentPlayerToSpace()` method in `GameController`, which was marked with a TODO comment.
2. Retrieved the current player using `Board.getCurrentPlayer()`.
3. We checked if the clicked space was empty.
4. If the space was empty, we updated the player’s position and incremented the move counter.
5. We updated the board state by setting the next player as the current player.
6. We tested this functionality using the provided unit test and manually verified the expected behavior in the GUI.

Next, I introduced a move counter in the `Board` class:
1. Added an integer attribute `private int counter;`.
2. Created getter and setter methods (`getCounter()` and `setCounter(int count)`).
3. Modified `setCounter()` to increment the counter and call `notifyChange()` to update the GUI.
4. Integrated the counter increment into `moveCurrentPlayerToSpace()` so that every successful move increases the count.

To reflect the move counter in the game’s status bar:
1. Modified `Board.getStatusMessage()` to append the move count.
2. Ensured the GUI updates properly when a move is made.

We manually tested the implementation by running the game and ensuring:
- Players could move to empty spaces.
- The move counter updated correctly.
- The correct player’s turn was displayed.
- The unit test passed successfully.


## 4B 
In this solution, we implemented the BoardFactory class to support multiple boards for the RoboRally game.

#### The primary goal for the BoardFactory was to implement two methods:
- `getBoardNames()`: This method provides a list of all available board names as strings. It will be used in the app to display the list of options to the user when starting a new game.
- `createBoard(String name)`: This method creates a new board based on the provided name. It retrieves the corresponding creation function from the internal map (boardCreators) and uses it to create the desired board. If the name is not recognized, the method throws an exception to handle invalid inputs. 
- The following subsections describe how we achieved this functionality

#### Board Management with a Map:
- The `boardCreators` map is used to register and manage board creation functions. Each board name is associated with its respective creation.
- This setup makes it easy to add new boards in the future. New board types can be registered using the `registerBoard()` method, where the name and creation function are mapped together.

#### Boards:
- In the private constructor of the `BoardFactory`, predefined boards are registered using `registerBoard()`. Each type is linked to its corresponding creation function, such as createDefaultBoard or createAdvancedBoard.

#### Finally, the `BoardFactory` is integrated into the AppController. In the `newGame()` method:
- A choice dialog is displayed to the user, showing the list of available board names provided by getBoardNames().
- The user's selection is passed to `createBoard(String name)` to create the corresponding board.
- The new game is then started on the created board.

To complete the second part of the task, the game board was updated to have the spaceView class include walls, conveyor belts, and checkpoints, to display them in the GUI.

#### Walls Implementation:
- The `drawWalls()` method adds visual representations of walls to the board. Depending on the wall's heading (`NORTH`, `SOUTH`, `EAST`, `WEST`), a red line is drawn on the appropriate edge of the space and are added to the Pane.
- The `drawFieldActions()` method iterates through all field actions associated with a space and calls specific methods to draw its action.
- FieldAction Conveyor Belts:
- The `drawConveyorBelt()` method draws a gray arrow representing the conveyor belt, with its given heading. 

#### FieldAction Checkpoints:
- A new `CheckPoint` class was created (similar to the `conveyorBelt` class) to represent checkpoints. Each checkpoint has a number associated with it.
- The `drawCheckPoint()` method in the spaceView class, draws a yellow circle in the center of the space to represent the checkpoint. The checkpoint number is displayed within the circle.
- The `drawWalls()` and `drawFieldAction()` are not in the `updateView()`.
- The idea is that we only need to draw the walls and field actions once per game so that's why we drew them in the constructor of each spaceView object.
- This approach ensures that all board features (walls, conveyor belts, and checkpoints) are visually represented in the GUI.

## 4c
Overall - in the broader perspective - this assignment 4c regards how a robot is moved by a GameController, especially in accordance with other robots, walls, and conveyor-belts;
they all have implications on the further movement of the robot.
The GameController handles most of the coordination and controls between the parts.

This task 4c has two different overall features:
(1) Stepwise movement and checking for an occupied space via the `getNeighbour`-function, and
(2) pre-programmed movement using the commmand-cards found in the bottom of the game-GUI.
A further detail is that the pre-programmed movement builds upon the stepwise-movement, which will be presented first.

(1) Stepwise movement - and checking for `getNeighbour()` spaces: 
Before the robot moves to a field, we check the neighbour-space's emptiness via the `getNeighbour(space, heading)` function.
Besides this, we check that there is no wall between the starting and its end spaces.

To rotate the robot, if necessary, we use the next() and prev() functions;
these simply change the textual property of our robot, so that the board and GUI can orient the robot according to its heading. 

(2) The automation of the robot movements:
Now, we are ready to proceed into the more automated part of these robot movements.
To make the robot go by itself, we handle its movements in 3 separate stages:
* "Finish programming" 
* "Execute program"
* "Execute Current Register"

When the user drags the randomly-generated cards from the bottom of the screen to the playing-cards-fields just above the source-fields, this will dictate the behaviour of the robot’s next movements; 
i.e. when the user clicks the “Finish programming”-button, the game progresses. 
This specific functionality is handled in the PlayerView's `finishButton.seOnAction()`. 
This notifies the GameController to save the cards to be executed upon.
Then, the command-card will be executed in order, while the game is in stepmode. 
This means that it will keep executing the remaining cards, step-by-step, until done.

When the `executeButton` is clicked, the GameController's `exeutePrograms()` will be run and follow-through all the cards that were set-up during the `programming`-phase.
Next follows the `activation`-phase where the card-actions are run inside the game.  
Here, as previously mentioned, all the enqueued cards are executed until no more are left. 

Alternatively - if the user only wishes to play a single command-card from the queue, the user simply clicks the `stepButton`.
This will make the GameController run its `executeStep()`-function. 
The `executeStep()`-function ensures that the user can follow along - stepwise, as the board is set in stepMode via the `board.setStepMode(true)`. 
Here, the game executes each step separately on the user's request and demand.

A quick note is that the `notImplemented()`-functions have been replaced by the eventhandler-functions; so, not implemented is officially deprecated and being get rid of…
We have replaced the `notImplemented()`-function with the `setOnAction`-eventHandler-functions.
This is given the parameters `finishProgrammingPhase()`, `executePrograms()`, and `executeStep()`, which guarantees that all the functionality is fully compatible with our overall design.   


## 4d
#### Implementation
The task of pushing robots when a robot moves is implemented using the recursive method `moveToSpace()`. This method ensures that if a robot encounters another robot in its path, it pushes the robot (and any others in front of it) forward, as long as space is available.

The `moveToSpace()` method works as follows:
* It checks if the target space already has a robot.
* If a robot is present, the method recursively attempts to move the pushed robot to the next space in the same heading.
* If the next space is unavailable, an `ImpossibleMoveException` is thrown, indicating the move is not possible.
* Once there are no robots in the target space, the pusher robot is placed in that space using `pusher.setSpace(space)`.

The `executeNextStep()` method is a crucial part of the game logic in the game. It handles the execution of command cards for the current player, manages the transition between players, and ensures that field actions are executed at the correct times.
1. Command Card Execution:
   * The method starts by checking if the game is in the `ACTIVATION` phase and if there is a current player.
   * It retrieves the current step and the command card for the current player.
   * If the command card is `LEFT_OR_RIGHT`, the game phase is set to `PLAYER_INTERACTION`, and the method returns to wait for player input.
   * Otherwise, the command is executed using the `executeCommand()` method.
2. Player Transition:
   * After executing the command, the method checks if there are more players to execute the current step.
   * If there are more players, it sets the next player as the current player.
   * If all players have executed the current step, it proceeds to execute field actions.
3. Field Actions:
   * The `executeFieldActions()` method is called to handle any actions associated with the spaces on the board.
   * This ensures that field actions are executed after all players have completed their commands for the current step.
4. Phase Transition:
   * After executing field actions, the method checks if the game phase has changed to `WINNER`.
   * If the game phase is `WINNER`, a popup is displayed with the winning message.
   * If the game phase is still `ACTIVATION`, the method increments the step and makes the program fields visible for the next step.
   * If all steps have been executed, the game transitions back to the `PROGRAMMING` phase.

The `executeCommand()` method in the GameController class is responsible for executing a specific command for a given player. This method takes two parameters: a Player object and a Command object. The method checks if the player is on the current board and if the command is not null. Depending on the command, it calls the appropriate method to perform the action.

1. Check Player and Command:
   * Ensure the player is on the current board and the command is not null.
2. Switch Statement:
   * Use a switch statement to determine which command to execute.
3. Execute Command: 
   * Call the corresponding method for each command: 
     * `FORWARD`: Calls `moveForward(player)`. 
     * `RIGHT`: Calls `turnRight(player)`.
     * `LEFT`: Calls `turnLeft(player)`. 
     * `FAST_FORWARD`: Calls `fastForward(player)`. 
     * `UTURN`: Calls `uTurn(player)`. 
     * `BACKWARD`: Calls `moveBackward(player)`.

#### Error Handling:

The move forward/fastforward and move backward methods uses a try/catch block to handle exceptions (ImpossibleMoveException) that may arise if a robot cannot be pushed further (due to walls).

## 4e
#### Winning the game 
The Checkpoint class (Controller) contains the main logic, ensuring players pass through checkpoints in the correct order and awarding points when appropriate. Upon reaching the final checkpoint, the game transitions to the winner phase. In the Board class (model), we implemented the logic to generate the string that announces the winner. Additionally, in the GameController class, within the `executeNextStep()` method, a check is performed to see if the phase has shifted to `WINNER`. If so, a popup displaying the wonMessage is shown, signaling the end of the game.

#### Interactive command cards.
* Normal flow of the game must be interrupted when a Turn Right or Left command is executed 
* To implement, we check the card type in `exeucteNextStep()` before executing the command.
* If it is, we set the game phase to interactive mode, and notify the PlayerView to rebuild the buttons for UI
* Once player clicks on a button, this calls a method to execute the command they wanted (either turn left or turn right)
  * We change the game state back to Activation, and must either allow all other players to continue their turn
  * Or if all players have finished their turn, we need to go back to the programming phase.
* We need to add a new method in the GameController to handle the interactive phase, and a new method in the PlayerView to rebuild the buttons.
* To implement, we keep track of the number of steps in the current round of execution. 
  * If the number of steps is less than the total number of card registers, we are still inside a round, so all players must have their turn to execute their remaining registers
  * else if the number of steps is greater than the number of card registers, the round is over, so we go back to the programming phase to start a new round 

#### Feature: Wrap-wround out-of-bounds position
If the player moves of the board, the player's position wraps around to the other side.


# GIT COMMANDS 
* first make sure you are in the correct folder on your local device (do not put the < > when running commands)
```bash 
cd </path/to/your/folder>
````
### Pull (Download changes from remote repo):


```bash
git pull 
```
* if the above command doesn't work it is usually bc your local branch is not configured properly
* first way to solve this issue is to specify which branch to pull from. 
* If you do this then you have to specify the name of branch every time you pull: 
```bash 
git pull origin <branch-name you want to pull from>
```

* alternatively, 
```bash 
* git branch --set-upstream-to=origin/<branch name>
```
* if you do this then from now on you only need to run "git pull" without having to specify the branch name 


* after pulling, resolve any merge conflicts 

### Branches :
* see what branch you are currently on 
```bash
git branch
```
* to create a new branch:
```bash 
  git checkout -b <branch name> 
```
* to switch to a new branch : 
```bash 
git checkout <branch name>
```

### PUSH
* You need to be on the branch where you've made changes and want to push to . 
* check which branch you're currently on (git branch)
* once on desired branch
* First, ADD your changes 
```bash
git add . 
```
* Second, commit 
```bash
git commit -m "commit message"
```
* Third, push to the branch of your choice 
```bash
git push origin <name of branch you want to push to>
```

## Extra 
* if you know you will keep pushing to the same branch in future (after adding and committing) 
```bash
git push -u origin <branch name>
```
* now when you want to push to the same remote branch again, you can just run
```bash
git push
```
* instead of having to write the -u origin etc 

### more stuff 
* if you have messed up your cloning with the wrong url or something you can reset the origin
```bash 
git remote set-url origin <repo url>
```

* *How to add a new origin
``` bash
git remote add origin <repo url>
```