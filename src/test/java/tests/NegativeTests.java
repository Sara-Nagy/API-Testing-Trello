package tests;

import com.trello.pogo.PogoCardBody;
import coreLibraries.validation.Assertion;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;


public class NegativeTests extends BaseTest {
    public String boardId;
    @Epic("Negative Tests")
    @Feature("Create Board to be used in Negative Tests")
    @Story("Create a new board to be used in subsequent negative tests")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to create a new board for negative tests", priority = 0)
    public void testCreateBoardForNegativeTests() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("name", "Negative_Test_Board")
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .post("/boards");
        boardId = response.jsonPath().getString("id");/// save id to use it later
        /// *********************************Assertions on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);

        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
    }
    @Epic("Negative Tests")
    @Feature("List All Boards with Invalid Token")
    @Story("Attempt to list all boards using an invalid token and verify the error response")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to list all boards with an invalid token", priority = 1)
    public void testListAllBoardsWithInvalidToken() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", "invalid_token") // Invalid token
                        .when()
                        .get("/members/me/boards");
        /// *********************************Assertions on the response*****************************///
        //verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 401 Unauthorized", response, 401);
        //Verify Error Message
        String errorMessage = response.asString();
        Assertion.verifyEquals("Error message should indicate invalid token", errorMessage, "invalid app token");
    }
    @Epic("Negative Tests")
    @Feature("Get Board with Invalid ID")
    @Story("Attempt to get a board using an invalid ID and verify the error response")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to get a board with an invalid ID", priority = 2)
    public void testGetBoardWithInvalidId() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .get("/boards/invalid_board_id"); // Invalid board ID
        /// *********************************Assertions on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 400 bad request", response, 400);
        // Verify Error Message
        String errorMessage = response.asString();
        Assertion.verifyEquals("Error message should indicate board not found", errorMessage, "invalid id");
    }
    @Epic("Negative Tests")
    @Feature("Create List without Name")
    @Story("Attempt to create a list without providing a name and verify the error response")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to create a list without a name", priority = 3,dependsOnMethods = "testCreateBoardForNegativeTests")
    public void testCreateListWithoutName() {
        Response response =
                given()
                        .baseUri(baseUrl)
                         .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .queryParam("idBoard", boardId)
                        .when()
                        .post("/lists");
        /// *********************************Assertions on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 400 bad request", response, 400);
        // Verify Error Message
        String errorMessage = response.asString();
        Assertion.verifyEquals("Error message should indicate missing name", errorMessage, "invalid value for name");
    }
    @Epic("Negative Tests")
    @Feature("Create Card with Invalid List ID")
    @Story("Attempt to create a card using an invalid list ID and verify the error response")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to create a card with an invalid list ID", priority = 4)
    public void testCreateCardWithInvalidListId() {

        Response response =
                given()
                        .baseUri(baseUrl)
                        .contentType(ContentType.fromContentType("application/json"))
                        .body(new PogoCardBody("Test Card","This card was created automatically for testing", "invalidListIdt6543"))
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .post("/cards");
        /// *********************************Assertions on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 400 bad request", response, 400);
        // Verify Error Message
        String errorMessage = response.asString();
        Assertion.verifyEquals("Error message should indicate invalid list ID", errorMessage, "invalid value for idList");
    }
    @Epic("Negative Tests")
    @Feature("Update Card with Invalid ID")
    @Story("Attempt to update a card using an invalid ID and verify the error response")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to update a card with an invalid ID", priority = 5)
    public void testUpdateCardWithInvalidId() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("name", "Updated Card Name")
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .put("/cards/invalid_card_id"); // Invalid card ID
        /// *********************************Assertions on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 400 bad request", response, 400);
        // Verify Error Message
        String errorMessage = response.asString();
        Assertion.verifyEquals("Error message should indicate invalid card ID", errorMessage, "invalid id");
    }
}
