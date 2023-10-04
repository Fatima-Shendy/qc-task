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

public class Products {

	/*
	 * 1. Request <GET_PRODUCT_BY_ID> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK>
	 * 
	 * 3. Assert that product response JSON contains: 
	 *    - name = "Super Mario Maker - Nintendo Wii U"
	 *    
	 * */
	@Test
	public void get_product_by_id() {
		try {
			Response response = RestAssured.get(Url.Products.GET_PRODUCT_BY_ID);
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

			JsonPath jsonObj = new JsonPath(response.asString());
			Assert.assertEquals(jsonObj.getString("name"), "Super Mario Maker - Nintendo Wii U");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <GET_PRODUCT_BY_ID_AND_NAME> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK>
	 * 
	 * 3. Assert that service response JSON contains:
	 *    - name = "Super Mario Maker - Nintendo Wii U"
	 *    - id = "7425383"
	 *    - JSON object contains 2 element keys
	 *  
	 * */
	@Test
	public void get_product_by_id_and_name() {
		try {
			Response response = RestAssured.get(Url.Products.GET_PRODUCT_BY_ID_AND_NAME);
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

			JsonPath jsonPath = new JsonPath(response.asString());
			JSONObject jsonObject = new JSONObject(response.body().asString());

			Assert.assertEquals(jsonPath.getString("id"), "7425383");
			Assert.assertEquals(jsonPath.getString("name"), "Super Mario Maker - Nintendo Wii U");
			Assert.assertEquals(Utility.getNames(jsonObject).length, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <GET_PRODUCT_NOT_FOUND> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_NOT_FOUND> 404
	 *  
	 * */
	@Test
	public void product_not_found() {
		try {
			given().get(Url.Products.GET_PRODUCT_NOT_FOUND).then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <GET_PRODUCTS> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that product response JSON contains: 
	 *    - data array that contains 10 object
	 *  
	 * */
	@Test
	public void get_products() {
		try {
			Response response = RestAssured.get(Url.Products.GET_PRODUCTS);
			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");
			Assert.assertEquals(data.length(), 10);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <GET_PAGINATED_PRODUCTS> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that products response JSON contains: 
	 *    - data array that contains 15 object
	 *    - JSON object contains skip element equals 15
	 *  
	 * */
	@Test
	public void get_paginated_products() {
		try {
			Response response = RestAssured.get(Url.Products.GET_PAGINATED_PRODUCTS);

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
	 * 1. Request <SEARCH_PRODUCTS> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_OK> 
	 * 
	 * 3. Assert that service response JSON contains: 
	 *    - data array isn't empty
	 *  
	 * */
	@Test
	public void search_products() {
		try {
			Response response = RestAssured.get(Url.Products.SEARCH_PRODUCTS);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray data = (JSONArray) jsonObject.getJSONArray("data");

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
			Assert.assertTrue(data.length() > 0);
			Assert.assertTrue(jsonObject.getInt("total") > data.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Request <PRODUCTS_REQUIRED_FIELDS> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_BAD_REQUEST> 
	 * 
	 * 3. Assert that product response JSON contains: 
	 *    - error message for each required element, if there is required element hasn't message it is considered failed.
	 *  
	 * */
	@Test
	public void products_required_fields() {
		try {

			String[] requiredFields = { "name", "type", "upc", "description", "model" };

			Response response = given().contentType("application/json").body("").when()
					.post(Url.Products.PRODUCTS_REQUIRED_FIELDS).thenReturn();

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
	 * 1. Request <CREATE_PRODUCT> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_BAD_REQUEST> 
	 * 
	 * 3. Assert that product response JSON contains: 
	 *    - not empty errors array
	 *    - first element in array errors = 'price' should be multiple of 0.01
	 *  
	 * */
	@Test
	public void create_product_failed() {
		try {

			JSONObject requestBody = new JSONObject();
			requestBody.put("name", "Test Product With Categories");
			requestBody.put("description", "This is a test product with categories");
			requestBody.put("upc", "123");
			requestBody.put("type", "Electronics");
			requestBody.put("model", "Product354546");
			requestBody.put("price", 100.111);

			Response response = RestAssured.given().contentType("application/json").body(requestBody.toString()).when()
					.post(Url.Products.CREATE_PRODUCT);

			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray validationErrors = (JSONArray) jsonObject.getJSONArray("errors");

			Assert.assertEquals(response.statusCode(), HttpStatus.SC_BAD_REQUEST);
			Assert.assertEquals(validationErrors.length(), 1);
			Assert.assertEquals(validationErrors.get(0), "'price' should be multiple of 0.01");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 1. Request <CREATE_PRODUCT> url
	 * 
	 * 2. Assert that API respond with <HttpStatus.SC_CREATED> 
	 * 
	 * 3. Assert that product response JSON contains: 
	 *    - response JSON product object has id > 0
	 *  
	 * */
	@Test
	public void create_product_success() {
		try {

			JSONObject requestBody = new JSONObject();
			requestBody.put("name", "Test Product");
			requestBody.put("description", "This is a test product");
			requestBody.put("upc", "123");
			requestBody.put("type", "Electronics");
			requestBody.put("model", "Product354546");

			Response response = RestAssured.given().contentType("application/json").body(requestBody.toString()).when()
					.post(Url.Products.CREATE_PRODUCT);

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
	 * Posting data to /products doesn't return HttpStatus.SC_METHOD_NOT_ALLOWED
	 * 
	 * There is no sample to set API in read only mode
	 * 
	 * */
}
