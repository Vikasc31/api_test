Feature: API Testing using REST Assured

  Scenario: Validate GET request from echo endpoint
    Given I send a GET request to "https://echo.free.beeceptor.com/sample-request?author=beeceptor"
    Then the response should contain "path", "ip", and "headers"

  Scenario: Validate POST request with order details
    Given I send a POST request to "http://echo.free.beeceptor.com/sample-request?author=beeceptor" with order payload
    Then the response should contain valid customer, payment, and product information
