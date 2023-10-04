package com.playground.api.util;

import java.util.Random;
import java.util.Set;

import org.json.JSONObject;

public class Utility {

	public static String[] getNames(JSONObject x) {
		Set<String> names = x.keySet();
		String[] r = new String[names.size()];
		try {

			int i = 0;
			for (String name : names) {
				r[i++] = name;
			}
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public static int randomId() {
		Random rand = new Random();
		int rand_int1 = rand.nextInt(1000);
		return rand_int1;
	}
}
