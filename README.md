

This project is a UI automation framework built using Selenium and TestNG and integrated with Jenkins for CI execution.  

It supports running tests both locally and on Selenium Grid using Docker.



The Jenkins job is parameterized so the same tests can run on different browsers, environments, and execution modes.



\*Tech used

\- Java, Selenium WebDriver, TestNG

\- Maven

\- Jenkins

\- Docker + Selenium Grid

\- Allure \& HTML reports



\*What this project demonstrates

\- Real CI execution using Jenkins

\- Browser and environment selection using parameters

\- Selenium Grid execution using Docker

\- Automated report generation after every build



\*How tests are executed

Local run:

mvn clean test -Dexecution=local -Dbrowser=chrome -Denv=qa

Grid run:

mvn clean test -Dexecution=grid -Dgrid.host=localhost -Denv=qa



\*Reports can be viewed directly from the Jenkins job after execution.





