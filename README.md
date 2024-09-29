This project is a UI test automation framework using Selenide and Java, following the Page Object Model (POM) pattern. It automates the extraction and comparison of data from two tables in a web application to identify any discrepancies.

## Overview
* Web Application: https://ui-automation-app.web.app/
* Objective: Extract data from Table 1 and Table 2, compare them, and report any discrepancies.
* Technologies:
  - Java
  - Selenide
  - JUnit 5
## Project Structure

```
src
└── test
    └── java
        ├── com.example.models
        │   └── TableRow.java
        ├── com.example.pages
        │   └── MainPage.java
        ├── com.example.tests
        │   └── TableComparisonTest.java
        └── com.example.utils
            └── DataComparator.java
```

`TableRow.java`: Data model representing a row in the table. \
`MainPage.java`: Page Object Model class for interacting with the web page. \
`DataComparator.java`: Utility class for comparing data between tables. \
`TableComparisonTest.java`: Test class containing the test case. 

## Setup and Execution
Clone the Repository

```shell
git clone <repository-url>
```
Navigate to the Project Directory

```shell
cd <project-directory>
```
Build the Project with Maven

```shell
mvn clean install
```
Run the Tests

```shell
mvn test
```