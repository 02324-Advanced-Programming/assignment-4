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

# GIT COMMANDS 
* first make sure you are in the correct folder on your local device (do not put the < > when running commands)
```bash 
cd </path/to/your/folder>
````
### Pull (Download changes from remote repo):


```bash
git pull 
```

* resolve any merge conflicts 

* The above command will only work if you have correctly set the origin of the repo correctly 
  * How to reset origin
```bash 
git remote set-url origin <repo url>
```

* *How to add a new origin 
``` bash
git remote add origin <repo url>
```


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