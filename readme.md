# Technical specification overview

You are tasks with producing a standalone component. This component must conform to these rules. 
- You must use maven to build you application
- The target after this build must contain a single jar that can be executed without any embedded or otherwise third party lib. (only core java 8)
- You can use third party lib’s for testing for example Hamcrest and Mockito are allowed. You can assume that the application will run on a JVM that has infinite virtual memory but has between 4 -12 maximum single CPU core available.

Other vital information:

1. There is not a fixed number of instruments to support and this is a variable that will change at runtime.
2. You are required to make it easy to add new merge rules into the code.
3. You must have comprehensive testing and the solution must be appropriately scalable and thread safe.
4. This is meant to be a very simple application DO NOT OVER ENGINEER IT!!.  We require a simple code only NO CONFIGURATION! application.
5. There are some obvious patterns expected in the code and we are interested in seeing simple elegant solution.



# EPIC

Imagine that you work in a small team for an long standing investment bank. Currently the reference data system in the bank is not fit for purpose so you need to fill the gap. You are charges with writing and application that can receive instrument data and merge it real time based on a certain business rules.
You are tasked with providing a aggregated view of Instrument reference data. For the purpose of the test we will limit this to the few stories below.

# STORIES

```sh
Feature: Aggregated view of Instrument reference data

  Scenario: Instrument comes from LME only
    Given the "LME" instrument "PB_03_2018" with these details:
      | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              |
      | 15-03-2018        | 17-03-2018    | PB     | Lead 13 March 2018 |
    When "LME" publishes instrument "PB_03_2018"
    Then the application publishes the following instrument internally
      | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | TRADABLE |
      | 15-03-2018        | 17-03-2018    | PB     | Lead 13 March 2018 | TRUE     |

  Scenario: The same instrument comes from LME followed by PRIME
    Given the "LME" instrument "PB_03_2018" with these details:
      | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | LME_CODE   |
      | 15-03-2018        | 17-03-2018    | LME_PB | Lead 13 March 2018 | PB_03_2018 |
    And a  "PRIME" instrument "PRIME_PB_03_2018" with these details:
      | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | EXCHANGE_CODE | TRADABLE |
      | 14-03-2018        | 18-03-2018    | LME_PB | Lead 13 March 2018 | PB_03_2018    | FALSE    |
    When "LME" publishes instrument "PB_03_2018"
    Then the application publishes the following instrument internally
      | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | TRADABLE |
      | 15-03-2018        | 17-03-2018    | LME_PB | Lead 13 March 2018 | TRUE     |
    When "PRIME" publishes instrument "PRIME_PB_03_2018"
    Then the application publishes the following instrument internally
      | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | TRADABLE |
      | 15-03-2018        | 17-03-2018    | LME_PB | Lead 13 March 2018 | FALSE    |

  
```

### Design / Assumptions

1. Bare minimum Java docs / Logging
2. Bare minimum validations
3. In Instrument.java
- alternateIds : holds all sorts of alternate ids/keys/codes
- optionalFields : holds optional instrument data
4. World.java is a singleton just for the sake of not using any dependency injection framework 



### Technology
- Maven
- JDK 1.8
- Junit 4
 

### Running Tests
```
mvn clean test
```

### Building Executable Jar 

```
mvn clean package
java -jar target/InstrumentUpdater-0.0.1-SNAPSHOT.jar
```


