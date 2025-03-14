# Roborally

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
