package com.playground.api.test;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.playground.api.util.Url;
import com.playground.api.util.Utility;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Services {

	/*
	 * 1. Request <GET_SERVICE_BY_ID> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK>
	 * 
	 * 3. Assert that service response JSON contains: 
	 *    - name = "Geek Squad Services"
	 *    
	 * */
	
	@Test
	public void get_service_by_id() {
		try {
			Response response = RestAssured.get(Url.Services.GET_SERVICE_BY_ID);
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

			JsonPath jsonObj = new JsonPath(response.asString());
			Assert.assertEquals(jsonObj.getString("name"), "Geek Squad Services");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * 1. Request <GET_SERVICE_BY_ID_AND_NAME> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK>
	 * 
	 * 3. Assert that service response JSON contains:
	 *    - name = "Geek Squad Services"
	 *    - id = "1"
	 *    - JSON object contains 2 element keys
	 *  
	 * */
	@Test
	public void get_service_by_id_and_name() {
		try {
			Response response = RestAssured.get(Url.Services.GET_SERVICE_BY_ID_AND_NAME);
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

			JsonPath jsonPath = new JsonPath(response.asString());
			JSONObject jsonObject = new JSONObject(response.body().asString());

			Assert.assertEquals(jsonPath.getString("id"), "1");
			Assert.assertEquals(jsonPath.getString("name"), "Geek Squad Services");
			Assert.assertEquals(Utility.getNames(jsonObject).length, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*
	 * 1. Request <GET_SERVICE_NOT_FOUND> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_NOT_FOUND> 404
	 *  
	 * */
	@Test
	public void service_not_found() {
		try {
			given().get(Url.Services.GET_SERVICE_NOT_FOUND).then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <GET_SERVICES> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that service response JSON contains: 
	 *    - data array that contains 10 object
	 *  
	 * */
	@Test
	public void get_services() {
		try {
			Response response = RestAssured.get(Url.Services.GET_SERVICES);
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");
			Assert.assertEquals(data.length(), 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*
	 * 1. Request <GET_PAGINATED_SERVICES> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that services response JSON contains: 
	 *    - data array that contains 3 object
	 *    - JSON object contains skip element equals 5
	 *  
	 * */
	@Test
	public void get_paginated_services() {
		try {
			Response response = RestAssured.get(Url.Services.GET_PAGINATED_SERVICES);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
			Assert.assertEquals(data.length(), 3);
			Assert.assertEquals(jsonObject.getInt("skip"), 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * 1. Request <GET_SERVICES> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that service response JSON contains: 
	 *    - data array isn't empty
	 *  
	 * */
	@Test
	public void search_services() {
		try {
			Response response = RestAssured.get(Url.Services.GET_SERVICES);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
			Assert.assertTrue(data.length() > 0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <servicesRequiredFields> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_BAD_REQUEST> 
	 * 
	 * 3. Assert that service response JSON contains: 
	 *    - error message for each required element, if there is required element hasn't message it is considered failed.
	 *  
	 * */
	@Test
	public void services_required_fields() {
		try {

			String[] requiredFields = { "name" };

			Response response = given().contentType("application/json").body("").when()
					.post(Url.Services.SERVICES_REQUIRED_FIELDS).thenReturn();

			JSONObject responseBody = new JSONObject(response.body().asString());
			JSONArray validationErrors = (JSONArray) responseBody.getJSONArray("errors");

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_BAD_REQUEST);
			Assert.assertEquals(validationErrors.length(), requiredFields.length);

			for (String field : requiredFields) {
				int index = -1;
				for (int i = 0; i < validationErrors.length(); i++) {

					index = validationErrors.get(i).toString().indexOf(field);

					if (validationErrors.get(i).toString().indexOf(field) > -1) {
						break;
					}
				}
				Assert.assertNotEquals(index, -1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <CREATE_SERVICE> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_CREATED> 
	 * 
	 * 3. Assert that service response JSON contains: 
	 *    - response JSON object created
	 *    - response JSON service object has id > 0
	 *  
	 * */
	@Test
	public void create_service() {
		try {
			JSONObject requestBody = new JSONObject();
			requestBody.put("name", "Test Service");

			Response response = RestAssured.given().contentType("application/json").body(requestBody.toString()).when()
					.post(Url.Services.CREATE_SERVICE);

			JSONObject jsonObject = new JSONObject(response.body().asString());

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_CREATED);
			Assert.assertNotNull(jsonObject.getInt("id") > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/*
	 * 
	 * Stores Read Only API Note - 'denies write attempts when in read only mode' !
	 * 
	 * Posting data to /services doesn't return HttpStatus.SC_METHOD_NOT_ALLOWED
	 * 
	 * There is no sample to set API in read only mode
	 * 
	 * */
}
