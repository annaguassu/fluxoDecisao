package com.fluxodecisao.util;

import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private JsonUtils() {
	}

	public static String objToJson(Object obj) {

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return obj.toString();
		}
	}

	public static Map<String, Object> jsonToMap(Object json) {

		return new ObjectMapper().convertValue(json, new TypeReference<Map<String, Object>>() {
		});

	}
}
