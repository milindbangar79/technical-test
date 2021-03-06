# Technical Test

This repository contains the code for technical test.

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

![tradeclassdiagram](https://user-images.githubusercontent.com/7010363/53756553-67b42880-3edf-11e9-9ca5-d842cc978144.png)

# Project Test Coverage Report

The below report is generate dusing ECL Emma Jacoco Plugin on Eclipse:

<img width="749" alt="screenshot 2019-03-04 at 02 31 16" src="https://user-images.githubusercontent.com/7010363/53701691-a38ab780-3e25-11e9-8f6a-c15dd7d1f50f.png">

The coverage report can be generated by running the following maven command:

```
mvn clean jacoco:prepare-agent install jacoco:report
```

# Project SonarLint Report

<img width="980" alt="screenshot 2019-03-04 at 02 18 08" src="https://user-images.githubusercontent.com/7010363/53701472-de8beb80-3e23-11e9-9107-de5bd19c1637.png">

# Notes

1. Other than AED and SGP, GBP as currency was used to calculate the trades (incoming and outgoing)

2. The trade data is assumed to be received as plain text files only

3. Following is the sample trade data :

```
100.25::250::0.50::2019-01-02::2019-02-02::FOO::B::SAR
100.25::200::0.50::2019-01-02::2019-01-01::FOO::B::SAR
100.25::200::0.50::2019-01-02::2019-01-01::FOO::S::SAR
125.25::100::0.50::2019-01-02::2019-01-01::FOO::S::SAR
100.25::300::0.50::2018-02-05::2018-02-05::BAR::S::SAR
150.50::350::0.22::2018-02-06::2018-02-10::BAR::B::AED
150.50::400::0.22::2018-02-06::2018-02-10::BAR::B::AED
150.50::500::0.22::2018-10-02::2018-10-06::FOO::S::AED
150.50::500::0.22::2019-03-02::2019-03-03::FOO::B::AED
150.50::450::0.78::2019-02-14::2019-02-15::FOO::B::GBP
150.50::400::0.78::2019-02-24::2019-02-25::FOO::S::GBP
150.50::150::0.78::2019-02-08::2019-02-09::BAR::S::GBP
150.50::300::0.78::2019-02-23::2019-02-24::FOO::B::GBP

```

4. The output of the code is printed on console as logged statements

5. Maven based project

6. Used Log4J2 library for logging asynchronously

7. The Ranking report can also be generated using the HTMLFlow library, which creates an HTML 

Maven dependency :

```
<dependency>
    <groupId>com.github.xmlet</groupId>
    <artifactId>htmlflow</artifactId>
    <version>3.2</version>
</dependency>
```


8. Used Mockito framework for mocking the Classes

9. Used SystemRules to test main() method, including the edge/boundary conditions primarily for DailyTrading class


