# Omni:us
An selenium based 'Page Factory Model' automation framework for web based tests, which can be extended for API's also (partly integrated).

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Prerequisites
What things you need to install the software and how to install them
```
Maven
Java
Eclipse/IntelliJ
```

## Running the tests
1. Git clone https://github.com/avinpatel29/omnius.git
2. Once clone is successful, import the project as “Existing maven project” in IDE (eclipse of IntelliJ)
3. Once the project is imported successfully, you can trigger the API or UI tests by opening the terminal in the IDE and providing below command:
```
For Challenge-1: 
    mvn clean install -Dcomponent=<component_name>
    Eg: mvn clean install -Dcomponent=API (this won't show any results)
    or
    Go to /src/test/java/com/omnius/api & execute 'API_Tests.java' as this has main() method.
```
```
For challenge-2:(UI tests)
    mvn clean install -Dcomponent=<component_name> 
    Eg: mvn clean install -Dcomponent=UI 
```

<b>Note:</b> Drivers are also included in the project. Chrome driver included in the project is 78.0.x. Please feel free to replace with the version of driver where you want to run the tests.</font>


## Project Structure
As the project is maven based, have followed 'standard' maven project structure as below:

```
src/main/java       - Consists of Page classes & methods
src/main/resources  - Consists of resources utilised by across (such as log4j.properties, setup.properties etc.)   
src/main/test       - Consists of tests to be executed
src/main/resources  - Consists of test data which is required for the tests to run
/TestReport         - Consists of reports & logs files for UI tests
```

## Built With
* Dependency Management - [Maven](https://maven.apache.org/)  
* Web framework used    - Selenium WebDriver + PageFactory Model
* API Framework used    - [RestAssured](http://rest-assured.io/)
* Testing tool          - [TestNG](https://testng.org/doc/)
* Extent Reporting      - [Extent Reports](https://extentreports.com/)
* Logging               - [Log4J](https://logging.apache.org/log4j/2.x/)
