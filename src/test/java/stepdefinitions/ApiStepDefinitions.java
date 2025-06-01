package stepdefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import utils.PayloadUtil;

public class ApiStepDefinitions {

    private Response response;

    @Given("I send a GET request to {string}")
    public void i_send_a_get_request(String url) {
        response = given()
                      .log().all()
                   .when()
                      .get(url)
                   .then()
                      .log().all()
                      .statusCode(200)
                      .extract().response();
    }

    @Then("the response should contain {string}, {string}, and {string}")
    public void validate_get_response(String path, String ip, String headers) {
        response.then()
                .body("$", hasKey(path))
                .body("$", hasKey(ip))
                .body("$", hasKey(headers));
    }

    @Given("I send a POST request to {string} with order payload")
    public void i_send_a_post_request_with_payload(String url) {
        String payload = PayloadUtil.getOrderPayload();

        response = given()
                      .header("Content-Type", "application/json")
                      .body(payload)
                      .log().all()
                   .when()
                      .post(url)
                   .then()
                      .log().all()
                      .statusCode(200)
                      .extract().response();
    }

    @Then("the response should contain valid customer, payment, and product information")
    public void validate_post_response() {
    	response.then().log().body()
        .body("request.body.customer.name", equalTo("Jane Smith"))
        .body("request.body.customer.email", equalTo("janesmith@example.com"))
        .body("request.body.items[0].name", equalTo("Wireless Headphones"))
        .body("request.body.items[1].name", equalTo("Smartphone Case"))
        .body("request.body.payment.method", equalTo("credit_card"))
        .body("request.body.order_status", equalTo("processing"));


    }
}
