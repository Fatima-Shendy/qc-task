package com.playground.api.util;

public class Url {

	public static class Categories {
		public static final String GET_CATEGORY_BY_ID = "http://localhost:3030/categories/abcat0010000";
		public static final String GET_CATEGORY_BY_ID_AND_NAME = "http://localhost:3030/categories/abcat0010000?$select[]=name";
		public static final String GET_CATEGORY_NOT_FOUND = "http://localhost:3030/categories/123";
		public static final String GET_CATEGORIES = "http://localhost:3030/categories";
		public static final String CREATE_CATEGORY = "http://localhost:3030/categories";
		public static final String SEARCH_CATEGORIES = "http://localhost:3030/categories?name[$like]=*TV*";
		public static final String CATEGORIES_REQUIRED_FIELDS = "http://localhost:3030/categories";
		public static final String GET_PAGINATED_CATEGORIES = "http://localhost:3030/categories?$limit=15&$skip=15";
	}

	public static class Products {
		public static final String GET_PRODUCT_BY_ID = "http://localhost:3030/products/7425383";
		public static final String GET_PRODUCT_BY_ID_AND_NAME = "http://localhost:3030/products/7425383?$select[]=name";
		public static final String GET_PRODUCT_NOT_FOUND = "http://localhost:3030/products/123";
		public static final String GET_PRODUCTS = "http://localhost:3030/products";
		public static final String GET_PAGINATED_PRODUCTS = "http://localhost:3030/products?$limit=15&$skip=15";
		public static final String CREATE_PRODUCT = "http://localhost:3030/products";
		public static final String PRODUCTS_REQUIRED_FIELDS = "http://localhost:3030/products";
		public static final String SEARCH_PRODUCTS = "http://localhost:3030/products?name[$like]=*TV*";
	}

	public static class Services {
		public static final String GET_SERVICE_BY_ID = "http://localhost:3030/services/1";
		public static final String GET_SERVICE_BY_ID_AND_NAME = "http://localhost:3030/services/1?$select[]=name";
		public static final String GET_SERVICE_NOT_FOUND = "http://localhost:3030/services/123";
		public static final String GET_SERVICES = "http://localhost:3030/services";
		public static final String SEARCH_SERVICES = "http://localhost:3030/services?name[$like]=*Kitchen*";
		public static final String GET_PAGINATED_SERVICES = "http://localhost:3030/services?$limit=3&$skip=5";
		public static final String CREATE_SERVICE = "http://localhost:3030/services";
		public static final String SERVICES_REQUIRED_FIELDS = "http://localhost:3030/services";
	}

	public static class Stores {
		public static final String GET_STORE_BY_ID = "http://localhost:3030/stores/7";
		public static final String GET_STORE_BY_ID_AND_NAME = "http://localhost:3030/stores/7?$select[]=name&$select[]=hours";
		public static final String GET_STORE_NOT_FOUND = "http://localhost:3030/stores/99999";
		public static final String GET_STORES = "http://localhost:3030/stores";
		public static final String SEARCH_BY_NEAR_STORES = "http://localhost:3030/stores?near=90210";
		public static final String SEARCH_BY_NAME_PARTIALLY = "http://localhost:3030/stores?name[$like]=*Richfield*";
		public static final String GET_PAGINATED_STORES = "http://localhost:3030/stores?$limit=15&$skip=15";
		public static final String SEARCH_BY_PROPERTIES = "http://localhost:3030/stores?$select[]=name&$select[]=hours";
		public static final String STORES_REQUIRED_FIELDS = "http://localhost:3030/stores";
		public static final String CREATE_STORE = "http://localhost:3030/stores";
		
		public static final String SEARCH_BY_SERVICES = "http://localhost:3030/stores?service.name=Best Buy Mobile";
		public static final String SEARCH_BY_SERVICES_AND_ALTERNATIVE = "http://localhost:3030/stores?service[name]=Best Buy Mobile";
	}
}
