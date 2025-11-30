package tests;

import com.trello.pogo.PogoLabelBody;
import coreLibraries.utils.dataReader.PropertyReader;
import coreLibraries.validation.Assertion;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class MemberLabelTests extends BaseTest {
    private String memberId = PropertyReader.getProperty("memberId");
    private String labelId;
    private String boardId;

    @Epic("Member Label Tests")
    @Feature("Create Board for Member Label Tests")
    @Story("Create a new board to be used in Member Label Tests")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to create a new board for Member Label Tests", priority = 1)
    public void testCreateBoardForMemberLabelTests() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("name", "ITI_Project_Board_Member")
                        .queryParam("key", apiKey)
                        .queryParam("token", token)

                        .when()
                        .post("/boards");
        boardId = response.jsonPath().getString("id");
        /// **************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify Board Name
        String boardName = response.jsonPath().getString("name");
        Assertion.verifyEquals("Board name should be 'ITI_Project_Board_Member'", boardName, "ITI_Project_Board_Member");
        // Verify Board ID is not null
        Assertion.verifyIsNotNull("Board ID should not be null", boardId);
    }

    @Epic("Member Label Tests")
    @Feature("Get Member of Board")
    @Story("Retrieve member of the created board and verify the specified member exists")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Test to get members of the created board and verify the specified member exists", priority = 2, dependsOnMethods = "testCreateBoardForMemberLabelTests")
    public void testGetMembersOfBoard() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .get("/members/" + memberId);
        /// **************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify Member Exists
        String retrievedMemberId = response.jsonPath().getString("id");
        Assertion.verifyEquals("Member ID should match the expected member ID", retrievedMemberId, memberId);
    }

    @Epic("Member Label Tests")
    @Feature("Create Label for Member")
    @Story("Create a new label for the specified member and verify its creation")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to create a new label for the specified member", priority = 3, dependsOnMethods = "testGetMembersOfBoard")
    public void testCreateLabelForMember() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("name", "Urgent")
                        .queryParam("color", "red")
                        .queryParam("idBoard", boardId)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .post("/labels");
        labelId = response.jsonPath().getString("id");
        /// **************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify Label Name
        String labelName = response.jsonPath().getString("name");
        Assertion.verifyEquals("Label name should be 'Urgent'", labelName, "Urgent");
        // Verify Label ID is not null
        Assertion.verifyIsNotNull("Label ID should not be null", labelId);
        // Verify Label is associated with the correct Board
        String labelBoardId = response.jsonPath().getString("idBoard");
        Assertion.verifyEquals("Label should be associated with the correct Board ID", labelBoardId, boardId);
        // Verify Label Color
        String labelColor = response.jsonPath().getString("color");
        Assertion.verifyEquals("Label color should be 'red'", labelColor, "red");

    }

    @Epic("Member Label Tests")
    @Feature("Get Labels of Member")
    @Story("Retrieve labels of the specified member and verify the created label exists")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Test to get labels of the specified member and verify the created label exists", priority = 4, dependsOnMethods = "testCreateLabelForMember")
    public void testGetLabelsOfMember() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .get("/boards/" + boardId + "/labels");
        /// **************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify Created Label Exists
        boolean labelExists = response.jsonPath().getList("id").contains(labelId);
        Assertion.verifyTrue("Created label should exist in the member's labels", labelExists);
    }

    @Epic("Member Label Tests")
    @Feature("Update Label for Member")
    @Story("Update the created label for the specified member and verify the update")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to update the created label for the specified member", priority = 5, dependsOnMethods = "testGetLabelsOfMember")
    public void testUpdateLabelForMember() {
        PogoLabelBody updatedLabel = new PogoLabelBody();
        updatedLabel.setName("Updated Label Name");
        Response response =
                given()
                        .baseUri(baseUrl)
                        .header("Content-Type", "application/json")
                        .body(updatedLabel)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .put("/labels/" + labelId);
        /// **************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify Updated Label Name
        String updatedLabelName = response.jsonPath().getString("name");
        Assertion.verifyEquals("Label name should be updated to 'High Priority'", updatedLabelName, "Updated Label Name");
        // Verify Label ID remains the same
        String updatedLabelId = response.jsonPath().getString("id");
        Assertion.verifyEquals("Label ID should remain the same after update", updatedLabelId, labelId);
        // Verify Label Color remains unchanged
        String labelColor = response.jsonPath().getString("color");
        Assertion.verifyEquals("Label color should remain 'red' after update", labelColor, "red");
    }
    @Epic("Member Label Tests")
    @Feature("Delete label for Member")
    @Story("Delete the created label for the specified member and verify its deletion")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Test to delete the created label for the specified member", priority = 6, dependsOnMethods = "testUpdateLabelForMember")
    public void testDeleteLabelForMember() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .delete("/labels/" + labelId);
        /// **************************Assertions  on the response*****************************///
        // Verify Response Time
        Assertion.verifyResponseTimeLessThanOrEqual("Response time should be less than or equal to 3000 ms",
                response, 3000);
        // Verify Status Code
        Assertion.verifyStatusCode("Status code should be 200", response, 200);
        // Verify Label Deletion by attempting to retrieve the deleted label
        Response getResponse =
                given()
                        .baseUri(baseUrl)
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .when()
                        .get("/labels/" + labelId);
        /// **************************Assertions  on the getResponse*****************************///
        // Verify Status Code for retrieval of deleted label
        Assertion.verifyStatusCode("Status code should be 404 for deleted label retrieval", getResponse, 404);
    }

}




