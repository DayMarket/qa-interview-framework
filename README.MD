# QA Interview Framework

This project provides a framework for automated testing.

## Project Structure

- `src/main` - Main source code
- `src/test` - Test sources
- `build/` - Build outputs and reports
- `build/tests-run-logs` - Test run network logs
- `build/allure-results` - Test run generated report

<details>
<summary>Detailed structure</summary>

<details>
<summary>Key packages and responsibilities</summary>

- `apis`
    - `AllureOkHttpInterceptor.java` - captures HTTP request/response details and attaches them to Allure results.
    - `LoggingOkHttpInterceptor.java` - logs HTTP traffic for debugging.
    - `OrderApi` package
        - `BaseOrderApi.java` - base HTTP client/config for order-related endpoints (common headers, base URL, OkHttp setup).
        - `ClientsApi.java` - client-related API methods (create, fetch clients).
        - `OrdersApi.java` - order-related API methods (create, fetch orders).
    - `theCatApi` package
        - `BaseCatApi.java` - base client for TheCatApi.
        - `CatApi.java` - endpoints for cat-breeds / related requests.

- `classes/OrderApi`
    - Domain DTOs and error models: `Client.java`, `Order.java`, `OrderApiError.java`, `OrdersData.java`.

- `enums/OrderApi`
    - `ClientStatus.java`, `OrderStatus.java` - enumerations for domain status fields.

- `utils/OrderApi`
    - `ClientUtils.java`, `OrderApiMappers.java`, `OrderUtils.java` - helpers to build test data, map API responses to domain models and common utilities.

- 'resources/order-api-specification.json' - OpenAPI specification for OrderApi endpoints used for reference.

</details>

<details>
<summary>Tests and test helpers</summary>

- `src/test/java/uz/daymarket/QaInterviewFramework`
    - `BaseTest.java` - test base class: common setup/teardown, client initialization, Allure configuration and shared utilities.
    - `catApi/*` - tests for TheCatApi endpoints.
    - `orderApi/*` - tests that OrderApi endpoint.

- `src/test/resources`
    - `allure.properties` - Allure reporting configuration.
    - `logback-test.xml` - logging configuration for tests.

Test artifacts:
- Test run network logs are placed in `build/tests-run-logs` and a `testRun.log` is produced for HTTP request logging.
- Allure results are saved in `build/allure-results`.

</details>
</details>

## Build & Run

To build the project:

```sh
./gradlew clean build
```

To run all tests:

```sh
./gradlew clean test
```

To run defined tests:

```sh
./gradlew clean test --tests "uz.daymarket.QaInterviewFramework.orderApi.*"
```

Called request's logs will be placed at `testRun.log` file.

<details>
<summary>Typical workflow</summary>

- Update or implement API client or domain model under src/main/java.
- Domain models and mappers centralize transformations between HTTP JSON and test objects.
- Add/adjust tests under src/test/java.
- Run ./gradlew clean test.
- Inspect build/tests-run-logs/testRun.log for raw HTTP traces and build/allure-results for Allure output.
- Interceptors provide request/response capture for both debugging and Allure attachments.

</details>



