package tests;

import coreLibraries.validation.Assertion;
import io.qameta.allure.*;
import io.restassured.response.Response;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BoardTests extends BaseTest {


    private String boardId;


    @Epic("Board Management")
    @Feature("Create Board")
    @Story("Create a new board and verify its creation")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to create a new board", priority = 1)
    public void testCreateBoard() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("name", "ITI_Project_Board")
                        .queryParam("key", apiKey)
                        .queryParam("token", token)

                        .when()
                        .post("/boards");


        /// *********************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);

        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);

        // Verify Board Name
        String boardName = response.jsonPath().getString("name");
        Assertion.verifyEquals("Board name should be 'ITI_Project_Board'", boardName, "ITI_Project_Board");

        // Verify Board ID is not null
        boardId = response.jsonPath().getString("id");/// save id to use it later
        Assertion.verifyIsNotNull("Board ID should not be null", boardId);
    }

    @Epic("Board Management")
    @Feature("Get Boards")
    @Story("Retrieve all boards and verify the created board exists")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Test to get all boards and verify the created board exists", priority = 2, dependsOnMethods = "testCreateBoard")
    public void testGetBoards() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .get("/members/me/boards");
        /// *********************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify the created board exists in the list
        boolean boardExists = response.jsonPath().getList("id").contains(boardId);
        Assertion.verifyTrue("The created board should exist in the list of boards", boardExists);

    }

    @Epic("Board Management")
    @Feature("Get Board By ID")
    @Story("Retrieve the created board by ID and verify its details")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Test to get the created board by ID and verify its details", priority = 3, dependsOnMethods = "testCreateBoard")
    public void testGetBoardById() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .get("/boards/" + boardId);

        /// *********************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify Board ID
        String retrievedBoardId = response.jsonPath().getString("id");
        Assertion.verifyEquals("Board ID should match the created board ID", retrievedBoardId, boardId);
        // Verify Board Name
        String boardName = response.jsonPath().getString("name");
        Assertion.verifyEquals("Board name should be 'ITI_Project_Board'", boardName, "ITI_Project_Board");
        //check the permission level of the board
        String permissionLevel = response.jsonPath().getString("prefs.permissionLevel");
        Assertion.verifyEquals("Board permission level should be 'private'", permissionLevel, "private");
    }
    @Epic("Board Management")
    @Feature("Update Board")
    @Story("Update the created board's name and verify the update")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Test to update the created board's name and verify the update", priority = 4, dependsOnMethods = "testCreateBoard")
    public void testUpdateBoard() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("name", "ITI_Project_Board_Updated")
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .put("/boards/" + boardId);
        /// *********************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify Updated Board Name
        String updatedBoardName = response.jsonPath().getString("name");
        Assertion.verifyEquals("Board name should be updated to 'ITI_Project_Board_Updated'", updatedBoardName, "ITI_Project_Board_Updated");
        //check the id of the board
        String retrievedBoardId = response.jsonPath().getString("id");
        Assertion.verifyEquals("Board ID should match the created board ID", retrievedBoardId, boardId);
        //check the permission level of the board
        String permissionLevel = response.jsonPath().getString("prefs.permissionLevel");
        Assertion.verifyEquals("Board permission level should be 'private'", permissionLevel, "private");
    }
    @Epic("Board Management")
    @Feature("Delete Board")
    @Story("Delete the created board and verify its deletion")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to delete the created board and verify its deletion", priority = 5, dependsOnMethods = "testCreateBoard")
    public void testDeleteBoard() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .delete("/boards/" + boardId);
        /// *********************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        //check if the response body contains the expected message
        String responseMessage = response.jsonPath().getString("_value");
        Assertion.verifyEquals("Response message should indicate board deletion", responseMessage, null);
        // Verify Board Deletion by attempting to retrieve it
        Response getResponse =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .get("/boards/" + boardId);
        // Verify Status Code for retrieval attempt
        Assertion.verifyStatusCode("Status code should be 404 for deleted board retrieval", getResponse, 404);
        // Verify Error Message
        String errorMessage = getResponse.asString();
        Assertion.verifyEquals("Error message should indicate board not found", errorMessage, "The requested resource was not found.");
    }

    }