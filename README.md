# Roborally

# TODO


# DELEGATIONS
* Ben:  
* Josh: 
* Laxmanan:  
* Rasmus:   
* Kasper: 



    The functionality requested at each assignment should be present in the game as described in the statement of each assignment. In short:
        4a: move player to space by clicking, changing turns and counting moves shown in the status bar;
        4b: standard and advanced board generation at game start;
        4c: executing commands cards and programs, including two new command cards;
        4d: field actions and player pushing;
        4e: winning the game and interactive command cards.
    No exceptions should appear in the console at any point (test test test!)
    All tests must be passing, including the additional tests you have implemented. In detail:
        there should be tests for the functionality added in classes of the model and controller packages to do the assignments;
        you should have 100% coverage of code in the GameController class, excluding only assert false statements and eventual dead code;
        you should have 100% coverage of the doAction() methods of the ConveyorBelt and CheckPoint classes, and any other class implemented by you that extends FieldAction;
        all tests mentioned above should pass without errors.

    You must provide JavaDoc comments in:
        all methods and classes implemented by you, e.g. the CheckPoint class and the moveForward() method;
        all methods and classes where you wrote code in, e.g. the PlayerView class and the executeNextStep() method.





    Write a README.txt (or .md or .pdf) file and add it at top level inside the roborally/ directory.
        Structure the file per assignment, making one section for assignment 4a, another for assignment 4b, etc.
        For each assignment, briefly explain what did you do to implement your solutions. As a rule of thumb for the level of detail you should go into, the description for each assignment should be between half a page and one page (A4), written in Arial 10 with 2 cm margins on every side.
        If you have implemented any extra functionality over the basic requirements, e.g. special graphics, extra command cards, more field actions, etc., comment on these on an "Extras" subsection within the corresponding assignment section (for extras, go wild on details if you want).

## 4a Benjamin
### Implementation
For each assignment, briefly explain what did you do to implement your solutions. As a rule of thumb for the level of detail you should go into, the description for each assignment should be between half a page and one page (A4), written in Arial 10 with 2 cm margins on every side.

### Extra functionality
If you have implemented any extra functionality over the basic requirements, e.g. special graphics, extra command cards, more field actions, etc., comment on these on an "Extras" subsection within the corresponding assignment section (for extras, go wild on details if you want).


## 4B Rasmus
### Implementation
For each assignment, briefly explain what did you do to implement your solutions. As a rule of thumb for the level of detail you should go into, the description for each assignment should be between half a page and one page (A4), written in Arial 10 with 2 cm margins on every side.

### Extra functionality
If you have implemented any extra functionality over the basic requirements, e.g. special graphics, extra command cards, more field actions, etc., comment on these on an "Extras" subsection within the corresponding assignment section (for extras, go wild on details if you want).


## 4c Kasper
### Implementation
For each assignment, briefly explain what did you do to implement your solutions. As a rule of thumb for the level of detail you should go into, the description for each assignment should be between half a page and one page (A4), written in Arial 10 with 2 cm margins on every side.

### Extra functionality
If you have implemented any extra functionality over the basic requirements, e.g. special graphics, extra command cards, more field actions, etc., comment on these on an "Extras" subsection within the corresponding assignment section (for extras, go wild on details if you want).
____________________________
Overall - in the broader perspective - this assignment 4c regards how a robot moves, especially in accordance with other robots, walls, and conveyor-belts; further movement.
The Gamecontroller handles most of the coordination and controls between the parts.

So, before the robot moves to a field, we check - via the getNeighbour(space, heading) function - that the neighbouring field (the one referenced from the space-param) is both empty, and there is no wall between the two fields, and that the neighbouring field - from its own perspective - has no wall either; because then, our robot can move to that field/space.

The other part of the movement regards how we can pre-program the robot movement.
We do so, by dragging the randomly-generated cards from the bottom of the screen, to the playing-cards-fields just above the fields from which we drew the cards, will dictate the behaviour of the robot’s next movements; i.e. when we click the “Finish programming”-butt.
Some of the most important parts of this interactive addition to the game, is that we have these 3 buttons for action for when the player has their turn - then, the card-actions will be executed in order, as the game is in stepmode, meaning that it will keep executing the cards, step-by-step until done.
These 3 action-buttons are implemented through eventHandlers on each button (via the setOnAction), which ensures to then notify (and also calling the functions in) the game-controller-class  via the StartProgrammingPhase(), executePrograms(), and executeStep(); the implementations for each of them are found in the game-controller-class.
The exeutePrograms() and executeStep() are used to implement that the playing-cards - all of them - will be executed on the robot - now that the stepmode is enabled; thus, all playing-cards are executed in order, and then it will be the next player’s cards to be taken into action after that.

Also, it should be noted that the functions next() and prev() are direction-oriented; they are some abbr. for nextHeading and prevHeading - they shift to the next or prev enum-heading, we shift our direction.

A quick note is that the notImplemented()-functions have been replaced by the eventhandler-functions; so, not implemented is officially deprecated and being get rid of…


## 4d Lauritz
### Implementation

For each assignment, briefly explain what did you do to implement your solutions. As a rule of thumb for the level of detail you should go into, the description for each assignment should be between half a page and one page (A4), written in Arial 10 with 2 cm margins on every side.

### Extra functionality
If you have implemented any extra functionality over the basic requirements, e.g. special graphics, extra command cards, more field actions, etc., comment on these on an "Extras" subsection within the corresponding assignment section (for extras, go wild on details if you want).


## 4e Josh
### Implementation
### Winning the game 
* The game is won when the first player reaches the last checkpoint. 

#### Interactive command cards.
* Normal flow of the game must be interrupted when a Turn Right or Left command is executed 
* To implement, we check the card type in exeucteNextStep() before executing the command.
* If it is, we set the game phase to interactive mode, and notify the PlayerView to rebuild the buttons for UI
* Once player clicks on a button, this calls a method to execute the command they wanted (either turn left or turn right)
  * We change the game state back to Activation, and must either allow all other players to continue their turn
  * Or if all players have finished their turn, we need to go back to the programming phase.
* We need to add a new method in the GameController to handle the interactive phase, and a new method in the PlayerView to rebuild the buttons.
* To implement, we keep track of the number of steps in the current round of execution. 
  * If the number of steps is less than the total number of card registers, we are still inside a round, so all players must have their turn to execute their remaining registers
  * else if the number of steps is greater than the number of card registers, the round is over, so we go back to the programming phase to start a new round 

### Extra functionality
If you have implemented any extra functionality over the basic requirements, e.g. special graphics, extra command cards, more field actions, etc., comment on these on an "Extras" subsection within the corresponding assignment section (for extras, go wild on details if you want).


Tasks for 4c:
* implement the four existing commands in the corresponding 
methods in class  GameController (according to the rules provided during class)
* the three buttons, “Finish Programming”, “Execute Programm” and “Execute Current Register”
Associate these buttons, with the correct methods in the GameController. And then check whether the robots’ programs 
are now correctly executed when these buttons are pressed.
* write some tests for each command (for movement operations there also must be a test when there is a wall blocking the movement).
* At last, add commands and their implementing methods for move “backwards” and “U turn”, add tests and JavaDocs. Check also manually, whether these new commands 
work properly.

And add JavaDoc to everything
```java
/**
 * This is a function that returns the param as a string.
 * @param pram any string
 * @return String with some text
 */
public static string myFunction(String pram) {
 return "This is the pram:" + pram;
}
```

# DONE
* Implement `BoardFactory()`
  * Provides a list of all available board names as Strings
  * Creates a new board for such a name, which is given as a parameter
  * Then start the game when a user have chosen what board
* Implement UI to show the board elements like walls ie:
* Make a new board layout - Lauritz is working on this one.
  * Need to contain
    * Walls
    * conveyor belts facing different directions
    * Checkpoints with their number
* Create class checkpoint that extends FieldAction (look at walls and conveyor belts for help)
* Implement `updateView()`


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
