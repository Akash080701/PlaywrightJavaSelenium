package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

public class GetAPITest {
	
	@Test
	
	public void getUserApiTest() throws IOException {
		
		Playwright playwright = Playwright.create();
		APIRequest request = playwright.request();
		APIRequestContext apicontext = request.newContext();
		APIResponse apiresponse =  apicontext.get("https://reqres.in/api/users?page=2");
		
		int statusCode = apiresponse.status();
		System.out.println("response status code: "+ statusCode);
		
		Assert.assertEquals(statusCode, 200);
		
		String statusText = apiresponse.statusText();
		System.out.println("response data from API: "+ statusText);
		
		apiresponse.body();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonResponse =  objectMapper.readTree(apiresponse.body());
		String jsonPrettyResponse = jsonResponse.toPrettyString();
		System.out.println(jsonPrettyResponse);
		
		System.out.println("getting URL from API:"+apiresponse.url()); 
		
	}

}
