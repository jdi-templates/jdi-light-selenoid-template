# JDI Light project template
To run UI automated tests just download and open this project
Powered by [JDI Light](https://github.com/jdi-testing/jdi-light) and Selenium

# Instruction:
### Preconditions:
1. Install docker and run Selenoid with default remote url http://localhost:4444/wd/hub

### Main steps:
1. Clone repository
2. Tests execution
    - one by one: mvn clean install -Pci
4. Allure report
    - mvn allure:serve