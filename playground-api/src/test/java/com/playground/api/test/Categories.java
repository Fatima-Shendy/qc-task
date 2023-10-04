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

public class Categories {

	/*
	 * 1. Request <GET_CATEGORY_BY_ID> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK>
	 * 
	 * 3. Assert that category response JSON contains: 
	 *    - name = "Gift Ideas"
	 *    - id = "abcat0010000"
	 *    
	 * */
	@Test
	public void get_category_by_id() {
		try {
			Response response = RestAssured.get(Url.Categories.GET_CATEGORY_BY_ID);
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

			JsonPath jsonObj = new JsonPath(response.asString());
			Assert.assertEquals(jsonObj.getString("id"), "abcat0010000");
			Assert.assertEquals(jsonObj.getString("name"), "Gift Ideas");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <GET_CATEGORY_BY_ID_AND_NAME> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK>
	 * 
	 * 3. Assert that category response JSON contains:
	 *    - name = "Gift Ideas"
	 *    - id = "abcat0010000"
	 *    - JSON object contains 2 element keys
	 *  
	 * */
	@Test
	public void get_category_by_id_and_name() {
		try {
			Response response = RestAssured.get(Url.Categories.GET_CATEGORY_BY_ID_AND_NAME);
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

			JsonPath jsonPath = new JsonPath(response.asString());
			JSONObject jsonObject = new JSONObject(response.body().asString());

			Assert.assertEquals(jsonPath.getString("id"), "abcat0010000");
			Assert.assertEquals(jsonPath.getString("name"), "Gift Ideas");
			Assert.assertEquals(Utility.getNames(jsonObject).length, 2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 1. Request <GET_CATEGORY_NOT_FOUND> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_NOT_FOUND> 404
	 *  
	 * */
	@Test
	public void category_not_found() {
		try {
			given().get(Url.Categories.GET_CATEGORY_NOT_FOUND).then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <GET_CATEGORIES> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that category response JSON contains: 
	 *    - data array that contains 10 object
	 *  
	 * */
	@Test
	public void get_categories() {
		try {
			Response response = RestAssured.get(Url.Categories.GET_CATEGORIES);
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");
			Assert.assertEquals(data.length(), 10);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <GET_PAGINATED_CATEGORIES> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that categories response JSON contains: 
	 *    - data array that contains 15 object
	 *    - JSON object contains skip element equals 15
	 *  
	 * */
	@Test
	public void get_paginated_categories() {
		try {
			Response response = RestAssured.get(Url.Categories.GET_PAGINATED_CATEGORIES);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
			Assert.assertEquals(data.length(), 15);
			Assert.assertEquals(jsonObject.getInt("skip"), 15);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <SEARCH_CATEGORIES> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that category response JSON contains: 
	 *    - data array isn't empty
	 *  
	 * */
	@Test
	public void search_categories() {
		try {
			Response response = RestAssured.get(Url.Categories.SEARCH_CATEGORIES);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
			Assert.assertTrue(data.length() > 0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <CATEGORIES_REQUIRED_FIELDS> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_BAD_REQUEST> 
	 * 
	 * 3. Assert that category response JSON contains: 
	 *    - error message for each required element, if there is required element hasn't message it is considered failed.
	 *  
	 * */
	@Test
	public void categories_required_fields() {
		try {
			String[] requiredFields = { "id", "name" };

			Response response = given().contentType("application/json").body("").when()
					.post(Url.Categories.CATEGORIES_REQUIRED_FIELDS).thenReturn();

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
	 * 1. Request <CREATE_CATEGORY> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_CREATED> 
	 * 
	 * 3. Assert that category response JSON contains: 
	 *    - response JSON object created
	 *    - response JSON category object has id > 0
	 *  
	 * */
	@Test
	public void create_category() {
		try {
			String id = "pcat" + String.valueOf(Utility.randomId());

			JSONObject requestBody = new JSONObject();
			requestBody.put("name", "Test Category");
			requestBody.put("id", id); // id must be unique for each request

			Response response = RestAssured.given().contentType("application/json").body(requestBody.toString()).when()
					.post(Url.Categories.CREATE_CATEGORY);

			JSONObject jsonObject = new JSONObject(response.body().asString());

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_CREATED);
			Assert.assertEquals(jsonObject.getString("id"), id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * Stores Read Only API Note - 'denies write attempts when in read only mode' !
	 * 
	 * Posting data to /categories doesn't return HttpStatus.SC_METHOD_NOT_ALLOWED
	 * 
	 * There is no sample to set API in read only mode
	 * 
	 * */
}
