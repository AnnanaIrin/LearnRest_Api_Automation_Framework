package twitterApiTest;

import Utils.LearnRandomNumber;
import base.RestBase;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import twitterApi.Tweet;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static twitterApi.Tweet.GET_USER_TWEET_ENDPOINT;

public class TweetTest extends RestBase {
    //Reference variable
    private  Tweet tweet;
    @BeforeClass
    public void  setUpTweetAPI(){
        //Object
        this.tweet=new Tweet();
    }

    @Test
    public void testGetUserTimeLineTweet(){
        Tweet tweet=new Tweet();
        ValidatableResponse response=tweet.getUserTimeLineTweet();
        System.out.println(response.statusCode(200));
        //System.out.println(response.extract().body().asPrettyString());//command
        String expectedValue="welcome back";
        String expectedName="irin";
        long expectedId=1567972456228585474l;
        String actualText= response.extract().body().path("[0].text");
        long actualId= response.extract().body().path("[0].id");
        String actualName= response.extract().body().path("[0].user.name");
        Assert.assertEquals(actualText,expectedValue,"Text value does not match");
        Assert.assertEquals(actualId,expectedId,"ID value does not match");
        System.out.println(actualName);

        Assert.assertEquals(actualName,expectedName,"Name value does not match");

    }
    @Test
    public void testGetUserTimeLineTweetNew(){
        Tweet tweet=new Tweet();
        ValidatableResponse response=tweet.getUserTimeLineTweetNew();
        response.statusCode(200);
        //System.out.println(response.statusCode(200));
        System.out.println(response.extract().body().asPrettyString());//command
        String expectedValue="welcome back";
        String expectedName="irin";
        //long expectedId=1567868920735965185l;
        long expectedId=1567972456228585474l;
        String actualText= response.extract().body().path("[0].text");
        long actualId= response.extract().body().path("[0].id");
        String actualName= response.extract().body().path("[0].user.name");
        Assert.assertEquals(actualText,expectedValue,"Text value does not match");
        Assert.assertEquals(actualId,expectedId,"ID value does not match");
        System.out.println(actualName);

        Assert.assertEquals(actualName,expectedName,"Name value does not match");

    }
    @Test
    public void verifyCreateTweet(){
        String tweetMessage="Learn Api"+ LearnRandomNumber.randomNumberGenerate();
        ValidatableResponse response= this.tweet.createTweet(tweetMessage);
        response.statusCode(200);
        response.contentType("application/json");
        //response.log().all(); //This is for response header
        System.out.println(response.extract().body().asPrettyString());//This command we use for full body print
        //I want to verify text value
        String actualTweet=response.extract().body().path("text");
        String actualScreenName=response.extract().body().path("user.screen_name");
        System.out.println(actualTweet);
        Assert.assertEquals(actualTweet,tweetMessage,"Tweet not match");
        Assert.assertEquals(actualScreenName,"irin33918852","Screen name does not match");



    }
    @Test
    public void verifyUserCanNotCreateSameTweetTwiceInARow(){
        String tweetMessage="Learn Api";
        ValidatableResponse response= this.tweet.createTweet(tweetMessage);
        response.statusCode(403);
        response.contentType("application/json");
        //response.log().all(); //This is for response header
        System.out.println(response.extract().body().asPrettyString());//This command we use for full body print
        // verify tweet value
        String actualMessage=response.extract().body().path("errors[0].message");
        Assert.assertEquals(actualMessage,"Status is a duplicate.","message does not match");

    }
    @Test
    public void verifyDeleteTweet(){
        ValidatableResponse response= this.tweet.deleteTweet(1568313342443327489l);
        response.statusCode(200);
        //response.log().all(); //This is for response header
        System.out.println(response.extract().body().asPrettyString());//This command we use for full body print
    }
    @Test
    public void verifyResponseTime(){
        long actualResponse= this.tweet.responseTimeCheck(GET_USER_TWEET_ENDPOINT);
        //Assert.assertEquals(actualResponse,actualResponse<700,"response time does not match");
        Assert.assertTrue(actualResponse<700,"response time exit the default time does not match ");

    }
    @Test
    public void verifyHeaderValue1(){
        this.tweet.headerValue1(GET_USER_TWEET_ENDPOINT);

    }
    @Test
    public void verifyHeaderValue(){
      Headers response =this.tweet.headerValue(GET_USER_TWEET_ENDPOINT);
      String actualHeaderValue=response.getValue("content-Type");
      System.out.println(response.getValue("content-Type"));
      Assert.assertEquals(actualHeaderValue,"application/json;charset=utf-8","Header value doesn't match");

    }

    @Test
    public void testPropertyFromResponse(){
        String tweet="Without practice, We can't learn API"+ UUID.randomUUID().toString();
        ValidatableResponse response= this.tweet.createTweet(tweet);
        response.statusCode(200);
        //System.out.println(response.extract().body().asPrettyString());//This command we use for full body print
        System.out.println(response.extract().body().asPrettyString().contains("id"));//This command we use for full body print
        Response response1=given().auth().oauth(this.apiKey,this.apiSecretKey,this.accessToken,this.accessTokenSecret).when().get(this.baseUrl+GET_USER_TWEET_ENDPOINT);
        JsonPath pathEvaluator=response1.jsonPath();
        String property=pathEvaluator.get("[0].text");
        System.out.println("Property value : "+property);
        System.out.println(response1.body().asPrettyString());//This command we use for full body print


    }



}
