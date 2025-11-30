package coreLibraries.validation;

import coreLibraries.utils.logs.LogsManager;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

public class Assertion {

    // ---------------- Core Assertions ----------------

    /** Check equality */
    @Step("Verify Equals: actual [{actual}] , expected [{expected}]")
    public static <T> void verifyEquals(String message, T actual, T expected) {
        assertThat(message, actual, equalTo(expected));
        LogsManager.info("Assertion Passed: "+message );
    }

    /** Check not equals */
    @Step("Verify Not Equals: actual [{actual}] , expected [{expected}]")
    public static <T> void verifyNotEquals(String message, T actual, T expected) {
        assertThat(message, actual, not(equalTo(expected)));
        LogsManager.info("Assertion Passed: "+message );
    }

    /** Check actual > expected */
    @Step("Verify Greater Than: actual [{actual}] , expected [{expected}]")
    public static void verifyGreaterThan(String message, int actual, int expected) {
        assertThat(message, actual, greaterThan(expected));
        LogsManager.info("Assertion Passed: "+message );
    }

    /** Check actual >= expected */
    @Step("Verify greater than or equal: actual [{actual}] , expected [{expected}]")
    public static void verifyGreaterThanOrEqual(String message, int actual, int expected) {
        assertThat(message, actual, greaterThanOrEqualTo(expected));
        LogsManager.info("Assertion Passed: "+message );
    }
    /** Check actual < expected */
    @Step("Verify Less Than: actual [{actual}] , expected [{expected}]")
    public static void verifyLessThan(String message, int actual, int expected) {
        assertThat(message, actual, lessThan(expected));
        LogsManager.info("Assertion Passed: "+message );
    }

    /** Check actual <= expected */
    @Step("Verify less than or equal: actual [{actual}] , expected [{expected}]")
    public static void verifyLessThanOrEqual(String message, int actual, int expected) {
        assertThat(message, actual, lessThanOrEqualTo(expected));
        LogsManager.info("Assertion Passed: "+message );
    }
    /** Check value is null */
    @Step("Verify Is Null: actual [{actual}]")
    public static <T> void verifyIsNull(String message, T actual) {
        assertThat(message, actual, nullValue());
        LogsManager.info("Assertion Passed: "+message );
    }
    /** Check value is not null */
    @Step("Verify Is Not Null: actual [{actual}]")
    public static <T> void verifyIsNotNull(String message, T actual) {
        assertThat(message, actual, notNullValue());
        LogsManager.info("Assertion Passed: "+message );
    }
    /** Check boolean is false */
    @Step("Verify condition Is False")
    public static void verifyFalse(String s, boolean result) {
        assertThat(s,result, is(false));
        LogsManager.info("Assertion Passed: "+s );
    }
    /** Check boolean is true */
    @Step("Verify condition Is True")
    public static void verifyTrue(String s, boolean result) {
        assertThat(s, result, is(true));
        LogsManager.info("Assertion Passed: "+s );
    }
    /** Check string contains substring */
    @Step("Verify String Contains: actual [{actual}] , expected substring [{expectedSubstring}]")
    public static void verifyContains(String message, String actual, String expectedSubstring) {
        assertThat(message, actual, containsString(expectedSubstring));
        LogsManager.info("Assertion Passed: "+message );
    }

    /** Check string starts with */
    @Step("Verify String Starts With: actual [{actual}] , expected prefix [{prefix}]")
    public static void verifyStartsWith(String message, String actual, String prefix) {
        assertThat(message, actual, startsWith(prefix));
        LogsManager.info("Assertion Passed: "+message );
    }

    /** Check string ends with */
    @Step("Verify String Ends With: actual [{actual}] , expected suffix [{suffix}]")
    public static void verifyEndsWith(String message, String actual, String suffix) {
        assertThat(message, actual, endsWith(suffix));
        LogsManager.info("Assertion Passed: "+message );
    }

    /** Check list contains item */
    @Step("Verify List Has Item: item [{item}] in list")
    public static <T> void verifyListHasItem(String message, List<T> list, T item) {
        assertThat(message, list, hasItem(item));
        LogsManager.info("Assertion Passed: "+message );
    }

    /** Check map has key */
    @Step("Verify Map Has Key: key [{key}] in map")
    public static <K> void verifyMapHasKey(String message, Map<K, ?> map, K key) {
        assertThat(message, map, hasKey(key));
        LogsManager.info("Assertion Passed: "+message );
    }

    /** Check map has value */
    @Step("Verify Map Has Value: value [{value}] in map")
    public static <V> void verifyMapHasValue(String message, Map<?, V> map, V value) {
        assertThat(message, map, hasValue(value));
        LogsManager.info("Assertion Passed: "+message );
    }
    /** check map contains entry */
    @Step("Verify Map Contains Entry: entry [{key}={value}] in map")
    public static <K, V> void verifyMapContainsEntry(String message, Map<K, V> map, K key, V value) {
            assertThat(message, map, hasEntry(key, value));
        LogsManager.info("Assertion Passed: "+message );
        }
    /** Multiple conditions at once */
    @Step("Verify All Conditions")
    public static <T> void verifyAll(String message, T actual, Matcher<? super T>... matchers) {
        assertThat(message, actual, allOf(matchers));
        LogsManager.info("Assertion Passed: "+message );
    }

    // ---------------- JSON / Rest-Assured Assertions ----------------
/** check status code */
    @Step("Verify Status Code is {expectedStatusCode}")
    public static void verifyStatusCode(String message, Response response, int expectedStatusCode) {
        assertThat(message, response.statusCode(), equalTo(expectedStatusCode));
        LogsManager.info("Assertion Passed: "+message );
    }
    /** Check response time less than  or equal  */
    @Step("Verify Response Time is less than {maxResponseTime} ms")
    public static void verifyResponseTimeLessThanOrEqual(String message, Response response, long maxResponseTime) {
        assertThat(message, response.time(), lessThanOrEqualTo(maxResponseTime));
        LogsManager.info("Assertion Passed: "+message );
    }
    /** check header exists */
    @Step("Verify Header Exists: header [{headerName}]")
    public static void verifyHeaderExists(String message, Response response, String headerName) {
        assertThat(message, response.getHeaders().hasHeaderWithName(headerName), is(true));
        LogsManager.info("Assertion Passed: "+message );
    }
    /** Check header value */
    @Step("Verify Header Equals: expected [{expectedValue}] for header [{headerName}]")
    public static void verifyHeaderEquals(String message, Response response, String headerName, String expectedvalue) {
        String actualValue = response.getHeader(headerName);
        assertThat(message + " [Header: " + headerName + "]", actualValue, equalTo(expectedvalue));
        LogsManager.info("Assertion Passed: "+message );
    }
    /** Check JSON field exists */
    @Step("Verify JSON Field Exists: field at path [{jsonPath}]")
    public static void verifyJsonFieldExists(String message, Response response, String jsonPath) {
        Object actualValue = response.jsonPath().get(jsonPath);
        assertThat(message + " [JSON path: " + jsonPath + "]", actualValue, notNullValue());
        LogsManager.info("Assertion Passed: "+message );
    }
    /** Check JSON field not exists */
    @Step("Verify JSON Field Not Exists: field at path [{jsonPath}]")
    public static void verifyJsonFieldNotExists(String message, Response response, String jsonPath) {
        Object actualValue = response.jsonPath().get(jsonPath);
        assertThat(message + " [JSON path: " + jsonPath + "]", actualValue, nullValue());
        LogsManager.info("Assertion Passed: "+message );
    }
    /** Check JSON field matches condition */
    @Step("Verify JSON Field Matches Condition at path [{jsonPath}]")
    public static <T> void verifyJsonFieldMatches(String message, Response response, String jsonPath, Matcher<? super T> matcher) {
        T actualValue = response.jsonPath().get(jsonPath);
        assertThat(message + " [JSON path: " + jsonPath + "]", actualValue, matcher);
        LogsManager.info("Assertion Passed: "+message );
    }
    /** Check JSON field value in list of expected values */
    @Step("Verify JSON Field In: expected values [{expectedValues}] at path [{jsonPath}]")
    public static <T> void verifyJsonFieldIn(String message, Response response, String jsonPath, List<T> expectedValues) {
        T actualValue = response.jsonPath().get(jsonPath);
        assertThat(message + " [JSON path: " + jsonPath + "]", actualValue, isIn(expectedValues));
        LogsManager.info("Assertion Passed: "+message );
    }
    /** Check JSON field value by path */
    @Step("Verify JSON Field Equals: expected [{expectedValue}] at path [{jsonPath}]")
    public static void verifyJsonFieldEquals(String message,Response response, String jsonPath, Object expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPath);
        assertThat(message + " [JSON path: " + jsonPath + "]", actualValue, equalTo(expectedValue));
        LogsManager.info("Assertion Passed: "+message );
    }

    /** Check JSON string contains text */
    @Step("Verify JSON Field Contains: expected substring [{expectedSubstring}] at path [{jsonPath}]")
    public static void verifyJsonFieldContains(String message, Response response, String jsonPath, String expectedSubstring) {
        String actualValue = response.jsonPath().getString(jsonPath);
        assertThat(message + " [JSON path: " + jsonPath + "]", actualValue, containsString(expectedSubstring));
        LogsManager.info("Assertion Passed: "+message );
    }

    /** Check JSON array contains item */
    @Step("Verify JSON Array Contains: expected item [{expectedItem}] at path [{jsonPath}]")
    public static <T> void verifyJsonArrayContains(String message, Response response, String jsonPath, T expectedItem) {
        List<T> list = response.jsonPath().getList(jsonPath);
        assertThat(message + " [JSON path: " + jsonPath + "]", list, hasItem(expectedItem));
        LogsManager.info("Assertion Passed: "+message );
    }

    /** Check JSON array size */
    @Step("Verify JSON Array Size: expected size [{expectedSize}] at path [{jsonPath}]")
    public static void verifyJsonArraySize(String message, Response response, String jsonPath, int expectedSize) {
        List<?> list = response.jsonPath().getList(jsonPath);
        assertThat(message + " [JSON path: " + jsonPath + "]", list, hasSize(expectedSize));
        LogsManager.info("Assertion Passed: "+message );
    }


}

