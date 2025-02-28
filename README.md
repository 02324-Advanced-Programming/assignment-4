# Roborally

# TODO
A list of our todos
* Implement UI to show the board elements like walls ie: 
* Make a new board layout
  * Need to contain
    * Walls
    * conveyor belts facing different directions
    * Checkpoints with their number
* Create class checkpoint that extends FieldAction (look at walls and conveyor belts for help)
* Implement `updateView()`
* 

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
