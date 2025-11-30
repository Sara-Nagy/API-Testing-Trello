package tests;

import coreLibraries.utils.dataReader.PropertyReader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected String baseUrl = PropertyReader.getProperty(("baseUrl"));
    protected String apiKey = PropertyReader.getProperty(("apiKey"));
    protected String token = PropertyReader.getProperty(("token"));

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
    }
}
