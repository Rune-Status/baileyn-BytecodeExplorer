# BytecodeExplorer

## About
Bytecode Explorer is meant to be a tool for analyzing the bytecode for Java applications. My goal is to implement most of the features I love from IDA Pro for binary file analysis. 

***NOTE:*** This is currently in the VERY beginning stages. Anything and everything will most-probably change.

### Current Functionality
* General
  * Load the classes contained in Java applications
    * Load from single Class File
    * Load recursively from a directory
    * Load from a JAR
  * Save modified application.
    * Save to JAR.
* Refactoring
  * Classes
    * Name
  * Fields
    * Name
  * Methods
    * Name
* Instruction Navigation
  * Jump to target of a jump instruction

## Contributing
Anyone is welcome to contribute, regardless of skill level. If you have any questions at all, feel free to open an issue. I don't currently have a styling guide. I am just going with IDEA's basic styling so far.

## Requirements
* JDK >= 1.8
* Gradle

### Build Instructions
```
git clone https://github.com/baileyn/BytecodeExplorer.git
cd BytecodeExplorer
./gradlew build
```

### Execution Instructions
```
./gradlew run
```

## Screenshots
### The information currently displayed for a method:
![Current Method Information](https://bytecode-explorer.s3.us-east-2.amazonaws.com/function-view.png)

### The instructions for a method:
![Current Method Instructions](https://bytecode-explorer.s3.us-east-2.amazonaws.com/instruction-view.png)

### Pressing the 'enter' key while on a jump instruction navigates to the target of the instruction
![Jump Instruction Navigation](https://bytecode-explorer.s3.us-east-2.amazonaws.com/jump-result.png)

### Process of renaming a method
![Before Renaming Method](https://bytecode-explorer.s3.us-east-2.amazonaws.com/rename-function-before.png)
![During Renaming Method](https://bytecode-explorer.s3.us-east-2.amazonaws.com/rename-function-during.png)
![After Renaming Method](https://bytecode-explorer.s3.us-east-2.amazonaws.com/rename-function-after.png)

### Process of renaming a class
![During Renaming Class](https://bytecode-explorer.s3.us-east-2.amazonaws.com/class-rename-during.png)
![After Renaming Class](https://bytecode-explorer.s3.us-east-2.amazonaws.com/class-rename-after.png)
