# TP1_QA
Authors :
* Megane Dandurand, 20145024
* Julien Thibeault, B0610

Repository : 
https://github.com/xOnyxia/TP1_QA

## Introduction
JavaCodeAnalytics is a command-line application created to provide global analytics for a given java project.
This project is part of IFT3913 course at Université de Montréal.

## Installation
TP1_QA is provided as an executable jar, there is no further compilation to do.

Prior to launch the application, you must install Java Runtime Environment (JRE).
If you already have the JDK 15.0.1 already installed, you don't need to install JRE.

This requirments is the environment in which the application was developed.
The application might work with previous versions, but it has not been tested yet.

## Running the command-line application
To run the command-line application you must provide the source directory of the project.
In terminal, use this command with actual source path and output path :
```
java -jar TP1_QA.jar src_path output_path
```
`src_path` and `output_path` must end with `/`.

Make sure to enter the path of the project directory and not the first package you want to parse.
Otherwise, you won't get the top level package analytics in the CSV.

### Output example
The command-line application will output two CSV files.
The first one about classes analytics is named `Classes_Analytics.csv`
The second one about packages analytics is named `Packages_Analytics.csv`
#### Classes Analytics CSV example 

| chemin                             | class                                        | classe_LOC | classe_CLOC | classe_DC  | WMC | classes_BC   |
|------------------------------------|----------------------------------------------|------------|-------------|------------|-----|--------------|
| ~ root ~                           | module-info.java                             | 40         | 3           | 0.075      | 1   | 0.075        |
| org/jfree/data/                    | KeyedValues2D.java                           | 12         | 91          | 7.5833335  | 10  | 0.7583333    |
| org/jfree/chart/                   | ChartElement.java                            | 4          | 44          | 11.0       | 4   | 2.75         |
| org/jfree/data/                    | KeyToGroupMap.java                           | 9          | 295         | 32.77778   | 34  | 0.9640523    |
| org/jfree/data/                    | DomainOrder.java                             | 2          | 48          | 24.0       | 5   | 4.8          |
| org/jfree/chart/                   | JFreeChart.java                              | 60         | 1577        | 26.283333  | 130 | 0.20217948   |
| org/jfree/data/                    | KeyedValues.java                             | 8          | 81          | 10.125     | 10  | 1.0125       |
| org/jfree/chart/                   | ChartTransferable.java                       | 10         | 241         | 24.1       | 20  | 1.205        |
| org/jfree/chart/                   | StandardChartTheme.java                      | 64         | 1727        | 26.984375  | 106 | 0.2545696    |
| ...                                | ...                                          | ...        | ...         | ...        | ... | ...          |

#### Packages Analytics CSV example
| chemin           | paquet                   | paquet_LOC | paquet_CLOC | paquet_DC | WCP      | paquet_BC     |
|------------------|--------------------------|------------|-------------|-----------|----------|---------------|
| ~ root ~         | ~ root ~                 | 812        | 11258       | 13.864532 | 43010615 | 3.223514E-7   |
| ~ root ~         | org.jfree.data           | 4797       | 37723       | 7.8638735 | 3873     | 0.0020304348  |
| org/jfree/data/  | org.jfree.data.time      | 108        | 791         | 7.3240743 | 56       | 0.13078704    |
| org/jfree/data/  | org.jfree.data.json      | 324        | 401         | 1.2376543 | 80       | 0.015470679   |
| ~ root ~         | org.jfree.chart          | 6332       | 90944       | 14.362602 | 121328   | 1.18378295E-4 |
| org/jfree/chart/ | org.jfree.chart.text     | 55         | 992         | 18.036364 | 57       | 0.31642744    |
| org/jfree/chart/ | org.jfree.chart.renderer | 2364       | 30748       | 13.006768 | 2938     | 0.0044270824  |
| org/jfree/chart/ | org.jfree.chart.swing    | 390        | 3366        | 8.630769  | 252      | 0.034249082   |
| org/jfree/chart/ | org.jfree.chart.plot     | 533        | 13609       | 25.532833 | 975      | 0.02618752    |
