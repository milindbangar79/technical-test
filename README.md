# Technical Test

This repository contains the sample code for technical test.

# Project Run Options

1. It can be imported into Eclipse or other IDE and an argument , which essently is the file name needs to be set to run the code

2. Build the JAR using :

```
mvn clean install
```

3. Project can be run either as :

```
java -jar trade-ranking.jar "tradedata.txt"
```

## or ## 

```
java DailyTradingImpl "tradedata.txt"
```

`In case no arguments are provided, a DailyTradeCustomException will be thrown`

# Project Class Diagram


# Project Test Coverage Report


# Project SonarLint Report


# Notes

1. Maven based project

2. Used Log4J2 library for logging asynchronously

3. The Ranking report can also be generated using the HTMLFlow library, which creates an HTML 

Maven dependency :

```
<dependency>
    <groupId>com.github.xmlet</groupId>
    <artifactId>htmlflow</artifactId>
    <version>3.2</version>
</dependency>
```

4. Used Mockito framework for mocking the Classes

4. Used SystemRules to test main() method of DailyTrading class


