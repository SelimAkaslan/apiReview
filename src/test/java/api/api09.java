package api;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

import org.junit.Assert;
import org.junit.Test;
import utilities.TestBase;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class api09 extends TestBase {
    /*
    store this uri in TestBase class as spec02 and use it from testbase
When
    I send a GET request to REST API URL
   http://dummy.restapiexample.com/api/v1/employees
       And accept type is "application/JSON"
   Then
    HTTP Status Code should be 200
    And Response format should be "application/JSON"
    And there should be 24 profile_image
    And "61" should be one of the employee_age
    And "320800", "162700", "205500" and  should be among the employee salary
  */
    @Test
    public void test(){
        Response response = given().
                accept("application/JSON").
                spec(spec02).
                when().
                get();
        response.prettyPrint();

        response.
                then().
                assertThat().
                statusCode(200).
                contentType("application/JSON").
                body("data.id", hasSize(24),
                        "data.employee_age",hasItem("61"),
                        "data.employee_salary",hasItems("320800", "162700", "205500"));

    }

    // jsonpath ile cozumu
    @Test
    public void test02(){
        Response response = given().spec(spec02).when().get();
        response.prettyPrint();

        JsonPath jsonPath = response.jsonPath();

        System.out.println(jsonPath.getList("data.profile_image").size());
        Assert.assertEquals(24,jsonPath.getList("data.profile_image").size());
        Assert.assertTrue(jsonPath.getString("data.employee_age").contains("61"));

        List<String> salary = new ArrayList<>();
        salary.add("320800");
        salary.add("162700");
        salary.add("205500");

        Assert.assertTrue(jsonPath.getList("data.employee_salary").containsAll(salary));
    }
}
