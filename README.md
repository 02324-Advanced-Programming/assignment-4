# Roborally

# TODO
A list of our todos

* New game dialog box with every board option
* Make a new board layout
  * Need to contain
    * Walls
    * conveyor belts facing different directions
    * Checkpoints with their number
* Implement `BoardFactory()`
  * Provides a list of all available board names as Strings
  * Creates a new board for such a name, which is given as a parameter
  * Then start the game when a user have chosen what board
* Create class checkpoint that extends FeildAction (look at walls and conveyor belts for help)
* Implement `updateView()`

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