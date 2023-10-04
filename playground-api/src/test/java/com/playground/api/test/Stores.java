package com.playground.api.test;

import static io.restassured.RestAssured.given;

import java.util.Arrays;

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

public class Stores {
	
	
	/*
	 * 1. Request <GET_STORE_BY_ID> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK>
	 * 
	 * 3. Assert that store response JSON contains: 
	 *    - name = "Roseville"
	 *    
	 * */
	@Test
	public void get_store_by_id() {
		try {
			Response response = RestAssured.get(Url.Stores.GET_STORE_BY_ID);
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

			JsonPath jsonObj = new JsonPath(response.asString());
			Assert.assertEquals(jsonObj.getString("name"), "Roseville");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <GET_STORE_BY_ID_AND_NAME> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK>
	 * 
	 * 3. Assert that store response JSON contains:
	 *    - name = "Roseville"
	 *    - id = "7"
	 *    - hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8"
	 *    - JSON object contains 3 element keys
	 *  
	 * */
	@Test
	public void get_storet_by_id_and_name() {
		try {
			Response response = RestAssured.get(Url.Stores.GET_STORE_BY_ID_AND_NAME);
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

			JsonPath jsonPath = new JsonPath(response.asString());
			JSONObject jsonObject = new JSONObject(response.body().asString());

			Assert.assertEquals(jsonPath.getString("id"), "7");
			Assert.assertEquals(jsonPath.getString("name"), "Roseville");
			Assert.assertEquals(jsonPath.getString("hours"),
					"Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8");
			Assert.assertEquals(Utility.getNames(jsonObject).length, 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <GET_STORE_NOT_FOUND> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_NOT_FOUND> 404
	 *  
	 * */
	@Test
	public void store_not_found() {
		try {
			given().get(Url.Stores.GET_STORE_NOT_FOUND).then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <GET_STORES> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that store response JSON contains: 
	 *    - data array that contains 10 object
	 *  
	 * */
	@Test
	public void get_stores() {
		try {
			Response response = RestAssured.get(Url.Stores.GET_STORES);
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");
			Assert.assertEquals(data.length(), 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * 1. Request <GET_PAGINATED_STORES> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that stores response JSON contains: 
	 *    - data array that contains 15 object
	 *    - JSON object contains skip element equals 15
	 *  
	 * */
	@Test
	public void get_paginated_stores() {
		try {
			Response response = RestAssured.get(Url.Stores.GET_PAGINATED_STORES);

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
	 * 1. Request <SEARCH_BY_NAME_PARTIALLY> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that stores response JSON contains: 
	 *    - data array that is not empty 
	 *  
	 * */
	@Test
	public void search_by_stores_name_partially() {
		try {
			Response response = RestAssured.get(Url.Stores.SEARCH_BY_NAME_PARTIALLY);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
			Assert.assertTrue(data.length() > 0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <SEARCH_BY_NEAR_STORES> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that stores response JSON contains: 
	 *    - first element in data array has city element = 'Los Angeles'
	 *    - JSON object contains total element less than 20
	 *  
	 * */
	@Test
	public void search_by_near_stores() {
		try {
			Response response = RestAssured.get(Url.Stores.SEARCH_BY_NEAR_STORES);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
			Assert.assertEquals(data.getJSONObject(0).getString("city"), "Los Angeles");
			Assert.assertTrue(jsonObject.getInt("total") < 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <SEARCH_BY_SERVICES> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that stores response JSON contains: 
	 *    - data array that is not empty
	 *  
	 * */
	@Test
	public void search_by_stores_service_1() {
		try {
			Response response = RestAssured.get("Url.Stores.SEARCH_BY_SERVICES");

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
			Assert.assertTrue(data.length() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <SEARCH_BY_SERVICES_AND_ALTERNATIVE> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that stores response JSON contains: 
	 *     - data array that is not empty
	 *  
	 * */
	@Test
	public void search_by_stores_service_2() {
		try {
			Response response = RestAssured.get(Url.Stores.SEARCH_BY_SERVICES_AND_ALTERNATIVE);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
			Assert.assertTrue(data.length() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <SEARCH_BY_PROPERTIES> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that stores response JSON contains: 
	 *    - data array contains more than one object consists of only 2 element keys (name, hours)
	 *    - there is no price element key in data array's objects
	 *  
	 * */
	@Test
	public void search_by_stores_properties() {
		try {
			Response response = RestAssured.get(Url.Stores.SEARCH_BY_PROPERTIES);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");
			
			String[] objectKeys = Utility.getNames(data.getJSONObject(0));
			
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
			Assert.assertEquals(objectKeys.length, 2);
			Assert.assertTrue(Arrays.asList(objectKeys).indexOf("name") >= 0);
			Assert.assertTrue(Arrays.asList(objectKeys).indexOf("hours") >= 0);
			Assert.assertFalse(Arrays.asList(objectKeys).indexOf("price") >= 0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <STORES_REQUIRED_FIELDS> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_BAD_REQUEST> 
	 * 
	 * 3. Assert that store response JSON contains: 
	 *    - error message for each required element, if there is required element hasn't message it is considered failed.
	 *  
	 * */
	@Test
	public void stores_required_fields() {
		try {
			String[] requiredFields = { "name", "address", "city", "state", "zip" };

			Response response = given().contentType("application/json").body("").when()
					.post(Url.Stores.STORES_REQUIRED_FIELDS).thenReturn();

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
	 * 3. Assert that store response JSON contains: 
	 *    - response JSON object created
	 *    - response JSON service store has id > 0
	 *  
	 * */
	@Test
	public void create_store() {
		try {

			JSONObject requestBody = new JSONObject();
			requestBody.put("name", "Test Store");
			requestBody.put("address", "123 Fake Street");
			requestBody.put("city", "Richfield");
			requestBody.put("state", "MN");
			requestBody.put("zip", "55437");

			Response response = RestAssured.given().contentType("application/json").body(requestBody.toString()).when()
					.post(Url.Stores.CREATE_STORE);

			JSONObject jsonObject = new JSONObject(response.body().asString());

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_CREATED);
			Assert.assertNotNull(jsonObject.getInt("id") > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Stores Read Only API Note - 'denies write attempts when in read only mode' !
	 * 
	 * Posting data to /stores doesn't return HttpStatus.SC_METHOD_NOT_ALLOWED
	 * 
	 * There is no sample to set API in read only mode
	 * 
	 * */
}
