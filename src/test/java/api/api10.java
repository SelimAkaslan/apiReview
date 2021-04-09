package api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.TestBase;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class api10 extends TestBase {

    /*
        When
            I send a GET request to REST API URL
             protected RequestSpecification spec01;
   @Before
   public void setUp(){
       spec01 =new RequestSpecBuilder().
               setBaseUri("https://jsonplaceholder.typicode.com/todos")
               .build();
   }
}
        Then
            HTTP Status Code should be 200
            And "Server" in Headers should be "cloudflare"
            And response content type is “application/JSON”
            And "userId" should be 7,
            And "title" should be "esse et quis iste est earum aut impedit"
            And "completed" should be false
    */
    @Test
    public void test(){
        spec01.pathParam("id",123);
        Response response = given().spec(spec01).when().get("/{id}");
        response.prettyPrint();

        JsonPath json = response.jsonPath();
        System.out.println(json.getInt("userId"));

        System.out.println(json.getString("title"));

        System.out.println(json.getBoolean("completed"));


    }
    @Test
    public void test02(){
        Response response = given().spec(spec01).when().get();
        response.prettyPrint();
        JsonPath json = response.jsonPath();

        json.getList("id");
        // 123 153 183

        List<Integer> expectedId = new ArrayList<>();
        expectedId.add(123);
        expectedId.add(153);
        expectedId.add(183);
        //birden fazla data validate yapilacaksa containsAll() method kullanilir <collections>
        Assert.assertTrue(json.getList("id").containsAll(expectedId));
        // bir tane data validet yapilacaksa contains() mthodu
        Assert.assertTrue(json.getList("id").contains(12));

    }

}
