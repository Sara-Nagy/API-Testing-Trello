package tests;

import com.trello.pogo.PogoCardBody;
import coreLibraries.validation.Assertion;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ListCardTests extends BaseTest {

    private String boardId;
    private String listId;
    private String cardId;

    @Epic("List and Card Management")
    @Feature("Create Board")
    @Story("Create a new board to manage lists and cards")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to create a new board for lists and cards", priority = 1)
    public void testCreateBoardForListsAndCards() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("name", "ITI_List_Card_Board")
                        .queryParam("key", apiKey)
                        .queryParam("token", token)

                        .when()
                        .post("/boards");
        boardId = response.jsonPath().getString("id");/// save id to use it later
        /// *************************************Assertions  on the response*****************************///
        //Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify Board Name
        String boardName = response.jsonPath().getString("name");
        Assertion.verifyEquals("Board name should be 'ITI_List_Card_Board'", boardName, "ITI_List_Card_Board");
        // Verify Board ID is not null
        Assertion.verifyIsNotNull("Board ID should not be null", boardId);
    }

    @Epic("List and Card Management")
    @Feature("Create List")
    @Story("Create a new list in the created board")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to create a new list in the created board", priority = 2, dependsOnMethods = "testCreateBoardForListsAndCards")
    public void testCreateListInBoard() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("name", "To Do List")
                        .queryParam("idBoard", boardId)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .post("/lists");
        listId = response.jsonPath().getString("id");/// save id to use it later
        /// *********************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify List Name
        String listName = response.jsonPath().getString("name");
        Assertion.verifyEquals("List name should be 'To Do List'", listName, "To Do List");
        // Verify List ID is not null
        Assertion.verifyIsNotNull("List ID should not be null", listId);
        // Verify List is associated with the correct Board
        String associatedBoardId = response.jsonPath().getString("idBoard");
        Assertion.verifyEquals("List should be associated with the correct Board ID", associatedBoardId, boardId);
    }

    @Epic("List and Card Management")
    @Feature("List all Lists in Board")
    @Story("Retrieve all lists in the created board and verify the created list exists")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Test to get all lists in the created board and verify the created list exists", priority = 3, dependsOnMethods = "testCreateListInBoard")
    public void testGetListsInBoard() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .get("/boards/" + boardId + "/lists");
        /// *********************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify the created list exists in the list using its ID
        boolean listExists = response.jsonPath().getList("id").contains(listId);
        Assertion.verifyTrue("The created list should exist in the board", listExists);
        // Verify List Name
        String listName = response.jsonPath().getString("find { it.id == '" + listId + "' }.name");
        Assertion.verifyEquals("List name should be 'To Do List'", listName, "To Do List");
        // Verify the created list is associated with the correct Board
        String associatedBoardId = response.jsonPath().getString("find { it.id == '" + listId + "' }.idBoard");
        Assertion.verifyEquals("List should be associated with the correct Board ID", associatedBoardId, boardId);
    }

    @Epic("List and Card Management")
    @Feature("Create Card in List")
    @Story("Create a new card in the created list")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to create a new card in the created list", priority = 4, dependsOnMethods = "testCreateListInBoard")
    public void testCreateCardInList() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .contentType("application/json")
                        .body(new PogoCardBody("Test Card 1", "This card was created automatically for testing", listId))
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .post("/cards");
        cardId = response.jsonPath().getString("id");/// save id to use it later
        /// *********************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify Card Name
        String cardName = response.jsonPath().getString("name");
        Assertion.verifyEquals("Card name should be 'Test Card 1'", cardName, "Test Card 1");
        // Verify Card ID is not null
        Assertion.verifyIsNotNull("Card ID should not be null", cardId);
        // Verify Card is associated with the correct List
        String associatedListId = response.jsonPath().getString("idList");
        Assertion.verifyEquals("Card should be associated with the correct List ID", associatedListId, listId);
        // Verify idBoard is correct
        String associatedBoardId = response.jsonPath().getString("idBoard");
        Assertion.verifyEquals("Card should be associated with the correct Board ID", associatedBoardId, boardId);
    }

    @Epic("List and Card Management")
    @Feature("List all Cards in List")
    @Story("Retrieve all cards in the created list and verify the created card exists")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Test to get all cards in the created list and verify the created card exists", priority = 5, dependsOnMethods = "testCreateCardInList")
    public void testGetCardsInList() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .get("/lists/" + listId + "/cards");
        /// *********************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify the created card exists in the list using its ID
        boolean cardExists = response.jsonPath().getList("id").contains(cardId);
        Assertion.verifyTrue("The created card should exist in the list", cardExists);
        // Verify Card Name
        String cardName = response.jsonPath().getString("find { it.id == '" + cardId + "' }.name");
        Assertion.verifyEquals("Card name should be 'Test Card 1'", cardName, "Test Card 1");
        // Verify the created card is associated with the correct List
        String associatedListId = response.jsonPath().getString("find { it.id == '" + cardId + "' }.idList");
        Assertion.verifyEquals("Card should be associated with the correct List ID", associatedListId, listId);
        // Verify idBoard is correct
        String associatedBoardId = response.jsonPath().getString("find { it.id == '" + cardId + "' }.idBoard");
        Assertion.verifyEquals("Card should be associated with the correct Board ID", associatedBoardId, boardId);
    }

    @Epic("List and Card Management")
    @Feature("Update Card")
    @Story("Update the created card's name and description")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Test to update the created card's name and description", priority = 6, dependsOnMethods = "testCreateCardInList")
    public void testUpdateCard() {
        PogoCardBody updatedCardBody = new PogoCardBody();
        updatedCardBody.setName("Updated Test Card 1");
        updatedCardBody.setDesc("This card has been updated automatically for testing");

        Response response =
                given()
                        .baseUri(baseUrl)
                        .body(updatedCardBody)
                        .header("Content-Type", "application/json")
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .put("/cards/" + cardId);


        /// *********************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify Updated Card Name
        String updatedCardName = response.jsonPath().getString("name");
        Assertion.verifyEquals("Card name should be updated to 'Updated Test Card 1'", updatedCardName, "Updated Test Card 1");
        // Verify Updated Card Description
        String updatedCardDesc = response.jsonPath().getString("desc");
        Assertion.verifyEquals("Card description should be updated accordingly", updatedCardDesc, "This card has been updated automatically for testing");

    }

    @Epic("List and Card Management")
    @Feature("Delete Card")
    @Story("Delete the created card and verify its deletion")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Test to delete the created card and verify its deletion", priority = 7, dependsOnMethods = "testCreateCardInList")
    public void testDeleteCard() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .delete("/cards/" + cardId);
        /// *********************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify Card Deletion by attempting to retrieve the deleted card
        Response getResponse =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .get("/cards/" + cardId);
        // Verify Status Code for retrieval attempt
        Assertion.verifyStatusCode("Status code should be 404 for deleted card retrieval", getResponse, 404);
        // Optionally, verify the error message
        String errorMessage = getResponse.asString();
        Assertion.verifyEquals("Error message should indicate card not found", errorMessage, "The requested resource was not found.");
    }
}




