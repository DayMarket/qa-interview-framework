# QA Interview Framework

This project provides a framework for automated testing

## Project Structure

- `src/main` - Main source code
- `src/test` - Test sources
- `build/` - Build outputs and reports
- `build/tests-run-logs` - Test run network logs
- `build/allure-results` - Test run generated report

## Build & Run

To build the project, run:

```sh
./gradlew clean build
```

To run all tests, run:
```sh
./gradlew clean test
```

To run defined tests, run:
```sh
./gradlew clean test --tests "uz.daymarket.QaInterviewFramework.orderApi.*"
```

Called request's logs will be placed at testRun.log file


