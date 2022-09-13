package twitterApiTest;

import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import twitterApi.Tweet;
import twitterApi.Tweet2;

public class Tweet2Test {
    //Reference variable
    private Tweet2 tweet2;
    @BeforeClass
    public void  setUpTweetAPI(){
        //Object
        this.tweet2=new Tweet2();
    }

    @Test
    public void testGetUserTimeLineTweet(){
        ValidatableResponse response=tweet2.getTweetTimeLine(1557365649118306304L);
        response.statusCode(200);
        response.log().all();
       // System.out.println(response.statusCode(200));
        System.out.println(response.extract().body().asPrettyString());//command

    }

    @Test
    public void testGetUserTimeLineTweetFollowing() {
        ValidatableResponse response = this.tweet2.getTweetTimeLineWithFollowing(1557365649118306304L);
       // response.log().all();
        System.out.println(response.extract().body().asPrettyString());
    }
}
