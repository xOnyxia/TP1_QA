# JavaCodeAnalytics
## Introduction
JavaCodeAnalytics is a command-line application created to provide global analytics for a given java project.
This project is part of IFT3913 course at Université de Montréal.

## Installation
JavaCodeAnalytics is provided as an executable jar, there is no further compilation to do.

Prior to launch the application, you must install Java Runtime Environment (JRE).
If you already have the JDK 15.0.1 already installed, you don't need to install JRE.

This requirments is the environment in which the application was developed.
The application might work with previous versions, but it has not been tested yet.

## Running the command-line application
To run the command-line application you must provide the source directory of the project.
In terminal, use this command with actual source path and output path :
```
java -jar JavaCodeAnalytics.jar src_path output_path
```
Make sure to enter the path of the project directory and not the first package you want to parse.
Otherwise, you won't get the top level package analytics in the CSV.

### Output example
The command-line application will output two CSV files.
The first one about classes analytics is named `Classes_Analytics.csv`
The second one about packages analytics is named `Packages_Analytics.csv`
#### Classes Analytics CSV example 
```
chemin, class, classe_LOC, classe_CLOC, classe_DC, WMC, classe_BC
TODO data exemple here
```
#### Packages Analytics CSV example
```
chemin, paquet, paquet_LOC, paquet_CLOC, paquet_DC, WCP, paquet_BC
TODO data exemple here
```
