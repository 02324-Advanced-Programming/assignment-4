# Roborally

# TODO
A list of our todos
* Implement UI to show the board elements like walls ie: 
* Make a new board layout - Lauritz is working on this one.
  * Need to contain
    * Walls
    * conveyor belts facing different directions
    * Checkpoints with their number
* Create class checkpoint that extends FieldAction (look at walls and conveyor belts for help)
* Implement `updateView()`


# DELEGATIONS
* Ben: Checkpoints, winner-phase; 
* Josh: Error/bug corrections, design, meta-stuff, refactoring, etc.; 
* Laxmanan: To be assigned.... 
* Rasmus: To be assigned...
* Lauritz: Model/controller communication in-between etc; 
* Kasper: All visuals, GUIs, updateView()-implementations etc.;

Add below if anything extra is missing, so that we can assign it to people. 

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



Also, if people have good/useful git commands for this project: 
* Insert in here...