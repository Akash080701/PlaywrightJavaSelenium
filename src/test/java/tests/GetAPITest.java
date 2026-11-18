package tests;

import java.io.IOException;
import java.util.Map;

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
	public void getSpecificUser() throws IOException {
		Playwright playwright = Playwright.create();
		APIRequest request = playwright.request();
		APIRequestContext apicontext = request.newContext();
		APIResponse apiresponse = apicontext.get("https://reqres.in/api/users/2");
		
		int statusCode = apiresponse.status();
		System.out.println("response status code: " + statusCode);
		Assert.assertEquals(statusCode, 200);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonResponse = objectMapper.readTree(apiresponse.body());
		String jsonPrettyResponse = jsonResponse.toPrettyString();
		System.out.println("Specific User API Response:");
		System.out.println(jsonPrettyResponse);
		
		System.out.println("getting URL from API: " + apiresponse.url());
		
		Map<String, String> apiHeaders = apiresponse.headers();
		System.out.println("response apiHeaders in API: " + apiHeaders);
		Assert.assertTrue(apiHeaders.get("content-type").contains("application/json"));
		
		// Optional: assert specific fields from response
		JsonNode userData = jsonResponse.get("data");
		Assert.assertNotNull(userData);
		Assert.assertEquals(userData.get("id").asInt(), 2);
		System.out.println("User Email: " + userData.get("email").asText());
	}
	
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
		
		Map<String, String> apiHeaders =  apiresponse.headers();
		System.out.println("response apiHeaders in API:"+apiHeaders);
		Assert.assertEquals(apiHeaders.get("content-type"), "application/json");
		
		//
	}

}